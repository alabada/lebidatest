package com.lbd.wechat.message.event;

import lombok.Data;

/**
 * 事件基类
 *
 * @author wenzhida
 */
@Data
public class BaseEvent {
    //开发者微信号
    private String ToUserName;
    //发送方微信号（一个openID）
    private String FromUserName;
    //消息创建时间（整型）
    private long CreateTime;
    //消息类型
    private String MsgType;
    // 事件类型
    private String Event;
}
