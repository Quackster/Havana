package org.alexdev.havana.game.catalogue;

import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.EasterUtil;
import org.alexdev.havana.util.config.GameConfiguration;

import java.util.ArrayList;
import java.util.List;

public class CataloguePage {
    private int id;
    private int parentId;
    private PlayerRank minRole;
    private boolean isNavigatable;
    private boolean isClubOnly;
    private String name;
    private int icon;
    private int colour;
    private String layout;
    private List<String> images;
    private List<String> texts;
    private String seasonalStartDate;
    private int seasonalLength;

    public CataloguePage(int id, int parentId, PlayerRank minRole, boolean isNavigatable, boolean isClubOnly, String name, int icon, int colour, String layout, List<String> images, List<String> texts,
                         String seasonalStartDate, int seasonalLength) {
        this.id = id;
        this.parentId = parentId;
        this.minRole = minRole;
        this.isNavigatable = isNavigatable;
        this.isClubOnly = isClubOnly;
        this.name = name;
        this.icon = icon;
        this.colour = colour;
        this.layout = layout;
        this.images = images;
        this.texts = texts;
        //this.images = StringUtil.GSON.fromJson(images, new TypeToken<List<String>>(){}.getType());
        //this.texts = StringUtil.GSON.fromJson(texts, new TypeToken<List<String>>(){}.getType());
        this.seasonalStartDate = seasonalStartDate;
        this.seasonalLength = seasonalLength;

        if (this.minRole == null) {
            this.minRole = PlayerRank.ADMINISTRATOR;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public PlayerRank getMinRole() {
        return minRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLayout() {
        return layout;
    }


    /*public String getBody() {
        if (RareManager.getInstance().getCurrentRare() != null &&
                this.getId() == GameConfiguration.getInstance().getInteger("rare.cycle.page.id")) {

            TimeUnit rareManagerUnit = TimeUnit.valueOf(GameConfiguration.getInstance().getString("rare.cycle.refresh.timeunit"));

            long interval = rareManagerUnit.toSeconds(GameConfiguration.getInstance().getInteger("rare.cycle.refresh.interval"));
            long currentTick = RareManager.getInstance().getTick().get();
            long timeUntil = interval - currentTick;

            return (this.body + " The time until the next rare is " + DateUtil.getReadableSeconds(timeUntil) + "!");
        }

        return body;
    }*/

    public int getParentId() {
        return parentId;
    }

    public int getIcon() {
        return icon;
    }

    public int getColour() {
        return colour;
    }

    public boolean isNavigatable() {
        return isNavigatable;
    }

    public boolean isClubOnly() {
        return isClubOnly;
    }

    public List<String> getImages() {
        if (this.layout.equalsIgnoreCase("frontpage3")) {
            List<String> imageList = new ArrayList<>();

            for (var image : images) {
                String text = image;

                for (int i = 1; i < 5; i++) {
                    if (i == 1) {
// FilenameUtils.removeExtension(client.post().getString("catalogue.frontpage.input.1"))
                        var fileName = GameConfiguration.getInstance().getString("catalogue.frontpage.input." + i);
                        fileName = fileName.substring(0, fileName.lastIndexOf("."));

                        text = text.replace("{input" + i + "}", fileName);
                    } else {
                        text = text.replace("{input" + i + "}", GameConfiguration.getInstance().getString("catalogue.frontpage.input." + i));
                    }
                }

                imageList.add(text);
            }

            return imageList;
        }

        return images;
    }

    public List<String> getTexts() {
        if (this.layout.equalsIgnoreCase("frontpage3")) {
            List<String> textList = new ArrayList<>();

            for (var image : texts) {
                String text = image;

                for (int i = 1; i < 5; i++) {
                    text = text.replace("{input" + i + "}", GameConfiguration.getInstance().getString("catalogue.frontpage.input." + i));
                }

                textList.add(text);
            }

            return textList;
        }

        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public String getSeasonalStartDate() {
        return seasonalStartDate;
    }

    public int getSeasonalLength() {
        return seasonalLength;
    }

    public boolean isValidSeasonal() {
        if (!GameConfiguration.getInstance().getBoolean("seasonal.items")) {
            return true;
        }

        if (this.seasonalStartDate == null) {
            return true;
        }

        if (this.seasonalStartDate.equals("EASTER")) {
            return EasterUtil.isEasterMonday();
        }

        var currentTime = DateUtil.getCurrentTimeSeconds();
        var currentYear = DateUtil.getDate(currentTime, "yyyy");

        var startTime = DateUtil.getFromFormat("yyyy-MM-dd", currentYear + "-" +this.seasonalStartDate);
        var endTime = startTime + this.seasonalLength;

        return (currentTime > startTime) && (endTime > DateUtil.getCurrentTimeSeconds());
    }

    public CataloguePage copy() {
        /*
            private int id;
    private int parentId;
    private PlayerRank minRole;
    private boolean isNavigatable;
    private boolean isClubOnly;
    private String name;
    private int icon;
    private int colour;
    private String layout;
    private List<String> images;
    private List<String> texts;
    private String seasonalStartDate;
    private int seasonalLength;
         */
        return new CataloguePage(id, parentId, minRole, isNavigatable, isClubOnly, name, icon, colour, layout, images, texts, seasonalStartDate, seasonalLength);
    }
}
