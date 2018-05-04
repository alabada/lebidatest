package com.lbd.filesystem.upload.service.impl;

import com.lbd.filesystem.common.annotation.SystemServiceLog;
import com.lbd.filesystem.common.configuration.SystemConfigProperties;
import com.lbd.filesystem.common.constant.Constants;
import com.lbd.filesystem.common.exception.BasesException;
import com.lbd.filesystem.common.utils.FileUtils;
import com.lbd.filesystem.common.utils.ImageZipUtils;
import com.lbd.filesystem.download.message.State;
import com.lbd.filesystem.upload.entity.FileMessage;
import com.lbd.filesystem.upload.service.UploadService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.*;

/**
 *
 * @company: qishon
 * @author zhanghua.luo
 * @date: 2016年4月11日 下午1:05:33
 * @Description: 文件上传Service实现类
 */
@Service
public class UploadServiceImpl implements UploadService {

	private SystemConfigProperties systemConfigProperties;

    @Autowired
    public UploadServiceImpl(SystemConfigProperties systemConfigProperties) {
        this.systemConfigProperties = systemConfigProperties;
    }
    @Resource
    ImageZipUtils imageZipUtils;

	@SystemServiceLog
	@Override
	public Map<String, Object> fileUploadCorss(HttpSession session, HttpServletRequest request) throws Exception {
		final Map<String, Object> params = this.checkParams(request);
		if (null == params) {
			throw new BasesException(State.ERROR_PARAM.getCode(), State.ERROR_PARAM.getMessage());
		}

		final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

		List<FileMessage> dataList;
		final Map<String, Object> dataMap = new HashMap<>();
		if (multipartResolver.isMultipart(request)) {
			Map<String, String> newFileNames = null;
			final String saveFileName = request.getParameter(Constants.SAVE_FILE_NAME);
			if (saveFileName != null && !"".equals(saveFileName)) {
				newFileNames = this.getMapFromJsonArrStr(saveFileName);
			}
			final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			dataList = new ArrayList<>();
			final Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				final MultipartFile file = multiRequest.getFile(iter.next());
				final FileMessage fileMessage = new FileMessage();
                boolean checkFile;
                final int l = file.getOriginalFilename().toCharArray().length;
                if (file.getOriginalFilename() != null && l != 0) {
                    checkFile = this.checkFile(file, fileMessage);
                } else {
                    break;
                }
				if (checkFile) {
                    final MessageDigest md5 = MessageDigest.getInstance("MD5");
					final String oldFileName = file.getOriginalFilename();
					final String newFileName = this.getNewFileName(oldFileName, newFileNames);
					final String fileType = oldFileName.substring(oldFileName.lastIndexOf(Constants.POINT) + Constants.NUM_1);
					fileMessage.setFileSize(file.getSize() + "");
					fileMessage.setFileName(oldFileName);
					fileMessage.setSaveFileName(newFileName);
					final String fileSource = (String) params.get(Constants.FILE_SOURCE);
					final String filePath =  (String) params.get(Constants.SAVE_DIRECTORY);
                	final Path saveServicePath = this.getSaveServicePath(fileMessage, fileSource, filePath);
					final File saveDirectory = saveServicePath.toFile();
					FileUtils.mkDir(saveServicePath);
					String serviceFileMd5;
					final File uploadFile = new File(saveDirectory, newFileName);
					//保存文件，返回Md5
                	serviceFileMd5 = FileUtils.saveFileByNio(file, uploadFile, md5);
					fileMessage.setFileMd5(serviceFileMd5);
					fileMessage.setCode(State.OK.getCode());
					this.compressImage(fileType, params, saveDirectory, newFileName, fileSource, fileMessage);
					dataList.add(fileMessage);
				} else {
                    dataList.add(fileMessage);
                }
			}
            if (dataList.size() != 0) {
                dataMap.put(Constants.FILE_SERVER_KEY, params.get(Constants.FILE_SERVER_KEY));
                dataMap.put(Constants.CODE, State.OK.getCode());
                dataMap.put(Constants.MESSAGE, "上传完成");
                dataMap.put(Constants.RESULT, dataList);

            } else {
                throw new BasesException(State.NO_FILE_UPLOAD.getCode(), State.NO_FILE_UPLOAD.getMessage());
            }
		}
        return dataMap;
	}

	/**
	 * @Description 检验传入的参数是否齐全
	 * @param  request request参数
	 * @return Map
	 */
	private Map<String, Object> checkParams(HttpServletRequest request) throws BasesException {
		final Map<String, Object> params = new HashMap<>();
		final String path = request.getParameter(Constants.SAVE_DIRECTORY);
		final String fileServerKey = request.getParameter(Constants.FILE_SERVER_KEY);
		if (fileServerKey != null) {
			params.put(Constants.FILE_SERVER_KEY, fileServerKey);
		}
        if (path != null) {
            if (!path.contains("../")) {
                params.put(Constants.SAVE_DIRECTORY, path);
            } else {
                throw new BasesException(State.ERROR_PATH.getCode(), State.ERROR_PATH.getMessage());
            }
        }
		final String fileSource = request.getParameter(Constants.FILE_SOURCE);
		if (fileSource != null && !"".equals(fileSource)) {
			params.put(Constants.FILE_SOURCE, fileSource);
		} else {
			throw new BasesException(State.ERROR_PARAM.getCode(), "fileSource参数未填写!");
		}
		final String typeStr = request.getParameter(Constants.TYPE_STR);
		params.put(Constants.TYPE_STR, typeStr);
		return params;
	}

	/**
	 * @Description
	 * @param jsonArrStr ArrString的JSON
	 * @return Map
	 */
	private Map<String, String> getMapFromJsonArrStr(String jsonArrStr) throws BasesException {
		final JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		if (jsonArr == null || jsonArr.size() == 0) {
			throw new BasesException(State.ERROR_PARAM.getCode(), State.ERROR_PARAM.getMessage());
		}
		final Map<String, String> map = new HashMap<>();
        for (Object o : jsonArr) {
            final JSONObject jsonObject = JSONObject.fromObject(o);
            final Iterator it = jsonObject.keys();
            String oldFileName = null;
            String newFileName = null;
            while (it.hasNext()) {
                final String key = (String) it.next();
                if (key.contains(Constants.OLD_FILE_NAME)) {
                    oldFileName = String.valueOf(jsonObject.get(key));
                }
                if (key.contains(Constants.NEW_FILE_NAME)) {
                    newFileName = String.valueOf(jsonObject.get(key));
                }
            }
            map.put(oldFileName, newFileName);
        }
		return map;
	}
	
	/**
	 * @Description 如果是图片，则压缩图片
	 * @param params 参数
	 * @param saveDirectory 保存目录
	 * @param newFileName 文件新名字
	 * @param fileSource 文件来源
	 * @throws Exception 所有异常
	 */
	private void compressImage(String fileType, Map<String, Object> params, File saveDirectory, String newFileName, String fileSource, FileMessage fileMessage) throws Exception {
		List<Map<String, Object>> compressImageMsg;
		if (Arrays.asList(Constants.EXTENSION_PERMIT).contains(fileType.toLowerCase())) {
			final String typeStr = (String) params.get(Constants.TYPE_STR);
			if (typeStr != null && !"".equals(typeStr)) {
				try {
					compressImageMsg = this.imageZipUtils.compressPictures(typeStr, saveDirectory, newFileName,
							fileMessage.getFilePath(), fileSource);
				} catch (Exception e) {
					throw new BasesException(State.ERROR_COMPRESS.getCode(), State.ERROR_COMPRESS.getMessage());
				}
				fileMessage.setIsCompress(1);
				fileMessage.setCompressImageMsg(compressImageMsg);
			}
		} else {
			fileMessage.setIsCompress(0);
		}
	}
	
	/**
	 * @Description 如果存在新的名字，则重命名文件
	 * @param oldFileName 旧名字
	 * @param newFileNames 新名字
	 * @return String
	 */
	private String getNewFileName(String oldFileName, Map<String, String> newFileNames) {
		String newFileName;
		if (newFileNames != null && newFileNames.containsKey(oldFileName)) {
			final String fileName = newFileNames.get(oldFileName);
			if (fileName != null && !"".equals(fileName)) {
				newFileName = fileName;
			} else {
				newFileName = oldFileName;
			}
		} else {
			newFileName = oldFileName;
		}
		return newFileName;
	}
	
	/**
	 * 
	 * @param fileMessage 文件信息
	 * @param fileSource 文件来源
	 * @param filePath 文件路径
	 * @return Path
	 */
    private Path getSaveServicePath(FileMessage fileMessage, String fileSource, String filePath) {
        final String saveServicePath = this.systemConfigProperties.getConfig(Constants.SAVE_PATH);
		Path path;
        if (filePath != null && !"".equals(filePath)) {
			path = Paths.get(saveServicePath, fileSource, filePath);
            fileMessage.setFileSource(fileSource);
            fileMessage.setFilePath(filePath);
        } else {
        	final String defaultPath = FileUtils.getDefaultSaveDir();
			path = Paths.get(saveServicePath, fileSource, defaultPath);
            fileMessage.setFileSource(fileSource);
            fileMessage.setFilePath(defaultPath);
        }
        return path;
    }

    /**
     * 校验文件
     * @param file 文件
     * @param fileMessage 文件信息
     * @return boolean
     * @throws BasesException 文件不符合要求错误
     */
	private boolean checkFile(MultipartFile file, FileMessage fileMessage) throws BasesException {
		final String fileName = file.getOriginalFilename();
		final String fileType = fileName.substring(fileName.lastIndexOf(Constants.POINT) + Constants.NUM_1);

		if (file.getSize() < Constants.MINSIZE && file.getSize() > Constants.MAXSIZX) {
			fileMessage.setFileSize(String.valueOf(file.getSize()));
			fileMessage.setFileName(file.getOriginalFilename());
			fileMessage.setCode(State.FILE_SIZE_ERROR.getCode());
			return false;
			//throw new BasesException(State.FILE_SIZE_ERROR.getCode(), State.FILE_SIZE_ERROR.getMessage());
		} else {
			boolean flag = false;
            String[] EXTENSION_PICTURE_PERMIT = this.systemConfigProperties.getConfig("EXTENSION_PICTURE_PERMIT").replaceAll(Constants.BLANK, Constants.EMPTY)
                    .replaceAll(Constants.CHSEMICOLON, Constants.SEMICOLON).split(Constants.SEMICOLON);
            String[] EXTENSION_MEDIA_PERMIT = this.systemConfigProperties.getConfig("EXTENSION_MEDIA_PERMIT").replaceAll(Constants.BLANK, Constants.EMPTY)
                    .replaceAll(Constants.CHSEMICOLON, Constants.SEMICOLON).split(Constants.SEMICOLON);
            String[] EXTENSION_ZIP_PERMIT = this.systemConfigProperties.getConfig("EXTENSION_ZIP_PERMIT").replaceAll(Constants.BLANK, Constants.EMPTY)
                    .replaceAll(Constants.CHSEMICOLON, Constants.SEMICOLON).split(Constants.SEMICOLON);
            String[] EXTENSION_OTHER_PERMIT = this.systemConfigProperties.getConfig("EXTENSION_OTHER_PERMIT").replaceAll(Constants.BLANK, Constants.EMPTY)
                    .replaceAll(Constants.CHSEMICOLON, Constants.SEMICOLON).split(Constants.SEMICOLON);
			if (Arrays.asList(EXTENSION_PICTURE_PERMIT).contains(fileType.toLowerCase())) {
				flag = true;
			} else if (Arrays.asList(EXTENSION_MEDIA_PERMIT).contains(fileType.toLowerCase())) {
				flag = true;
			} else if (Arrays.asList(EXTENSION_ZIP_PERMIT).contains(fileType.toLowerCase())) {
				flag = true;
			} else if (Arrays.asList(EXTENSION_OTHER_PERMIT).contains(fileType.toLowerCase())) {
				flag = true;
			} else {
				fileMessage.setFileName(file.getOriginalFilename());
				fileMessage.setCode(State.NO_SUPPORT_EXTENSION.getCode());
				//throw new BasesException(State.NO_SUPPORT_EXTENSION.getCode(), State.NO_SUPPORT_EXTENSION.getMessage());
			}
			return flag;
		}
	}
}
