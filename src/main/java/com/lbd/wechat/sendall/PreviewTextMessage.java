package com.lbd.wechat.sendall;

import lombok.Data;

import java.util.Map;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Description 预览群发消息文本实体类
 */
@Data
public class PreviewTextMessage {

	private String touser;

	private Map<String, String> text;

	private String msgtype;
}
