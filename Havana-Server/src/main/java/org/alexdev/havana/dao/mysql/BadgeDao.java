package org.alexdev.havana.dao.mysql;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.game.badges.Badge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BadgeDao {
    public record BadgeAssignmentAdmin(int userId, String username, String badge, boolean equipped, int slotId) {}
    public record BadgeCatalogueAdmin(String badge, int assignmentCount, boolean rankBadge) {}
    public record BadgeAuditAdmin(String action, int staffId, int targetId, String message, String extraNotes, String createdAt) {}

    public static Map<Integer, List<String>> getRoomBadges()  {
        Map<Integer, List<String>> badges = new ConcurrentHashMap<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms_entry_badges", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int roomId = resultSet.getInt("room_id");
                String badgeCode = resultSet.getString("badge");

                if (!badges.containsKey(roomId)) {
                    badges.put(roomId, new ArrayList<>());
                }

                badges.get(roomId).add(badgeCode);
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return badges;
    }

    public static List<BadgeAssignmentAdmin> getAdminAssignments(String query) {
        List<BadgeAssignmentAdmin> assignments = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String normalisedQuery = query == null ? "" : query.trim().toLowerCase();
            int userId = -1;

            try {
                userId = Integer.parseInt(normalisedQuery);
            } catch (NumberFormatException ignored) {

            }

            sqlConnection = Storage.getStorage().getConnection();

            if (normalisedQuery.isBlank()) {
                preparedStatement = Storage.getStorage().prepare("SELECT users_badges.*, users.username FROM users_badges LEFT JOIN users ON users_badges.user_id = users.id ORDER BY users_badges.user_id DESC, users_badges.badge ASC LIMIT 100", sqlConnection);
            } else {
                preparedStatement = Storage.getStorage().prepare("SELECT users_badges.*, users.username FROM users_badges LEFT JOIN users ON users_badges.user_id = users.id WHERE users_badges.user_id = ? OR LOWER(users.username) LIKE ? OR LOWER(users_badges.badge) LIKE ? ORDER BY users_badges.user_id DESC, users_badges.badge ASC LIMIT 100", sqlConnection);
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, "%" + normalisedQuery + "%");
                preparedStatement.setString(3, "%" + normalisedQuery + "%");
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                assignments.add(new BadgeAssignmentAdmin(
                        resultSet.getInt("user_id"),
                        username == null ? "" : username,
                        resultSet.getString("badge"),
                        resultSet.getBoolean("equipped"),
                        resultSet.getInt("slot_id")
                ));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return assignments;
    }

    public static List<BadgeCatalogueAdmin> getAdminCatalogue() {
        List<BadgeCatalogueAdmin> badges = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT badge, SUM(assignment_count) AS assignment_count, MAX(rank_badge) AS rank_badge FROM (" +
                    "SELECT badge, COUNT(*) AS assignment_count, 0 AS rank_badge FROM users_badges GROUP BY badge " +
                    "UNION ALL SELECT badge, 0 AS assignment_count, 1 AS rank_badge FROM rank_badges" +
                    ") badge_catalogue GROUP BY badge ORDER BY badge ASC LIMIT 500", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                badges.add(new BadgeCatalogueAdmin(resultSet.getString("badge"), resultSet.getInt("assignment_count"), resultSet.getBoolean("rank_badge")));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return badges;
    }

    public static boolean hasAdminBadge(int userId, String badgeCode) {
        boolean exists = false;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT badge FROM users_badges WHERE user_id = ? AND badge = ? LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, badgeCode);
            resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return exists;
    }

    public static List<BadgeAuditAdmin> getAdminBadgeAudit() {
        List<BadgeAuditAdmin> logs = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM housekeeping_audit_log WHERE action IN ('badge_grant', 'badge_remove', 'badge_update') ORDER BY created_at DESC LIMIT 100", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                logs.add(new BadgeAuditAdmin(
                        resultSet.getString("action"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("target_id"),
                        resultSet.getString("message"),
                        resultSet.getString("extra_notes"),
                        resultSet.getString("created_at")
                ));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return logs;
    }

    public static void addAdminBadgeAudit(String action, int staffId, int targetId, String badgeCode, String extraNotes) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("INSERT INTO housekeeping_audit_log (action, user_id, target_id, message, extra_notes) VALUES (?, ?, ?, ?, ?)", sqlConnection);
            preparedStatement.setString(1, action);
            preparedStatement.setInt(2, staffId);
            preparedStatement.setInt(3, targetId);
            preparedStatement.setString(4, badgeCode);
            preparedStatement.setString(5, extraNotes);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }


    public static void deleteRoomBadge(String roomId, String badgeCode) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM rooms_entry_badges WHERE room_id = ? AND badge = ?", sqlConnection);
            preparedStatement.setString(1, roomId);
            preparedStatement.setString(2, badgeCode);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void createEntryBadge(int roomId, String badgeCode) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("INSERT INTO rooms_entry_badges (room_id, badge) VALUES (?, ?)", sqlConnection);
            preparedStatement.setInt(1, roomId);
            preparedStatement.setString(2, badgeCode);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void updateBadges(Map<Integer, List<String>> badges) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();

            preparedStatement = Storage.getStorage().prepare("DELETE FROM rooms_entry_badges", sqlConnection);
            preparedStatement.execute();

            preparedStatement = Storage.getStorage().prepare("INSERT INTO rooms_entry_badges (room_id, badge) VALUES (?, ?)", sqlConnection);
            sqlConnection.setAutoCommit(false);

            for (var badgeData : badges.entrySet()) {
                for (var badge : badgeData.getValue()) {
                    preparedStatement.setInt(1, badgeData.getKey());
                    preparedStatement.setString(2, badge);
                    preparedStatement.addBatch();
                }
            }

            preparedStatement.executeBatch();
            sqlConnection.setAutoCommit(true);

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static List<Badge> getBadges(int userId) {
        List<Badge> ranks = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM users_badges WHERE user_id = ?", sqlConnection);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ranks.add(new Badge(resultSet.getString("badge"), resultSet.getBoolean("equipped"), resultSet.getInt("slot_id")));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return ranks;
    }

    public static void newBadge(int userId, String badgeCode) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("INSERT INTO users_badges (user_id, badge) VALUES (?, ?)", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, badgeCode);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void removeBadge(int userId, String badgeCode) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM users_badges WHERE user_id = ? AND badge = ?", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, badgeCode);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void removeBadge(String badgeCode) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM users_badges WHERE badge = ?", sqlConnection);
            preparedStatement.setString(1, badgeCode);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void saveBadgeChanges(int userId, String badgeCode, boolean isEquipped, int slotId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE users_badges SET equipped = ?, slot_id = ? WHERE user_id = ? AND badge = ?", sqlConnection);
            preparedStatement.setBoolean(1, isEquipped);
            preparedStatement.setInt(2, slotId);
            preparedStatement.setInt(3, userId);
            preparedStatement.setString(4, badgeCode);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }


    public static void saveBadgeChanges(int userId, Set<Badge> badgesToSave) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE users_badges SET equipped = ?, slot_id = ? WHERE user_id = ? AND badge = ?", sqlConnection);
            sqlConnection.setAutoCommit(false);

            for (Badge badge : badgesToSave) {
                preparedStatement.setBoolean(1, badge.isEquipped());
                preparedStatement.setInt(2, badge.getSlotId());
                preparedStatement.setInt(3, userId);
                preparedStatement.setString(4, badge.getBadgeCode());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            sqlConnection.setAutoCommit(true);

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    /**
     * Get all rank badges
     *
     * @return list of badges
     */
    public static List<String> getRankBadges() {
        List<String> badges = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet row = null;

        try {
            conn = Storage.getStorage().getConnection();
            stmt = Storage.getStorage().prepare("SELECT badge FROM rank_badges", conn);
            row = stmt.executeQuery();

            while (row.next()) {
                badges.add(row.getString("badge"));
            }
        } catch (Exception err) {
            Storage.logError(err);
        } finally {
            Storage.closeSilently(row);
            Storage.closeSilently(stmt);
            Storage.closeSilently(conn);
        }

        return badges;
    }
}
