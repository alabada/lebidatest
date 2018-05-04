package com.lbd.filesystem.common.pojo;

/**
 * Created by luozhanghua on 2016/9/4 0004.
 */
public class FileInfoPojo {

    //文件名
    private String fileName;
    //文件大小
    private Integer fileSize;
    //文件类型
    private String fileType;
    //文件md5
    private String fileMd5;
    //文件全路径
    private String filePath;
    //是否文件，1表示是，0表示否
    private Integer isFile;
    //上级文件路径
    private String fileUpDirectoryPath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getIsFile() {
        return isFile;
    }

    public void setIsFile(Integer isFile) {
        this.isFile = isFile;
    }

    public String getFileUpDirectoryPath() {
        return fileUpDirectoryPath;
    }

    public void setFileUpDirectoryPath(String fileUpDirectoryPath) {
        this.fileUpDirectoryPath = fileUpDirectoryPath;
    }
}
