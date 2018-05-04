package com.lbd.filesystem.common.utils;

import com.lbd.filesystem.common.configuration.SystemConfigProperties;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
*
* @company: qishon
* @author zhanghua.luo
* @date: 2016年4月11日 下午1:05:33
* @Description: 文件上传Service实现类
*/
@Component
public class FileUtils {

	@Autowired
	private SystemConfigProperties systemConfigProperties;

	/**
	 * @Description 新建文件夹
	 * @param
	 */
	public static void mkDir(Path path) throws IOException {
		if (Files.notExists(path)) {
			Files.createDirectories(path);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getDefaultSaveDir() {
         final DateFormat format = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
         final String date = format.format(new Date());
         final String[] dateArr = date.split(":");
         String path = "";
         for (String s : dateArr) {
             path += File.separator + s;
         }
         return path;
	}
	
	/**
	 * 使用Nio方式保存文件
	 * @param uploadFile
	 * @param saveFile
	 * @param md5
	 * @return
	 * @throws Exception
	 */
	public static String saveFileByNio(MultipartFile uploadFile, File saveFile, MessageDigest md5) throws Exception {
		final InputStream in = uploadFile.getInputStream();
		final BufferedInputStream bis = new BufferedInputStream(in);
		final FileOutputStream out = new FileOutputStream(saveFile);
		final FileChannel outChannel = out.getChannel();
		final int byteBufferSize = 1024 * 100;
		ByteBuffer bf = ByteBuffer.allocate(byteBufferSize);
		byte[] bytes = new byte[1024 * 100];
		try {
			int b = -1;
			while ((b = bis.read(bytes)) != -1) {
                bf.flip();
                outChannel.write(ByteBuffer.wrap(bytes,0 , b));
                md5.update(bytes, 0, b);
                bf.clear();
			}
            final String serviceFileMd5 = new String(Hex.encodeHex(md5.digest()));
            return serviceFileMd5;
		} catch (Exception e) {
			throw e;
		} finally {
			out.close();
			bis.close();
			in.close();
		}

	}
	
}
