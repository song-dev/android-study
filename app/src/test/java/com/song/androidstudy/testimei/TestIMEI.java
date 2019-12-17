package com.song.androidstudy.testimei;

import org.junit.Test;

public class TestIMEI {

    @Test
    public void test_imei() {

        System.out.println(isIMEI("866696022549032"));

    }

    private boolean isIMEI(String imei) {
        char[] imeiChar = imei.toCharArray();
        int resultInt = 0;
        for (int i = 0; i < imeiChar.length - 1; i++) {
            int a = Integer.parseInt(String.valueOf(imeiChar[i]));
            i++;
            final int temp = Integer.parseInt(String.valueOf(imeiChar[i])) * 2;
            final int b = temp < 10 ? temp : temp - 9;
            resultInt += a + b;
        }
        resultInt %= 10;
        resultInt = resultInt == 0 ? 0 : 10 - resultInt;
        int crc = Integer.parseInt(String.valueOf(imeiChar[14]));
        return (resultInt == crc);
    }
}
