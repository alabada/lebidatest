package com.lbd.wechat.sendall;

import java.util.Map;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/3/30 14:11
 * @Description 群发消息图文实体类
 */
public class MpnewsMessageToAll {

	private Map<String, Object> filter;

	private Map<String, String> mpnews;

	private String msgtype;

	public Map<String, Object> getFilter() {
		return filter;
	}

	public void setFilter(Map<String, Object> filter) {
		this.filter = filter;
	}

	public Map<String, String> getMpnews() {
		return mpnews;
	}

	public void setMpnews(Map<String, String> mpnews) {
		this.mpnews = mpnews;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
}
