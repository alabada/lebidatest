package com.lbd.wechat.message.resp;

import lombok.Data;

/**
 * 响应消息基类（公众账号 -> 普通用户）
 *
 * @author wenzhida
 */
@Data
public class BaseMessage {
    // 接收方账号（收到的OpenID）
    private String ToUserName;
    // 开发者微信号
    private String FromUserName;
    // 消息创建时间（整型）
    private long CreateTime;
    // 消息类型（text/music/news）
    private String MsgType;
}
