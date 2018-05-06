package com.lbd.wechat.message.resp;

import lombok.Data;

/**
 * 视频消息
 *
 * @author wenzhida
 */
@Data
public class VideoMessage extends BaseMessage{
	// 视频
	private Video Video;
}
