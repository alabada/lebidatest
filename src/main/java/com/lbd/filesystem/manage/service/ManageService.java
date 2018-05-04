package com.lbd.filesystem.manage.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 
 * @company: qishon
 * @author zhiheng.li
 * @date: 2016年1月27日 下午5:56:22
 * @Description: TODO 文件管理service
 */
public interface ManageService {
	
	/**
	 * 重命名文件或文件夹方法
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> rename(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 多文件或文件夹删除方法
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> deleteFiles(HttpServletRequest request, HttpServletResponse response);
	

	/**
	 * 单文件或文件夹删除方法
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> deleteFile(HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> isFileExist(HttpServletRequest request, HttpServletResponse response);
}
