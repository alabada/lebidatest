package com.lbd.filesystem.upload.core;

import com.lbd.filesystem.common.constant.Constants;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpSession;
import java.text.NumberFormat;


/**
 * 
 * @company: qishon
 * @author zhiheng.li
 * @date: 2016年1月27日 下午5:55:41
 * @Description: TODO 文件进度监听器
 */
public class FileProcessListener implements ProgressListener {

	/** 日志对象 */
	private Log logger = LogFactory.getLog(this.getClass());

	private HttpSession session;

	/**
	 * 
	 * @param session
	 */
	public FileProcessListener(HttpSession session) {
		this.session = session;
		this.session.removeAttribute(Constants.FILE_UPLOAD_PROCESS);
	}

	/**
	 * 
	 */
	public void update(long pBytesRead, long pContentLength, int pItems) {
		final double readByte = pBytesRead;
		final double totalSize = pContentLength;
		if (pContentLength == -1) {
			this.logger.debug("first: item index[" + pItems + " ] " + pBytesRead + "  bytes have been read.");
		} else {
			this.logger.debug(
					"item index[" + pItems + "] " + pBytesRead + " of " + pContentLength + " bytes have been read.");
			final String p = NumberFormat.getPercentInstance().format(readByte / totalSize);
			this.session.setAttribute("fileUploadProcess", p);
		}
	}

}
