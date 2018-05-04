package com.lbd.wechat.util;

import com.lbd.common.utils.LoggerUtils;
import com.lbd.wechat.pojo.SNSUserInfo;
import com.lbd.wechat.pojo.WeixinOauth2Token;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/3/14 13:43
 * @Description 
 */
public class AdvancedUtil {

    /**
     * @Author 温枝达
     * @Email alabadazi@gmail.com
     * @Date 2017/3/14 14:10
     * @Description 获取网页授权凭证
     */
    public static WeixinOauth2Token getOauth2AccessToken (String appId, String appSecret, String code) {
        WeixinOauth2Token wot = null;

        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);

        // 获取网页授权凭证
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wot = new WeixinOauth2Token();
                wot.setAccessToken(jsonObject.getString("access_token"));
                wot.setExpiresIn(jsonObject.getInt("expires_in"));
                wot.setRefreshToken(jsonObject.getString("refresh_token"));
                wot.setOpenId(jsonObject.getString("openid"));
                wot.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wot = null;
                int errCode = jsonObject.getInt("errcode");
                String errMsg = jsonObject.getString("errmsg");
                LoggerUtils.fmtError(AdvancedUtil.class, e, "获取网页授权凭证失败errcode：[%s],errmsg:[%s]", errCode, errMsg);
            }
        }

        return wot;
    }

    /**
     * @Author 温枝达
     * @Email alabadazi@gmail.com
     * @Date 2017/3/14 14:09
     * @Description 刷新网页授权凭证
     */
    public static WeixinOauth2Token refreshOauth2AccessToken (String appId, String refreshToken) {
        WeixinOauth2Token wot = null;

        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);

        // 刷新网页授权凭证
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wot = new WeixinOauth2Token();
                wot.setAccessToken(jsonObject.getString("access_token"));
                wot.setExpiresIn(jsonObject.getInt("expires_in"));
                wot.setRefreshToken(jsonObject.getString("refresh_token"));
                wot.setScope(jsonObject.getString("scope"));
                wot.setOpenId(jsonObject.getString("openid"));
            } catch (Exception e) {
                wot = null;
                int errCode = jsonObject.getInt("errcode");
                String errMsg = jsonObject.getString("errmsg");
                LoggerUtils.fmtError(AdvancedUtil.class, e, "刷新网页授权凭证失败errcode：[%s],errmsg:[%s]", errCode, errMsg);
            }
        }
        return wot;
    }

    /**
     * @Author 温枝达
     * @Email alabadazi@gmail.com
     * @Date 2017/3/14 16:45
     * @Description 获取用户基本信息
     */
    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
        SNSUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别 （1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 用户特权信息
                snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errCode = jsonObject.getInt("errcode");
                String errMsg = jsonObject.getString("errmsg");
                LoggerUtils.fmtError(AdvancedUtil.class, e, "获取用户信息失败 errcode：[%s], errmsg:[%s]", errCode, errMsg);
            }
        }

        return snsUserInfo;
    }

}
