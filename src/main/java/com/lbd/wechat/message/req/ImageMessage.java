package com.lbd.wechat.message.req;

import lombok.Data;

/**
 * 图片消息
 *
 * @author wenzhida
 */
@Data
public class ImageMessage extends BaseMessage{
	// 图片链接
	private String PicUrl;
	// 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	private String MediaId;
}
