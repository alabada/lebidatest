package com.lbd.wechat.main;

import com.lbd.wechat.constant.WechatConstants;
import com.lbd.wechat.pojo.Token;
import com.lbd.wechat.sendall.ArticleToAll;
import com.lbd.wechat.util.CommonUtil;
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
 * @Description 素材上传
 */
public class UploadManager {

    private static Logger log = LoggerFactory.getLogger(UploadManager.class);


    public static void main(String[] args) throws IOException{

        // 调用接口获取凭证
        Token token = CommonUtil.getToken(WechatConstants.APP_ID, WechatConstants.APP_SECRET);

        if (null != token) {
            if (false) {
                String result = UploadUtil.uploadImg(token.getAccessToken(), WechatConstants.PIC_DIR_WIN);
                System.out.println(result);
            } else {

                Map<String, Object> articles = new HashMap<String, Object>();
                ArticleToAll articleToAll = new ArticleToAll();
                articleToAll.setThumb_media_id(UploadUtil.uploadImg(token.getAccessToken(), WechatConstants.PIC_DIR_WIN));
                articleToAll.setAuthor("");
                articleToAll.setTitle("乐必达给您送积分了");
                articleToAll.setContent_source_url("https://www.baidu.com/");
                articleToAll.setDigest("");
                articleToAll.setContent("乐必达给您送积分了。。。");
                articleToAll.setShow_cover_pic("1");
                List<ArticleToAll> articleToAlls = new ArrayList<ArticleToAll>();
                articleToAlls.add(articleToAll);
                articles.put("articles", articleToAlls);

                String result = UploadUtil.uploadNews(token.getAccessToken(), articles);
                System.out.println(result);
            }

        }
    }
}
