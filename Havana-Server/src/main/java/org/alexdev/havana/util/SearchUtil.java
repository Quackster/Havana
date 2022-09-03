package org.alexdev.havana.util;

public class SearchUtil {
    public static String getOwnerTag(String searchQuery) {
        for (String search : searchQuery.split(" ")) {
            if (search.startsWith("owner:"))
                return search;
        }

        return null;
    }
}
