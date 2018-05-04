package com.lbd.filesystem.common.exception;

/**
 * 
 * @company: qishon
 * @author zhiheng.li
 * @date: 2016年1月27日 下午5:55:58
 * @Description: TODO 不支持的扩展名异常类
 */
public class NoSupportExtensionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param message
	 */
	public NoSupportExtensionException(String message) {
		super(message);
	}
}
