package org.alexdev.havana.game.encryption;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

public class DiffieHellman {
    private String clientPrivateKey;

    private BigInteger publicKey;
    private BigInteger privateKey;
    private BigInteger sharedKey;
    private BigInteger clientP;
    private BigInteger clientG;
    private String clientPublicKey;

    public DiffieHellman() {
        this.clientPrivateKey = DiffieHellman.generateRandomNumString(64);
        this.privateKey = new BigInteger(this.clientPrivateKey);
        this.clientP = new BigInteger(SecurityCode.assign(SecurityCode.getLoginParameter("p")));
        this.clientG = new BigInteger(SecurityCode.assign(SecurityCode.getLoginParameter("g")));
    }

    /**
     * Generate shared key.
     *
     * @param publicKey the ckey
     */
    public void generateSharedKey(String publicKey) {
        this.clientPublicKey = publicKey;
        this.publicKey = new BigInteger(publicKey);
        this.sharedKey = this.publicKey.modPow(this.privateKey, this.clientP);
    }


    public static String generateRandomHexString(int len) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < len; i++) {
            int rand = 1 + (int) (ThreadLocalRandom.current().nextDouble() * 254); // 1 - 255
            result.append(Integer.toString(rand, 16));
        }
        return result.toString();
    }

    public static String generateRandomNumString(int len) {
        int rand = 0;
        StringBuilder result = new StringBuilder();

        char[] numbers = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

        for (int i = 0; i < len; i++) {
            result.append(Character.toString(numbers[ThreadLocalRandom.current().nextInt(numbers.length)]));
        }
        return result.toString();
    }


    public BigInteger getClientP() {
        return clientP;
    }

    public BigInteger getClientG() {
        return clientG;
    }

    public BigInteger getPrivateKey() {
        return this.privateKey;
    }

    public BigInteger getPublicKey() {
        return this.publicKey;
    }

    public BigInteger getSharedKey() {
        return sharedKey;
    }

    public String getClientPublicKey() {
        return clientPublicKey;
    }

    public String getClientPrivateKey() {
        return clientPrivateKey;
    }
}
