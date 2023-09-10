package org.alexdev.havana.game.encryption;

public class SecurityCode {
    public static int encode(int tPlain) {
        int tSeed = 5678;
        int[] tSBox = new int[]{7530, 6652, 4115, 1750, 3354, 3647, 5188, 2844, 818, 2026, 7133, 2592, 3578};
        int tIterations = 54;
        int tCipher = tPlain;
        for (int i = 1; i <= tIterations; i++) {
            tSeed = ((69069 * tSeed) + (139 * i) + 92541) % 10000;
            tSeed = tSeed + (int) Math.pow(i, 3);
            tSeed = ((tSBox[(i % tSBox.length)] * tSeed) + 2541) % 10000;
            tCipher = (tSeed ^ tCipher);
            tCipher = (1379 + tSBox[(i % tSBox.length)]) ^ tCipher;
            tCipher = (((14 * tSBox[(i % tSBox.length)]) + 13) % 10000) ^ tCipher;
            tCipher = tCipher * 2;
            int tHighBit = tCipher & 32768;
            tCipher = tCipher & 32767;
            tCipher = tCipher | (tHighBit != 0 ? 1 : 0);
        }
        tCipher = 7639 ^ tCipher;
        return tCipher;
    }


    public static int decode(int tInput) {
        int tSeed = 5678;
        int[] tSBox = new int[]{7530, 6652, 4115, 1750, 3354, 3647, 5188, 2844, 818, 2026, 7133, 2592, 3578};

        int tIterations = 54;
        int[] tSeedCycle = new int[tIterations + 1];

        for (int i = 1; i < tIterations + 1; i++) {
            tSeed = ((69069 * tSeed) + (139 * i) + 92541) % 10000;
            tSeed = tSeed + (int)Math.pow(i, 3);
            tSeed = (tSBox[i % tSBox.length] * tSeed + 2541) % 10000;
            tSeedCycle[i] = tSeed;
        }

        int tCipher = tInput;
        tCipher = 7639 ^ tCipher;

        for (int i = 1; i < tIterations + 1; i++) {
            int tLowBit = (tCipher & 1);
            tCipher = tCipher / 2;
            tLowBit = tLowBit * 16384;
            tCipher = (tCipher | tLowBit);
            int tOffset = tIterations - i + 1;
            tCipher = (tSeedCycle[tOffset ] ^ tCipher);
            tCipher = (1379 + tSBox[tOffset % tSBox.length] ^ tCipher);
            tCipher = ((14 * tSBox[tOffset % tSBox.length] + 13) % 10000 ^ tCipher);
        }

        return tCipher;
    }

    public static int[] getLoginParameter(String parameter) {
        if (parameter.equals("p")) {
            return new int[]{7428, 22321, 14152, 3853, 6961, 15119, 23348, 18690, 24373, 11593, 22349, 23808, 22451, 15709, 18190, 16198, 29452, 10173, 17854, 12040, 10164, 21926, 23423, 11034, 2334, 6950, 1841, 21795, 25351};
        }

        if (parameter.equals("g")) {
            return new int[]{32561, 8998, 950, 29459, 18193, 11607, 5954, 10035, 21438, 11179};
        }

        return null;
    }
}
