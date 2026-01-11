package org.alexdev.http.controller.api;

import org.alexdev.havana.game.ads.AdManager;
import org.alexdev.havana.game.ads.Advertisement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdvertisementController {

    @GetMapping("/api/ad/img")
    public String getAdImage(@RequestParam(value = "ad", defaultValue = "0") int adId) {
        Advertisement advertisement = AdManager.getInstance().getAd(adId);

        if (advertisement == null) {
            return "redirect:/";
        }

        return "redirect:" + advertisement.getImage();
    }

    @GetMapping("/api/ad/url")
    public String getAdUrl(@RequestParam(value = "ad", defaultValue = "0") int adId) {
        Advertisement advertisement = AdManager.getInstance().getAd(adId);

        if (advertisement == null) {
            return "redirect:/";
        }

        return "redirect:" + advertisement.getUrl();
    }
}
