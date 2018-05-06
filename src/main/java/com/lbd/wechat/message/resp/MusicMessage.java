package com.lbd.wechat.message.resp;

import lombok.Data;

/**
 * 音乐消息
 *
 * @author wenzhida
 */
@Data
public class MusicMessage extends BaseMessage{
	// 音乐
	private Music Music;
}
