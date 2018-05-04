package com.lbd.filesystem.download.message;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @company: qishon
 * @author zhiheng.li
 * @date: 2016年1月27日 下午5:56:09
 * @Description: TODO 状态
 */
public enum State {
	OK(200, "操作成功!"),
    ERROR_PARAM(400, "存在必填参数未填或参数格式错误!"),
	ERROR(500, "操作失败!"),
	ERROR_501(501, "部分操作失败!"),
	NO_SUPPORT_EXTENSION(502, "不支持的扩展名!"),
	ERROR_PATH(503, "文件路径错误!"),
	FILE_NO_FOUNE(504, "文件或文件夹不存在!"),
	NO_FILE_UPLOAD(507, "没有符合要求的文件被上传!"),
	ERROR_COMPRESS(506, "压缩失败!"),
	FILE_SIZE_ERROR(505, "文件大小超出限制!");

	private int code;
	private String message;

	State(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 
	 * @Description:TODO
	 * @return
	 * @author zhiheng.li
	 * @date 2016年1月27日 下午6:10:53
	 */
	public int getCode() {
		return this.code;
	}

	/**
	 * 
	 * @Description:TODO
	 * @return
	 * @author zhiheng.li
	 * @date 2016年1月27日 下午6:11:03
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * 
	 * @Description:TODO
	 * @return
	 * @author zhiheng.li
	 * @date 2016年1月27日 下午6:11:07
	 */
	public Map<String, Object> toMap() {
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", this.code);
		map.put("message", this.message);
		return map;
	}

	/**
	 * 
	 * @Description:TODO
	 * @return
	 * @author zhiheng.li
	 * @date 2016年1月30日 下午4:55:59
	 */
	public String toCode() {
		return this.code + "";
	}

	/**
	 * 
	 * @Description:TODO
	 * @return
	 * @author zhiheng.li
	 * @date 2016年1月27日 下午6:11:07
	 */
	public String toValitate() {
		return "{\"message\":\"" + this.message + "\",\"code\":\"" + this.code + "\"}";
	}

}