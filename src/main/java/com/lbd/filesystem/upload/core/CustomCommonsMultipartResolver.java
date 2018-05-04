package com.lbd.filesystem.upload.core;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * @company: qishon
 * @author zhiheng.li
 * @date: 2016年1月27日 下午5:55:22
 * @Description: TODO 自定义CommonsMultipartResolver类
 */
public class CustomCommonsMultipartResolver extends CommonsMultipartResolver {

	@Override
	protected MultipartParsingResult parseRequest(HttpServletRequest request)
			throws MultipartException {
		final String encoding = determineEncoding(request);
		final FileUpload fileUpload = prepareFileUpload(encoding);

		// 加入文件进度监听器 (原Source上添加) start
		final FileProcessListener processListener = new FileProcessListener(
				request.getSession());
		fileUpload.setProgressListener(processListener);
		// 加入文件进度监听器 (原Source上添加) end

		try {
			final List<FileItem> fileItems = ((ServletFileUpload) fileUpload).
					parseRequest(request);
			return parseFileItems(fileItems, encoding);
		} catch (FileUploadBase.SizeLimitExceededException ex) {
			throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(),
					ex);
		} catch (FileUploadException ex) {
			throw new MultipartException(
					"Could not parse multipart servlet request", ex);
		}
	}
}
