package com.lbd.filesystem.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** 
 * MD5验证工具 
 *  
 * @author victor 
 */ 
public class MD5Utils {
	/**
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合 
     */
    private static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    
    private static MessageDigest messagedigest;
    
    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
  
    /**
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(File file) throws IOException {
        final InputStream fis;
        fis = new FileInputStream(file);
        final int a = 1024;
        final byte[] buffer = new byte[a];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(messagedigest.digest());
    }
    
    /**
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static String getMultipartFileMd5String(MultipartFile file) throws IOException {
    	final InputStream fis;
        fis = file.getInputStream();
        final int a = 1024;
        final byte[] buffer = new byte[a];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(messagedigest.digest());
    }
  
    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }
  
    private static String bufferToHex(byte[] bytes, int m, int n) {
        final StringBuffer stringbuffer = new StringBuffer(2 * n);
        final int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }
  
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        final char c0 = hexDigits[(bt & 0xf0) >> 4]; // 取字节中高 4 位的数字转换
        // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同 
        final char c1 = hexDigits[bt & 0xf]; // 取字节中低 4 位的数字转换
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
