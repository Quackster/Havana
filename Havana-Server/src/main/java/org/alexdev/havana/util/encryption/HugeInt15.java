package org.alexdev.havana.util.encryption;

import java.util.ArrayList;

public class HugeInt15 {
    public static int encode(int tPlain) {
        var tSeed = 5678;
        var tSBox = new int[] {7530, 6652, 4115, 1750, 3354, 3647, 5188, 2844, 818, 2026, 7133, 2592, 3578};
        var tIterations = 54;
        var tCipher = tPlain;
        var i = 1;
        while (i <= tIterations) {
            tSeed = ((((69069 * tSeed) + (139 * i)) + 92541) % 10000);
            tSeed = (tSeed + ((int)Math.pow(i, 3)));
            tSeed = (((tSBox[(i % tSBox.length)] * tSeed) + 2541) % 10000);
            tCipher = tSeed ^ tCipher;
            tCipher = (1379 + tSBox[(i % tSBox.length)]) ^ tCipher;
            tCipher = ((((14 * tSBox[((i % tSBox.length))]) + 13) % 10000) ^ tCipher);
            tCipher = (tCipher * 2);
            var tHighBit = (tCipher & 32768);
            tCipher = (tCipher & 32767);
            tCipher = (tCipher | (tHighBit != 0 ? 1 : 0));
            i = (1 + i);
        }

        tCipher = (7639 ^ tCipher);
        return tCipher;
    }

    public static int decode(int tInput) {
        var tSeed = 5678;
        var tSBox = new int[]{7530, 6652, 4115, 1750, 3354, 3647, 5188, 2844, 818, 2026, 7133, 2592, 3578};
        var tIterations = 54;
        var tSeedCycle = new ArrayList<Integer>();
        var i = 1;
        while (i <= tIterations) {
            tSeed = (((69069 * tSeed) + (139 * i)) + 92541) % 10000;
            tSeed = (tSeed + ((int) Math.pow(i, 3)));
            tSeed = ((tSBox[i % tSBox.length] * tSeed) + 2541) % 10000;
            tSeedCycle.add(tSeed);
            i = (1 + i);
        }
        var tCipher = tInput;
        tCipher = (7639 ^ tCipher);
        i = 1;
        while (i <= tIterations) {
            var tLowBit = (tCipher & 1);
            tCipher = (tCipher / 2);
            tLowBit = (tLowBit * 16384);
            tCipher = (tCipher | tLowBit);
            var tOffset = tIterations - i;
            tCipher = (tSeedCycle.get(tOffset) ^ tCipher);
            tCipher = ((1379 + tSBox[(tOffset + 1) % tSBox.length]) ^ tCipher);
            tCipher = ((((14 * tSBox[(tOffset + 1) % tSBox.length]) + 13) % 10000) ^ tCipher);
            i = (1 + i);
        }

        return tCipher;
    }
}