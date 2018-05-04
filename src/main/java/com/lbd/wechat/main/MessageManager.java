package com.lbd.wechat.main;

import com.lbd.wechat.constant.WechatConstants;
import com.lbd.wechat.pojo.Token;
import com.lbd.wechat.sendall.ArticleToAll;
import com.lbd.wechat.sendall.MpnewsMessageToAll;
import com.lbd.wechat.sendall.PreviewMpnewsMessage;
import com.lbd.wechat.sendall.PreviewTextMessage;
import com.lbd.wechat.util.CommonUtil;
import com.lbd.wechat.util.SendallUtil;
import com.lbd.wechat.util.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/3/30 13:17
 * @Description 群发消息
 */
public class MessageManager {

    private static Logger log = LoggerFactory.getLogger(MessageManager.class);

    /**
     * 定义预览文本消息结构
     *
     * @return
     */
    private static PreviewTextMessage getPreviewTextMessage() {
        PreviewTextMessage previewTextMessage = new PreviewTextMessage();

        previewTextMessage.setMsgtype("text");
        previewTextMessage.setTouser("oklr7wBTSQtkjhqnmOM8A1_6nhuM");

        Map<String, String> map = new HashMap<String, String>();
        map.put("content", "test send to openid");
        previewTextMessage.setText(map);

        return previewTextMessage;
    }

    private static Map<String, Object> getArticles(String accessToken) throws IOException {
        Map<String, Object> articles = new HashMap<String, Object>();
        ArticleToAll articleToAll = new ArticleToAll();
        articleToAll.setThumb_media_id(UploadUtil.uploadImg(accessToken, WechatConstants.PIC_DIR_MAC_LOCAL));
        articleToAll.setAuthor("");
        articleToAll.setTitle("乐必达给您送积分了");
        articleToAll.setContent_source_url("http://m.liangzehe.com/");
        articleToAll.setDigest("");
        articleToAll.setContent("乐必达给您送积分了。。。");
        articleToAll.setShow_cover_pic("1");
        List<ArticleToAll> articleToAlls = new ArrayList<ArticleToAll>();
        articleToAlls.add(articleToAll);
        articles.put("articles", articleToAlls);

        return articles;
    }

    /**
     * 定义预览图文消息结构
     * @return
     */
    private static PreviewMpnewsMessage getPreviewMpnewsMessage(String accessToken) {

        PreviewMpnewsMessage previewMpnewsMessage = new PreviewMpnewsMessage();
        previewMpnewsMessage.setMsgtype("mpnews");
        previewMpnewsMessage.setTouser("oklr7wBTSQtkjhqnmOM8A1_6nhuM");

        Map<String, String> mpNews = new HashMap<String, String>();
        try {
            Map<String, Object> articles = getArticles(accessToken);
            mpNews.put("media_id", UploadUtil.uploadNews(accessToken, articles));
        } catch (IOException e) {

        }
        previewMpnewsMessage.setMpnews(mpNews);

        return previewMpnewsMessage;
    }

    /**
     * 定义群发图文消息结构
     * @return
     */
    private static MpnewsMessageToAll getMpnewsMessageToAll(String accessToken) {

        MpnewsMessageToAll mpnewsMessageToAll = new MpnewsMessageToAll();
        mpnewsMessageToAll.setMsgtype("mpnews");
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("is_to_all", true);
        mpnewsMessageToAll.setFilter(filter);

        Map<String, String> mpnews = new HashMap<String, String>();
        try {
            Map<String, Object> articles = getArticles(accessToken);
            mpnews.put("media_id", UploadUtil.uploadNews(accessToken, articles));
        } catch (IOException e) {

        }
        mpnewsMessageToAll.setMpnews(mpnews);

        return mpnewsMessageToAll;
    }


    public static void main(String[] args) {

        // 调用接口获取凭证
        Token token = CommonUtil.getToken(WechatConstants.APP_ID, WechatConstants.APP_SECRET);

        if (null != token) {
            boolean result = false;
            // 群发消息 flag: 0-发送预览文本消息    1-发送图文预览消息      2-发送图文消息
            int flag = 1;
            if (0 == flag) {
                result = SendallUtil.createPreviewTextMessage(getPreviewTextMessage(), token.getAccessToken());
            } else if (1 == flag) {
                result = SendallUtil.createPreviewMpnewsMessage(getPreviewMpnewsMessage(token.getAccessToken()), token.getAccessToken());
            } else if (2 == flag) {
                result = SendallUtil.createMpnewsMessage(getMpnewsMessageToAll(token.getAccessToken()), token.getAccessToken());
            }

            // 判断群发结果
            if (result) {
                log.info("群发成功!");
            } else {
                log.info("群发失败!");
            }
        }
    }
}
