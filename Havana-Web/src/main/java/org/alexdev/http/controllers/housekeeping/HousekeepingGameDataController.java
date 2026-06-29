package org.alexdev.http.controllers.housekeeping;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.havana.dao.mysql.BadgeDao;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.dao.mysql.GroupMemberDao;
import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.dao.mysql.NavigatorDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.dao.mysql.RoomModelDao;
import org.alexdev.havana.dao.mysql.VoucherDao;
import org.alexdev.havana.dao.mysql.WordfilterDao;
import org.alexdev.havana.game.ecotron.EcotronManager;
import org.alexdev.havana.game.catalogue.CatalogueManager;
import org.alexdev.havana.game.catalogue.collectables.CollectablesManager;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.navigator.NavigatorManager;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.game.room.models.RoomModelManager;
import org.alexdev.havana.game.room.models.RoomModelTriggerType;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.http.Routes;
import org.alexdev.http.dao.RecommendedDao;
import org.alexdev.http.game.housekeeping.HousekeepingManager;
import org.alexdev.http.server.Watchdog;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class HousekeepingGameDataController {
    private static final String ITEM_DEFINITIONS_PERMISSION = "item_definitions/manage";
    private static final String VOUCHERS_PERMISSION = "vouchers/manage";
    private static final String WORDFILTER_PERMISSION = "wordfilter/manage";
    private static final String RECYCLER_PERMISSION = "recycler/manage";
    private static final String ROOM_CATEGORIES_PERMISSION = "room_categories/manage";
    private static final String ROOM_MODELS_PERMISSION = "room_models/manage";
    private static final String ROOMS_PERMISSION = "rooms/manage";
    private static final String GROUPS_PERMISSION = "groups/manage";
    private static final String BADGES_PERMISSION = "badges";

    public static void rooms(WebConnection client) {
        if (!ensurePermission(client, ROOMS_PERMISSION)) {
            return;
        }

        String query = client.get().contains("query") ? client.get().getString("query").trim() : "";
        Template tpl = template(client, "housekeeping/rooms", "Rooms");
        tpl.set("query", query);
        tpl.set("rooms", RoomDao.getAdminRooms(query));
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void editRoom(WebConnection client) {
        if (!ensurePermission(client, ROOMS_PERMISSION)) {
            return;
        }

        if (!client.get().contains("id")) {
            setAlert(client, "danger", "Room ID is required");
            client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/rooms");
            return;
        }

        int id = client.get().getInt("id");
        RoomDao.RoomAdmin room = RoomDao.getAdminRoom(id);

        if (room == null) {
            setAlert(client, "danger", "Room does not exist");
            client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/rooms");
            return;
        }

        if (client.post().queries().size() > 0) {
            String name = client.post().getString("name").trim();
            String model = client.post().getString("model").trim();
            int ownerId = client.post().getInt("owner_id");
            int categoryId = client.post().getInt("category");
            int accessType = client.post().getInt("accesstype");
            int visitorsMax = client.post().getInt("visitors_max");

            if (name.isBlank()) {
                setAlert(client, "danger", "Room name cannot be blank");
            } else if (model.isBlank() || !isValidRoomModelId(model)) {
                setAlert(client, "danger", "Room model must match an existing model ID");
            } else if (ownerId > 0 && PlayerDao.getDetails(ownerId) == null) {
                setAlert(client, "danger", "Owner ID must be 0 for public rooms or an existing user ID");
            } else if (NavigatorDao.getAdminCategory(categoryId) == null) {
                setAlert(client, "danger", "Room category must exist");
            } else if (accessType < 0 || accessType > 2) {
                setAlert(client, "danger", "Room access type must be open, closed, or password");
            } else if (visitorsMax < 1) {
                setAlert(client, "danger", "Visitor limit must be at least 1");
            } else {
                RoomDao.saveAdminRoom(new RoomDao.RoomAdmin(
                        id,
                        ownerId,
                        room.ownerName(),
                        categoryId,
                        name,
                        client.post().getString("description").trim(),
                        model,
                        client.post().getString("ccts").trim(),
                        client.post().getInt("wallpaper"),
                        client.post().getInt("floor"),
                        client.post().getString("landscape").trim(),
                        client.post().contains("showname"),
                        client.post().contains("superusers"),
                        accessType,
                        client.post().getString("password"),
                        room.visitorsNow(),
                        visitorsMax,
                        client.post().getInt("rating"),
                        client.post().getString("icon_data").trim(),
                        client.post().getInt("group_id"),
                        client.post().contains("is_hidden"),
                        room.createdAt(),
                        room.updatedAt(),
                        room.staffPick()
                ));

                refreshRoom(id);
                setAlert(client, "success", "Room saved successfully");
                client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/rooms/edit?id=" + id);
                return;
            }
        }

        Template tpl = template(client, "housekeeping/room_edit", "Edit Room");
        tpl.set("room", room);
        tpl.set("categories", NavigatorDao.getAdminCategories());
        tpl.set("models", RoomModelDao.getAdminModels());
        tpl.set("rights", RoomDao.getAdminRoomRights(id));
        tpl.set("bans", RoomDao.getAdminRoomBans(id));
        tpl.set("events", RoomDao.getAdminRoomEvents(id));
        tpl.set("entryBadges", RoomDao.getAdminRoomEntryBadges(id));
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void hideRoom(WebConnection client) {
        if (!ensurePermission(client, ROOMS_PERMISSION)) {
            return;
        }

        if (client.get().contains("id")) {
            int roomId = client.get().getInt("id");
            boolean hidden = !client.get().contains("hidden") || Boolean.parseBoolean(client.get().getString("hidden"));
            RoomDao.setAdminRoomHidden(roomId, hidden);
            refreshRoom(roomId);
            setAlert(client, "success", hidden ? "Room hidden successfully" : "Room unhidden successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/rooms");
    }

    public static void setRoomStaffPick(WebConnection client) {
        if (!ensurePermission(client, ROOMS_PERMISSION)) {
            return;
        }

        if (client.get().contains("id")) {
            int roomId = client.get().getInt("id");

            if (RoomDao.getAdminRoom(roomId) == null) {
                setAlert(client, "danger", "Room does not exist");
            } else {
                boolean enabled = !client.get().contains("enabled") || Boolean.parseBoolean(client.get().getString("enabled"));
                RecommendedDao.setRecommended(roomId, "ROOM", true, enabled);
                Watchdog.STAFF_PICK_ROOMS = RecommendedDao.getRecommendedRooms(true);
                setAlert(client, "success", enabled ? "Room added to staff picks" : "Room removed from staff picks");
            }
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/rooms");
    }

    public static void deleteRoom(WebConnection client) {
        if (!ensurePermission(client, ROOMS_PERMISSION)) {
            return;
        }

        if (client.get().contains("id")) {
            var room = RoomDao.getRoomById(client.get().getInt("id"));

            if (room != null) {
                RoomDao.delete(room);
                refreshRoom(room.getId());
                setAlert(client, "success", "Room deleted successfully");
            }
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/rooms");
    }

    public static void groups(WebConnection client) {
        if (!ensurePermission(client, GROUPS_PERMISSION)) {
            return;
        }

        String query = client.get().contains("query") ? client.get().getString("query").trim() : "";
        Template tpl = template(client, "housekeeping/groups", "Groups");
        tpl.set("query", query);
        tpl.set("groups", GroupDao.getAdminGroups(query));
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void editGroup(WebConnection client) {
        if (!ensurePermission(client, GROUPS_PERMISSION)) {
            return;
        }

        if (!client.get().contains("id")) {
            setAlert(client, "danger", "Group ID is required");
            client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/groups");
            return;
        }

        int id = client.get().getInt("id");
        GroupDao.GroupAdmin group = GroupDao.getAdminGroup(id);

        if (group == null) {
            setAlert(client, "danger", "Group does not exist");
            client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/groups");
            return;
        }

        if (client.post().queries().size() > 0) {
            String name = client.post().getString("name").trim();
            String alias = client.post().getString("alias").trim();
            int ownerId = client.post().getInt("owner_id");
            int roomId = client.post().getInt("room_id");
            int forumType = client.post().getInt("forum_type");
            int forumPermission = client.post().getInt("forum_premission");

            if (name.isBlank()) {
                setAlert(client, "danger", "Group name cannot be blank");
            } else if (PlayerDao.getDetails(ownerId) == null) {
                setAlert(client, "danger", "Owner ID must be an existing user ID");
            } else if (roomId > 0 && RoomDao.getAdminRoom(roomId) == null) {
                setAlert(client, "danger", "Home room must be 0 or an existing room ID");
            } else if (GroupDao.hasAdminAliasConflict(alias, id)) {
                setAlert(client, "danger", "Group alias is already in use");
            } else if (forumType < 0 || forumType > 1) {
                setAlert(client, "danger", "Forum type must be public or private");
            } else if (forumPermission < 0 || forumPermission > 2) {
                setAlert(client, "danger", "Forum posting permission is invalid");
            } else {
                GroupDao.saveAdminGroup(new GroupDao.GroupAdmin(
                        id,
                        name,
                        client.post().getString("description").trim(),
                        ownerId,
                        group.ownerName(),
                        roomId,
                        client.post().getString("badge").trim(),
                        client.post().contains("recommended"),
                        client.post().getString("background").trim(),
                        client.post().getInt("views"),
                        client.post().getInt("topics"),
                        client.post().getInt("group_type"),
                        forumType,
                        forumPermission,
                        alias,
                        group.createdAt(),
                        group.memberCount(),
                        group.pendingCount(),
                        group.threadCount(),
                        group.staffPick()
                ));

                setAlert(client, "success", "Group saved successfully");
                client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/groups/edit?id=" + id);
                return;
            }
        }

        Template tpl = template(client, "housekeeping/group_edit", "Edit Group");
        tpl.set("group", group);
        tpl.set("members", GroupDao.getAdminMembers(id));
        tpl.set("threads", GroupDao.getAdminThreads(id));
        tpl.set("replies", GroupDao.getAdminReplies(id));
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void updateGroupMember(WebConnection client) {
        if (!ensurePermission(client, GROUPS_PERMISSION)) {
            return;
        }

        int groupId = client.post().contains("group_id") ? client.post().getInt("group_id") : client.get().getInt("group_id");
        int userId = client.post().contains("user_id") ? client.post().getInt("user_id") : client.get().getInt("user_id");

        if (client.get().contains("delete")) {
            GroupMemberDao.deleteMember(userId, groupId);
            setAlert(client, "success", "Group member removed successfully");
        } else {
            int rank = client.post().getInt("member_rank");

            if (rank < 1 || rank > 3) {
                setAlert(client, "danger", "Member rank must be 1, 2, or 3");
            } else {
                GroupDao.saveAdminMember(groupId, userId, rank, client.post().contains("is_pending"));
                setAlert(client, "success", "Group member saved successfully");
            }
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/groups/edit?id=" + groupId);
    }

    public static void updateGroupThread(WebConnection client) {
        if (!ensurePermission(client, GROUPS_PERMISSION)) {
            return;
        }

        int groupId = client.post().contains("group_id") ? client.post().getInt("group_id") : client.get().getInt("group_id");

        if (client.get().contains("delete")) {
            GroupDao.deleteAdminThread(client.get().getInt("thread_id"));
            setAlert(client, "success", "Discussion thread deleted successfully");
        } else {
            String topicTitle = client.post().getString("topic_title").trim();

            if (topicTitle.isBlank()) {
                setAlert(client, "danger", "Thread title cannot be blank");
            } else {
                GroupDao.saveAdminThread(client.post().getInt("thread_id"), topicTitle, client.post().contains("is_open"), client.post().contains("is_stickied"));
                setAlert(client, "success", "Discussion thread saved successfully");
            }
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/groups/edit?id=" + groupId);
    }

    public static void updateGroupReply(WebConnection client) {
        if (!ensurePermission(client, GROUPS_PERMISSION)) {
            return;
        }

        int groupId = client.get().getInt("group_id");
        int replyId = client.get().getInt("reply_id");

        if (client.get().contains("delete")) {
            GroupDao.deleteAdminReply(replyId);
            setAlert(client, "success", "Discussion reply deleted successfully");
        } else {
            boolean deleted = !client.get().contains("deleted") || Boolean.parseBoolean(client.get().getString("deleted"));
            GroupDao.setAdminReplyDeleted(replyId, deleted);
            setAlert(client, "success", deleted ? "Discussion reply hidden successfully" : "Discussion reply restored successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/groups/edit?id=" + groupId);
    }

    public static void deleteGroup(WebConnection client) {
        if (!ensurePermission(client, GROUPS_PERMISSION)) {
            return;
        }

        if (client.get().contains("id")) {
            GroupDao.deleteAdminGroup(client.get().getInt("id"));
            setAlert(client, "success", "Group deleted successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/groups");
    }

    public static void setGroupStaffPick(WebConnection client) {
        if (!ensurePermission(client, GROUPS_PERMISSION)) {
            return;
        }

        if (client.get().contains("id")) {
            int groupId = client.get().getInt("id");

            if (GroupDao.getAdminGroup(groupId) == null) {
                setAlert(client, "danger", "Group does not exist");
            } else {
                boolean enabled = !client.get().contains("enabled") || Boolean.parseBoolean(client.get().getString("enabled"));
                RecommendedDao.setRecommended(groupId, "GROUP", true, enabled);
                Watchdog.STAFF_PICK_GROUPS = RecommendedDao.getRecommendedGroups(true);
                setAlert(client, "success", enabled ? "Group added to staff picks" : "Group removed from staff picks");
            }
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/groups");
    }

    public static void badges(WebConnection client) {
        if (!ensurePermission(client, BADGES_PERMISSION)) {
            return;
        }

        String query = client.get().contains("query") ? client.get().getString("query").trim() : "";
        Template tpl = template(client, "housekeeping/badges", "Badges");
        tpl.set("query", query);
        tpl.set("assignments", BadgeDao.getAdminAssignments(query));
        tpl.set("catalogue", BadgeDao.getAdminCatalogue());
        tpl.set("audit", BadgeDao.getAdminBadgeAudit());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void grantBadge(WebConnection client) {
        if (!ensurePermission(client, BADGES_PERMISSION)) {
            return;
        }

        int userId = client.post().getInt("user_id");
        String badge = client.post().getString("badge").trim();

        if (PlayerDao.getDetails(userId) == null) {
            setAlert(client, "danger", "Target user does not exist");
        } else if (!isValidBadgeCode(badge)) {
            setAlert(client, "danger", "Badge code must be 1 to 50 letters, numbers, underscores, or hyphens");
        } else if (BadgeDao.hasAdminBadge(userId, badge)) {
            setAlert(client, "warning", "Target user already has that badge");
        } else {
            BadgeDao.newBadge(userId, badge);
            BadgeDao.addAdminBadgeAudit("badge_grant", getHousekeepingUserId(client), userId, badge, "Granted from housekeeping");
            setAlert(client, "success", "Badge granted successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/badges?query=" + userId);
    }

    public static void updateBadge(WebConnection client) {
        if (!ensurePermission(client, BADGES_PERMISSION)) {
            return;
        }

        int userId = client.post().getInt("user_id");
        String badge = client.post().getString("badge").trim();
        int slotId = client.post().getInt("slot_id");

        if (!BadgeDao.hasAdminBadge(userId, badge)) {
            setAlert(client, "danger", "Badge assignment does not exist");
        } else if (slotId < 0 || slotId > 5) {
            setAlert(client, "danger", "Badge slot must be between 0 and 5");
        } else {
            BadgeDao.saveBadgeChanges(userId, badge, client.post().contains("equipped"), slotId);
            BadgeDao.addAdminBadgeAudit("badge_update", getHousekeepingUserId(client), userId, badge, "Updated equipped/slot from housekeeping");
            setAlert(client, "success", "Badge assignment updated successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/badges?query=" + userId);
    }

    public static void removeBadge(WebConnection client) {
        if (!ensurePermission(client, BADGES_PERMISSION)) {
            return;
        }

        int userId = client.get().getInt("user_id");
        String badge = client.get().getString("badge").trim();

        if (BadgeDao.hasAdminBadge(userId, badge)) {
            BadgeDao.removeBadge(userId, badge);
            BadgeDao.addAdminBadgeAudit("badge_remove", getHousekeepingUserId(client), userId, badge, "Removed from housekeeping");
            setAlert(client, "success", "Badge removed successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/badges?query=" + userId);
    }

    public static void roomCategories(WebConnection client) {
        if (!ensurePermission(client, ROOM_CATEGORIES_PERMISSION)) {
            return;
        }

        Template tpl = template(client, "housekeeping/room_categories", "Room Categories");
        tpl.set("categories", NavigatorDao.getAdminCategories());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void editRoomCategory(WebConnection client) {
        if (!ensurePermission(client, ROOM_CATEGORIES_PERMISSION)) {
            return;
        }

        int id = client.get().contains("id") ? client.get().getInt("id") : 0;
        NavigatorDao.RoomCategoryAdmin category = id > 0 ? NavigatorDao.getAdminCategory(id) : null;

        if (id > 0 && category == null) {
            setAlert(client, "danger", "Room category does not exist");
            client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/room_categories");
            return;
        }

        if (client.post().queries().size() > 0) {
            String name = client.post().getString("name").trim();

            if (name.isBlank()) {
                setAlert(client, "danger", "Category name cannot be blank");
            } else {
                int savedId = NavigatorDao.saveAdminCategory(new NavigatorDao.RoomCategoryAdmin(
                        id,
                        client.post().getInt("order_id"),
                        client.post().getInt("parent_id"),
                        client.post().contains("isnode"),
                        name,
                        client.post().contains("public_spaces"),
                        client.post().contains("allow_trading"),
                        client.post().getInt("minrole_access"),
                        client.post().getInt("minrole_setflatcat"),
                        client.post().contains("club_only"),
                        client.post().contains("is_top_priority")
                ));

                refreshNavigator();
                setAlert(client, "success", "Room category saved successfully");
                client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/room_categories/edit?id=" + savedId);
                return;
            }
        }

        Template tpl = template(client, "housekeeping/room_category_edit", category == null ? "Create Room Category" : "Edit Room Category");
        tpl.set("category", category);
        tpl.set("categories", NavigatorDao.getAdminCategories());
        tpl.set("ranks", PlayerRank.values());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void deleteRoomCategory(WebConnection client) {
        if (!ensurePermission(client, ROOM_CATEGORIES_PERMISSION)) {
            return;
        }

        if (client.get().contains("id")) {
            NavigatorDao.deleteAdminCategory(client.get().getInt("id"));
            refreshNavigator();
            setAlert(client, "success", "Room category deleted successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/room_categories");
    }

    public static void roomModels(WebConnection client) {
        if (!ensurePermission(client, ROOM_MODELS_PERMISSION)) {
            return;
        }

        Template tpl = template(client, "housekeeping/room_models", "Room Models");
        tpl.set("models", RoomModelDao.getAdminModels());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void editRoomModel(WebConnection client) {
        if (!ensurePermission(client, ROOM_MODELS_PERMISSION)) {
            return;
        }

        int id = client.get().contains("id") ? client.get().getInt("id") : 0;
        RoomModelDao.RoomModelAdmin model = id > 0 ? RoomModelDao.getAdminModel(id) : null;

        if (id > 0 && model == null) {
            setAlert(client, "danger", "Room model does not exist");
            client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/room_models");
            return;
        }

        if (client.post().queries().size() > 0) {
            String modelId = client.post().getString("model_id").trim();
            String triggerClass = client.post().getString("trigger_class").trim();
            Double doorZ = parseDouble(client.post().getString("door_z"));

            if (modelId.isBlank()) {
                setAlert(client, "danger", "Model ID cannot be blank");
            } else if (doorZ == null) {
                setAlert(client, "danger", "Door Z must be a valid number");
            } else if (!isValidRoomModelTrigger(triggerClass)) {
                setAlert(client, "danger", "Trigger class must match a known room model trigger");
            } else {
                int savedId = RoomModelDao.saveAdminModel(new RoomModelDao.RoomModelAdmin(
                        id,
                        modelId,
                        client.post().getString("model_name").trim(),
                        client.post().getInt("door_x"),
                        client.post().getInt("door_y"),
                        doorZ,
                        client.post().getInt("door_dir"),
                        client.post().getString("heightmap").trim(),
                        triggerClass
                ));

                refreshRoomModels();
                setAlert(client, "success", "Room model saved successfully");
                client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/room_models/edit?id=" + savedId);
                return;
            }
        }

        Template tpl = template(client, "housekeeping/room_model_edit", model == null ? "Create Room Model" : "Edit Room Model");
        tpl.set("model", model);
        tpl.set("triggers", Stream.of(RoomModelTriggerType.values()).map(value -> value.name().toLowerCase()).toList());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void deleteRoomModel(WebConnection client) {
        if (!ensurePermission(client, ROOM_MODELS_PERMISSION)) {
            return;
        }

        if (client.get().contains("id")) {
            RoomModelDao.deleteAdminModel(client.get().getInt("id"));
            refreshRoomModels();
            setAlert(client, "success", "Room model deleted successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/room_models");
    }

    public static void itemDefinitions(WebConnection client) {
        if (!ensurePermission(client, ITEM_DEFINITIONS_PERMISSION)) {
            return;
        }

        Template tpl = template(client, "housekeeping/item_definitions", "Item Definitions");
        tpl.set("definitions", ItemDao.getAdminItemDefinitions());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void editItemDefinition(WebConnection client) {
        if (!ensurePermission(client, ITEM_DEFINITIONS_PERMISSION)) {
            return;
        }

        int id = client.get().contains("id") ? client.get().getInt("id") : 0;
        ItemDao.ItemDefinitionAdmin definition = id > 0 ? ItemDao.getAdminItemDefinition(id) : null;

        if (id > 0 && definition == null) {
            setAlert(client, "danger", "Item definition does not exist");
            client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/item_definitions");
            return;
        }

        if (client.post().queries().size() > 0) {
            String sprite = client.post().getString("sprite").trim();
            String behaviour = normaliseCsv(client.post().getString("behaviour"));
            String interactor = client.post().getString("interactor").trim();
            String behaviourError = validateBehaviours(behaviour);
            Double topHeight = parseDouble(client.post().getString("top_height"));

            if (sprite.isBlank()) {
                setAlert(client, "danger", "Sprite cannot be blank");
            } else if (topHeight == null) {
                setAlert(client, "danger", "Top height must be a valid number");
            } else if (interactor.isBlank() || !isValidInteractor(interactor)) {
                setAlert(client, "danger", "Interactor must match a known interaction type");
            } else if (behaviourError != null) {
                setAlert(client, "danger", behaviourError);
            } else {
                int savedId = ItemDao.saveAdminItemDefinition(new ItemDao.ItemDefinitionAdmin(
                        id,
                        sprite,
                        client.post().getString("name").trim(),
                        client.post().getString("description").trim(),
                        client.post().getInt("sprite_id"),
                        client.post().getInt("length"),
                        client.post().getInt("width"),
                        topHeight,
                        client.post().getString("max_status").trim(),
                        behaviour,
                        interactor,
                        client.post().contains("is_tradable"),
                        client.post().contains("is_recyclable"),
                        normaliseCsv(client.post().getString("drink_ids")),
                        client.post().getInt("rental_time"),
                        normaliseCsv(client.post().getString("allowed_rotations")),
                        normaliseCsv(client.post().getString("heights"))
                ));

                refreshItemDefinitions();
                setAlert(client, "success", "Item definition saved successfully");
                client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/item_definitions/edit?id=" + savedId);
                return;
            }
        }

        Template tpl = template(client, "housekeeping/item_definition_edit", definition == null ? "Create Item Definition" : "Edit Item Definition");
        tpl.set("definition", definition);
        tpl.set("behaviours", Stream.of(ItemBehaviour.values()).map(value -> value.name().toLowerCase()).toList());
        tpl.set("interactors", Stream.of(InteractionType.values()).map(value -> value.name().toLowerCase()).toList());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void deleteItemDefinition(WebConnection client) {
        if (!ensurePermission(client, ITEM_DEFINITIONS_PERMISSION)) {
            return;
        }

        if (client.get().contains("id")) {
            ItemDao.deleteAdminItemDefinition(client.get().getInt("id"));
            refreshItemDefinitions();
            setAlert(client, "success", "Item definition deleted successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/item_definitions");
    }

    public static void vouchers(WebConnection client) {
        if (!ensurePermission(client, VOUCHERS_PERMISSION)) {
            return;
        }

        Template tpl = template(client, "housekeeping/vouchers", "Vouchers");
        tpl.set("vouchers", VoucherDao.getAdminVouchers());
        tpl.set("history", VoucherDao.getAdminVoucherHistory(null, 50));
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void editVoucher(WebConnection client) {
        if (!ensurePermission(client, VOUCHERS_PERMISSION)) {
            return;
        }

        String code = client.get().contains("code") ? client.get().getString("code") : "";
        VoucherDao.VoucherAdmin voucher = code.isBlank() ? null : VoucherDao.getAdminVoucher(code);

        if (!code.isBlank() && voucher == null) {
            setAlert(client, "danger", "Voucher does not exist");
            client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/vouchers");
            return;
        }

        if (client.post().queries().size() > 0) {
            String voucherCode = client.post().getString("voucher_code").trim();

            if (voucherCode.isBlank()) {
                setAlert(client, "danger", "Voucher code cannot be blank");
            } else {
                VoucherDao.saveAdminVoucher(
                        new VoucherDao.VoucherAdmin(
                                voucherCode,
                                client.post().getInt("credits"),
                                client.post().getString("expiry_date").trim(),
                                client.post().contains("is_single_use"),
                                client.post().contains("allow_new_users")
                        ),
                        parseLines(client.post().getString("catalogue_sale_codes")),
                        code.isBlank() ? null : code
                );

                setAlert(client, "success", "Voucher saved successfully");
                client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/vouchers/edit?code=" + voucherCode);
                return;
            }
        }

        Template tpl = template(client, "housekeeping/voucher_edit", voucher == null ? "Create Voucher" : "Edit Voucher");
        tpl.set("voucher", voucher);
        tpl.set("voucherItems", code.isBlank() ? List.of() : VoucherDao.getAdminVoucherItems(code));
        tpl.set("history", code.isBlank() ? List.of() : VoucherDao.getAdminVoucherHistory(code, 50));
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void deleteVoucher(WebConnection client) {
        if (!ensurePermission(client, VOUCHERS_PERMISSION)) {
            return;
        }

        if (client.get().contains("code")) {
            VoucherDao.deleteAdminVoucher(client.get().getString("code"));
            setAlert(client, "success", "Voucher deleted successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/vouchers");
    }

    public static void wordfilter(WebConnection client) {
        if (!ensurePermission(client, WORDFILTER_PERMISSION)) {
            return;
        }

        Template tpl = template(client, "housekeeping/wordfilter", "Wordfilter");
        tpl.set("words", WordfilterDao.getAdminWords());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void editWordfilter(WebConnection client) {
        if (!ensurePermission(client, WORDFILTER_PERMISSION)) {
            return;
        }

        int id = client.get().contains("id") ? client.get().getInt("id") : 0;
        WordfilterDao.WordfilterAdmin word = id > 0 ? WordfilterDao.getAdminWord(id) : null;

        if (id > 0 && word == null) {
            setAlert(client, "danger", "Wordfilter entry does not exist");
            client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/wordfilter");
            return;
        }

        if (client.post().queries().size() > 0) {
            String value = client.post().getString("word").trim();

            if (value.isBlank()) {
                setAlert(client, "danger", "Word cannot be blank");
            } else {
                int savedId = WordfilterDao.saveAdminWord(new WordfilterDao.WordfilterAdmin(
                        id,
                        value,
                        client.post().contains("is_bannable"),
                        client.post().contains("is_filterable")
                ));

                WordfilterManager.reset();
                RconUtil.sendCommand(RconHeader.REFRESH_WORDFILTER, new HashMap<>());
                setAlert(client, "success", "Wordfilter entry saved successfully");
                client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/wordfilter/edit?id=" + savedId);
                return;
            }
        }

        Template tpl = template(client, "housekeeping/wordfilter_edit", word == null ? "Create Wordfilter Entry" : "Edit Wordfilter Entry");
        tpl.set("word", word);
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void deleteWordfilter(WebConnection client) {
        if (!ensurePermission(client, WORDFILTER_PERMISSION)) {
            return;
        }

        if (client.get().contains("id")) {
            WordfilterDao.deleteAdminWord(client.get().getInt("id"));
            WordfilterManager.reset();
            RconUtil.sendCommand(RconHeader.REFRESH_WORDFILTER, new HashMap<>());
            setAlert(client, "success", "Wordfilter entry deleted successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/wordfilter");
    }

    public static void recyclerRewards(WebConnection client) {
        if (!ensurePermission(client, RECYCLER_PERMISSION)) {
            return;
        }

        Template tpl = template(client, "housekeeping/recycler_rewards", "Recycler Rewards");
        tpl.set("rewards", ItemDao.getAdminRecyclerRewards());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void editRecyclerReward(WebConnection client) {
        if (!ensurePermission(client, RECYCLER_PERMISSION)) {
            return;
        }

        String sprite = client.get().contains("sprite") ? client.get().getString("sprite") : "";
        ItemDao.RecyclerRewardAdmin reward = sprite.isBlank() ? null : ItemDao.getAdminRecyclerReward(sprite);

        if (!sprite.isBlank() && reward == null) {
            setAlert(client, "danger", "Recycler reward does not exist");
            client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/recycler_rewards");
            return;
        }

        if (client.post().queries().size() > 0) {
            String rewardSprite = client.post().getString("sprite").trim();

            if (rewardSprite.isBlank()) {
                setAlert(client, "danger", "Sprite cannot be blank");
            } else {
                ItemDao.saveAdminRecyclerReward(
                        new ItemDao.RecyclerRewardAdmin(rewardSprite, client.post().getInt("order_id"), client.post().getInt("chance")),
                        sprite.isBlank() ? null : sprite
                );

                EcotronManager.reset();
                RconUtil.sendCommand(RconHeader.REFRESH_ECOTRON, new HashMap<>());
                setAlert(client, "success", "Recycler reward saved successfully");
                client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/recycler_rewards/edit?sprite=" + rewardSprite);
                return;
            }
        }

        Template tpl = template(client, "housekeeping/recycler_reward_edit", reward == null ? "Create Recycler Reward" : "Edit Recycler Reward");
        tpl.set("reward", reward);
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void deleteRecyclerReward(WebConnection client) {
        if (!ensurePermission(client, RECYCLER_PERMISSION)) {
            return;
        }

        if (client.get().contains("sprite")) {
            ItemDao.deleteAdminRecyclerReward(client.get().getString("sprite"));
            EcotronManager.reset();
            RconUtil.sendCommand(RconHeader.REFRESH_ECOTRON, new HashMap<>());
            setAlert(client, "success", "Recycler reward deleted successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/recycler_rewards");
    }

    private static Template template(WebConnection client, String template, String pageName) {
        Template tpl = client.template(template);
        tpl.set("housekeepingManager", HousekeepingManager.getInstance());
        tpl.set("pageName", pageName);
        return tpl;
    }

    private static boolean ensurePermission(WebConnection client, String permission) {
        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.redirect("/" + Routes.HOUSEKEEPING_PATH);
            return false;
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(client.session().getInt("user.id"));

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), permission)) {
            client.redirect("/" + Routes.HOUSEKEEPING_PATH);
            return false;
        }

        return true;
    }

    private static List<String> parseLines(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }

        return Arrays.stream(value.split("\\R|,"))
                .map(String::trim)
                .filter(line -> !line.isBlank())
                .distinct()
                .toList();
    }

    private static void refreshItemDefinitions() {
        ItemManager.reset();
        CatalogueManager.reset();
        CollectablesManager.reset();
        RconUtil.sendCommand(RconHeader.REFRESH_ITEM_DEFINITIONS, new HashMap<>());
    }

    private static String normaliseCsv(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(part -> !part.isBlank())
                .reduce((left, right) -> left + "," + right)
                .orElse("");
    }

    private static String validateBehaviours(String behaviour) {
        if (behaviour.isBlank()) {
            return null;
        }

        for (String part : behaviour.split(",")) {
            try {
                ItemBehaviour.valueOf(part.toUpperCase());
            } catch (Exception ex) {
                return "Unknown behaviour: " + part;
            }
        }

        return null;
    }

    private static boolean isValidInteractor(String interactor) {
        try {
            InteractionType.valueOf(interactor.toUpperCase());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private static boolean isValidRoomModelTrigger(String triggerClass) {
        try {
            RoomModelTriggerType.valueOf(triggerClass.toUpperCase());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private static boolean isValidRoomModelId(String modelId) {
        return RoomModelDao.getAdminModels().stream().anyMatch(model -> model.modelId().equals(modelId));
    }

    private static boolean isValidBadgeCode(String badge) {
        return badge != null && badge.length() > 0 && badge.length() <= 50 && badge.matches("[A-Za-z0-9_-]+");
    }

    private static int getHousekeepingUserId(WebConnection client) {
        return client.session().getInt("user.id");
    }

    private static Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception ex) {
            return null;
        }
    }

    private static void setAlert(WebConnection client, String colour, String message) {
        client.session().set("alertColour", colour);
        client.session().set("alertMessage", message);
    }

    private static void refreshNavigator() {
        NavigatorManager.reset();
        RconUtil.sendCommand(RconHeader.REFRESH_NAVIGATOR, new HashMap<>());
    }

    private static void refreshRoomModels() {
        RoomModelManager.reset();
        RconUtil.sendCommand(RconHeader.REFRESH_ROOM_MODELS, new HashMap<>());
    }

    private static void refreshRoom(int roomId) {
        RoomManager.getInstance().removeRoom(roomId);
        refreshNavigator();
    }
}
