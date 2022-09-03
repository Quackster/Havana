package org.alexdev.havana.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    public static boolean validateWallPosition(String wallPosition) {
        //:w=3,2 l=9,63 l
        try {
            if (wallPosition.contains(Character.toString((char) 13))) {
                return false;
            }
            if (wallPosition.contains(Character.toString((char) 9))) {
                return false;
            }

            String[] posD = wallPosition.split(" ");

            if (!posD[2].equals("l") && !posD[2].equals("r"))
                return false;

            String[] widD = posD[0].substring(3).split(",");
            int widthX = Integer.parseInt(widD[0]);
            int widthY = Integer.parseInt(widD[1]);
            if (widthX < 0 || widthY < 0 || widthX > 200 || widthY > 200)
                return false;

            String[] lenD = posD[1].substring(2).split(",");
            int lengthX = Integer.parseInt(lenD[0]);
            int lengthY = Integer.parseInt(lenD[1]);

            if (lengthX < 0 || lengthY < 0 || lengthX > 200 || lengthY > 200)
                return false;

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean validateAnstiMutant(String Look, String Gender) {
        boolean HasHead = false;

        if (Look.length() < 1) {
            return false;
        }

        try {
            String[] Sets = Look.split(Pattern.quote("."));

            if (Sets.length < 4) {
                return false;
            }

            for (String Set : Sets) {
                String[] Parts = Set.split(Pattern.quote("-"));

                if (Parts.length < 2 || Parts.length > 3) {
                    return false;
                }

                String Name = Parts[0];
                int Type = Integer.parseInt(Parts[1]);
                int Color = Integer.parseInt(Parts[1]);

                if (Type <= 0 || Color < 0) {
                    return false;
                }

                if (Name.length() != 2) {
                    return false;
                }

                if (Name.equals("hd")) {
                    HasHead = true;
                }
            }
        } catch (Exception ex) {
            return false;
        }

        return HasHead && (Gender.equals("M") || Gender.equals("F"));
    }
}
