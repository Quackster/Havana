package org.alexdev.havana.dao.mysql;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.util.config.GameConfiguration;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class GroupDao {
    public record GroupAdmin(
            int id,
            String name,
            String description,
            int ownerId,
            String ownerName,
            int roomId,
            String badge,
            boolean recommended,
            String background,
            int views,
            int topics,
            int groupType,
            int forumType,
            int forumPermission,
            String alias,
            String createdAt,
            int memberCount,
            int pendingCount,
            int threadCount,
            boolean staffPick
    ) {}

    public record GroupMemberAdmin(int userId, String username, int memberRank, boolean pending, String createdAt) {}
    public record GroupThreadAdmin(int id, String topicTitle, int posterId, String posterName, boolean open, boolean stickied, int views, String createdAt, String modifiedAt, int replyCount) {}
    public record GroupReplyAdmin(int id, int threadId, String topicTitle, int posterId, String posterName, String message, boolean edited, boolean deleted, String createdAt, String modifiedAt) {}

    public static Group getGroup(int groupId) {
        Group group = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM groups_details WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, groupId);
            resultSet =  preparedStatement.executeQuery();

            if (resultSet.next()) {
                group = fill(resultSet);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return group;
    }

    public static List<GroupAdmin> getAdminGroups(String query) {
        List<GroupAdmin> groups = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String normalisedQuery = query == null ? "" : query.trim().toLowerCase();
            int groupId = -1;

            try {
                groupId = Integer.parseInt(normalisedQuery);
            } catch (NumberFormatException ignored) {

            }

            sqlConnection = Storage.getStorage().getConnection();

            String select = "SELECT groups_details.*, users.username AS owner_name, " +
                    "(SELECT COUNT(*) FROM groups_memberships WHERE groups_memberships.group_id = groups_details.id AND is_pending = 0) AS member_count, " +
                    "(SELECT COUNT(*) FROM groups_memberships WHERE groups_memberships.group_id = groups_details.id AND is_pending = 1) AS pending_count, " +
                    "(SELECT COUNT(*) FROM cms_forum_threads WHERE cms_forum_threads.group_id = groups_details.id) AS thread_count, " +
                    "EXISTS(SELECT 1 FROM cms_recommended WHERE cms_recommended.recommended_id = groups_details.id AND cms_recommended.type = 'GROUP' AND cms_recommended.is_staff_pick = 1) AS staff_pick " +
                    "FROM groups_details LEFT JOIN users ON groups_details.owner_id = users.id ";

            if (normalisedQuery.isBlank()) {
                preparedStatement = Storage.getStorage().prepare(select + "ORDER BY groups_details.id DESC LIMIT 100", sqlConnection);
            } else {
                preparedStatement = Storage.getStorage().prepare(select + "WHERE groups_details.id = ? OR LOWER(groups_details.name) LIKE ? OR LOWER(users.username) LIKE ? OR LOWER(groups_details.alias) LIKE ? ORDER BY groups_details.id DESC LIMIT 100", sqlConnection);
                preparedStatement.setInt(1, groupId);
                preparedStatement.setString(2, "%" + normalisedQuery + "%");
                preparedStatement.setString(3, "%" + normalisedQuery + "%");
                preparedStatement.setString(4, "%" + normalisedQuery + "%");
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                groups.add(readAdminGroup(resultSet));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return groups;
    }

    public static GroupAdmin getAdminGroup(int groupId) {
        GroupAdmin group = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT groups_details.*, users.username AS owner_name, " +
                    "(SELECT COUNT(*) FROM groups_memberships WHERE groups_memberships.group_id = groups_details.id AND is_pending = 0) AS member_count, " +
                    "(SELECT COUNT(*) FROM groups_memberships WHERE groups_memberships.group_id = groups_details.id AND is_pending = 1) AS pending_count, " +
                    "(SELECT COUNT(*) FROM cms_forum_threads WHERE cms_forum_threads.group_id = groups_details.id) AS thread_count, " +
                    "EXISTS(SELECT 1 FROM cms_recommended WHERE cms_recommended.recommended_id = groups_details.id AND cms_recommended.type = 'GROUP' AND cms_recommended.is_staff_pick = 1) AS staff_pick " +
                    "FROM groups_details LEFT JOIN users ON groups_details.owner_id = users.id " +
                    "WHERE groups_details.id = ? LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, groupId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                group = readAdminGroup(resultSet);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return group;
    }

    private static GroupAdmin readAdminGroup(ResultSet resultSet) throws SQLException {
        String ownerName = resultSet.getString("owner_name");

        return new GroupAdmin(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("owner_id"),
                ownerName == null ? "" : ownerName,
                resultSet.getInt("room_id"),
                resultSet.getString("badge"),
                resultSet.getBoolean("recommended"),
                resultSet.getString("background"),
                resultSet.getInt("views"),
                resultSet.getInt("topics"),
                resultSet.getInt("group_type"),
                resultSet.getInt("forum_type"),
                resultSet.getInt("forum_premission"),
                resultSet.getString("alias"),
                resultSet.getString("created_at"),
                resultSet.getInt("member_count"),
                resultSet.getInt("pending_count"),
                resultSet.getInt("thread_count"),
                resultSet.getBoolean("staff_pick")
        );
    }

    public static void saveAdminGroup(GroupAdmin group) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE groups_details SET name = ?, description = ?, owner_id = ?, room_id = ?, badge = ?, recommended = ?, background = ?, views = ?, topics = ?, group_type = ?, forum_type = ?, forum_premission = ?, alias = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, group.name());
            preparedStatement.setString(2, group.description());
            preparedStatement.setInt(3, group.ownerId());
            preparedStatement.setInt(4, group.roomId());
            preparedStatement.setString(5, group.badge());
            preparedStatement.setBoolean(6, group.recommended());
            preparedStatement.setString(7, group.background());
            preparedStatement.setInt(8, group.views());
            preparedStatement.setInt(9, group.topics());
            preparedStatement.setInt(10, group.groupType());
            preparedStatement.setInt(11, group.forumType());
            preparedStatement.setInt(12, group.forumPermission());

            if (group.alias() == null || group.alias().isBlank()) {
                preparedStatement.setNull(13, Types.VARCHAR);
            } else {
                preparedStatement.setString(13, group.alias());
            }

            preparedStatement.setInt(14, group.id());
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static boolean hasAdminAliasConflict(String alias, int groupId) {
        if (alias == null || alias.isBlank()) {
            return false;
        }

        boolean conflict = false;
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT id FROM groups_details WHERE alias = ? AND id != ? LIMIT 1", sqlConnection);
            preparedStatement.setString(1, alias);
            preparedStatement.setInt(2, groupId);
            resultSet = preparedStatement.executeQuery();
            conflict = resultSet.next();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return conflict;
    }

    public static List<GroupMemberAdmin> getAdminMembers(int groupId) {
        List<GroupMemberAdmin> members = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT groups_memberships.*, users.username FROM groups_memberships LEFT JOIN users ON groups_memberships.user_id = users.id WHERE groups_memberships.group_id = ? ORDER BY groups_memberships.is_pending DESC, groups_memberships.member_rank DESC, users.username ASC LIMIT 100", sqlConnection);
            preparedStatement.setInt(1, groupId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                members.add(new GroupMemberAdmin(resultSet.getInt("user_id"), username == null ? "" : username, resultSet.getInt("member_rank"), resultSet.getBoolean("is_pending"), resultSet.getString("created_at")));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return members;
    }

    public static void saveAdminMember(int groupId, int userId, int memberRank, boolean pending) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE groups_memberships SET member_rank = ?, is_pending = ? WHERE group_id = ? AND user_id = ?", sqlConnection);
            preparedStatement.setString(1, String.valueOf(memberRank));
            preparedStatement.setBoolean(2, pending);
            preparedStatement.setInt(3, groupId);
            preparedStatement.setInt(4, userId);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static List<GroupThreadAdmin> getAdminThreads(int groupId) {
        List<GroupThreadAdmin> threads = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT cms_forum_threads.*, users.username AS poster_name, (SELECT COUNT(*) FROM cms_forum_replies WHERE cms_forum_replies.thread_id = cms_forum_threads.id) AS reply_count FROM cms_forum_threads LEFT JOIN users ON cms_forum_threads.poster_id = users.id WHERE cms_forum_threads.group_id = ? ORDER BY cms_forum_threads.modified_at DESC LIMIT 50", sqlConnection);
            preparedStatement.setInt(1, groupId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String posterName = resultSet.getString("poster_name");
                threads.add(new GroupThreadAdmin(
                        resultSet.getInt("id"),
                        resultSet.getString("topic_title"),
                        resultSet.getInt("poster_id"),
                        posterName == null ? "" : posterName,
                        resultSet.getBoolean("is_open"),
                        resultSet.getBoolean("is_stickied"),
                        resultSet.getInt("views"),
                        resultSet.getString("created_at"),
                        resultSet.getString("modified_at"),
                        resultSet.getInt("reply_count")
                ));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return threads;
    }

    public static List<GroupReplyAdmin> getAdminReplies(int groupId) {
        List<GroupReplyAdmin> replies = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT cms_forum_replies.*, cms_forum_threads.topic_title, users.username AS poster_name FROM cms_forum_replies INNER JOIN cms_forum_threads ON cms_forum_replies.thread_id = cms_forum_threads.id LEFT JOIN users ON cms_forum_replies.poster_id = users.id WHERE cms_forum_threads.group_id = ? ORDER BY cms_forum_replies.created_at DESC LIMIT 50", sqlConnection);
            preparedStatement.setInt(1, groupId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String posterName = resultSet.getString("poster_name");
                replies.add(new GroupReplyAdmin(
                        resultSet.getInt("id"),
                        resultSet.getInt("thread_id"),
                        resultSet.getString("topic_title"),
                        resultSet.getInt("poster_id"),
                        posterName == null ? "" : posterName,
                        resultSet.getString("message"),
                        resultSet.getBoolean("is_edited"),
                        resultSet.getBoolean("is_deleted"),
                        resultSet.getString("created_at"),
                        resultSet.getString("modified_at")
                ));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return replies;
    }

    public static void saveAdminThread(int threadId, String topicTitle, boolean open, boolean stickied) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE cms_forum_threads SET topic_title = ?, is_open = ?, is_stickied = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, topicTitle);
            preparedStatement.setBoolean(2, open);
            preparedStatement.setBoolean(3, stickied);
            preparedStatement.setInt(4, threadId);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void deleteAdminThread(int threadId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM cms_forum_replies WHERE thread_id = ?", sqlConnection);
            preparedStatement.setInt(1, threadId);
            preparedStatement.execute();

            preparedStatement = Storage.getStorage().prepare("DELETE FROM cms_forum_threads WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, threadId);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void setAdminReplyDeleted(int replyId, boolean deleted) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE cms_forum_replies SET is_deleted = ? WHERE id = ?", sqlConnection);
            preparedStatement.setBoolean(1, deleted);
            preparedStatement.setInt(2, replyId);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void deleteAdminReply(int replyId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM cms_forums_read_replies WHERE reply_id = ?", sqlConnection);
            preparedStatement.setInt(1, replyId);
            preparedStatement.execute();

            preparedStatement = Storage.getStorage().prepare("DELETE FROM cms_forum_replies WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, replyId);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void deleteAdminGroup(int groupId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE cms_forums_read_replies FROM cms_forums_read_replies INNER JOIN cms_forum_replies ON cms_forums_read_replies.reply_id = cms_forum_replies.id INNER JOIN cms_forum_threads ON cms_forum_replies.thread_id = cms_forum_threads.id WHERE cms_forum_threads.group_id = ?", sqlConnection);
            preparedStatement.setInt(1, groupId);
            preparedStatement.execute();

            preparedStatement = Storage.getStorage().prepare("DELETE cms_forum_replies FROM cms_forum_replies INNER JOIN cms_forum_threads ON cms_forum_replies.thread_id = cms_forum_threads.id WHERE cms_forum_threads.group_id = ?", sqlConnection);
            preparedStatement.setInt(1, groupId);
            preparedStatement.execute();

            preparedStatement = Storage.getStorage().prepare("DELETE FROM cms_forum_threads WHERE group_id = ?", sqlConnection);
            preparedStatement.setInt(1, groupId);
            preparedStatement.execute();

            preparedStatement = Storage.getStorage().prepare("DELETE FROM cms_guestbook_entries WHERE group_id = ?", sqlConnection);
            preparedStatement.setInt(1, groupId);
            preparedStatement.execute();

            preparedStatement = Storage.getStorage().prepare("DELETE FROM groups_memberships WHERE group_id = ?", sqlConnection);
            preparedStatement.setInt(1, groupId);
            preparedStatement.execute();

            preparedStatement = Storage.getStorage().prepare("DELETE FROM groups_edit_sessions WHERE group_id = ?", sqlConnection);
            preparedStatement.setInt(1, groupId);
            preparedStatement.execute();

            preparedStatement = Storage.getStorage().prepare("UPDATE users SET favourite_group = 0 WHERE favourite_group = ?", sqlConnection);
            preparedStatement.setInt(1, groupId);
            preparedStatement.execute();

            preparedStatement = Storage.getStorage().prepare("DELETE FROM groups_details WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, groupId);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static Group getGroupByAlias(String groupAlias) {
        Group group = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM groups_details WHERE alias = ?", sqlConnection);
            preparedStatement.setString(1, groupAlias);
            resultSet =  preparedStatement.executeQuery();

            if (resultSet.next()) {
                group = fill(resultSet);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return group;
    }

    public static List<Group> getGroups(int userId) {
        List<Group> groupList = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM groups_details WHERE owner_id = ?", sqlConnection);
            preparedStatement.setInt(1, userId);
            resultSet =  preparedStatement.executeQuery();

            while (resultSet.next()) {
                groupList.add(fill(resultSet));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return groupList;
    }

    public static List<Group> getJoinedGroups(int userId) {
        List<Group> groupList = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT " +
                    "groups_details.* " +
                    "FROM groups_memberships " +
                    "RIGHT JOIN " +
                    "groups_details ON groups_memberships.group_id = groups_details.id " +
                    "WHERE owner_id = ? " +
                    "OR (groups_memberships.user_id = ? AND groups_memberships.is_pending = 0)", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int groupId = resultSet.getInt("id");

                if (groupList.stream().noneMatch(group -> group.getId() == groupId)) {
                    groupList.add(fill(resultSet));
                }
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return groupList.stream()
                .sorted(Comparator.comparingInt((Group group) -> group.getMemberCount(false)).reversed())
                .collect(Collectors.toList());
    }

    public static int addGroup(String name, String description, int ownerId) {
        int groupId = 0;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("INSERT INTO groups_details (name, description, owner_id) VALUES (?, ?, ?)", sqlConnection);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, ownerId);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                groupId = resultSet.getInt(1);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(preparedStatement);
        }

        return groupId;
    }

    public static int saveGroup(Group group) {
        int groupId = 0;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE groups_details SET name = ?, description = ?, room_id = ?, badge = ?, recommended = ?, group_type = ?, forum_type = ?, forum_premission = ?, alias = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, group.getName());
            preparedStatement.setString(2, group.getDescription());
            preparedStatement.setInt(3, group.getRoomId());
            preparedStatement.setString(4, group.getBadge());
            preparedStatement.setInt(5, group.isRecommended() ? 1 : 0);
            preparedStatement.setInt(6, group.getGroupType());
            preparedStatement.setInt(7, group.getForumType().getId());
            preparedStatement.setInt(8, group.getForumPermission().getId());

            if (group.getAlias() == null || group.getAlias().isBlank()) {
                preparedStatement.setNull(9, Types.VARCHAR);
            }
            else {
                preparedStatement.setString(9, group.getAlias());
            }

            preparedStatement.setInt(10, group.getId());
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                groupId = resultSet.getInt(1);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(preparedStatement);
        }

        return groupId;
    }

    public static void saveBackground(Group group) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE groups_details SET background = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, group.getBackground());
            preparedStatement.setInt(2, group.getId());
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(preparedStatement);
        }
    }

    public static List<Group> querySearch(String query) {
        List<Group> groups = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM groups_details WHERE name LIKE ? LIMIT 30", sqlConnection);
            preparedStatement.setString(1, "%" + query + "%");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                groups.add(fill(resultSet));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return groups;
    }

    public static void saveBadge(Group group) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE groups_details SET badge = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, group.getBadge());
            preparedStatement.setInt(2, group.getId());
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(preparedStatement);
        }
    }

    public static String getGroupBadge(int groupId) {
        String group = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT badge FROM groups_details WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, groupId);
            resultSet =  preparedStatement.executeQuery();

            if (resultSet.next()) {
                group = resultSet.getString("badge");
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return group;
    }

    public static void delete(int groupId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM groups_details WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, groupId);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static boolean hasGroupByAlias(String url) {
        boolean group = false;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM groups_details WHERE alias = ?", sqlConnection);
            preparedStatement.setString(1, url);
            resultSet =  preparedStatement.executeQuery();

            if (resultSet.next()) {
                group = true;
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return group;
    }

    public static void deleteHomeRoom(int roomId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE groups_details SET room_id = 0 WHERE room_id = ?", sqlConnection);
            preparedStatement.setInt(1, roomId);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static int getGroupOwner(int groupId) {
        int ownerId = 0;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT owner_id FROM groups_details WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, groupId);
            resultSet =  preparedStatement.executeQuery();

            if (resultSet.next()) {
                ownerId = resultSet.getInt("owner_id");
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return ownerId;
    }

    public static String getGroupName(int groupId) {
        String groupName = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT name FROM groups_details WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, groupId);
            resultSet =  preparedStatement.executeQuery();

            if (resultSet.next()) {
                groupName = resultSet.getString("name");
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return groupName;
    }

    public static Group fill(ResultSet resultSet) throws SQLException {
        return new Group(resultSet.getInt("id"),  resultSet.getString("name"), resultSet.getString("description"), resultSet.getInt("owner_id"),
                resultSet.getInt("room_id"), resultSet.getString("badge"), resultSet.getBoolean("recommended"), resultSet.getString("background"), resultSet.getInt("views"),
                resultSet.getInt("topics"), resultSet.getInt("group_type"), resultSet.getInt("forum_type"), resultSet.getInt("forum_premission"),
                resultSet.getString("alias"), resultSet.getTime("created_at").getTime() / 1000L);
    }
}
