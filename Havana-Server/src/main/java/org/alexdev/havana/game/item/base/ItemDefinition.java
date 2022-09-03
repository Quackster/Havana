package org.alexdev.havana.game.item.base;

import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.texts.TextsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemDefinition {
    public static final double DEFAULT_TOP_HEIGHT = 0.001;


    private int id;
    private String sprite;
    private String name;
    private String description;
    private int spriteId;
    private String behaviourData;
    private double topHeight;
    private int length;
    private int width;
    private int maxStatus;
    private List<ItemBehaviour> behaviourList;
    private InteractionType interactionType;
    private boolean isTradable;
    private boolean isRecyclable;
    private int[] drinkIds;
    private int rentalTime;
    private List<Integer> allowedRotations;
    private List<Double> heights;

    public ItemDefinition() {
        this.sprite = "";
        this.behaviourData = "";
        this.topHeight = DEFAULT_TOP_HEIGHT;
        this.length = 1;
        this.width = 1;
        this.maxStatus = 0;
        this.behaviourList = new ArrayList<>();
        this.interactionType = null;
        this.drinkIds = new int[0];
        this.allowedRotations = new ArrayList<>();
    }

    public ItemDefinition(int id, String sprite, String name, String description, int spriteId, String behaviourData, double topHeight, int length, int width, int maxStatus, String interactor, boolean isTradable, boolean isRecyclable, String drinkList, int rentalTime, String allowedRotations,
                          String heights) {
        this.id = id;
        this.sprite = sprite;
        this.name = name;
        this.description = description;
        this.spriteId = spriteId;
        this.behaviourData = behaviourData;
        this.topHeight = topHeight;
        this.length = length;
        this.width = width;
        this.maxStatus = maxStatus;
        this.behaviourList = parseBehaviour(this.behaviourData);
        this.interactionType = InteractionType.valueOf(interactor.toUpperCase());
        this.isTradable = isTradable;
        this.isRecyclable = isRecyclable;

        if (allowedRotations.length() > 0) {
            this.allowedRotations = Stream.of(allowedRotations.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } else {
            this.allowedRotations = new ArrayList<>();
        }

        if (heights != null && heights.length() > 0) {
            this.heights = Stream.of(heights.split(","))
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());
        } else {
            this.heights = new ArrayList<>();
        }

        if (drinkList != null) {
            this.drinkIds = new int[drinkList.split(",").length];

            if (drinkList.length() > 0) {
                int i = 0;
                for (String data : drinkList.split(",")) {
                    this.drinkIds[i++] = Integer.parseInt(data);
                }
            }
        }

        // If the item is a gate (checked below) then the top height is set to 0 so the item can be walked in
        /*if (!this.behaviourList.contains(ItemBehaviour.CAN_SIT_ON_TOP)
                && !this.behaviourList.contains(ItemBehaviour.CAN_LAY_ON_TOP)
                && !this.behaviourList.contains(ItemBehaviour.CAN_STACK_ON_TOP)) {
            this.topHeight = 0;
        }*/

        // If the top height 0, then make it 0.001 to make it taller than the default room tile, that the
        // furni collision map can be generated.
        if (this.topHeight == 0) {
            this.topHeight = DEFAULT_TOP_HEIGHT;
        }

        this.rentalTime = rentalTime;
    }

    /**
     * Parse the behaviour list seperated by comma.
     *
     * @param behaviourData the behaviourData to parse
     * @return the behaviour list
     */
    private List<ItemBehaviour> parseBehaviour(String behaviourData) {
        List<ItemBehaviour> behaviourList = new ArrayList<>();

        try {
            if (behaviourData.length() > 0) {
                for (String behaviourEnum : behaviourData.split(",")) {
                    behaviourList.add(ItemBehaviour.valueOf(behaviourEnum.toUpperCase()));
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception occurred for definition: " + this.id);
            ex.printStackTrace();
        }

        return behaviourList;
    }

    /**
     * Get if the item has a type of behaviour.
     *
     * @param behaviour the behaviour to check
     * @return true, if successful
     */
    public boolean hasBehaviour(ItemBehaviour behaviour) {
        return this.behaviourList.contains(behaviour);
    }

    /**
     * Add a behaviour to the list.
     *
     * @param behaviour the behaviour to add
     */
    public void addBehaviour(ItemBehaviour behaviour) {
        if (this.behaviourList.contains(behaviour)) {
            return;
        }

        this.behaviourList.add(behaviour);
    }

    /**
     * Remove a behaviour from the list.
     *
     * @param behaviour the behaviour to remove
     */
    public void removeBehaviour(ItemBehaviour behaviour) {
        this.behaviourList.remove(behaviour);
    }

    /**
     * Get the item name by creating an external text key and reading external text entries.
     *
     * @param specialSpriteId the special sprite id
     * @return the name
     */
    public String getName(int specialSpriteId) {
        if (this.hasBehaviour(ItemBehaviour.DECORATION)) {
            return this.sprite;
        }

        String etxernalTextKey = this.getExternalTextKey(specialSpriteId);
        String name = etxernalTextKey + "_name";

        String value = TextsManager.getInstance().getValue(etxernalTextKey);

        if (value == null) {
            return "null";
        }

        return name;
    }

    /**
     * Get the item description by creating an external text key and reading external text entries.
     *
     * @param specialSpriteId the special sprite id
     * @return the description
     */
    public String getDescription(int specialSpriteId) {
        if (this.hasBehaviour(ItemBehaviour.CAN_STACK_ON_TOP)) {
            return this.sprite;
        }

        String etxernalTextKey = this.getExternalTextKey(specialSpriteId);
        String name = etxernalTextKey + "_desc";

        String value = TextsManager.getInstance().getValue(etxernalTextKey);

        if (value == null) {
            return "null";
        }

        return name;
    }

    /**
     * Create the catalogue icon through using the special sprite id.
     *
     * @param specialSpriteId the special sprite id
     * @return the catalogue icon
     */
    public String getIcon(int specialSpriteId) {
        String icon = "";

        icon += this.sprite;

        if (specialSpriteId > 0) {
            icon += " " + specialSpriteId;
        }

        return icon;
    }

    /**
     * Get external text key by definition.
     *
     * @param specialSpriteId the special sprite id
     * @return the external text key
     */
    private String getExternalTextKey(int specialSpriteId) {
        String key = "";

        if (specialSpriteId == 0) {
            if (this.hasBehaviour(ItemBehaviour.WALL_ITEM)) {
                key = "wallitem";
            } else {
                key = "furni";
            }

            key += "_";
        }

        key += this.sprite;

        if (specialSpriteId > 0) {
            key += ("_" + specialSpriteId);
        }

        return key;
    }

    public int getId() {
        return id;
    }

    public String getSprite() {
        return sprite;
    }

    public int getColour() {
        if (this.sprite.contains("*")) {
            return Integer.parseInt(this.sprite.split("\\*")[1]);
        }

        return 0;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public double getTopHeight() {
        return topHeight;
    }

    public double getPositiveTopHeight() {
        if (this.topHeight < 0) {
            return DEFAULT_TOP_HEIGHT;
        }

        return topHeight;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public void setTopHeight(double topHeight) {
        this.topHeight = topHeight;
    }

    public String getBehaviourData() {
        return behaviourData;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<ItemBehaviour> getBehaviourList() {
        return behaviourList;
    }

    public int getSpriteId() {
        return spriteId;
    }

    public void setSpriteId(int spriteId) {
        this.spriteId = spriteId;
    }

    public int getMaxStatus() {
        return maxStatus;
    }

    public InteractionType getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(InteractionType interactionType) {
        this.interactionType = interactionType;
    }

    public boolean isTradable() {
        return isTradable;
    }

    public void setTradable(boolean tradable) {
        isTradable = tradable;
    }

    public boolean isRecyclable() {
        return isRecyclable;
    }

    public int[] getDrinkIds() {
        return drinkIds;
    }

    public int getRentalTime() {
        return rentalTime;
    }

    public int getRentalTimeAsMinutes() {
        if (this.rentalTime > 0) {
            return (int) TimeUnit.SECONDS.toMinutes(this.rentalTime);
        }

        return -1;
    }

    public List<Integer> getAllowedRotations() {
        return allowedRotations;
    }

    public void setAllowedRotations(List<Integer> allowedRotations) {
        this.allowedRotations = allowedRotations;
    }

    public List<Double> getHeights() {
        return heights;
    }
}
