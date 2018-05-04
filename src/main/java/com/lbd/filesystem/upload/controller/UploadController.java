package com.lbd.filesystem.upload.controller;

import com.lbd.filesystem.upload.service.UploadService;
import com.lbd.filesystem.upload.utils.IsAllUploaded;
import com.lbd.filesystem.upload.utils.SaveFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    private UploadService uploadServiceImpl;

	@Autowired
	public UploadController(UploadService uploadServiceImpl) {
		this.uploadServiceImpl = uploadServiceImpl;
	}
	/**
	 * 
	 * @Description 文件上传controller类
	 * @param session session
	 * @param request request
	 * @return Map
	 * @throws Exception 所有异常
	 * @author zhanghua.luo
	 * @date 2016年1月27日 下午6:20:51
	 */
	@RequestMapping (method = RequestMethod.POST, value = "/fileupload")
	@ResponseBody
	public Map<String, Object> fileUpload(HttpSession session, HttpServletRequest request) throws Exception {
        Map<String, Object> dataMap;
        dataMap = this.uploadServiceImpl.fileUploadCorss(session, request);
		return dataMap;
	}

    /**
     * @param guid             临时文件名
     * @param md5value         客户端生成md5值
     * @param chunks           分块数
     * @param chunk            分块序号
     * @param id               文件id便于区分
     * @param name             上传文件名
     * @param type             文件类型
     * @param lastModifiedDate 上次修改时间
     * @param size             文件大小
     * @param file             文件本身
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "/largeFileUpload", method = RequestMethod.POST)
	public String largeFileUpload(String guid, String md5value, String chunks, String chunk, String id, String name, String type, String lastModifiedDate, int size, MultipartFile file) throws Exception {
        String fileName = "";
        try {
            int xuhao = 0;
            String path = UploadController.class.getResource("/").getFile();
            /*int index = path.indexOf("target");
            String tempPath = "/src/main/webapp/upload/";
            String uploadFolderPath = path.substring(0, index) + tempPath;*/
            String tempPath = "G:\\uploadFile\\";
            String uploadFolderPath = "G:\\uploadFile\\tempPath";
            //创建临时文件夹保存分块文件
            String newTempPath = tempPath;
            //分块文件临时保存路径
            String mergePath = newTempPath + "mergePath";
            String ext = name.substring(name.lastIndexOf("."));

            //判断文件是否分块
            if (chunks != null && chunk != null) {
                xuhao = Integer.parseInt(chunk);
                fileName = String.valueOf(xuhao) + ext;
                // 将文件分块保存到临时文件夹里，便于之后的合并文件
                SaveFile.saveFile(mergePath, fileName, file);
                // 验证所有分块是否上传成功，成功的话进行合并
                IsAllUploaded.Uploaded(md5value, guid, chunk, chunks, uploadFolderPath, fileName, ext);
            } else {
                fileName = guid + ext;
                //上传文件没有分块的话就直接保存
                SaveFile.saveFile(uploadFolderPath, fileName, file);
            }

        } catch (Exception ex) {
            return "{\"error\":true}";
        }

        return "{jsonrpc = \"2.0\",id = id,filePath = \"/Upload/\" + fileFullName}";
	}

    /**
     * 转向操作页面
     *
     * @return 操作页面
     */
    @RequestMapping(value = "/Index", method = RequestMethod.GET)
    public String Index1() {
        return "BigFileUpload/Index";
    }

}
