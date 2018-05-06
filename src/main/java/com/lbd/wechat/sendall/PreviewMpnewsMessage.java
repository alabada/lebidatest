package com.lbd.wechat.sendall;

import lombok.Data;

import java.util.Map;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/3/30 14:11
 * @Description 预览群发消息图文实体类
 */
@Data
public class PreviewMpnewsMessage {

	private String touser;

	private Map<String, String> mpnews;

	private String msgtype;
}
