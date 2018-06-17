package com.medbox.medbox.ui.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Sephiroth on 17/3/5.
 */
public class ResponsePaser {

    public static String getString(InputStream inStream) throws Exception {
        byte[] data = getByte(inStream);
        // 转化为字符串
        return new String(data, "UTF-8");
    }

    // 将输入流转化为byte型
    public static byte[] getByte(InputStream inStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inStream.close();
        return outputStream.toByteArray();
    }
}
