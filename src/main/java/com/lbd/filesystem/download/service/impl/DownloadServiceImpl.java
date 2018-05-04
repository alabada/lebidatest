package com.lbd.filesystem.download.service.impl;

import com.lbd.filesystem.common.annotation.SystemServiceLog;
import com.lbd.filesystem.common.configuration.SystemConfigProperties;
import com.lbd.filesystem.common.constant.Constants;
import com.lbd.filesystem.common.constant.HttpConstants;
import com.lbd.filesystem.common.exception.BasesException;
import com.lbd.filesystem.common.utils.*;
import com.lbd.filesystem.download.message.State;
import com.lbd.filesystem.download.service.DownloadService;
import com.lbd.filesystem.upload.entity.FileMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DownloadServiceImpl implements DownloadService {


	private SystemConfigProperties systemConfigProperties;

	private ImageZipUtils imageZipUtils;

	@Autowired
	public DownloadServiceImpl(SystemConfigProperties systemConfigProperties, ImageZipUtils imageZipUtils) {
		this.systemConfigProperties = systemConfigProperties;
        this.imageZipUtils = imageZipUtils;
	}

	@Override
	@SystemServiceLog
	public void downloadFiles(HttpServletResponse response, HttpServletRequest request, HttpSession session)
			throws Exception {
		final String fileMessages = request.getParameter(Constants.FILE_MESSAGES);
		final List<FileMessage> fileMessageList = this.getListFromJsonArrStr(fileMessages);
		final String root = this.systemConfigProperties.getConfig(Constants.SAVE_PATH);
		// 只下载一个文件
		if (fileMessageList.size() == 1) {
			final FileMessage f = fileMessageList.get(0);
			final Path filePath;
			if (f.getFileSource() != null && !"".equals(f.getFileSource())) {
				filePath = Paths.get(root, f.getFileSource(), f.getFilePath());
			} else {
				filePath = Paths.get(root, f.getFilePath());
			}
			final String myFileName = filePath.toFile().getName();
			// 获取文件类型
			final String openType = myFileName.substring(myFileName.lastIndexOf(Constants.POINT) + Constants.NUM_1);
            // 直接输出文件
			this.prepareWriteFile(request, response, filePath, openType, true);
		} else {
			String saveFileName;
			final Path[] fileUrlArr = new Path[fileMessageList.size()];
			final String[] fileNameArr = new String[fileMessageList.size()];
			for (int i = 0; i < fileMessageList.size(); i++) {
				final FileMessage f = fileMessageList.get(i);
				if (f.getFileSource() != null && !"".equals(f.getFileSource())) {
					fileUrlArr[i] = Paths.get(root, f.getFileSource(), f.getFilePath());
				} else {
					fileUrlArr[i] = Paths.get(root, f.getFilePath());
				}
			}
			final File[] files = new File[fileUrlArr.length];
			for (int i = 0; i < fileUrlArr.length; i++) {
                final Path path = fileUrlArr[i];
				if (Files.notExists(fileUrlArr[i])) {
				    throw new BasesException(State.FILE_NO_FOUNE.getCode(), State.FILE_NO_FOUNE.getMessage());
				}
				files[i] = path.toFile();
				fileNameArr[i] = files[i].getName();
			}

			saveFileName = "【批量下载】" + files[0].getName().split("\\.")[0] + ".zip";
			final Path zipDirectory = Paths.get(this.systemConfigProperties.getConfig(Constants.TEMP_PATH));
            final Path zipPath = Paths.get(zipDirectory.toString(), System.currentTimeMillis() + "_" + saveFileName);
            FileUtils.mkDir(zipDirectory);
			if (Files.notExists(zipPath)) {
				final ZipOutputStream zosm;
				zosm = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(zipPath)));
				CompressionFilesUtils.compressionFiles(zosm, files, "", fileNameArr); // 创建zip文件
				zosm.close();
				final String myFileName = zipPath.toFile().getName();
				// 获取文件类型
				final String openType = myFileName.substring(myFileName.lastIndexOf(Constants.POINT) + Constants.NUM_1);
				this.prepareWriteFile(request, response, zipPath, openType,true);
			    Files.delete(zipPath);
			}
		}
	}

	@Override
	@SystemServiceLog
	public void downloadFile(HttpServletResponse response, HttpServletRequest request, HttpSession session)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		final String isDown = request.getParameter(Constants.IS_DOWN);
		// 文件来源
		final String fileSource = request.getParameter(Constants.FILE_SOURCE);
		//文件路径
		final String filePath =new String(request.getParameter(Constants.FILE_PATH).getBytes("iso8859-1"), "utf-8") ;
		//str参数,2016/9/2日增加
		final String typeStr = request.getParameter(Constants.TYPE_STR);
		// 文件在服务器的路径
		final String root = this.systemConfigProperties.getConfig(Constants.SAVE_PATH);
		Path path;
		if (fileSource != null && !"".equals(fileSource)) {
			path = Paths.get(root, fileSource, filePath);
		} else {
			path = Paths.get(root, filePath);
		}

        if (!Files.exists(path)) {
            throw new BasesException(State.FILE_NO_FOUNE.getCode(), State.FILE_NO_FOUNE.getMessage());
        }
		//如果typeStr存在，则返回压缩后的图片
        if (typeStr != null) {
            String saveFileName = FilenameUtils.getName(path.toString());
            Path saveDirectoryPath =  path.getParent();
            String oldFilePath = path.toString();
            String zipName = imageZipUtils.compressPicturesForDownload(typeStr, saveDirectoryPath.toFile(), saveFileName, oldFilePath);
            if (zipName != null) {
                path = Paths.get(zipName);
            }
        }
        
		final String strTrue = "true";

		final String myFileName = path.toFile().getName();
		// 获取文件类型
		final String openType = myFileName.substring(myFileName.lastIndexOf(Constants.POINT) + Constants.NUM_1);
		//如果文件属于视频，则使用播放视频方法
		if (isDown != null && strTrue.equals(isDown) || !"".equals(isDown) && strTrue.equals(isDown)) {
			// 写出文件
			this.prepareWriteFile(request, response, path, openType, true);
		} else {
            String[] EXTENSION_MEDIA_PERMIT = this.systemConfigProperties.getConfig("EXTENSION_MEDIA_PERMIT").toLowerCase().replaceAll(Constants.BLANK, Constants.EMPTY)
                    .replaceAll(Constants.CHSEMICOLON, Constants.SEMICOLON).split(Constants.SEMICOLON);
			if (Arrays.asList(EXTENSION_MEDIA_PERMIT).contains(openType.toLowerCase())) {
				this.writeVideo(request, response,openType, path);
			} else {
				// 打开文件
				this.prepareWriteFile(request, response, path, openType, false);
			}
		}
	}

	/**
	 * 
	 * @Description FileMessage Json集合转FileMessage集合
	 * @author zhanghua.luo
	 * @return List
	 * @date 2016年4月11日 下午1:18:52
	 */
	private List<FileMessage> getListFromJsonArrStr(String jsonArrStr) throws BasesException {
        final JSONObject jsonObject;
        try {
            jsonObject = JSONObject.fromObject(jsonArrStr);
        } catch (Exception e) {
            throw new BasesException(State.ERROR_PARAM.getCode(), State.ERROR_PARAM.getMessage());
        }
		final String files = jsonObject.getString("files");
		final List<FileMessage> fileMessageList = new ArrayList<>();
		final JSONArray jsonArray = JSONArray.fromObject(files);
		for (Object obj : jsonArray) {
			final JSONObject json = JSONObject.fromObject(obj);
			fileMessageList.add((FileMessage) JSONObject.toBean(json, FileMessage.class));
		}
		return fileMessageList;
	}

	/**
	 * @Description 输出文件
	 * @param request request
	 * @param response response
	 * @param path path
	 * @param download download
	 * @throws Exception 所有异常
	 */
	private void prepareWriteFile(HttpServletRequest request, HttpServletResponse response, Path path, String openType, boolean download)
			throws Exception {
			if (Files.notExists(path)) {
				throw new BasesException(State.FILE_NO_FOUNE.getCode(), State.FILE_NO_FOUNE.getMessage());
			}
			if (download) {
				// 设置没有缓存
				response.reset();
                String range = request.getHeader(HttpConstants.RANGE);
                String browser = request.getHeader(HttpConstants.USER_AGENT);
                byte[] data = getBytesFromFile(path.toFile());
                assert data != null;
                int dataLength = data.length;
                long pos = 0;
                if (null != range) {
                    // 断点续传
                    response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                    if (browser.contains(HttpConstants.EDGE)) {
                        //EDGE浏览器
                        response.setContentLength(dataLength);
                        response.setHeader(HttpConstants.CONTENT_RANGE, range);
                    } else if (browser.contains(HttpConstants.FIREFOX) || browser.contains(HttpConstants.CHROME)) {
                        //火狐
                        response.setContentLength(dataLength - (int)pos);
                        response.setHeader(HttpConstants.ACCEPT_RANGES, Constants.BYTES);
                        String contentRange = Constants.BYTES + Constants.BLANK + pos + Constants.WHIPPLETREE + (data.length - 1) + Constants.SLASH + data.length;
                        response.setHeader(HttpConstants.CONTENT_RANGE, contentRange);
                    } else if (browser.contains(HttpConstants.SAFARI)) {
                        // TODO: 14/10/2016  添加safari的下载
                    }
                    try {
                        pos = Long.parseLong(range.replaceAll(Constants.BYTESEQ, Constants.EMPTY).replaceAll(Constants.WHIPPLETREE, Constants.EMPTY));
                    } catch (NumberFormatException e) {
                        pos = 0;
                    }
                }


				final String encoding = request.getCharacterEncoding();
				final String httpFileName = HttpFileNameUtils.getFileName(browser, encoding, path.toFile().getName());
				// 设置输出的格式
				response.setContentType(HttpConstants.DOWNLOAD_CONTENT_TYPE); // 设置为下载application/x-download
				response.addHeader(HttpConstants.CONTENT_TYPE, HttpConstants.DOWNLOAD_HEADER);
				response.setContentType(HttpConstants.OCTET_STREAM_CONTENT_TYPE);
				// 设置输出的文件名
				response.setHeader(HttpConstants.CONTENT_DISPOSITION, HttpConstants.ATTACHMENT + Constants.SEMICOLON + httpFileName);
                this.writingVideo(response, path, pos);
			} else {
				if (!ContentTypeUtils.setContentType(openType.toLowerCase(), response)) {
					this.prepareWriteFile(request, response, path, openType, true);
                } else {
					ContentTypeUtils.setContentType(openType.toLowerCase(), response);
                    this.writingFile(response, path);
				}
			}

	}

	/**
	 * 20160923
	 * 视频文件输出方法
	 * @param request request
	 * @param response response
	 * @param openType openType
	 * @param path path
	 * @throws Exception 异常
	 */
	private void writeVideo(HttpServletRequest request, HttpServletResponse response, String openType, Path path) throws Exception {
        String range = request.getHeader(HttpConstants.RANGE);
        String browser = request.getHeader(HttpConstants.USER_AGENT);
        byte[] data = getBytesFromFile(path.toFile());
        assert data != null;
        int dataLength = data.length;
        long pos;
		long end = 0;
		if (null != range) {
			try {
				String[] ranges =  range.replaceAll(Constants.BYTESEQ, Constants.EMPTY).split(Constants.WHIPPLETREE);
				pos = Long.parseLong(ranges[0]);
				if (ranges.length > 1) {
					end = Long.parseLong(ranges[1]);
				}
			} catch (NumberFormatException e) {
				pos = 0;
			}
            // 断点续传
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            if (browser.contains(HttpConstants.EDGE)) {
                //EDGE浏览器
                String diskfilename = path.toFile().getName();
                response.setContentLength(dataLength);
                response.setContentType(HttpConstants.VIDEO_MPEG);
                response.setHeader(HttpConstants.CONTENT_DISPOSITION, "attachment; filename=\"" + diskfilename + "\"");
                response.setHeader(HttpConstants.CONTENT_RANGE, range);
                response.setHeader(HttpConstants.ACCEPT_RANGES, HttpConstants.TEXT_X_DVI);
            } else if (browser.contains(HttpConstants.FIREFOX) || browser.contains(HttpConstants.CHROME)) {
                //火狐
                ContentTypeUtils.setContentType(openType.toLowerCase(), response);
                response.setContentLength(dataLength - (int)pos);
                response.setHeader(HttpConstants.ACCEPT_RANGES, Constants.BYTES);
                String contentRange = Constants.BYTES + Constants.BLANK + pos + Constants.WHIPPLETREE + (data.length - 1) + Constants.SLASH + data.length;
                response.setHeader(HttpConstants.CONTENT_RANGE, contentRange);
            } else if (browser.contains(HttpConstants.SAFARI)) {
                //其他浏览器
                response.setHeader(HttpConstants.ACCEPT_RANGES, Constants.BYTES);
                ContentTypeUtils.setContentType(openType.toLowerCase(), response);
                /*response.setContentType("video/mp4");*/
                response.setContentLength((int) (end - pos) + 1);
                String contentRange = Constants.BYTES + Constants.BLANK + pos + Constants.WHIPPLETREE + end + Constants.SLASH + data.length;
                response.setHeader(HttpConstants.CONTENT_RANGE, contentRange);
            } else {
                response.setHeader(HttpConstants.ACCEPT_RANGES, Constants.BYTES);
                ContentTypeUtils.setContentType(openType.toLowerCase(), response);
                /*response.setContentType("video/mp4");*/
                if (end != 0) {
                    response.setContentLength((int) (end - pos) + 1);
                    String contentRange = Constants.BYTES + Constants.BLANK + pos + Constants.WHIPPLETREE + end + Constants.SLASH + data.length;
                    response.setHeader(HttpConstants.CONTENT_RANGE, contentRange);
                } else {
                    response.setContentLength(dataLength - (int)pos);
                    String contentRange = Constants.BYTES + Constants.BLANK + pos + Constants.WHIPPLETREE + (data.length - 1) + Constants.SLASH + data.length;
                    response.setHeader(HttpConstants.CONTENT_RANGE, contentRange);
                }

            }
            this.writingVideo(response, path, pos);
        } else {
            this.prepareWriteFile(request, response, path, openType, false);
        }

	}

	private void writingVideo(HttpServletResponse response, Path path, Long start) {
		InputStream inputStream = null;
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
            inputStream = new FileInputStream(path.toFile());
			inputStream.skip(start);
			byte[] buffer = new byte[1024*50];
			int length;
			while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, length);
			}
			out.flush();
		} catch (IOException e) {
			//暂时不做处理
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
                //只捕获,不处理
			}
		}


	}

	private void writingFile (HttpServletResponse response, Path path) throws Exception {
        final FileInputStream in = new FileInputStream(path.toFile());
        final FileChannel inChannel = in.getChannel();
        final int byteBufferSize = 1024 * 100;
        final ByteBuffer buff = ByteBuffer.allocate(byteBufferSize);
        int len;
        final OutputStream out = response.getOutputStream();
        final BufferedOutputStream outStream = new BufferedOutputStream(out);
        // PrintStream out = new PrintStream(outStream);
        while ((len = inChannel.read(buff)) > 0) {
            outStream.write(buff.array(), 0, len);
            outStream.flush();
            buff.clear();
        }
        outStream.close();
        inChannel.close();
        in.close();
    }

    /**
     * 读取文件字节
     *
     * @param file file
     * @return byte[]
     * @throws IOException IO异常
     */
    private static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            return null;
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead ;
        while ((offset < bytes.length) && ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        is.close();
        return bytes;
    }
}
