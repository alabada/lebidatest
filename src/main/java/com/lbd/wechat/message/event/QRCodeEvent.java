package com.lbd.wechat.message.event;

import lombok.Data;

/**
 * 扫描带参数二维码事件
 *
 * @author wenzhida
 */
@Data
public class QRCodeEvent extends BaseEvent {
    // 事件KEY值，qrscene_为前缀，后面为二维码的参数值
    private String EventKey;
    // 二维码的ticket，可用来换取二维码图片
    private String Ticket;
}
