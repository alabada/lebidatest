package com.lbd.filesystem.common.constant;

import java.io.File;

/**
 *
 * @company: qishon
 * @author zhiheng.li
 * @date:2016年1月27日 下午6:04:54
 * @Description:
 */
public class Constants {

    /** 上传目录名 */
    public static final String SAVE_PATH = "savePath";

    //接收iss文件临时存放目录
    public static final String TEMP_PATH = "tempPath";

    //需要三个路径

    /** 允许上传的扩展名 */
    public static final String[] EXTENSION_PERMIT = {"gif", "jpeg", "jpg", "bmp", "png"};

    public static final String[] EXTENSION_MEDIA_PERMIT = {"swf", "asx", "asf", "mpg", "wmv", "3gp", "mp4", "mov", "avi", "flv", "ogg", "ogv"};

    public static final String[] EXTENSION_ZIP_PERMIT = {Constants.ZIP, "rar", "7z"};

    public static final String[] EXTENSION_OTHER_PERMIT = {"assetbundle", "dat", "war"};

    public static final String FILE_UPLOAD_PROCESS = "fileUploadProcess";

    public static final String SLASH = "/";

    public static final String SLASH2 = "\\";

    public static final String POINT = ".";

    public static final int NUM_1 = 1;

    public static final int RANDOW_NUM = 50000;

    public static final String CFUNCTION = "cfunction";

    public static final int MAXSIZX = 1073741823;

    public static final int MINSIZE = 41984;

    public static final String IMAGE = "image";

    public static final String MEDIA = "media";

    public static final String ZIP = "zip";

    public static final String CODE = "code";

    public static final String MESSAGE = "message";

    public static final String RESULT = "result";

    public static final String FILE_MESSAGES = "fileMessages";

    public static final String IS_DOWN = "isDown";

    public static final String FILE_SOURCE = "fileSource";

    public static final String FILE_PATH = "filePath";

    public static final String NEW_FILE_NAME = "newFileName";

    public static final String SAVE_FILE_NAME = "saveFileName";

    public static final String SAVE_DIRECTORY = "saveDirectory";

    public static final String TYPE_STR = "typeStr";

    public static final String OLD_FILE_NAME = "oldFileName";

    public static final String FILE_SERVER_KEY = "fileServerKey";

    public static final String BRACKETS = "()";

    public static final String COMMA = ",";

    public static final String CLIENT_IP = "客户IP: ";

    public static final String REQUEST_ADDR = "请求地址: ";

    public static final String REQUEST_PARAM = "请求参数: ";

    //哑元文件对象，放在阻塞队列最后，用来标示文件已被遍历完
    public static final File DUMMY = new File("");

    public static final String SEMICOLON = ";";

    public static final String WHIPPLETREE = "-";

    public static final String BYTES = "bytes";

    public static final String BLANK = " ";

    public static final String BYTESEQ = "bytes=";

    public static final String EMPTY = "";

    public static final String CHSEMICOLON= "；";
}
