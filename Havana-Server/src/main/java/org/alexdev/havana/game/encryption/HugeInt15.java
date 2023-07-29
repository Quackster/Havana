package org.alexdev.havana.game.encryption;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HugeInt15 {
    public List<Integer> pData_NxIhNARqldyJyY2PfT03dK8t9OLUR;
    private boolean pNegative;
    private int pBase;
    private int pDigits;

    public HugeInt15() {
        pData_NxIhNARqldyJyY2PfT03dK8t9OLUR = new ArrayList<Integer>();
        pNegative = false;
        pBase = 10000;
        pDigits = Integer.toString(pBase).length() - 1;
    }

    public void neg() {
        if (pNegative) {
            pNegative = false;
        } else {
            pNegative = true;
        }
    }

    public void assign(Object tdata, Object tLimit, boolean tUseKey) {
        pData_NxIhNARqldyJyY2PfT03dK8t9OLUR = new ArrayList<Integer>();
        if (tdata instanceof String dataString) {
            if (dataString.charAt(0) == '-') {
                pNegative = true;
                dataString = dataString.substring(1);
            } else {
                pNegative = false;
            }
            int i = dataString.length();
            while (i > 0) {
                String tCoef = dataString.substring(Math.max(0, i - pDigits), i);
                i = i - tCoef.length();
                pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.add(Integer.valueOf(tCoef));
            }
        } else if (tdata instanceof int[] tIntArray) {
            List<Integer> dataList = new ArrayList<>();

            for (int j : tIntArray) {
                dataList.add(j);
            }

            pNegative = false;
            int tZeroes = 1;
            int limit = (tLimit == null) ? dataList.size() : Math.min((int) tLimit, dataList.size());

            for (int i = 0; i < limit; i++) {
                pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.add(null);
            }

            for (int i = 1; i <= limit; i++) {
                if (dataList.get(i - 1) != 0 || tZeroes == 0) {
                    if (!tUseKey) {
                        pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.set(limit - i, dataList.get(i - 1));
                    } else {
                        pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.set(limit - i, SecurityCode.decode(dataList.get(i - 1)));
                    }
                    tZeroes = 0;
                }
            }
        }

        //System.out.println("pData_NxIhNARqldyJyY2PfT03dK8t9OLUR - " + String.join(",", pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.stream().map(x -> x == null ? "" : x.toString()).toArray(String[]::new)));
    }

    public int getIntValue(Object tLimit) {
        int limit = (tLimit == null) ? 100000000 : (int) tLimit;
        int tLimitLo = limit / pBase * 10;
        int tLength = pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size();
        int tInt = pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.get(tLength - 1);
        int tIndex = tLength - 2;
        while (tInt < limit && tIndex >= 0) {
            int tCoef = pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.get(tIndex);
            if (tInt < tLimitLo) {
                tInt = (tInt * pBase) + tCoef;
            } else {
                int tCoefMultiplier = 10;
                while ((tInt * tCoefMultiplier) < limit) {
                    tCoefMultiplier = tCoefMultiplier * 10;
                }
                int tCoefDivider = pBase / tCoefMultiplier;
                tInt = (tInt * tCoefMultiplier) + (tCoef / tCoefDivider);
            }
            tIndex--;
        }
        return tInt;
    }

    public String getString() {
        if (pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size() == 0) {
            return "0";
        }
        String tStr = "";
        for (int i = 0; i < pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size(); i++) {
            String tValue = String.valueOf(pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.get(i));
            if (i < pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size() - 1) {
                while (tValue.length() < pDigits) {
                    tValue = "0" + tValue;
                }
            }
            tStr = tValue + tStr;
        }
        if (pNegative) {
            tStr = "-" + tStr;
        }
        return tStr;
    }

    public void copyFrom(HugeInt15 tValue) {
        pNegative = tValue.pNegative;
        pData_NxIhNARqldyJyY2PfT03dK8t9OLUR = new ArrayList<>(tValue.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR);
        trim();
    }

    public void trim() {
        for (int i = pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size() - 1; i >= 0; i--) {
            if (pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.get(i) == 0) {
                pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.remove(i);
            } else {
                break;
            }
        }
        if (pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.isEmpty()) {
            pNegative = false;
        }
    }

    public int[] getIntArray(boolean tUseKey) {
        ArrayList<Integer> tData = new ArrayList<>();
        for (int i = 0; i < pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size(); i++) {
            int tVal = pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.get(pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size() - (i + 1));
            if (tUseKey) {
                tVal = encode(tVal);
            }
            tData.add(tVal);
        }
        int[] tArray = new int[tData.size()];
        for (int i = 0; i < tData.size(); i++) {
            tArray[i] = tData.get(i);
        }
        return tArray;
    }

    private int encode(int value) {
        return (value < 0) ? (value + 256) : value;
    }


    public static int[] getByteArray(BigInteger tSharedKey) {
        byte[] arr = tSharedKey.toByteArray();

        // Remove the "sign bit" at the start that Java adds to the BigInteger so that
        // it can match Shockwave's output
        if (arr[0] == 0) {
            byte[] bytesWithoutSignBit = new byte[arr.length - 1];
            System.arraycopy(arr, 1, bytesWithoutSignBit, 0, bytesWithoutSignBit.length);
            arr = bytesWithoutSignBit;
        }

        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                result[i] = 256 - Math.abs(arr[i]);
            } else {
                result[i] = arr[i];
            }
        }
        return result;
    }

    public boolean lessThan(HugeInt15 tValue, boolean tUseSign) {
        if (equals(tValue)) {
            return false;
        }
        return !greaterThan(tValue, tUseSign);
    }

    public boolean greaterThan(HugeInt15 tValue, Boolean tUseSign) {
        if (equals(tValue)) {
            return false;
        }
        if (tUseSign == null) {
            tUseSign = true;
        }
        if (tUseSign) {
            if (!pNegative && tValue.pNegative) {
                return true;
            }
            if (pNegative && !tValue.pNegative) {
                return false;
            }
        }
        if (pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size() > tValue.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size()) {
            return tUseSign ? !pNegative : true;
        } else if (pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size() < tValue.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size()) {
            return tUseSign ? pNegative : false;
        }
        for (int i = pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size() - 1; i >= 0; i--) {
            if (pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.get(i) > tValue.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.get(i)) {
                return tUseSign ? !pNegative : true;
            } else if (pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.get(i) < tValue.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.get(i)) {
                return tUseSign ? pNegative : false;
            }
        }
        return false;
    }


    public boolean isZero() {
        for (int i = 0; i < pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size(); i++) {
            if (pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.get(i) != 0) {
                return false;
            }
        }
        return true;
    }

