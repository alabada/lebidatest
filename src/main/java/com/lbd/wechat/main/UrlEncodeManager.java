package com.lbd.wechat.main;

import java.io.UnsupportedEncodingException;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/3/14 16:01
 * @Description 对路径进行URL编码
 */
public class UrlEncodeManager {


    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {

//        String oauthUrl = "http://lbd-qa.linesum.com/authorization.shtml";
        String oauthUrl = "http://lbd-qa.linesum.com/authorization.shtml?redirect=xxx";
        System.out.println(urlEncodeUTF8(oauthUrl));

        // http%3A%2F%2Fweixin.idinu.com%2Flzh%2Fauthorization.shtml
        // http%3A%2F%2Fadmin.liangzehe.com%2Fauthorization.shtml

        // 最终地址：
        // https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxebe0dfbffbab06bb&redirect_uri=http%3A%2F%2Fadmin.liangzehe.com%2Fauthorization.shtml&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect

    }


}
