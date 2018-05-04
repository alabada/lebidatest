package com.lbd.wechat.message.resp;

/**
 * 语音消息
 *
 * @author wenzhida
 */
public class VoiceMessage extends BaseMessage{
	// 语音
	private Voice Voice;

	public com.lbd.wechat.message.resp.Voice getVoice() {
		return Voice;
	}

	public void setVoice(com.lbd.wechat.message.resp.Voice voice) {
		Voice = voice;
	}
}
