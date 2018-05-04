package com.lbd.filesystem.common.utils;

import com.lbd.filesystem.common.constant.Constants;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//import org.apache.tools.zip.ZipFile;

/**
 *
 * @company: qishon
 * @author zhanghua.luo
 * @date: 2016年4月11日 下午1:07:07
 * @Description: 文件压缩工具类
 */
public class CompressionFilesUtils {
	/**
	 * 多文件压缩
	 *
	 * <pre>
	 *    Example :
	 *    ZipOutputStream zosm = new ZipOutputStream(new FileOutputStream(&quot;c:/b.zip&quot;));
	 *    zipFiles(zosm, new File(&quot;c:/com&quot;), &quot;&quot;);
	 *    zosm.close();
	 * </pre>
	 *
	 * @param zosm
	 * @param file
	 * @param basePath
	 * @throws IOException
	 */
	public static void compressionFiles(ZipOutputStream zosm, File file, String basePath) {
		if (file.isDirectory()) {
			final File[] files = file.listFiles();
			try {
				zosm.putNextEntry(new ZipEntry(basePath + Constants.SLASH));
			} catch (IOException e) {
				e.printStackTrace();
			}
			basePath = basePath + (basePath.length() == 0 ? "" : "/") + file.getName();
			if(files != null && files.length > 0 ) {
				for (File f : files) {
					if(f !=null) {
						compressionFiles(zosm, f, basePath);
					}
				}
			}
		} else {
			FileInputStream fism = null;
			BufferedInputStream bism = null;
			try {
				byte[] bytes = new byte[1024];
				fism = new FileInputStream(file);
				bism = new BufferedInputStream(fism, 1024);
				basePath = basePath + (basePath.length() == 0 ? "" : "/") + file.getName();
				zosm.putNextEntry(new ZipEntry(basePath));
				int count;
				while ((count = bism.read(bytes, 0, 1024)) != -1) {
					zosm.write(bytes, 0, count);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bism != null) {
					try {
						bism.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fism != null) {
					try {
						fism.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void compressionFiles(ZipOutputStream zosm, File file, String basePath, String fileName ) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			try {
				zosm.putNextEntry(new ZipEntry(basePath + "/"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			basePath = basePath + (basePath.length() == 0 ? "" : "/") + fileName;
			for (File f : files) {
				compressionFiles(zosm, f, basePath);
			}
		} else {
			FileInputStream fism = null;
			BufferedInputStream bism = null;
			try {
				byte[] bytes = new byte[1024];
				fism = new FileInputStream(file);
				bism = new BufferedInputStream(fism, 1024);
				basePath = basePath + (basePath.length() == 0 ? "" : "/") + fileName;
				zosm.putNextEntry(new ZipEntry(basePath));
				int count;
				while ((count = bism.read(bytes, 0, 1024)) != -1) {
					zosm.write(bytes, 0, count);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bism != null) {
					try {
						bism.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fism != null) {
					try {
						fism.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 *
	 * @param zosm
	 * @param files
	 * @param basePath
	 */

	public static void compressionFiles(ZipOutputStream zosm, File[] files, String basePath , String[] fileNames) {
		for (int i=0; i<files.length;i++) {
			File f = files[i];
			compressionFiles(zosm, f, "",fileNames[i]);
		}
	}

	/**
	 * 解压缩zip文件
	 *
	 * @param zipFileName
	 *            压缩文件
	 * @param extPlace
	 *            解压的路径
	 */
	public static boolean decompressionZipFiles(String zipFileName, String extPlace) {
		boolean flag = false;
		try {
			unZip(zipFileName,extPlace);
			flag = true;
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return flag;
// java.util.zip.ZipInputStream in = null;
// java.util.zip.ZipEntry entry = null;
// FileOutputStream os = null;
// try {
// in = new java.util.zip.ZipInputStream(new FileInputStream(zipFileName));
// while ((entry = in.getNextEntry()) != null) {
// String entryName = entry.getName();
// int end = entryName.lastIndexOf("/");
// String name = "";
// if (end != -1) {
// name = entryName.substring(0, end);
// }
// File file = new File(extPlace + name);
// if (!file.exists()) {
// file.mkdirs();
// }
// if (entry.isDirectory()) {
// in.closeEntry();
// continue;
// } else {
// os = new FileOutputStream(extPlace + entryName);
// byte[] buf = new byte[1024];
// int len;
// while ((len = in.read(buf)) > 0) {
// os.write(buf, 0, len);
// }
// in.closeEntry();
// }
// }
// flag = true;
// } catch (FileNotFoundException e1) {
// flag = false;
// e1.printStackTrace();
// } catch (IOException e1) {
// flag = false;
// e1.printStackTrace();
// } finally {
// if (in != null) {
// try {
// in.close();
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
// if (os != null) {
// try {
// os.close();
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
// }
	}


	private static void getDir(String directory, String subDirectory){
		String dir[];
		File fileDir = new File(directory);
		try {
			if ("".equals(subDirectory) && !fileDir.exists())
				fileDir.mkdir();
			else if (!"".equals(subDirectory)) {
				dir = subDirectory.replace('\\', '/').split("/");
				for (int i = 0; i < dir.length; i++) {
					File subFile = new File(directory + File.separator + dir[i]);
					if (subFile.exists() == false)
						subFile.mkdir();
					directory += File.separator + dir[i];
				}
			}
		}catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	/**
	 *
	 * @param zipFileNaame
	 *            being unzip file including file name and path ;
	 * @param outputDirectory
	 *            unzip files to this directory
	 *
	 */
	public static  void unZip(String zipFileName, String outputDirectory){
//		try {
//			ZipFile zipFile = new ZipFile(zipFileName);
//			Enumeration<ZipEntry> e = zipFile.getEntries();
//			ZipEntry zipEntry = null;
//			getDir(outputDirectory, "");
//			while (e.hasMoreElements()) {
//				zipEntry = (ZipEntry) e.nextElement();
//				// System.out.println("unziping " + zipEntry.getName());
//				if (zipEntry.isDirectory()) {                // 如果得到的是个目录，
//					String name = zipEntry.getName();         // 就创建在指定的文件夹下创建目录
//					name = name.substring(0, name.length() - 1);
//					File f = new File(outputDirectory + File.separator + name);
//					f.mkdir();
//					// System.out.println("创建目录：" + outputDirectory + File.separator
//					// + name);
//				}
//				else {
//					String fileName = zipEntry.getName();
//					fileName = fileName.replace('\\', '/');
//					// System.out.println("测试文件1：" +fileName);
//					if (fileName.indexOf("/") != -1){
//						getDir(outputDirectory,
//								fileName.substring(0, fileName.lastIndexOf("/")));
//						// System.out.println("文件的路径："+fileName);
//						fileName=fileName.substring(fileName.lastIndexOf("/")+1,fileName.length());
//
//					}
//
//					File f = new File(outputDirectory + File.separator + zipEntry.getName());
//
//					f.createNewFile();
//					InputStream in = zipFile.getInputStream(zipEntry);
//					FileOutputStream out=new FileOutputStream(f);
//
//					byte[] by = new byte[1024];
//					int c;
//					while ( (c = in.read(by)) != -1) {
//						out.write(by, 0, c);
//					}
//					out.close();
//					in.close();
//				}
//			}
//		}catch (Exception ex) {
//			System.out.println(ex.getMessage());
//		}

	}
	/**
	 * this mothed will unzip all the files which in your specifeid folder;
	 *
	 * @param filesFolder
	 * @param outputDirectory
	 */
	public static  void unzipFiles(String filesFolder , String outputDirectory){
		File zipFolder = new File(filesFolder);
		String zipFiles[];
		String zipFileAbs;
		try{
			zipFiles=zipFolder.list();
			for(int i=0;i<zipFiles.length;i++){
				if(zipFiles[i].length()==(zipFiles[i].lastIndexOf(".zip")+4)){// 判断是不是zip包
					zipFileAbs=filesFolder+ File.separator+zipFiles[i];
					unZip(zipFileAbs,outputDirectory);
				}
			}
		}catch (SecurityException ex){
			ex.printStackTrace();
		}

	}




}
