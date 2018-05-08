package com.lbd.wechat.service;

import com.lbd.wechat.message.resp.Article;
import com.lbd.wechat.message.resp.NewsMessage;
import com.lbd.wechat.message.resp.TextMessage;
import com.lbd.wechat.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 核心服务类
 *
 * @author wenzhida
 */
public class CoreService {

    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public static String processRequest(HttpServletRequest request) {
        // XML格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent = "未知的消息类型";
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方账号
            String fromUserName = requestMap.get("FromUserName");
            // 开发者微信号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(System.currentTimeMillis());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                respContent = "您发送的是文本消息";
                // 设置文本消息的内容
                textMessage.setContent(respContent);
                // 将文本消息对象转换成XML
                respXml = MessageUtil.messageToXml(textMessage);
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息";
                // 设置文本消息的内容
                textMessage.setContent(respContent);
                // 将文本消息对象转换成XML
                respXml = MessageUtil.messageToXml(textMessage);
            }
            // 语音消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是语音消息";
                // 设置文本消息的内容
                textMessage.setContent(respContent);
                // 将文本消息对象转换成XML
                respXml = MessageUtil.messageToXml(textMessage);
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息";
                // 设置文本消息的内容
                textMessage.setContent(respContent);
                // 将文本消息对象转换成XML
                respXml = MessageUtil.messageToXml(textMessage);
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息";
                // 设置文本消息的内容
                textMessage.setContent(respContent);
                // 将文本消息对象转换成XML
                respXml = MessageUtil.messageToXml(textMessage);
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息";
                // 设置文本消息的内容
                textMessage.setContent(respContent);
                // 将文本消息对象转换成XML
                respXml = MessageUtil.messageToXml(textMessage);
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 关注(订阅)
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    textMessage.setContent("终于等到你，你已获取999大红包");
                    // 将消息对象转换成XML
                    respXml = MessageUtil.messageToXml(textMessage);
                }
                // 取消关注（取消订阅）
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后，用户不会再收到公众账号发送的消息，不需要任何回复。
                }
                // 扫描带参数二维码
                else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                }
                // 上报地理位置
                else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // 事件KEY值，与创建菜单时的key值对应
                    String eventKey = requestMap.get("EventKey");
                    // 根据key值判断用户点击的按钮
                    if (eventKey.equals("oschina")) {
                        Article article = new Article();
                        article.setTitle("开源中国");
                        article.setDescription("开源中国社区成立于2008年8月，巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉.");
                        article.setPicUrl("");
                        article.setUrl("http://m.oschina.net");
                        List<Article> articleList = new ArrayList<Article>();
                        articleList.add(article);
                        // 创建图文消息
                        NewsMessage newsMessage = new NewsMessage();
                        newsMessage.setToUserName(fromUserName);
                        newsMessage.setFromUserName(toUserName);
                        newsMessage.setCreateTime(new Date().getTime());
                        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                        newsMessage.setArticleCount(articleList.size());
                        newsMessage.setArticles(articleList);
                        respXml = MessageUtil.messageToXml(newsMessage);
                    } else if (eventKey.equals("iteye")) {
                        textMessage.setContent("ITeye即创办于2003年9月的斯科拉都激发了斯柯达就发了束带结发两地分居爱上了肯德基弗拉快递费");
                        respXml = MessageUtil.messageToXml(textMessage);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }

}
