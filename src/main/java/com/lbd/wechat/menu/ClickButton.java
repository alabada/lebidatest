package com.lbd.wechat.menu;

import lombok.Data;

/**
 * click类型的按钮
 *
 * @author wenzhida
 */
@Data
public class ClickButton extends Button {
    private String type;
    private String key;
}
