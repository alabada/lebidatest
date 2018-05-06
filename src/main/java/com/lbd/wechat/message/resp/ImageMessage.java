package com.lbd.wechat.message.resp;

import lombok.Data;

/**
 * 图片消息
 *
 * @author wenzhida
 */
@Data
public class ImageMessage extends BaseMessage{
	// 图片
	private Image Image;
}
