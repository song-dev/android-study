package com.song.androidstudy.crypto;

/**
 * 进制转换工具类
 */
public class HexBinDecOctUtils {

    private final static char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 将byte数组转换为十六进制文本
     *
     * @param buf
     * @return
     */
    public static String bytesToHex(byte[] buf) {
        if (buf == null || buf.length == 0) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            out.append(HEX[(buf[i] >> 4) & 0x0f]).append(HEX[buf[i] & 0x0f]);
//            out.append(' ');
        }
        return out.toString();
    }

    /**
     * 将十六进制文本转换为byte数组
     *
     * @param buf
     * @return
     */
    public static byte[] hexToBytes(String buf) {
        if (buf == null) {
            return null;
        }
        char[] hex = buf.toCharArray();
        int length = hex.length / 2;
        byte[] raw = new byte[length];
        for (int i = 0; i < length; i++) {
            int high = Character.digit(hex[i * 2], 16);
            int low = Character.digit(hex[i * 2 + 1], 16);
            int value = (high << 4) | low;
            if (value > 127)
                value -= 256;
            raw[i] = (byte) value;
        }
        return raw;
    }

}
