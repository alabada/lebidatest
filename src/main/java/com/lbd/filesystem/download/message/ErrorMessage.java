package com.lbd.filesystem.download.message;

/**
 * @Author 温枝达
 * @Date 2017/2/6 10:26
 * @Description
 */
public class ErrorMessage {
	
	private Integer code;
	
	private String errorMessage;

	/**
	 *
	 * @return Integer
	 */
	public Integer getCode() {
		return this.code;
	}
	
	/**
	 * 
	 * @param i
	 */
	public void setCode(Integer i) {
		this.code = i;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	/**
	 * 
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
