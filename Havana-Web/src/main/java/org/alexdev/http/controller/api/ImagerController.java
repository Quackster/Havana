package org.alexdev.http.controller.api;

import net.h4bbo.avatara4j.badges.Badge;
import net.h4bbo.avatara4j.badges.BadgeSettings;
import net.h4bbo.avatara4j.badges.RenderType;
import net.h4bbo.avatara4j.figure.Avatar;
import net.h4bbo.avatara4j.figure.readers.FiguredataReader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ImagerController {

    @GetMapping(value = "/habbo-imaging/avatarimage", produces = "image/png")
    public ResponseEntity<byte[]> avatarImage(
            @RequestParam(value = "figure", required = false) String figure,
            @RequestParam(value = "size", defaultValue = "b") String size,
            @RequestParam(value = "direction", defaultValue = "2") int bodyDirection,
            @RequestParam(value = "head_direction", defaultValue = "2") int headDirection,
            @RequestParam(value = "action", defaultValue = "std") String action,
            @RequestParam(value = "gesture", defaultValue = "std") String gesture,
            @RequestParam(value = "head", defaultValue = "false") boolean headOnly,
            @RequestParam(value = "frame", defaultValue = "1") int frame,
            @RequestParam(value = "crr", defaultValue = "-1") int carryDrink,
            @RequestParam(value = "crop", defaultValue = "false") boolean cropImage,
            @RequestParam(value = "drk", defaultValue = "false") boolean isDrinking) {

        if (figure == null || figure.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new byte[0]);
        }

        if (isDrinking) {
            action = "drk";
        }

        if (StringUtils.isEmpty(action)) {
            action = "std";
        }

        if (StringUtils.isEmpty(gesture)) {
            gesture = "std";
        }

        if (frame < 1) {
            frame = 1;
        }

        var avatar = new Avatar(
                FiguredataReader.getInstance(),
                figure, size, bodyDirection, headDirection, action, gesture, headOnly, frame, carryDrink, cropImage);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(avatar.run());
    }

    @GetMapping(value = "/habbo-imaging/badge/{badgePath}", produces = {"image/png", "image/gif"})
    public ResponseEntity<byte[]> badge(@PathVariable String badgePath) {
        try {
            boolean isGif = badgePath.endsWith(".gif");
            String badgeCode = badgePath.replace(".gif", "").replace(".png", "");

            BadgeSettings settings = new BadgeSettings();
            settings.setShockwaveBadge(true);
            settings.setForceWhiteBackground(false);
            settings.setRenderType(isGif ? RenderType.GIF : RenderType.PNG);

            var badge = Badge.parseBadgeData(settings, badgeCode);

            MediaType mediaType = isGif ? MediaType.IMAGE_GIF : MediaType.IMAGE_PNG;

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(badge.render());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new byte[0]);
        }
    }

    @GetMapping(value = "/habbo-imaging/badgefill/{badgePath}", produces = {"image/png", "image/gif"})
    public ResponseEntity<byte[]> badgeFill(@PathVariable String badgePath) {
        try {
            boolean isGif = badgePath.endsWith(".gif");
            String badgeCode = badgePath.replace(".gif", "").replace(".png", "");

            BadgeSettings settings = new BadgeSettings();
            settings.setShockwaveBadge(true);
            settings.setForceWhiteBackground(true);
            settings.setRenderType(isGif ? RenderType.GIF : RenderType.PNG);

            var badge = Badge.parseBadgeData(settings, badgeCode);

            MediaType mediaType = isGif ? MediaType.IMAGE_GIF : MediaType.IMAGE_PNG;

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(badge.render());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new byte[0]);
        }
    }
}
