package org.alexdev.http.controller.homes.widgets;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.SongMachineDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.song.Song;
import org.alexdev.http.dao.WidgetDao;
import org.alexdev.http.game.homes.Widget;
import org.alexdev.http.game.stickers.StickerType;
import org.alexdev.http.util.SessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TraxController {

    @PostMapping("/myhabbo/traxplayer/select")
    public String selectSong(
            @RequestParam(value = "widgetId", defaultValue = "0") int widgetId,
            @RequestParam(value = "songId", defaultValue = "0") int songId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        Widget widget = WidgetDao.getWidget(widgetId);

        if (widget == null || !widget.getProduct().getData().toLowerCase().equals("traxplayerwidget")) {
            return "";
        }

        boolean canSelect = false;

        if (widget.getProduct().getType() == StickerType.GROUP_WIDGET) {
            canSelect = (GroupDao.getGroupOwner(widget.getGroupId()) == playerDetails.getId());
        } else if (widget.getProduct().getType() == StickerType.HOME_WIDGET) {
            canSelect = (widget.getUserId() == playerDetails.getId());
        }

        if (!canSelect) {
            return "";
        }

        var songList = widget.getSongs();
        Song song = SongMachineDao.getSong(songId);

        if (songId == 0 || song == null || songList.stream().noneMatch(s -> s.getId() == song.getId())) {
            widget.setExtraData("");
        } else {
            widget.setExtraData("" + song.getId());
        }

        WidgetDao.save(widget);

        model.addAttribute("sticker", widget);
        return "homes/widget/habblet/trax_song";
    }

    @GetMapping("/myhabbo/traxplayer/song/{songData}")
    @ResponseBody
    public String getSong(@PathVariable String songData) {
        if (!StringUtils.isNumeric(songData)) {
            return "";
        }

        try {
            Song song = SongMachineDao.getSong(Integer.parseInt(songData));
            String data = song.getData().substring(0, song.getData().length() - 1);

            String trackData = data;
            trackData = trackData.replace(":4:", "&track4=");
            trackData = trackData.replace(":3:", "&track3=");
            trackData = trackData.replace(":2:", "&track2=");
            trackData = trackData.replace("1:", "&track1=");

            String author = PlayerDao.getName(song.getUserId());
            return "status=0&name=" + song.getTitle() + "&author=" + author + trackData;
        } catch (Exception ex) {
            return "";
        }
    }
}
