package com.lbd.filesystem.upload.utils;


import com.lbd.filesystem.upload.model.UploadInfo;

import java.util.ArrayList;
import java.util.List;
//import java.util.stream.Collectors;

public class IsAllUploaded {

    private static List<UploadInfo> uploadInfoList;

    /**
     * @param md5
     * @param chunks
     * @return
     */
    public static boolean isAllUploaded(final String md5, final String chunks) {
        return false;
//        int size = uploadInfoList.stream()
//                .filter(item -> item.getMd5().equals(md5))
//                .distinct()
//                .collect(Collectors.toList())
//                .size();
//        boolean bool = (size == Integer.parseInt(chunks));
//        if (bool) {
//            uploadInfoList.removeIf(item -> item.getMd5() == md5);
//        }
//        return bool;
    }

    /**
     * @param md5         MD5
     * @param guid        随机生成的文件名
     * @param chunk       文件分块序号
     * @param chunks      文件分块数
     * @param fileName    文件名
     * @param ext         文件后缀名
     * @param fileService fileService
     */
    public static void Uploaded(final String md5,
                                final String guid,
                                final String chunk,
                                final String chunks,
                                final String uploadFolderPath,
                                final String fileName,
                                final String ext)
            throws Exception {
        if (uploadInfoList == null) {
            uploadInfoList = new ArrayList<>();
        }
        uploadInfoList.add(new UploadInfo(md5, chunks, chunk, uploadFolderPath, fileName, ext));
        boolean allUploaded = isAllUploaded(md5, chunks);
        int chunksNumber = Integer.parseInt(chunks);

        if (allUploaded) {
            MergeFile.mergeFile(chunksNumber, ext, guid, uploadFolderPath);
        }
    }
}



