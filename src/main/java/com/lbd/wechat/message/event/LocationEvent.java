package com.lbd.wechat.message.event;

import lombok.Data;

/**
 * 上报地理位置事件
 *
 * @author wenzhida
 */
@Data
public class LocationEvent extends BaseEvent {
    // 地理位置纬度
    private String Latitude;
    // 地理位置经度
    private String Longitude;
    // 地理位置精度
    private String Precision;
}
