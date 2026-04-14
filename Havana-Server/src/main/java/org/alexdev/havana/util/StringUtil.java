package org.alexdev.havana.util;

import com.google.gson.Gson;
import org.alexdev.havana.dao.mysql.TagDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.util.config.GameConfiguration;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class StringUtil {
    public static Gson GSON = new Gson();

    /**
     * Checks if is null or empty.
     *
     * @param param the param
     * @return true, if is null or empty
     */
    public static boolean isNullOrEmpty(String param) {
        return param == null || param.trim().length() == 0;
    }

    /**
     * Filter input.
     *
     * @param input        the input
     * @param filerNewline if new lines (ENTER) should be filtered
     * @return the string
     */
    public static String filterInput(String input, boolean filerNewline) {
        input = input.replace((char) 1, ' ');
        input = input.replace((char) 2, ' ');
        input = input.replace((char) 9, ' ');
        input = input.replace((char) 10, ' ');
        input = input.replace((char) 12, ' ');

        if (filerNewline) {
            input = input.replace((char) 13, ' ');
        }

        if (GameConfiguration.getInstance().getBoolean("normalise.input.strings")) {
            input = Normalizer.normalize(input, Normalizer.Form.NFD);
        }

        if (input.contains("∂") && input.contains("∫") && input.contains("å") && input.contains("æ")) {
            input = input.replace("∂", "");
            input = input.replace("∫", "");
            input = input.replace("å", "");
            input = input.replace("æ", "");
        }

        return input;
    }

    /**
     * Paginate a list of items.
     *
     * @param <T>          the generic type
     * @param originalList the original list
     * @param chunkSize    the chunk size
     * @return the list
     */
    public static <T> Map<Integer, List<T>> paginate(List<T> originalList, int chunkSize) {
        return paginate(originalList, chunkSize, false);
    }

    public static <T> Map<Integer, List<T>> paginate(List<T> originalList, int chunkSize, boolean emptyFirstPage) {
        Map<Integer, List<T>> chunks = new ConcurrentHashMap<>();
        List<List<T>> listOfChunks = new CopyOnWriteArrayList<>();

        for (int i = 0; i < originalList.size() / chunkSize; i++) {
            listOfChunks.add(originalList.subList(i * chunkSize, i * chunkSize + chunkSize));
        }

        if (originalList.size() % chunkSize != 0) {
            listOfChunks.add(originalList.subList(originalList.size() - originalList.size() % chunkSize, originalList.size()));
        }

        for (int i = 0; i < listOfChunks.size(); i++) {
            chunks.put(i, listOfChunks.get(i));
        }

        if (emptyFirstPage && chunks.isEmpty()) {
            chunks.put(0, new ArrayList<>());
        }

        return chunks;
    }

    /**
     * Round to two decimal places.
     *
     * @param decimal the decimal
     * @return the double
     */
    public static double format(double decimal) {
        return Math.round(decimal * 100.0) / 100.0;
    }

    /**
     * Split.
     *
     * @param str   the string
     * @param delim the delimiter
     * @return the list
     */
    public static List<String> split(String str, String delim) {
        return new ArrayList<>(Arrays.asList(str.split(delim)));
    }

    /**
     * Get words in a string
     *
     * @param s the string to get the list for
     * @return the list of words
     */
    public static String[] getWords(String s) {
        String[] words = s.split("\\s+");

        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
        }

        return words;
    }

    /**
     * Get encoding for strings
     *
     * @return the encoding
     */
    public static Charset getCharset() {
        return StandardCharsets.ISO_8859_1;
    }

    public static String isValidTag(String tag, int userId, int roomId, int groupId) {
        String formatTag = StringUtil.normalizeSpace(StringUtil.filterInput(tag, false)).replaceAll("\\<[^>]*>", "").replace(",", "").toLowerCase();

        if (tag.length() <= 1 || tag.trim().isEmpty() || tag.length() > 20 || TagDao.hasTag(userId, roomId, groupId, tag)) {
            return null;
        }

        return formatTag;
    }

    public static String toAlphabetic(int i) {
        i = i - 1;

        if (i < 0) {
            return "-" + toAlphabetic(-i - 1);
        }

        int quot = i / 26;
        int rem = i % 26;
        char letter = (char) ((int) 'A' + rem);
        if (quot == 0) {
            return "" + letter;
        } else {
            return toAlphabetic(quot - 1) + letter;
        }
    }

    public static void addTag(String tag, int userId, int roomId, int groupId) {
        boolean checkAgain = false;

        if (tag.equalsIgnoreCase("br") || tag.equalsIgnoreCase("brasil")) {
            tag = "brazil";
            checkAgain = true;
        }

        if (tag.equalsIgnoreCase("spanish") || tag.equalsIgnoreCase("es")) {
            tag = "español";
            checkAgain = true;
        }

        if (checkAgain) {
            if (TagDao.hasTag(userId, roomId, groupId, tag)) {
                return;
            }
        }

        TagDao.addTag(userId, roomId, groupId, tag);
    }

    public static boolean hasValue(List<String> firstList, List<String> secondList) {
        for (var str : firstList) {
            if (secondList.contains(str)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if a string contains only numeric digits.
     * Replacement for Apache Commons Lang StringUtils.isNumeric().
     *
     * @param str the string to check
     * @return true if the string contains only digits, false otherwise
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Normalizes whitespace in a string by trimming and collapsing multiple spaces to single space.
     * Replacement for Apache Commons Lang StringUtils.normalizeSpace().
     *
     * @param str the string to normalize
     * @return the normalized string, or null if input was null
     */
    public static String normalizeSpace(String str) {
        if (str == null) {
            return null;
        }
        return str.trim().replaceAll("\\s+", " ");
    }

    /**
     * Right-pads a string to the specified size with the given pad string.
     * Replacement for Apache Commons Lang StringUtils.rightPad().
     *
     * @param str    the string to pad
     * @param size   the target size
     * @param padStr the string to pad with
     * @return the padded string
     */
    public static String rightPad(String str, int size, String padStr) {
        if (str == null) {
            str = "";
        }
        if (padStr == null || padStr.isEmpty()) {
            padStr = " ";
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() < size) {
            sb.append(padStr);
        }
        return sb.substring(0, Math.min(sb.length(), size));
    }

    /**
     * Checks if a string is null or empty.
     * Replacement for Apache Commons Lang StringUtils.isEmpty().
     *
     * @param str the string to check
     * @return true if the string is null or has zero length
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String replaceAlertMessage(String message, Player player) {
        String newString = message;
        newString = newString.replace("\r\n", "<br>");
        newString = newString.replace("\r", "<br>");
        newString = newString.replace("\n", "<br>");

        if (player.getNetwork().isFlashConnection()) {
            newString = newString.replace("<br>", "\r");
        }

        newString = newString.replace("%username%", player.getDetails().getName());
        return newString;
    }
}
