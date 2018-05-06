package com.lbd.wechat.message.req;

import lombok.Data;

/**
 * 请求消息基类（普通用户 -> 公众账号）
 *
 * @author wenzhida
 */
@Data
public class BaseMessage {
    // 开发者微信号
    private String ToUserName;
    // 发送方微信号（一个openID）
    private String FromUserName;
    // 消息创建时间（整型）
    private long CreateTime;
    // 消息类型（text/image/location/link/voice）
    private String MsgType;
    // 消息ID
    private long MsgId;
}
