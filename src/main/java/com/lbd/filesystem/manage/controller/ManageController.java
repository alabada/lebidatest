package com.lbd.filesystem.manage.controller;

import com.lbd.filesystem.manage.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 
 * @company: qishon
 * @author zhanghua.luo
 * @date: 2016年4月25日 下午1:56:22
 * @Description: TODO 文件管理控制类
 */
@Controller
@RequestMapping("/manage")
public class ManageController {
	
	@Autowired
	private ManageService manageServiceImpl;
	
	/**
	 * @Description: TODO 重命名文件
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/rename.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> renameFile(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, Object> dataMap = this.manageServiceImpl.rename(request, response);
		return dataMap;
	}
	
	/**
	 * @Description: TODO 删除文件
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteFile.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteFile(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, Object> result = this.manageServiceImpl.deleteFile(request, response);
		return result;
	}
	
	/**
	 * @Description: TODO 批量删除文件
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteFiles.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFiles(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, Object> result = this.manageServiceImpl.deleteFiles(request, response);
        return result;
	}

	@RequestMapping(value = "/isFileExist.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> isFileExist(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, Object> result = this.manageServiceImpl.isFileExist(request, response);
		return result;
	}
}
