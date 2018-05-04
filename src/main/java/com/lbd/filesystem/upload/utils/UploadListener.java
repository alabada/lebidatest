package com.lbd.filesystem.upload.utils;

import org.apache.commons.fileupload.ProgressListener;

public class UploadListener implements ProgressListener {
	private UploadStatus status;//��¼�ϴ���ϢΪJava Bean
	
	public UploadListener(UploadStatus status){
		this.status=status;
	}
	
	public void update(long byteRead,long contentLength,int items){
		status.setByteRead(byteRead);
		status.setContentLength(contentLength);
		status.setItems(items);
	}
}
