package com.lbd.filesystem.common.utils;

import com.lbd.filesystem.common.constant.Constants;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @company: qishon
 * @author zhiheng.li
 * @date:2016年2月25日 下午5:04:16
 * @Description:
 */
@Component("imageZipUtils")
public class ImageZipUtils {

	/**
	 * 等比例压缩图片文件<br>
	 * 先保存原文件，再压缩、上传
	 * 
	 * @param oldFile
	 *            要进行压缩的文件
	 * @param newFile
	 *            新文件
	 * @param width
	 *            宽度 //设置宽度时（高度传入0，等比例缩放）
	 * @param height
	 *            高度 //设置高度时（宽度传入0，等比例缩放）
	 * @param quality
	 *            质量
	 * @return 返回压缩后的文件的全路径
	 */
	public String zipImageFile(File oldFile, File newFile, int width, int height, float quality) {
		if (oldFile == null) {
			return null;
		}
		try {
			/** 对服务器上的临时文件进行处理 */
			final Image srcFile = ImageIO.read(oldFile);
			final int w = srcFile.getWidth(null);
			// System.out.println(w);
			final int h = srcFile.getHeight(null);
			// System.out.println(h);
			double bili;
			if (width > 0) {
				bili = width / (double) w;
				height = (int) (h * bili);
			} else {
				if (height > 0) {
					bili = height / (double) h;
					width = (int) (w * bili);
				}
			}
			/** 宽,高设定 */
			final BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);

			/** 压缩之后临时存放位置 */
			final FileOutputStream out = new FileOutputStream(newFile);
			/** 压缩质量 */
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newFile.getAbsolutePath();
	}
    /***
     * 将图片缩放到指定的高度或者宽度
     * @param sourceImagePath 图片源地址
     * @param destinationPath 压缩完图片的地址
     * @param width 缩放后的宽度
     * @param height 缩放后的高度
     * @param auto 是否自动保持图片的原高宽比例
     * @param format 图图片格式 例如 jpg
     */
    public static void scaleImageWithParams(File sourceImagePath,
                                            File destinationPath, int width, int height, boolean auto, String format) {

        try {
            File file = sourceImagePath;
            BufferedImage bufferedImage = null;
            bufferedImage = ImageIO.read(file);
            if (auto) {
                /*ArrayList<Integer> paramsArrayList = getAutoWidthAndHeight(bufferedImage,width,height);
                width = paramsArrayList.get(0);
                height = paramsArrayList.get(1);
                System.out.println("自动调整比例，width="+width+" height="+height); */
            }

            Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = outputImage.getGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            ImageIO.write(outputImage, format, destinationPath);
        } catch (Exception e) {
            System.out.println("scaleImageWithParams方法压缩图片时出错了");
            e.printStackTrace();
        }
    }
    /**
	 * 按宽度高度压缩图片文件<br>
	 * 先保存原文件，再压缩、上传
	 * 
	 * @param oldFile
	 *            要进行压缩的文件全路径
	 * @param newFile
	 *            新文件
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @return 返回压缩后的文件的全路径
	 */
	public static void zipWidthHeightImageFile(File oldFile, File newFile, int width, int height) throws IOException {
        try {
            /** 对服务器上的临时文件进行处理 */
            BufferedImage bi2 = ImageIO.read(oldFile);
            //获得文件类型
            String suffix = StringUtils.substringAfterLast(oldFile.getName(), ".").trim().toUpperCase();
            /**
             * 处理 png ， gif背景变黑的问题
             */
            if ("PNG".equals(suffix) || "GIF".equals(suffix)) {
                /** 宽,高设定 */
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = tag.createGraphics();
                tag = g2d.getDeviceConfiguration().createCompatibleImage(width,height, Transparency.TRANSLUCENT);
                g2d.dispose();
                g2d = tag.createGraphics();
                Image from = bi2.getScaledInstance(width, height, bi2.SCALE_AREA_AVERAGING);
                g2d.drawImage(from, 0, 0, null);
                g2d.dispose();
                ImageIO.write(tag, suffix, newFile);
            } else {
                // 高质量压缩，其实对清晰度而言没有太多的帮助
//            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            tag.getGraphics().drawImage(bufferedImage, 0, 0, width, height, null);
//
//            FileOutputStream out = new FileOutputStream(newPath);    // 将图片写入 newPath
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
//            jep.setQuality(1f, true);    //压缩质量, 1 是最高值
//            encoder.encode(tag, jep);
//            out.close();

                BufferedImage newImage = new BufferedImage(width, height, bi2.getType());
                Graphics g = newImage.getGraphics();
                g.drawImage(bi2, 0, 0, width, height, null);
                g.dispose();
                ImageIO.write(newImage, suffix, newFile);
            }

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> compressPictures(String typeStr, File saveDirectory, String saveFileName, String saveServicePath, String fileSource)
			throws NoSuchAlgorithmException, IOException {
		final List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if (typeStr != null && !"".equals(typeStr)) {
			final String[] typeStrs = typeStr.trim().split(",");
			for (int i = 0; i < typeStrs.length; i++) {
				final Map<String, Object> dataMap = new HashMap<String, Object>();
				if (typeStrs.length > 0) {
					final int x = Integer.parseInt(typeStrs[i].trim().split("\\*")[0]);
					final int y = Integer.parseInt(typeStrs[i].trim().split("\\*")[1]);
					final String fileName = FilenameUtils.getBaseName(saveFileName) + "_" + x + "x" + y + "."
							+ FilenameUtils.getExtension(saveFileName);
					final String newFileName = new String(fileName.getBytes(), "utf-8");
					final File oldFile = new File(saveDirectory, saveFileName);
					final File zipName = new File(saveDirectory + Constants.SLASH + newFileName);
					this.zipWidthHeightImageFile(oldFile, zipName, x, y);
					//2015/7/15增加对压缩后文件md5和大小的计算
					final FileInputStream fis = new FileInputStream(zipName);
					final int byteBufferSize = 1024 * 100;
					final ByteBuffer bf = ByteBuffer.allocate(byteBufferSize);
					final byte[] bytes = bf.array();
					final MessageDigest md5 = MessageDigest.getInstance("MD5");
					String serviceFileMd5 = "";
                    int b = -1;
                    while ((b = fis.read(bytes)) != -1) {
                        bf.flip();
                        md5.update(bytes, 0, b);
                        bf.clear();
						serviceFileMd5 = new String(Hex.encodeHex(md5.digest()));
						dataMap.put("fileMd5", serviceFileMd5);
					}
					FileChannel fc = fis.getChannel();
					dataMap.put("fileSize", fc.size());
                    fis.close();
                    //
					dataMap.put("fileSource", fileSource);
					dataMap.put("filePath", saveServicePath);
					dataMap.put("fileName", newFileName);
					dataList.add(dataMap);
				}
			}
			return dataList;
		} // end压缩图片
		return dataList;
	}

    public String compressPicturesForDownload(String typeStr, File saveDirectory, String saveFileName, String oldFilePath)
            throws Exception {
        File zipName = null;
        if (typeStr != null && !"".equals(typeStr)) {
            final String[] typeStrs = typeStr.trim().split(",");
            for (int i = 0; i < typeStrs.length; i++) {
                if (typeStrs.length > 0) {
                    final int x = Integer.parseInt(typeStrs[i].trim().split("\\*")[0]);
                    final int y = Integer.parseInt(typeStrs[i].trim().split("\\*")[1]);
                    final String fileName = FilenameUtils.getBaseName(saveFileName) + "_" + x + "x" + y + "."
                            + FilenameUtils.getExtension(saveFileName);
                    final String newFileName = new String(fileName.getBytes(), "utf-8");
                    final File oldFile = new File(oldFilePath);
                    zipName = new File(saveDirectory + Constants.SLASH + newFileName);
                    if (!zipName.canExecute()) {
                        this.zipWidthHeightImageFile(oldFile, zipName, x, y);
                    }
                    //2015/7/15增加对压缩后文件md5和大小的计算
                    final FileInputStream fis = new FileInputStream(zipName);
                    final int byteBufferSize = 1024 * 100;
                    final ByteBuffer bf = ByteBuffer.allocate(byteBufferSize);
                    final byte[] bytes = bf.array();
                    final MessageDigest md5 = MessageDigest.getInstance("MD5");
                    String serviceFileMd5 = "";
                    int b = -1;
                    while ((b = fis.read(bytes)) != -1) {
                        bf.flip();
                        md5.update(bytes, 0, b);
                        bf.clear();
                        serviceFileMd5 = new String(Hex.encodeHex(md5.digest()));
                    }
                    fis.close();
                }
            }
            return zipName.getPath();
        } // end压缩图片
        return null;
    }

}
