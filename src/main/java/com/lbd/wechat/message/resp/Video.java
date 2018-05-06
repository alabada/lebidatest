package com.lbd.wechat.message.resp;

import lombok.Data;

/**
 * 视频Model
 *
 * @author wenzhida
 */
@Data
public class Video {
	// 媒体文件ID
	private String MediaId;
	// 视频消息的标题
	private String Title;
	// 视频消息的描述
	private String Description;
}
