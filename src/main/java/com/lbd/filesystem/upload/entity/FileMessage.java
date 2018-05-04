package com.lbd.filesystem.upload.entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * @company: qishon
 * @author zhiheng.li
 * @date:2016年1月28日 下午2:18:13
 * @Description:
 */
public class FileMessage {

	private String fileServerKey;

	private String fileName;

	private String saveFileName;

	private String filePath;

	private String fileSize;

	private String fileSource;
	
	private Integer code;
	
	private String fileMd5;
	
	private Integer isCompress;
	
	private List<Map<String, Object>> compressImageMsg;
	
	/**
	 * getFileServerKey
	 * @return
	 */
	public String getFileServerKey() {
		return this.fileServerKey;
	}
	
	/**
	 * 
	 * @param fileServerKey
	 */
	public void setFileServerKey(String fileServerKey) {
		this.fileServerKey = fileServerKey;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFileName() {
		return this.fileName;
	}
	
	/**
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFilePath() {
		return this.filePath;
	}
	
	/**
	 * 
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFileSize() {
		return this.fileSize;
	}
	
	/**
	 * 
	 * @param fileSize
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFileSource() {
		return this.fileSource;
	}
	
	/**
	 * 
	 * @param fileSource
	 */
	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getCode() {
		return this.code;
	}
	
	/**
	 * 
	 * @param code
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFileMd5() {
		return this.fileMd5;
	}
	
	/**
	 * 
	 * @param fileMd5
	 */
	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getIsCompress() {
		return this.isCompress;
	}
	
	/**
	 * 
	 * @param isCompress
	 */
	public void setIsCompress(Integer isCompress) {
		this.isCompress = isCompress;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getCompressImageMsg() {
		return this.compressImageMsg;
	}
	
	/**
	 * 
	 * @param compressImageMsg
	 */
	public void setCompressImageMsg(List<Map<String, Object>> compressImageMsg) {
		this.compressImageMsg = compressImageMsg;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSaveFileName() {
		return this.saveFileName;
	}
	
	/**
	 * 
	 * @param saveFileName
	 */
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
}
