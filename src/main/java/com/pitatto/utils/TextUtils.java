package com.pitatto.utils;

public final class TextUtils {
    /**
     * 指定された String が null または空文字列かどうかを返します。
     * 
     * @param value
     *            チェックする String
     * @return null または空文字列かどうか。null または空文字列なら true 、それ以外なら false 。
     */
    public static boolean isEmpty(String value) {

        if (value == null || value.length() == 0)
            return true;
        else
            return false;
    }

    /**
     * バイトを16進数に変更します。
     * 
     * @param bytes
     *            変換したいbyte配列
     * @return 変換されたStringオブジェクト
     */
    public static String bytesToHexString(final byte[] bytes) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            final String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
