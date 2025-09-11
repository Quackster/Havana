package org.alexdev.havana.game.item.extradata;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.alexdev.havana.game.item.Item;

import java.lang.reflect.InvocationTargetException;

public class ExtraDataManager {
    private static final Gson gson = new Gson();

    /**
     * Gets the json data, will try restore to default if there's invalid JSON data.
     *
     * @param item the item
     * @param t the class type
     * @return the json data
     */
    public static <T> T getJsonData(Item item, Class<T> t) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T settings = null;

        if (isValidJSON(item.getCustomData(), t)) {
            settings = gson.fromJson(item.getCustomData(), t);
        } else {
            settings = t.getDeclaredConstructor().newInstance();

            saveExtraData(item, settings);
            item.save();
        }

        return settings;
    }

    /**
     * Validate the input json
     * @param jsonInString the json to parse
     */
    public static <T> boolean isValidJSON(String jsonInString, Class<T> obj) {
        try {

            gson.fromJson(jsonInString, obj);
            return true;
        } catch (JsonSyntaxException ex) {
            return false;
        }
    }


    /**
     * Save extra data to JSON format.
     *
     * @param item the item
     * @param src the data to serialise
     */
    public static <T> void saveExtraData(Item item, T src) {
        item.setCustomData(gson.toJson(src));
    }

    /**
     * Get GSON instance.
     *
     * @return the gson instance
     */
    public static Gson getGson() {
        return gson;
    }
}
