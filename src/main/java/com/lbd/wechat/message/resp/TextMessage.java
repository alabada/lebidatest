package com.lbd.wechat.message.resp;

import lombok.Data;

/**
 * 文本消息
 *
 * @author wenzhida
 */
@Data
public class TextMessage extends BaseMessage {
	// 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	private String Content;
}
