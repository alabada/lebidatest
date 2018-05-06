package com.lbd.wechat.sendall;

import lombok.Data;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Description 文章
 */
@Data
public class ArticleToAll {

	/**
	 * 图片缩略图id
	 */
	private String thumb_media_id;

	/**
	 * 图文消息作者
	 */
	private String author;

	/**
	 * 图文消息标题
	 */
	private String title;

	/**
	 * 图文消息页面，‘阅读原文’后的页面
	 */
	private String content_source_url;

	/**
	 * 图文消息页面的内容，支持HTML标签。具备微信支付权限的公众号，可以使用a标签，其他公众号不能使用
	 */
	private String content;

	/**
	 * 图文消息的描述
	 */
	private String digest;

	/**
	 * 是否显示封面，1为显示，0为不显示
	 */
	private String show_cover_pic;
}
