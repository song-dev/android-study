package com.song.androidstudy.testcrypto;

import com.song.androidstudy.crypto.HexBinDecOctUtils;

import org.junit.Test;

public class HexStringTest {

    @Test
    public void testBytesToHex() {

        String bfKey = "Yht1B4qsgEc0kB4CptWBm5pisvTkJZJY";
        String aesIv = "mrO3QzCLG31ViYzQ";
        String bfKeyHex = HexBinDecOctUtils.bytesToHex(bfKey.getBytes());
        System.out.println("bfKeyHex: " + bfKeyHex + "  bfKeyHex length: " + bfKeyHex.length());

        String aesIvHex = HexBinDecOctUtils.bytesToHex(aesIv.getBytes());
        System.out.println("aesIvHex: " + aesIvHex + "  aesIvHex length: " + aesIvHex.length());

    }

}