/*
    public HugeInt15 div(HugeInt15 tDivider, boolean tReturnModulo, boolean tKeepResult) {
        if (tDivider.isZero()) {
            return null;
        }
        HugeInt15 tResult = new HugeInt15();
        if (lessThan(tDivider, false)) {
            if (tReturnModulo) {
                tResult.copyFrom(this);
            }
            return tResult;
        }
        HugeInt15 tTemp = new HugeInt15();
        HugeInt15 tTemp2 = new HugeInt15();
        HugeInt15 tRemainder = new HugeInt15();
        HugeInt15 tRemainderTemp = new HugeInt15();
        tRemainder.copyFrom(this);
        int tDividerInt = tDivider.getIntValue(10000);
        List<Integer> tResultData = new ArrayList<Integer>();
        int tNegResult = 0;
        if (tRemainder.pNegative) {
            tRemainder.neg();
            tNegResult = 1;
        }
        if (tDivider.pNegative) {
            tDivider.neg();
            tNegResult = tNegResult ^ 1;
        }
        int tDividerDigits = tDivider.getLength();
        int tDividerIntLength = getIntLength(tDividerInt);
        while (!tRemainder.lessThan(tDivider)) {
            int tRemainderInt = tRemainder.getIntValue();
            int tRemainderDigits = tRemainder.getLength();
            int tRemainderIntLength = getIntLength(tRemainderInt);
            int tRemainderIntFirstDigits = tRemainderInt;
            for (int i = tRemainderIntLength - tDividerIntLength; i >= 1; i--) {
                tRemainderIntFirstDigits = tRemainderIntFirstDigits / 10;
            }
            int tFastCoef;
            if (tRemainderIntFirstDigits != tDividerInt) {
                tFastCoef = tRemainderInt / tDividerInt;
            } else {
                String tRemainderStr = tRemainder.getString().substring(0, tDividerDigits);
                tRemainderTemp.assign(tRemainderStr, null, false);
                if (tDivider.greaterThan(tRemainderTemp)) {
                    tFastCoef = (tRemainderInt / tDividerInt) - 1;
                } else {
                    tFastCoef = tRemainderInt / tDividerInt;
                }
            }
            int tDigitDelta = tRemainderDigits - tDividerDigits;
            int tDigitCount = tDigitDelta % pDigits;
            int tFastCoefLength = getIntLength(tFastCoef);
            if ((tFastCoefLength + tDividerIntLength) > tRemainderIntLength) {
                tDigitCount = tDigitCount + 1;
            }
            if (tDigitCount == 0) {
                tDigitCount = pDigits;
            }
            for (int i = tFastCoefLength - tDigitCount; i >= 1; i--) {
                tFastCoef = tFastCoef / 10;
            }
            tTemp.copyFrom(tDivider);
            tTemp.mul(tFastCoef);
            int tAddCount = tRemainder.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size() - tTemp.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size();
            for (int i = 1; i <= tAddCount; i++) {
                tTemp.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.add(0);
            }
            int tAddCountNew = tAddCount;
            while (true) {
                int tFastCoef = tRemainderDigit / tDividerDigit;
                tTemp2.copyFrom(tDivider);
                tTemp2.mul(tFastCoef);
                int tDividerIntLength = getIntLength(tDividerDigit);
                int tFastCoefLength = getIntLength(tFastCoef);
                int tRemainderIntLength = getIntLength(tRemainderDigit);
                int tDividerDigits = getDigits(tDividerDigit);
                int tRemainderDigits = getDigits(tRemainderDigit);
                int tDigitDelta = tRemainderDigits - tDividerDigits;
                int tDigitCount = tDigitDelta % pDigits;
                if ((tFastCoefLength + tDividerIntLength) > tRemainderIntLength) {
                    tDigitCount = tDigitCount + 1;
                }
                if (tDigitCount == 0) {
                    tDigitCount = pDigits;
                }
                if (tDigitCount > tDigitDelta) {
                    tDigitCount = tDigitCount - pDigits;
                }
                int tValidValue = 0;
                tTemp.copyFrom(tDivider);
                tTemp.mul(tFastCoef);
                for (int i = 1; i <= tDigitCount; i++) {
                    tTemp.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.addAt(1, 0);
                    if (tTemp.greaterThan(tRemainder)) {
                        tTemp.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.removeAt(1);
                        tValidValue = 1;
                        next repeat;
                    }
                }
                if (!tValidValue) {
                    tTemp2.copyFrom(tDivider);
                    tTemp2.mul(tFastCoef - 1);
                    int tTemp2IntLength = getIntLength(tTemp2);
                    if ((tTemp2IntLength + tDividerIntLength) > tRemainderIntLength) {
                        tDigitCount = tDigitCount - pDigits;
                    }
                    tTemp2.copyFrom(tDivider);
                    tTemp2.mul(tFastCoef - 1);
                    for (int i = 1; i <= tDigitCount; i++) {
                        tTemp2.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.addAt(1, 0);
                    }
                    if (tTemp2.greaterThan(tRemainder)) {
                        tFastCoef = tFastCoef - 1;
                        tValidValue = 1;
                    }
                }
                if (!tValidValue) {
                    tTemp.copyFrom(tDivider);
                    tTemp.mul(tFastCoef + 1);
                    for (int i = 1; i <= tDigitCount; i++) {
                        if (tDividerDigits == pDigits) {
                            tTempInt = tRemainder.getIntValue(tRemainderDigits - tDividerDigits + 1);
                        } else {
                            tTempString = "";
                            for (int j = 0; j < tDividerDigits; j++) {
                                int tIndex = tRemainderDigits - tDividerDigits + j + 1;
                                if (tIndex <= tRemainderDigits) {
                                    tTempString += Integer.toString(tRemainder.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.get(tIndex));
                                }
                            }
                            tTempInt = Integer.parseInt(tTempString);
                        }
                        tFastCoef = tTempInt / tDividerInt;
                        tTemp.copyFrom(tDivider);
                        tTemp.mul(tFastCoef);
                        tAddCountNew = tTemp.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size() - tRemainderDigits;
                        if (tAddCountNew > 0) {
                            int tTempCount = tAddCountNew - tAddCount;
                            for (int j = 1; j <= tTempCount; j++) {
                                tResultData.add(0);
                            }
                            tAddCount = tAddCountNew;
                        }
                        if (tAddCount > 0) {
                            for (int j = 1; j <= tAddCount; j++) {
                                tTemp.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.addAt(1, 0);
                            }
                        }
                        boolean tValidValue = false;
                        if (!tTemp.greaterThan(tRemainder)) {
                            if (tAddCountNew == tAddCount) {
                                tFastCoef--;
                                tValidValue = true;
                            } else {
                                tTemp2.copyFrom(tDivider);
                                tTemp2.mul(tFastCoef);
                                for (int j = 1; j <= tAddCount - 1; j++) {
                                    tTemp2.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.addAt(1, 0);
                                }
                                if (tTemp2.greaterThan(tTemp)) {
                                    tAddCount--;
                                } else {
                                    tFastCoef--;
                                    tValidValue = true;
                                }
                            }
                        } else {
                            tAddCount--;
                        }
                        if (!tValidValue) {
                            tTemp.copyFrom(tDivider);
                            tTemp.mul(tFastCoef);
                            for (int j = 1; j <= tAddCount; j++) {
                                tTemp.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.addAt(1, 0);
                            }
                        }
                        tResultData.add(tFastCoef);
                        tRemainder = tRemainder.dif(tTemp);
                        if (tRemainder.isZero()) {
                            for (int j = 1; j <= tAddCount; j++) {
                                tResultData.add(0);
                            }
                            break;
                        }
                        if (!tRemainder.lessThan(tDivider)) {
                            tFastCoef = tFastCoef + 1;
                            tTemp.copyFrom(tDivider);
                            tTemp.add(tDivider);
                            while (tTemp.lessThan(tRemainder)) {
                                tFastCoef = tFastCoef + 1;
                                tTemp.add(tDivider);
                            }
                            tTemp.sub(tDivider);
                            int tAddCountNew = tRemainder.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size() - tTemp.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.size();
                            if (tAddCountNew > tAddCount) {
                                tAddCount = tAddCountNew;
                            }
                            tValidValue = 0;
                            if (!tTemp.greaterThan(tRemainder)) {
                                if (tAddCountNew == tAddCount) {
                                    tFastCoef = tFastCoef - 1;
                                    tValidValue = 1;
                                } else {
                                    tTemp2.copyFrom(tDivider);
                                    tTemp2.mul(tFastCoef);
                                    for (int i = 1; i <= tAddCount - 1; i++) {
                                        tTemp2.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.addAt(0, 0);
                                    }
                                    if (tTemp2.greaterThan(tTemp)) {
                                        tAddCount = tAddCount - 1;
                                    } else {
                                        tFastCoef = tFastCoef - 1;
                                        tValidValue = 1;
                                    }
                                }
                            } else {
                                tAddCount = tAddCount - 1;
                            }
                            if (tValidValue == 0) {
                                tTemp.copyFrom(tDivider);
                                tTemp.mul(tFastCoef);
                                for (int i = 1; i <= tAddCount; i++) {
                                    tTemp.pData_NxIhNARqldyJyY2PfT03dK8t9OLUR.addAt(0, 0);
                                }
                            }
                        }
*/
}