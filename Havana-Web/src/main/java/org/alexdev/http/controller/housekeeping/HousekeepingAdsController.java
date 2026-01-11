package org.alexdev.http.controller.housekeeping;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.AdvertisementsDao;
import org.alexdev.havana.game.ads.AdManager;
import org.alexdev.havana.game.ads.Advertisement;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.http.Routes;
import org.alexdev.http.game.housekeeping.HousekeepingManager;
import org.alexdev.http.util.RconUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/" + Routes.HOUSEKEEPING_PATH)
public class HousekeepingAdsController {

    @GetMapping("/room_ads")
    public String roomAdsGet(HttpSession session, Model model) {
        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "room_ads")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        var advertisements = new ArrayList<>(AdManager.getInstance().getAds());
        advertisements.sort(Comparator.comparingInt(Advertisement::getId));

        model.addAttribute("pageName", "Room Ads");
        model.addAttribute("roomAds", advertisements);

        session.removeAttribute("alertMessage");

        return "housekeeping/room_ads";
    }

    @PostMapping("/room_ads")
    public String roomAdsPost(
            @RequestParam Map<String, String> allParams,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "room_ads")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        try {
            List<Advertisement> advertisementList = new ArrayList<>();

            for (var entry : allParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (!key.startsWith("roomad-id-")) {
                    continue;
                }

                int roomId = Integer.parseInt(allParams.getOrDefault("roomad-" + value + "-roomid", "0"));
                boolean isLoadingAd = "on".equalsIgnoreCase(allParams.get("roomad-" + value + "-loading-ad"));
                boolean isEnabled = "on".equalsIgnoreCase(allParams.get("roomad-" + value + "-enabled"));
                String image = allParams.getOrDefault("roomad-" + value + "-image", "");
                String url = allParams.getOrDefault("roomad-" + value + "-url", "");

                advertisementList.add(new Advertisement(Integer.parseInt(value), isLoadingAd, roomId, image, url, isEnabled));
            }

            AdvertisementsDao.updateAds(advertisementList);
            AdManager.getInstance().reset();

            session.setAttribute("alertColour", "success");
            session.setAttribute("alertMessage", "All room ads have been saved successfully!");

            RconUtil.sendCommand(RconHeader.REFRESH_ADS, new HashMap<>());
        } catch (Exception ex) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "An error occurred while saving ads");
        }

        var advertisements = new ArrayList<>(AdManager.getInstance().getAds());
        advertisements.sort(Comparator.comparingInt(Advertisement::getId));

        model.addAttribute("pageName", "Room Ads");
        model.addAttribute("roomAds", advertisements);

        session.removeAttribute("alertMessage");

        return "housekeeping/room_ads";
    }

    @GetMapping("/room_ads/delete")
    public String deleteAd(
            @RequestParam(value = "id", defaultValue = "0") int id,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "room_ads")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        try {
            AdvertisementsDao.deleteAd(id);

            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "Room ad has been deleted successfully");

            RconUtil.sendCommand(RconHeader.REFRESH_ADS, new HashMap<>());
        } catch (Exception ex) {
            // Ignore
        }

        AdManager.getInstance().reset();
        var advertisements = AdManager.getInstance().getAds();

        model.addAttribute("pageName", "Room Ads");
        model.addAttribute("roomAds", advertisements);

        session.removeAttribute("alertMessage");

        return "housekeeping/room_ads";
    }

    @GetMapping("/room_ads/create")
    public String createAdGet(HttpSession session, Model model) {
        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "room_ads")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());
        model.addAttribute("pageName", "Room Ads");

        session.removeAttribute("alertMessage");

        return "housekeeping/room_ads_create";
    }

    @PostMapping("/room_ads/create")
    public String createAdPost(
            @RequestParam(value = "roomid", defaultValue = "0") int roomId,
            @RequestParam(value = "url", defaultValue = "") String url,
            @RequestParam(value = "image", defaultValue = "") String image,
            @RequestParam(value = "enabled", required = false) String enabled,
            @RequestParam(value = "loading-ad", required = false) String loadingAd,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "room_ads")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        try {
            boolean isEnabled = "on".equalsIgnoreCase(enabled);
            boolean isRoomLoadingAd = "on".equalsIgnoreCase(loadingAd);

            AdvertisementsDao.create(roomId, url, image, isEnabled, isRoomLoadingAd);
            AdManager.getInstance().reset();

            session.setAttribute("alertColour", "success");
            session.setAttribute("alertMessage", "Room ad has been created successfully");

            RconUtil.sendCommand(RconHeader.REFRESH_ADS, new HashMap<>());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        model.addAttribute("pageName", "Room Ads");

        session.removeAttribute("alertMessage");

        return "housekeeping/room_ads_create";
    }
}
