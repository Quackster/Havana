package org.alexdev.havana.messages;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.incoming.SET_HOME_ROOM;
import org.alexdev.havana.messages.flash.incoming.modtool.FLASH_MODTOOL_ROOMINFO;
import org.alexdev.havana.messages.flash.incoming.modtool.FLASH_MODTOOL_ROOM_CHATLOG;
import org.alexdev.havana.messages.flash.incoming.navigator.*;
import org.alexdev.havana.messages.flash.incoming.navigator.beta.FLASH_GETGUESTROOMS;
import org.alexdev.havana.messages.flash.incoming.rooms.*;
import org.alexdev.havana.messages.incoming.catalogue.GET_ALIAS_LIST;
import org.alexdev.havana.messages.incoming.catalogue.GET_CATALOGUE_PAGE;
import org.alexdev.havana.messages.incoming.catalogue.GET_CATALOG_INDEX;
import org.alexdev.havana.messages.incoming.catalogue.GRPC;
import org.alexdev.havana.messages.incoming.club.GET_CLUB;
import org.alexdev.havana.messages.incoming.club.SCR_GIFT_APPROVAL;
import org.alexdev.havana.messages.incoming.club.SUBSCRIBE_CLUB;
import org.alexdev.havana.messages.incoming.ecotron.GET_RECYCLER_PRIZES;
import org.alexdev.havana.messages.incoming.ecotron.GET_RECYCLER_STATUS;
import org.alexdev.havana.messages.incoming.ecotron.RECYCLE_ITEMS;
import org.alexdev.havana.messages.incoming.effects.ACTIVATE_AVATAR_EFFECT;
import org.alexdev.havana.messages.incoming.effects.PURCHASE_AND_WEAR;
import org.alexdev.havana.messages.incoming.effects.USE_AVATAR_EFFECT;
import org.alexdev.havana.messages.incoming.events.*;
import org.alexdev.havana.messages.incoming.games.*;
import org.alexdev.havana.messages.incoming.handshake.*;
import org.alexdev.havana.messages.incoming.infobus.CHANGEWORLD;
import org.alexdev.havana.messages.incoming.infobus.TRYBUS;
import org.alexdev.havana.messages.incoming.infobus.VOTE;
import org.alexdev.havana.messages.incoming.inventory.GETSTRIP;
import org.alexdev.havana.messages.incoming.jukebox.*;
import org.alexdev.havana.messages.incoming.messenger.*;
import org.alexdev.havana.messages.incoming.moderation.*;
import org.alexdev.havana.messages.incoming.navigator.*;
import org.alexdev.havana.messages.incoming.pets.APPROVE_PET_NAME;
import org.alexdev.havana.messages.incoming.pets.GETPETSTAT;
import org.alexdev.havana.messages.incoming.purse.GETUSERCREDITLOG;
import org.alexdev.havana.messages.incoming.purse.REDEEM_VOUCHER;
import org.alexdev.havana.messages.incoming.rooms.*;
import org.alexdev.havana.messages.incoming.rooms.dimmer.MSG_ROOMDIMMER_CHANGE_STATE;
import org.alexdev.havana.messages.incoming.rooms.dimmer.MSG_ROOMDIMMER_GET_PRESETS;
import org.alexdev.havana.messages.incoming.rooms.dimmer.MSG_ROOMDIMMER_SET_PRESET;
import org.alexdev.havana.messages.incoming.rooms.idol.START_PERFORMANCE;
import org.alexdev.havana.messages.incoming.rooms.idol.VOTE_PERFORMANCE;
import org.alexdev.havana.messages.incoming.rooms.items.*;
import org.alexdev.havana.messages.incoming.rooms.moderation.*;
import org.alexdev.havana.messages.incoming.rooms.pool.*;
import org.alexdev.havana.messages.incoming.rooms.settings.*;
import org.alexdev.havana.messages.incoming.rooms.user.*;
import org.alexdev.havana.messages.incoming.songs.*;
import org.alexdev.havana.messages.incoming.trade.*;
import org.alexdev.havana.messages.incoming.tutorial.*;
import org.alexdev.havana.messages.incoming.user.*;
import org.alexdev.havana.messages.incoming.user.badges.GETAVAILABLEBADGES;
import org.alexdev.havana.messages.incoming.user.badges.GETSELECTEDBADGES;
import org.alexdev.havana.messages.incoming.user.badges.SETBADGE;
import org.alexdev.havana.messages.incoming.user.latency.PONG;
import org.alexdev.havana.messages.incoming.user.settings.GET_ACCOUNT_PREFERENCES;
import org.alexdev.havana.messages.incoming.wobblesquabble.PTM;
import org.alexdev.havana.messages.outgoing.rooms.groups.GROUP_BADGES;
import org.alexdev.havana.messages.outgoing.rooms.groups.GROUP_INFO;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.NettyPlayerNetwork;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;
import org.alexdev.havana.util.config.ServerConfiguration;
import org.oldskooler.simplelogger4j.SimpleLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MessageHandler {
    private ConcurrentHashMap<Integer, List<MessageEvent>> messages;

    private static final SimpleLog<MessageHandler> log = SimpleLog.of(MessageHandler.class);
    private static MessageHandler instance;

    private MessageHandler() {
        this.messages = new ConcurrentHashMap<>();

        registerHandshakePackets();
        registerPursePackets();
        registerUserPackets();
        registerClubPackets();
        registerNavigatorPackets();
        registerRoomPackets();
        registerRoomUserPackets();
        registerRoomBadgesPackets();
        registerRoomPoolPackets();
        registerRoomSettingsPackets();
        registerRoomItemPackets();
        //registerRoomTeleporterPackets();
        registerModerationPackets();
        registerRoomModerationPackets();
        registerRoomEventPackets();
        registerMessengerPackets();
        registerCataloguePackets();
        registerInventoryPackets();
        registerTradePackets();
        registerSongPackets();
        registerGamePackets();
        //registerJoystick();
        registerEcotron();
        registerEffects();
        registerInfobus();
        registerPetPackets();
        registerJukeboxPackets();
        registerPollPackets();
        registerTutorPackets();
        registerFlashModTool();

        registerEvent(230, (player, reader) -> {
            if (player.getRoomUser().getRoom() == null) {
                return;
            }

            Room room = player.getRoomUser().getRoom();
            HashMap<Integer, String> groupBadges = new HashMap<>();

            for (Player p : room.getEntityManager().getPlayers()) {
                if (p.getDetails().getFavouriteGroupId() > 0) {
                    if (groupBadges.containsKey(p.getDetails().getFavouriteGroupId())) {
                        continue;
                    }

                    var group = player.getJoinedGroup(p.getDetails().getFavouriteGroupId());

                    if (group == null) {
                        continue;
                    }

                    groupBadges.put(group.getId(), group.getBadge());
                }
            }

            player.send(new GROUP_BADGES(groupBadges));
        });

        registerEvent(231, (player, reader) -> {
            if (player.getRoomUser().getRoom() == null) {
                return;
            }

            int groupId = reader.readInt();

            Room room = player.getRoomUser().getRoom();

            for (Player p : room.getEntityManager().getPlayers()) {
                var group = p.getJoinedGroup(groupId);

                if (group == null) {
                  continue;
                }

                player.send(new GROUP_INFO(group));
                break;
            }
        });
    }

    /**
     * Register games packets
     */
    private void registerGamePackets() {
        registerEvent(159, new GETINSTANCELIST());
        registerEvent(160, new OBSERVEINSTANCE());
        registerEvent(161, new UNOBSERVEINSTANCE());
        registerEvent(162, new INITIATECREATEGAME());
        registerEvent(163, new GAMEPARAMETERVALUES());
        registerEvent(165, new INITIATEJOINGAME());
        registerEvent(167, new LEAVEGAME());
        registerEvent(168, new KICKPLAYER());
        registerEvent(169, new WATCHGAME());
        registerEvent(170, new STARTGAME());
        registerEvent(171, new GAMEEVENT());
        registerEvent(172, new GAMERESTART());
        registerEvent(250, new REQUEST_GAME_LOBBY());
        registerEvent(173, new REQUESTFULLGAMESTATUS());
        /*registerEvent(173, (player, reader) -> {
            List<GameObject> objects = new ArrayList<>();
            objects.add(new SnowStormSpawnPlayerEvent(player.getRoomUser().getGamePlayer()));

            GamePlayer gamePlayer = player.getRoomUser().getGamePlayer();
            SnowStormGame game = (SnowStormGame) gamePlayer.getGame();

            gamePlayer.getTurnContainer().calculateChecksum(objects);
            gamePlayer.getTurnContainer().getCurrentTurn().incrementAndGet();

            player.send(new SNOWSTORM_GAMESTATUS((SnowStormGame) game, List.of(), gamePlayer));//.compose(response);
        });*/
    }

    /**
     * Register handshake packets.
     */
    private void registerHandshakePackets() {
        registerEvent(206, new INIT_CRYPTO());
        registerEvent(2002, new GENERATEKEY());
        registerEvent(204, new SSO());
        registerEvent(415, new SSO());
        registerEvent(4, new TRY_LOGIN());
        registerEvent(756, new TRY_LOGIN());
        registerEvent(1817, new GET_SESSION_PARAMETERS());
        registerEvent(813, new UNIQUEID());
        registerEvent(1170, new VERSIONCHECK());
        registerEvent(251, new GET_FURNI_VERSIONS());
    }

    /**
     * Register purse packets.
     */
    private void registerPursePackets() {
        registerEvent(8, new GET_CREDITS());
        registerEvent(127, new GETUSERCREDITLOG());
        registerEvent(129, new REDEEM_VOUCHER());
    }

    /**
     * Register general purpose user packets.
     */
    private void registerUserPackets() {
        registerEvent(7, new GET_INFO());
        registerEvent(228, new GET_ACCOUNT_PREFERENCES());
        registerEvent(196, new PONG());
        registerEvent(370, new GET_POSSIBLE_ACHIEVEMENTS());
        registerEvent(360, new GET_IGNORE_LIST());
        registerEvent(319, new IGNORE_USER());
        registerEvent(322, new UNIGNORE_USER());
        //registerEvent(315, new TEST_LATENCY());
    }

    private void registerClubPackets() {
        registerEvent(26, new GET_CLUB());
        registerEvent(190, new SUBSCRIBE_CLUB());
        registerEvent(210, new SCR_GIFT_APPROVAL());
    }

    /**
     * Register navigator packets.
     */
    private void registerNavigatorPackets() {
        registerEvent(150, new NAVIGATE());
        registerEvent(16, new SUSERF());
        registerEvent(151, new GETUSERFLATCATS());
        registerEvent(264, new RECOMMENDED_ROOMS());
        registerEvent(17, new SRCHF());
        registerEvent(154, new GETSPACENODEUSERS());
        registerEvent(18, new GETFVRF());
        registerEvent(19, new ADD_FAVORITE_ROOM());
        registerEvent(20, new DEL_FAVORITE_ROOM());

        // Flash
        registerEvent(434, new FLASH_USERFLATS());
        registerEvent(382, new FLASH_POPULARTAGS());
        registerEvent(437, new FLASH_SEARCH());
        registerEvent(438, new FLASH_SEARCH_TAGS());
        registerEvent(430, new FLASH_POPULARROOMS());
        registerEvent(431, new FLASH_HIGHESTVOTEDROOMS());
        registerEvent(380, new FLASH_PUBLICROOMS());
        registerEvent(387, new FLASH_CANCREATEROOM());
        registerEvent(439, new FLASH_EVENTROOMS());
        registerEvent(433, new FLASH_FRIENDS_IN_ROOM());
        registerEvent(432, new FLASH_ROOMS_FRIENDS_OWN());
        registerEvent(436, new FLASH_ROOMS_RECENTLY_VISTED());
        registerEvent(435, new FLASH_FAVOURITE_ROOMS());

        // Flash r34
        registerEvent(381, new FLASH_GETGUESTROOMS());
    }

    /**
     * Register room packets.
     */
    private void registerRoomPackets() {
        registerEvent(57, new TRYFLAT());
        registerEvent(59, new GOTOFLAT());
        registerEvent(182, new GETINTEREST());
        registerEvent(2, new ROOM_DIRECTORY());
        registerEvent(126, new GETROOMAD());
        registerEvent(60, new G_HMAP());
        registerEvent(394, new GET_FLOORMAP());
        registerEvent(62, new G_OBJS());
        registerEvent(61,  new G_USRS());
        registerEvent(64, new G_STAT());
        registerEvent(63, new G_ITEMS());
        registerEvent(98, new LETUSERIN());
        registerEvent(261, new RATEFLAT());

        // Flash
        registerEvent(385, new FLASH_ROOM_INFO());
        registerEvent(391, new TRYFLAT());
        //registerEvent(390, new GET_ROOM_INFO());
        registerEvent(390, new GETROOMAD());
        registerEvent(390, new G_HMAP());

        registerEvent(388, new FLASH_GETPUBLICROOMDATA());
        registerEvent(400, new FLASH_ROOMEDITDATA());
        registerEvent(401, new FLASH_ROOMSAVEDATA());
        registerEvent(386, new FLASH_ROOMICONDATA());
        registerEvent(375, new FLASH_GETWARDROBE());
        registerEvent(376, new FLASH_SAVEWARDROBE());
    }

    /**
     * Register room user packets.
     */
    private void registerRoomUserPackets() {
        registerEvent(53, new QUIT());
        registerEvent(75, new WALK());
        registerEvent(115, new GOAWAY());
        registerEvent(52, new CHAT());
        registerEvent(55, new SHOUT());
        registerEvent(56, new WHISPER());
        registerEvent(317, new USER_START_TYPING());
        registerEvent(318, new USER_CANCEL_TYPING());
        registerEvent(79, new LOOKTO());
        registerEvent(80, new CARRYDRINK());
        registerEvent(87, new CARRYITEM());
        registerEvent(94, new WAVE());
        registerEvent(93, new DANCE());
        registerEvent(88, new STOP());
        registerEvent(229, new SET_SOUND_SETTING());
        registerEvent(117, new IIM());
        registerEvent(371, new RESPECT_USER());
        registerEvent(114, new PTM());
        registerEvent(263, new GET_USER_TAGS());
        registerEvent(44, new CHANGE_LOOKS());
        registerEvent(384, new SET_HOME_ROOM());
    }


    /**
     * Register room badges packets;
     */
    private void registerRoomBadgesPackets() {
        registerEvent(157, new GETAVAILABLEBADGES());
        registerEvent(158, new SETBADGE());
        registerEvent(159, new GETSELECTEDBADGES());
    }

    /**
     * Register room settings packets.
     */
    private void registerRoomSettingsPackets() {
        registerEvent(21, new GETFLATINFO());
        registerEvent(29, new CREATEFLAT());
        registerEvent(25, new SETFLATINFO());
        registerEvent(24, new UPDATEFLAT());
        registerEvent(153, new SETFLATCAT());
        registerEvent(152, new GETFLATCAT());
        registerEvent(23, new DELETEFLAT());
    }

    /**
     * Register room item packets.
     */
    private void registerRoomItemPackets() {
        registerEvent(90, new PLACESTUFF());
        registerEvent(73, new MOVESTUFF());
        registerEvent(67, new ADDSTRIPITEM());
        registerEvent(83, new G_IDATA());
        registerEvent(89, new USEITEM());
        registerEvent(84, new SETITEMDATA());
        registerEvent(393, new USEWALLITEM());
        registerEvent(85, new REMOVEITEM());
        registerEvent(183, new CONVERT_FURNI_TO_CREDITS());
        registerEvent(76, new THROW_DICE());
        registerEvent(77, new DICE_OFF());
        registerEvent(247, new SPIN_WHEEL_OF_FORTUNE());
        registerEvent(341, new MSG_ROOMDIMMER_GET_PRESETS());
        registerEvent(342, new MSG_ROOMDIMMER_SET_PRESET());
        registerEvent(343, new MSG_ROOMDIMMER_CHANGE_STATE());
        registerEvent(78, new PRESENTOPEN());
        registerEvent(392, new USEFURNITURE());
        registerEvent(314, new SET_RANDOM_STATE());
        registerEvent(232, new ENTER_ONEWAY_DOOR());
        registerEvent(410, new START_PERFORMANCE());
        registerEvent(411, new VOTE_PERFORMANCE());
    }

    /**
     * Register room pool packets.
     */
    private void registerRoomPoolPackets() {
        registerEvent(116, new SWIMSUIT());
        registerEvent(108, new CLOSE_UIMAKOPPI());
        registerEvent(104, new SIGN());
        registerEvent(105, new BTCKS());
        registerEvent(106, new DIVE());
        registerEvent(107, new SPLASH_POSITION());
    }

    /**
     * Register moderation packets
     */
    private void registerModerationPackets() {
        registerEvent(200, new MODERATORACTION());
        registerEvent(237, new REQUEST_CFH());
        registerEvent(86, new SUBMIT_CFH());
        registerEvent(48, new PICK_CALLFORHELP());
        registerEvent(199, new MESSAGETOCALLER());
        registerEvent(198, new CHANGECALLCATEGORY());
        registerEvent(238, new DELETE_CRY());
        registerEvent(323, new FOLLOW_CRYFORHELP());
    }

    /**
     * Register room moderation packets
     */
    private void registerRoomModerationPackets() {
        registerEvent(95, new KICK());
        registerEvent(96, new ASSIGNRIGHTS());
        registerEvent(97, new REMOVERIGHTS());
        registerEvent(155, new REMOVEALLRIGHTS());
        registerEvent(320, new BANUSER());
    }

    /**
     * Register room trade packets
     */
    private void registerTradePackets() {
        registerEvent(71, new TRADE_OPEN());
        registerEvent(72, new TRADE_ADDITEM());
        registerEvent(70, new TRADE_CLOSE());
        registerEvent(69, new TRADE_ACCEPT());
        registerEvent(68, new TRADE_UNACCEPT());
        registerEvent(402, new TRADE_CONFIRM_ACCEPT());
        registerEvent(403, new TRADE_UNACCEPT());
        registerEvent(405, new TRADE_REMOVE_ITEM());
    }

    /**
     * Register messenger packets.
     */
    private void registerMessengerPackets() {
        registerEvent(12, new MESSENGERINIT());
        registerEvent(41, new FINDUSER());
        registerEvent(39, new MESSENGER_REQUESTBUDDY());
        registerEvent(38, new MESSENGER_DECLINEBUDDY());
        registerEvent(37, new MESSENGER_ACCEPTBUDDY());
        registerEvent(233, new MESSENGER_GETREQUESTS());
        //registerEvent(191, new MESSENGER_GETMESSAGES());
        //registerEvent(36, new MESSENGER_ASSIGNPERSMSG());
        registerEvent(40, new MESSENGER_REMOVEBUDDY());
        registerEvent(33, new MESSENGER_SENDMSG());
        registerEvent(32, new MESSENGER_MARKREAD());
        registerEvent(262, new FOLLOW_FRIEND());
        registerEvent(15, new FRIENDLIST_UPDATE());
        registerEvent(34, new INVITE_FRIEND());
    }

    /**
     * Register catalogue packets
     */
    private void registerCataloguePackets() {
        registerEvent(101, new GET_CATALOG_INDEX());
        registerEvent(102, new GET_CATALOGUE_PAGE());
        registerEvent(100, new GRPC());
        registerEvent(215, new GET_ALIAS_LIST());
    }

    /**
     * Register inventory packets.
     */
    private void registerInventoryPackets() {
        registerEvent(65, new GETSTRIP());
        registerEvent(66, new FLATPROPBYITEM());

        // Flash
        registerEvent(404, new GETSTRIP());
    }

    /**
     * Register song packets.
     */
    private void registerSongPackets() {
        registerEvent(244, new GET_SONG_LIST());
        registerEvent(246, new GET_SONG_LIST());
        registerEvent(239, new NEW_SONG());
        registerEvent(219, new INSERT_SOUND_PACKAGE());
        registerEvent(220, new EJECT_SOUND_PACKAGE());
        registerEvent(240, new SAVE_SONG_NEW());
        registerEvent(243, new UPDATE_PLAY_LIST());
        registerEvent(221, new GET_SONG_INFO());
        registerEvent(245, new GET_PLAY_LIST());
        registerEvent(248, new DELETE_SONG());
        registerEvent(241, new EDIT_SONG());
        registerEvent(242, new SAVE_SONG_EDIT());
        registerEvent(254, new BURN_SONG());
    }

    /**
     * Register effect packets
     */
    private void registerEffects() {
        registerEvent(372, new USE_AVATAR_EFFECT());
        registerEvent(373, new ACTIVATE_AVATAR_EFFECT());
        registerEvent(374, new PURCHASE_AND_WEAR());
    }

    /**
     * Register recycler packets
     */
    private void registerEcotron() {
        registerEvent(414, new RECYCLE_ITEMS());
        registerEvent(413, new GET_RECYCLER_STATUS());
        registerEvent(412, new GET_RECYCLER_PRIZES());
    }

    /**
     * Register infobus packets
     */
    private void registerInfobus() {
        registerEvent(111, new CHANGEWORLD());
        registerEvent(112, new VOTE());
        registerEvent(113, new TRYBUS());
    }

    /**
     * Register room event packets
     */
    private void registerRoomEventPackets() {
        registerEvent(345, new CAN_CREATE_ROOMEVENT());
        registerEvent(346, new CREATE_ROOMEVENT());
        registerEvent(348, new EDIT_ROOMEVENT());
        registerEvent(347, new QUIT_ROOMEVENT());
        registerEvent(349, new GET_ROOMEVENT_TYPE_COUNT());
        registerEvent(350, new GET_ROOMEVENTS_BY_TYPE());
    }

    /**
     * Register pet packets.
     */
    private void registerPetPackets() {
        registerEvent(42, new APPROVE_PET_NAME());
        registerEvent(128, new GETPETSTAT());
    }

    /**
     * Register jukebox packets.
     */
    private void registerJukeboxPackets() {
        registerEvent(258, new GET_JUKEBOX_DISCS());
        registerEvent(259, new GET_USER_SONG_DISCS());
        registerEvent(255, new ADD_JUKEBOX_DISC());
        registerEvent(256, new REMOVE_JUKEBOX_DISC());
        registerEvent(257, new JUKEBOX_PLAYLIST_ADD());
        registerEvent(260, new RESET_JUKEBOX());
    }

    /**
     * Register polling packets.
     */
    private void registerPollPackets() {
        //registerEvent(234, new POLL_START());
    }

    /**
     * Register tutor packets.
     */
    private void registerTutorPackets() {
        registerEvent(356, new MSG_INVITE_TUTORS());
        registerEvent(355, new MSG_GET_TUTORS_AVAILABLE());
        registerEvent(362, new MSG_WAIT_FOR_TUTOR_INVITATIONS());
        registerEvent(363, new MSG_CANCEL_WAIT_FOR_TUTOR_INVITATIONS());
        registerEvent(313, new MSG_REMOVE_ACCOUNT_HELP_TEXT());
        registerEvent(357, new MSG_ACCEPT_TUTOR_INVITATION());
        registerEvent(358, new MSG_REJECT_TUTOR_INVITATION());
        registerEvent(359, new MSG_CANCEL_TUTOR_INVITATIONS());
        registerEvent(249, new RESET_TUTORIAL());
    }

    private void registerFlashModTool() {
        registerEvent(459, new FLASH_MODTOOL_ROOMINFO());
        registerEvent(456, new FLASH_MODTOOL_ROOM_CHATLOG());
    }

    /**
     * Register event.
     *
     * @param header the header
     * @param messageEvent the message event
     */
    private void registerEvent(int header, MessageEvent messageEvent) {
        if (!this.messages.containsKey(header)) {
            this.messages.put(header, new ArrayList<>());
        }


        this.messages.get(header).add(messageEvent);
    }

    /**
     * Handle request.
     *
     * @param message the message
     */
    public void handleRequest(Player player, NettyRequest message) {
        if (ServerConfiguration.getBoolean("log.received.packets")) {
            if (this.messages.containsKey(message.getHeaderId())) {
                MessageEvent event = this.messages.get(message.getHeaderId()).get(0);
                player.getLogger().info("Received (" + event.getClass().getSimpleName() + "): " + message.getHeaderId() + " / " + message.getMessageBody());
            } else {
                player.getLogger().info("Received (Unknown): " + message.getHeaderId() + " / " + message.getMessageBody());
            }
        }

        invoke(player, message.getHeaderId(), message);
    }

    /**
     * Invoke the request.
     *
     * @param messageId the message id
     * @param message the message
     */
    private void invoke(Player player, int messageId, NettyRequest message) {
        ByteBuf byteBuf = null;

        if (this.messages.containsKey(messageId)) {
            List<MessageEvent> events = this.messages.get(messageId);

            for (var event : events) {
                if (events.size() > 0) {
                    NettyRequest nettyRequest = null;

                    try {
                        nettyRequest = new NettyRequest(messageId, Unpooled.copiedBuffer(message.remainingBytes()));
                        event.handle(player, nettyRequest);
                    } catch (Exception ex) {
                        handlePacketException(player, ex, message);
                    } finally {
                        if (nettyRequest != null) {
                            nettyRequest.dispose();
                        }
                    }
                } else {
                    try {
                       event.handle(player, message);
                    } catch (Exception ex) {
                        handlePacketException(player, ex, message);
                    }
                }
            }
        }
    }

    /**
     * Global exception handler for packet errors.
     *
     * @param player the player where the error occurred
     * @param ex the exception message
     * @param message the message that was sent to the server
     */
    private void handlePacketException(Player player, Exception ex, NettyRequest message) {
        String name = "[NOT LOGGED IN/" + NettyPlayerNetwork.getIpAddress(player.getNetwork().getChannel()) + "]";

        if (player.isLoggedIn()) {
            name = player.getDetails().getName();
        }

        if (ex instanceof MalformedPacketException) {
            ex.printStackTrace();
            //SimpleLog.of(SnowStormGameTask.class).error("An invalid packet was sent to the server by " + name + ", kicking off client.");
            //SimpleLog.of(SnowStormGameTask.class).error("Packet contents: " + message.getHeaderId() + " / " + message.getMessageBody());
            /*if (GameConfiguration.getInstance().getBoolean("enforce.strict.packet.policy")) {
                long banExpiry = DateUtil.getCurrentTimeSeconds() + TimeUnit.MINUTES.toSeconds(60);
                BanDao.addBan(BanType.USER_ID, String.valueOf(player.getDetails().getId()), banExpiry, "Automatic ban for scripting (temporary 1 hr / 60 minute ban)");

                BanManager.getInstance().disconnectBanAccounts(new HashMap<>() {{
                    put(BanType.USER_ID, String.valueOf(player.getDetails().getId()));
                }});
            } else {
                player.getNetwork().disconnect();
            }*/
            return;
        }

        SimpleLog.of(MessageHandler.class).error("Error occurred when handling (" + message.getHeaderId() + ") for user (" + name + "):", ex);
        //SimpleLog.of(SnowStormGameTask.class).error("Packet contents: " + message.getHeaderId() + " / " + message.getHeader() + " / " + message.getMessageBody());
    }

    /**
     * Gets the messages.
     *
     * @return the messages
     */
    private ConcurrentHashMap<Integer, List<MessageEvent>> getMessages() {
        return messages;
    }

    /**
     * Get the instance of {@link RoomManager}
     *
     * @return the instance
     */
    public static MessageHandler getInstance() {
        if (instance == null) {
            instance = new MessageHandler();
        }

        return instance;
    }
}
