package com.lbd.wechat.message.req;

import lombok.Data;

/**
 * 文本消息
 *
 * @author wenzhida
 */
@Data
public class TextMessage extends BaseMessage{
	// 消息内容
	private String Content;
}
