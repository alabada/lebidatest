package com.lbd.wechat.message.req;

import lombok.Data;

/**
 * 链接消息
 *
 * @author wenzhida
 */
@Data
public class LinkMessage extends BaseMessage{
	// 消息标题
	private String Title;
	// 消息描述
	private String Description;
	// 消息链接
	private String Url;
}
