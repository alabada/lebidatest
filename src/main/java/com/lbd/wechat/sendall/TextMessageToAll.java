package com.lbd.wechat.sendall;

import java.util.Map;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/3/30 14:11
 * @Description 群发消息文本实体类
 */
public class TextMessageToAll {

	private Map<String, Object> filter;

	private Map<String, String> text;

	private String msgtype;

	public Map<String, Object> getFilter() {
		return filter;
	}

	public void setFilter(Map<String, Object> filter) {
		this.filter = filter;
	}

	public Map<String, String> getText() {
		return text;
	}

	public void setText(Map<String, String> text) {
		this.text = text;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
}
