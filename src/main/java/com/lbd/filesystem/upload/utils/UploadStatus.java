package com.lbd.filesystem.upload.utils;

public class UploadStatus {
	private long byteRead;
	private long contentLength;
	private int items;
	private long startTime= System.currentTimeMillis();
	
	public long getByteRead() {
		return byteRead;
	}
	public void setByteRead(long byteRead) {
		this.byteRead = byteRead;
	}
	public long getContentLength() {
		return contentLength;
	}
	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
	public int getItems() {
		return items;
	}
	public void setItems(int items) {
		this.items = items;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	
}
