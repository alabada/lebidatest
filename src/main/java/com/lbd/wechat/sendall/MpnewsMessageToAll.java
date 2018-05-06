package com.lbd.wechat.sendall;

import lombok.Data;

import java.util.Map;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Description 群发消息图文实体类
 */
@Data
public class MpnewsMessageToAll {

	private Map<String, Object> filter;

	private Map<String, String> mpnews;

	private String msgtype;
}
