package org.alexdev.havana.server.mus;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.alexdev.havana.dao.mysql.CurrencyDao;
import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.dao.mysql.PhotoDao;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.item.Photo;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.messages.outgoing.user.currencies.FILM;
import org.alexdev.havana.server.mus.connection.MusClient;
import org.alexdev.havana.server.mus.streams.MusMessage;
import org.alexdev.havana.server.mus.streams.MusPropList;
import org.alexdev.havana.server.mus.streams.MusTypes;
import org.alexdev.havana.server.netty.NettyPlayerNetwork;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.oldskooler.simplelogger4j.SimpleLog;

import java.io.IOException;

public class MusConnectionHandler extends SimpleChannelInboundHandler<MusMessage> {
    final private static AttributeKey<MusClient> MUS_CLIENT_KEY = AttributeKey.valueOf("MusClient");
    final private static SimpleLog<MusConnectionHandler> log = SimpleLog.of(MusConnectionHandler.class);

    public MusConnectionHandler() { }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        MusClient client = new MusClient(ctx.channel());
        ctx.channel().attr(MUS_CLIENT_KEY).set(client);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MusMessage message) {
        MusMessage reply;
        MusClient client = ctx.channel().attr(MUS_CLIENT_KEY).get();

        if (client == null) {
            ctx.close();
            return;
        }

        try {
            //log.info("[MUS] Message from {}: {}", ctx.channel().remoteAddress().toString().replace("/", "").split(":")[0], message.toString());

            if (message.getSubject().equals("Logon")) {
                reply = new MusMessage();
                reply.setSubject("Logon");
                reply.setContentType(MusTypes.String);
                reply.setContentString("Havana: Habbo Hotel shockwave emulator");
                ctx.channel().writeAndFlush(reply);

                reply = new MusMessage();
                reply.setSubject("HELLO");
                reply.setContentType(MusTypes.String);
                reply.setContentString("");
                ctx.channel().writeAndFlush(reply);
            }

            if (message.getSubject().equals("LOGIN")) {
                String[] credentials = message.getContentString().split(" ", 2);

                if (!StringUtils.isNumeric(credentials[0])) {
                    return;
                }

                int userId = Integer.valueOf(credentials[0]);
                Player player = PlayerManager.getInstance().getPlayerById(userId);
                
                // Er, ma, gerd, we logged in! ;O
                if (player != null && NettyPlayerNetwork.getIpAddress(player.getNetwork().getChannel()).equals(NettyPlayerNetwork.getIpAddress(ctx.channel()))) {
                    client.setUserId(userId);
                } else {
                    ctx.channel().close(); // Lol, bye, imposter scum!
                }
            }

            if (message.getSubject().equals("PHOTOTXT")) {
                if (client.getUserId() > 0) {
                    var photoText = StringUtil.filterInput(message.getContentString().substring(1), true);

                    if (photoText.length() >= 105) {
                        photoText = photoText.substring(0, 105);
                    }

                    client.setPhotoText(photoText);
                }
            }

            if (message.getSubject().equals("BINDATA")) {
                Player player = PlayerManager.getInstance().getPlayerById(client.getUserId());

                if (player == null) {
                    return;
                }

                if (client.getUserId() < 1) {
                    return;
                }

                if (player.getRoomUser().getRoom() == null) {
                    return;
                }

                long timeSeconds = DateUtil.getCurrentTimeSeconds();

                String time = message.getContentPropList().getPropAsString("time");
                Integer cs = message.getContentPropList().getPropAsInt("cs");
                byte[] image = message.getContentPropList().getPropAsBytes("image");
                String photoText = client.getPhotoText();

                Item photo = new Item();
                photo.setOwnerId(client.getUserId());
                photo.setDefinitionId(ItemManager.getInstance().getDefinitionBySprite("photo").getId());
                photo.setCustomData(DateUtil.getDate(timeSeconds, DateUtil.LONG_DATE) + "\r" + photoText);
                ItemDao.newItem(photo);

                PhotoDao.addPhoto(photo.getDatabaseId(), client.getUserId(), DateUtil.getCurrentTimeSeconds(), image, cs);

                reply = new MusMessage();
                reply.setSubject("BINDATA_SAVED");
                reply.setContentType(MusTypes.String);
                reply.setContentString(Integer.toString(client.getUserId()));
                ctx.channel().writeAndFlush(reply);

                player.getInventory().addItem(photo);
                player.getInventory().getView("new");

                CurrencyDao.decreaseFilm(player.getDetails(), 1);
                player.send(new FILM(player.getDetails()));
            }

            if (message.getSubject().equals("GETBINDATA")) {
                int photoID = Integer.parseInt(message.getContentString().split(" ")[0]);

                Player player = PlayerManager.getInstance().getPlayerById(client.getUserId());

                if (player == null || player.getRoomUser().getRoom() == null) {
                    return;
                }

                Item item = player.getRoomUser().getRoom().getItemManager().getById(photoID);

                if (item == null) {
                    return;
                }

                long databaseId = item.getDatabaseId();
                Photo photo = PhotoDao.getPhoto(databaseId);

                if (photo == null) {
                    return;
                }

                if (client.getUserId() < 1) {
                    return;
                }

                reply = new MusMessage();
                reply.setSubject("BINARYDATA");
                reply.setContentType(MusTypes.PropList);
                reply.setContentPropList(new MusPropList(3));
                reply.getContentPropList().setPropAsBytes("image", MusTypes.Media, photo.getData());
                reply.getContentPropList().setPropAsString("time", DateUtil.getDate(photo.getTime(), DateUtil.LONG_DATE));
                reply.getContentPropList().setPropAsInt("cs", photo.getChecksum());
                ctx.channel().writeAndFlush(reply);
            }

        } catch (Exception ex) {
            SimpleLog.of(MusConnectionHandler.class).error("Exception occurred when handling MUS message: ", ex);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof Exception) {
            if (!(cause instanceof IOException)) {
                SimpleLog.of(MusConnectionHandler.class).error("[MUS] Netty error occurred: ", cause);
            }
        }

        ctx.close();
    }
}
