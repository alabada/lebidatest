package com.lbd.wechat.util;

import com.lbd.wechat.sendall.MpnewsMessageToAll;
import com.lbd.wechat.sendall.PreviewMpnewsMessage;
import com.lbd.wechat.sendall.PreviewTextMessage;
import com.lbd.wechat.sendall.TextMessageToAll;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/3/30 14:34
 * @Description 自定义群发工具类
 */
public class SendallUtil {
    private static Logger log = LoggerFactory.getLogger(SendallUtil.class);

    // 根据分组进行群发消息（POST）
    public final static String sendall_by_group = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";

    // 根据openid列表群发（POST）
    public final static String sendall_by_openids = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";

    // 预览接口（POST）
    public final static String sendall_for_preview = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";


    /**
     * @Author 温枝达
     * @Email alabadazi@gmail.com
     * @Date 2017/3/30 15:29
     * @Description 群发预览文本消息
     */
    public static boolean createPreviewTextMessage(PreviewTextMessage previewTextMessage, String accessToken) {
        boolean result = false;
        String url = sendall_for_preview.replace("ACCESS_TOKEN", accessToken);
        // 将对象转换成JSON字符串
        String jsonText = JSONObject.fromObject(previewTextMessage).toString();
        // 发起POST请求创建菜单
        JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", jsonText);

        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) { // 发送正常
                result = true;
            } else {
                result = false;
                log.error("群发预览文本消息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return result;
    }


    /**
     * @Author 温枝达
     * @Email alabadazi@gmail.com
     * @Date 2017/3/30 15:29
     * @Description 群发文本消息
     */
    public static boolean createTextMessage(TextMessageToAll textMessageToAll, String accessToken) {
        boolean result = false;
        String url = sendall_by_group.replace("ACCESS_TOKEN", accessToken);
        // 将对象转换成JSON字符串
        String jsonText = JSONObject.fromObject(textMessageToAll).toString();
        // 发起POST请求创建菜单
        JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", jsonText);

        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) { // 发送正常
                result = true;
            } else {
                result = false;
                log.error("群发文本消息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return result;
    }



    /**
     * @Author 温枝达
     * @Email alabadazi@gmail.com
     * @Date 2017/3/30 23:44
     * @Description 发送预览图文消息
     */
    public static boolean createPreviewMpnewsMessage(PreviewMpnewsMessage previewMpnewsMessage, String accessToken) {
        boolean result = false;
        String url = sendall_for_preview.replace("ACCESS_TOKEN", accessToken);
        // 将对象转换成JSON字符串
        String jsonText = JSONObject.fromObject(previewMpnewsMessage).toString();
        // 发起POST请求创建菜单
        JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", jsonText);

        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) { // 发送正常
                result = true;
            } else {
                result = false;
                log.error("群发预览图文消息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return result;
    }


    /**
     * @Author 温枝达
     * @Email alabadazi@gmail.com
     * @Date 2017/3/31 0:16
     * @Description 发送图文消息
     */
    public static boolean createMpnewsMessage(MpnewsMessageToAll mpnewsMessageToAll, String accessToken) {
        boolean result = false;
        String url = sendall_by_group.replace("ACCESS_TOKEN", accessToken);
        // 将对象转换成JSON字符串
        String jsonText = JSONObject.fromObject(mpnewsMessageToAll).toString();
        // 发起POST请求创建菜单
        JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", jsonText);

        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) { // 发送正常
                result = true;
            } else {
                result = false;
                log.error("群发图文消息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return result;
    }


}
