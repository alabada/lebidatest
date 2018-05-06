package com.lbd.wechat.menu;

import lombok.Data;

/**
 * 复合类型的按钮
 *
 * @author wenzhida
 */
@Data
public class ComplexButton extends Button {
    private Button[] subButton;
}
