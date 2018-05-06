package com.lbd.wechat.message.resp;

import lombok.Data;

/**
 * 语音消息
 *
 * @author wenzhida
 */
@Data
public class VoiceMessage extends BaseMessage{
	// 语音
	private Voice Voice;
}
