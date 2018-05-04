package com.lbd.filesystem.manage.service.impl;

import com.lbd.filesystem.common.annotation.SystemServiceLog;
import com.lbd.filesystem.common.configuration.SystemConfigProperties;
import com.lbd.filesystem.common.constant.Constants;
import com.lbd.filesystem.common.utils.JsonUtils;
import com.lbd.filesystem.download.message.State;
import com.lbd.filesystem.manage.service.ManageService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @company: qishon
 * @author zhiheng.li
 * @date: 2016年1月27日 下午5:56:22
 * @Description: TODO 文件管理service实现类
 */
@Service
public class ManageServiceImpl implements ManageService {

	@Autowired
	private SystemConfigProperties systemConfigProperties;
	
    @Override
    @SystemServiceLog
    public Map<String, Object> rename(HttpServletRequest request, HttpServletResponse response) {
        final Map<String, Object> result = new HashMap<String, Object>();
        final String fileSource = request.getParameter(Constants.FILE_SOURCE);
        final String filePath = request.getParameter(Constants.FILE_PATH).replaceAll("\\\\", File.separator);
        final String newFileName = request.getParameter(Constants.NEW_FILE_NAME);
        final String curProjectPath = this.systemConfigProperties.getConfig(Constants.SAVE_PATH);
        final String fileServerPath = fileSource + filePath;
        final File f = new File(curProjectPath, fileServerPath);
        boolean state = false;
        if (f.exists()) {
            final File file = new File(f.getParent() + File.separator + newFileName);
            if (!file.exists()) {
                state = f.renameTo(file);
            }
        } else {
            result.put(Constants.CODE, State.FILE_NO_FOUNE.getCode());
            result.put(Constants.MESSAGE, State.FILE_NO_FOUNE.getMessage());
            return result;
        }


        return this.getResult(state);
    }

    @Override
    @SystemServiceLog
    public Map<String, Object> deleteFiles(HttpServletRequest request, HttpServletResponse response) {
        final Map<String, Object> result = new HashMap<>();
        Boolean flag = false;
        //String fileSource = request.getParameter("fileSource");
        final String fileMessages = request.getParameter(Constants.FILE_MESSAGES);
        final List<Map<String, String>> params = JsonUtils.getMapArrayFromJsonArrStr(fileMessages);
        for (int i = 0; i < params.size(); i++) {
        	final Map<String, String> param = params.get(i);
            final String curProjectPath = this.systemConfigProperties.getConfig(Constants.SAVE_PATH);
            final String fileServerPath = param.get(Constants.FILE_SOURCE) + param.get(Constants.FILE_PATH);
            final File file = new File(curProjectPath, fileServerPath);
            if (file.exists() && file.isFile()) {
                flag = file.delete();
            } else if (file.exists() && file.isDirectory()) {
                flag = this.deleteDirectory(file);

            }
        }
        if (flag) {
            result.put(Constants.CODE, State.OK.getCode());
            result.put(Constants.MESSAGE, State.OK.getMessage());
        } else {
            result.put(Constants.CODE, State.ERROR_501.getCode());
            result.put(Constants.MESSAGE, State.ERROR_501.getMessage());
        }
        return result;
    }

    @Override
    @SystemServiceLog
    public Map<String, Object> deleteFile(HttpServletRequest request, HttpServletResponse response) {
        final String fileSource = request.getParameter(Constants.FILE_SOURCE);
        final String filePath = request.getParameter(Constants.FILE_PATH);
        final String curProjectPath = this.systemConfigProperties.getConfig(Constants.SAVE_PATH);
        final String fileServerPath = fileSource + File.separator + filePath;
        final File file = new File(curProjectPath, fileServerPath);
        boolean flag = false;
        if (file.exists() && file.isFile()) {
            flag = file.delete();
        } else if (file.exists() && file.isDirectory()) {
            flag = this.deleteDirectory(file);
        }
        return this.getResult(flag);
    }

    @Override
    public Map<String, Object> isFileExist(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> dataMap = new HashedMap();
        final String fileSource = request.getParameter(Constants.FILE_SOURCE);
        final String filePath = request.getParameter(Constants.FILE_PATH);
        final String curProjectPath = this.systemConfigProperties.getConfig(Constants.SAVE_PATH);
        String fileServerPath = null;
        if (fileSource != null)
           fileServerPath = fileSource + File.separator + filePath;
        else
            fileServerPath = filePath;
        final File file = new File(curProjectPath, fileServerPath);
        boolean flag = false;
        if (file.exists())
            dataMap.put("code", 200);
        else
            dataMap.put("code", -200);

        return dataMap;
    }

    /**
     * @Description 递归删除文件夹
     * @param file
     */
    public Boolean deleteDirectory(File file) {
        Boolean flag = false;
        final String[] tempList = file.list();
        File fileTemp;
        for (String temp : tempList) {
            fileTemp = new File(file.getPath(), temp);
            if (fileTemp.isFile()) {
                flag = fileTemp.delete();
            } else if (fileTemp.isDirectory()) {
                this.deleteDirectory(fileTemp);
                flag = fileTemp.delete();
            }
        }
        flag = file.delete();
        return flag;
    }
    
    /**
     * 获取结果
     * @param flag
     * @return
     */
    public Map<String, Object> getResult(Boolean flag) {
        final Map<String, Object> result = new HashMap<String, Object>();
        if (flag) {
            result.put(Constants.CODE, State.OK.getCode());
            result.put(Constants.MESSAGE, State.OK.getMessage());
        } else {
            result.put("code", State.ERROR.getCode());
            result.put("message", State.ERROR.getMessage());
        }
        return result;
    }


}
