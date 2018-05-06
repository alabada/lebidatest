package com.lbd.wechat.menu;

import lombok.Data;

/**
 * view类型的按钮
 *
 * @author wenzhida
 */
@Data
public class ViewButton extends Button {
    private String type;
    private String url;
}
