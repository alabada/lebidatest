package com.lbd.filesystem.upload.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 
 * @company: qishon
 * @author zhanghua.luo
 * @date: 2016年4月11日 下午1:06:30
 * @Description: 文件上传Service
 */
public interface UploadService {

    /**
     * @Description 文件上传接口
     * @param session session
     * @param request request
     * @return Map
     * @throws Exception 所有异常
     */
	Map<String, Object> fileUploadCorss(HttpSession session, HttpServletRequest request) throws Exception;
	
}
