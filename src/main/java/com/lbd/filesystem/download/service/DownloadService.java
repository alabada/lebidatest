package com.lbd.filesystem.download.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author 温枝达
 * @Date 2017/2/6 10:31
 * @Description
 */
public interface DownloadService {
	
	/**
	 * @Author 温枝达
	 * @Date 2017/2/6 10:35
	 * @Description 多文件下载
	 */
    void downloadFiles(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception;
    
    /**
     * @Author 温枝达
     * @Date 2017/2/6 10:36
     * @Description 单文件下载
     */
    void downloadFile(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception;
}
