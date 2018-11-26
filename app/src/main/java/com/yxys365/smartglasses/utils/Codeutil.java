package com.yxys365.smartglasses.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class Codeutil {
    private static String hexString = "0123456789ABCDEF";

    /*
     * 将字符串换为16进制
     */
    public static String StringTohexString(String str) {
        // 根据默认编码获取字节数组
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    /*
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     */
    public static String hexStringToString(String bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                bytes.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
                    .indexOf(bytes.charAt(i + 1))));
        return new String(baos.toByteArray());
    }

    /*
     * 把16进制字符串转换成字节数组
     */
    public static byte[] hexStringToByte(String hex) {
        hex=hex.toUpperCase();
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) hexString.indexOf(c);
        return b;
    }

    /*
     * 数组转换成十六进制字符串
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return "null";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
//            Log.e("aaa","转成16进制的结果："+sb.toString());
        }
        return sb.toString();
    }

    public final static String qpDecoding(String str) {
        if (str == null) {
            return "";
        }
        try {
            StringBuffer sb = new StringBuffer(str);
            for (int i = 0; i < sb.length(); i++) {
                if (sb.charAt(i) == '\n' && sb.charAt(i - 1) == '=') {
                    // 解码这个地方也要修改一下
                    // sb.deleteCharAt(i);
                    sb.deleteCharAt(i - 1);
                }
            }
            str = sb.toString();
            byte[] bytes = str.getBytes("US-ASCII");
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                if (b != 95) {
                    bytes[i] = b;
                } else {
                    bytes[i] = 32;
                }
            }
            if (bytes == null) {
                return "";
            }
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            for (int i = 0; i < bytes.length; i++) {
                int b = bytes[i];
                if (b == '=') {
                    try {
                        int u = Character.digit((char) bytes[++i], 16);
                        int l = Character.digit((char) bytes[++i], 16);
                        if (u == -1 || l == -1) {
                            continue;
                        }
                        buffer.write((char) ((u << 4) + l));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                } else {
                    buffer.write(b);
                }
            }
            return new String(buffer.toByteArray(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    static double bb_left = 0;
    static double bb_right = 0;
    static double bb_top = 0;
    static double bb_bottom = 0;
    static double last_bb_left = 0;
    static double last_bb_right = 0;
    static double last_bb_top = 0;
    static double last_bb_bottom = 0;

    public static long last_lost_target_status_time = 0;


    public static byte[] toPrimitives(Byte[] oBytes) {

        byte[] bytes = new byte[oBytes.length];
        for (int i = 0; i < oBytes.length; i++) {
            bytes[i] = oBytes[i];
        }
        return bytes;

    }


    //byte[] to Byte[]
    public static Byte[] toObjects(byte[] bytesPrim) {

        Byte[] bytes = new Byte[bytesPrim.length];
        int i = 0;
        for (byte b : bytesPrim) bytes[i++] = b; //Autoboxing
        return bytes;

    }

    public static byte[] convertCoordinate(int[] bb_ret, int previewWidth, int previewHeight, int front_cam) {//1920 1080
        int left_offset = previewWidth / 2;
        int top_offset = previewHeight / 2;
        int limit = 1000;


        if (bb_ret[4] == 0 || bb_ret[0] > 1920 || bb_ret[0] < 0 || bb_ret[1] > 1080 || bb_ret[1] < 0) {
            bb_ret[0] = left_offset - 1;
            bb_ret[1] = top_offset + 1;
            last_lost_target_status_time = 0;

            last_bb_left = 0;
            last_bb_right = 0;
            last_bb_top = 0;
            last_bb_bottom = 0;
        } else
            last_lost_target_status_time++;


        bb_left = (bb_ret[0] - left_offset) <= 0 ? (float) (-(bb_ret[0] - left_offset) * 1.7) : 0;
        bb_right = (bb_ret[0] - left_offset) >= 0 ? (float) ((bb_ret[0] - left_offset) * 1.7) : 0;
        bb_top = (bb_ret[1] - top_offset) <= 0 ? (float) (-(bb_ret[1] - top_offset) * 1.5) : 0;
        bb_bottom = (bb_ret[1] - top_offset) >= 0 ? (float) ((bb_ret[1] - top_offset) * 1.5) : 0;


        if (bb_left > limit) bb_left = limit;
        else if (bb_left < -limit) bb_left = -limit;

        if (bb_right > limit) bb_right = limit;
        else if (bb_right < -limit) bb_right = -limit;

        if (bb_top > limit) bb_top = limit;
        else if (bb_top < -limit) bb_top = -limit;

        if (bb_bottom > limit) bb_bottom = limit;
        else if (bb_bottom < -limit) bb_bottom = -limit;

        if (last_lost_target_status_time < 100) {
            bb_left = bb_left * 0.2 + last_bb_left * 0.8;
            bb_right = bb_right * 0.2 + last_bb_right * 0.8;
            bb_top = (float) bb_top * 0.2 + (float) last_bb_top * 0.8;
            bb_bottom = (float) bb_bottom * 0.2 + (float) last_bb_bottom * 0.8;


            last_bb_left = bb_left;
            last_bb_right = bb_right;
            last_bb_top = bb_top;
            last_bb_bottom = bb_bottom;

            int refind_limit = 400;
            if (bb_left > refind_limit) bb_left = refind_limit;
            else if (bb_left < -refind_limit) bb_left = -refind_limit;

            if (bb_right > refind_limit) bb_right = refind_limit;
            else if (bb_right < -refind_limit) bb_right = -refind_limit;

            if (bb_top > refind_limit) bb_top = refind_limit;
            else if (bb_top < -refind_limit) bb_top = -refind_limit;

            if (bb_bottom > refind_limit) bb_bottom = refind_limit;
            else if (bb_bottom < -refind_limit) bb_bottom = -refind_limit;


        } else
            last_lost_target_status_time = 300;

        int temp1 = bb_ret[0] - left_offset;
        int temp2 = -(bb_ret[1] - top_offset);


        Log.i("", "convert:" + bb_left + " " + bb_right + " " + bb_top + " " + bb_bottom + " " + temp1 + " " + temp2);
        byte[] data = new byte[12];
        data[0] = (byte) 0xAA;
        data[1] = (byte) 0x57;
        data[2] = (byte) 0x09;
        byte sum_check = 0;
        data[3] = (byte) ((int) bb_right >> 8);
        data[4] = (byte) ((int) bb_right & 0xFF);

        data[7] = (byte) ((int) bb_left >> 8);
        data[8] = (byte) ((int) bb_left & 0xFF);

        if (front_cam == 1) {
            data[3] = (byte) ((int) bb_right >> 8);
            data[4] = (byte) ((int) bb_right & 0xFF);

            data[5] = (byte) ((int) bb_bottom >> 8);
            data[6] = (byte) ((int) bb_bottom & 0xFF);

            data[7] = (byte) ((int) bb_left >> 8);
            data[8] = (byte) ((int) bb_left & 0xFF);


            data[9] = (byte) ((int) bb_top >> 8);
            data[10] = (byte) ((int) bb_top & 0xFF);


        } else {
            data[3] = (byte) ((int) bb_left >> 8);
            data[4] = (byte) ((int) bb_left & 0xFF);

            data[5] = (byte) ((int) bb_top >> 8);
            data[6] = (byte) ((int) bb_top & 0xFF);

            data[7] = (byte) ((int) bb_right >> 8);
            data[8] = (byte) ((int) bb_right & 0xFF);

            data[9] = (byte) ((int) bb_bottom >> 8);
            data[10] = (byte) ((int) bb_bottom & 0xFF);
        }

        for (int j = 0; j < 8; j++) {
            sum_check += data[3 + j];
        }
        data[11] = (byte) (sum_check & 0xFF);
        return data;
    }


//    public static float byte2float(byte[] b) {
//        ByteBuffer buf = ByteBuffer.allocateDirect(4);
//        buf.put(b);
//        buf.rewind();
//        float f2 = buf.getFloat();
//        return f2;
//    }

    public static long bytes2Long(byte[] bb) {
        return paraFix((((long) bb[3] & 0xff) << 24)
                | (((long) bb[2] & 0xff) << 16)
                | (((long) bb[1] & 0xff) << 8)
                | (((long) bb[0] & 0xff) << 0));
    }

    public static float byte2float(byte[] b) {
        int l;
        l = b[0];
        l &= 0xff;
        l |= ((long) b[1] << 8);
        l &= 0xffff;
        l |= ((long) b[2] << 16);
        l &= 0xffffff;
        l |= ((long) b[3] << 24);
        return paraFix(Float.intBitsToFloat(l));
    }

    public static long paraFix(long raw) {
        if (raw > 65535) {
            raw = 4294967295l - raw;
        }
        return raw;
    }

    public static float paraFix(float raw) {
        if (raw > 65535) {
            raw = 4294967295l - raw;
        }
        return raw;
    }

    public static byte[] mergeTwoArray(byte[] str1, byte[] str2) {
        int str1Length = str1.length;
        int str2length = str2.length;
        str1 = Arrays.copyOf(str1, str1Length + str2length);//数组扩容
        System.arraycopy(str2, 0, str1, str1Length, str2length);
        return str1;
    }

    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(src, begin, bs, 0, count);
        return bs;
    }

    public static int getFirstIndexInArr(byte b, byte[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == b) {
                return i;
            }
        }
        return -1;
    }

    public static boolean checkSum(byte[] cmd, int length) {
        byte[] data = subBytes(cmd, 3, length - 1);
        byte sum = 0;
        for (byte b : data) {
            sum += b;
        }
        if (sum == cmd[cmd.length - 1]) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取和校验
     *
     * @return
     */
    public static String getNum(String s) {
        byte num = 0;
        byte[] sum = Codeutil.hexStringToByte(s);
        for (int i = 0; i < sum.length; i++) {
            num += sum[i];
        }
        byte [] sss={num};
//        Log.e(TAG, "num-------" + Codeutil.bytesToHexString(sss));
        return Codeutil.bytesToHexString(sss);
    }
}
