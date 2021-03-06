package com.lbd.wechat.pojo;

import lombok.Data;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Description 
 */
@Data
public class WeixinOauth2Token {

    // 网页授权接口调用凭证
    private String accessToken;

    // 凭证有效时长
    private int expiresIn;

    // 用于刷新凭证
    private String refreshToken;

    // 用户标识
    private String openId;

    // 用户授权作用域
    private String scope;
}
