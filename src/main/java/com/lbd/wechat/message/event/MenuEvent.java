package com.lbd.wechat.message.event;

import lombok.Data;

/**
 * 自定义菜单事件
 *
 * @author wenzhida
 */
@Data
public class MenuEvent extends BaseEvent {
    // 事件KEY值，与自定义菜单接口中KEY值对应
    private String EventKey;
}
