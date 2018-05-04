package com.lbd.wechat.message.resp;

/**
 * 视频消息
 *
 * @author wenzhida
 */
public class VideoMessage extends BaseMessage{
	// 视频
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}
