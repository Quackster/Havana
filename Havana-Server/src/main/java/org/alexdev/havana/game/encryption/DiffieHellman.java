package org.alexdev.havana.game.encryption;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;
import java.security.SecureRandom;

public class DiffieHellman {
    private static final int BITLENGTH = 64;

    private BigInteger clientP;
    private BigInteger clientG;
    private BigInteger publicKey;
    private BigInteger privateKey;
    private BigInteger sharedKey;

    public DiffieHellman() {
        this.privateKey = generatePrivateKey();

        var adobeClientG = new HugeInt15();
        var adobeClientP = new HugeInt15();

        adobeClientG.assign(SecurityCode.getLoginParameter("g"), null, true);
        adobeClientP.assign(SecurityCode.getLoginParameter("p"), null, true);

        this.clientG = new BigInteger(adobeClientG.getString());
        this.clientP = new BigInteger(adobeClientP.getString());

        this.publicKey = this.computePublicKey(this.privateKey);
    }

    private static BigInteger generatePrivateKey() {
        SecureRandom random = new SecureRandom();
        BigInteger privateKey;
        do {
            privateKey = new BigInteger(BITLENGTH, random);
        } while (privateKey.compareTo(BigInteger.ZERO) == 0);
        return privateKey;
    }

    private BigInteger computePublicKey(BigInteger privateKey) {
        return this.clientG.modPow(privateKey, this.clientP);
    }

    private BigInteger computeSharedSecret(BigInteger privateKey, BigInteger publicKey) {
        return publicKey.modPow(privateKey, this.clientP);
    }

    public void generateSharedKey(String publicServerKey) {
        this.sharedKey = computeSharedSecret(this.privateKey, new BigInteger(publicServerKey));
    }

    public static String generateRandomNumString(int len) {
        StringBuilder result = new StringBuilder();

        char[] numbers = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

        for (int i = 0; i < len; i++) {
            result.append(numbers[ThreadLocalRandom.current().nextInt(numbers.length)]);
        }
        return result.toString();
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getSharedKey() {
        return sharedKey;
    }
}
