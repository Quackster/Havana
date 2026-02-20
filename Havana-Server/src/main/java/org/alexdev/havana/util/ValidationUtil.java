package org.alexdev.havana.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class ValidationUtil {

    /**
     * Email validation pattern.
     * Matches standard email format: local-part@domain.tld
     */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    /**
     * Validates an email address.
     *
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates an IP address (either IPv4 or IPv6).
     *
     * @param ip the IP address to validate
     * @return true if the IP address is valid, false otherwise
     */
    public static boolean isValidIpAddress(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        try {
            InetAddress.getByName(ip);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    /**
     * Validates an IPv4 address.
     *
     * @param ip the IP address to validate
     * @return true if the IP address is a valid IPv4 address, false otherwise
     */
    public static boolean isValidInet4Address(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        try {
            InetAddress addr = InetAddress.getByName(ip);
            return addr instanceof Inet4Address;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    /**
     * Validates an IPv6 address.
     *
     * @param ip the IP address to validate
     * @return true if the IP address is a valid IPv6 address, false otherwise
     */
    public static boolean isValidInet6Address(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        try {
            InetAddress addr = InetAddress.getByName(ip);
            return addr instanceof Inet6Address;
        } catch (UnknownHostException e) {
            return false;
        }
    }
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
