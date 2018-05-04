package com.lbd.wechat.controller;

import com.lbd.common.model.UUser;
import com.lbd.common.utils.LoggerUtils;
import com.lbd.user.service.UUserService;
import com.lbd.wechat.constant.WechatConstants;
import com.lbd.wechat.main.UrlEncodeManager;
import com.lbd.wechat.pojo.SNSUserInfo;
import com.lbd.wechat.pojo.WeixinOauth2Token;
import com.lbd.wechat.service.CoreService;
import com.lbd.wechat.service.PayService;
import com.lbd.wechat.util.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class WechatController {

    @Autowired
    UUserService userService;

    @Autowired
    private PayService payService;


    /**
     * 请求校验
     *
     * @param request
     * @param response
     * @throws Exception
     * @Title accessWechat
     * @Description 用于用户在微信端连接服务器
     */
    @ResponseBody()
    @RequestMapping(value = "/accessWechat", method = RequestMethod.GET)
    public void accessWechat(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //微信加密签名
        String signature = request.getParameter("signature");
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 请求校验，若校验成功则原样返回 echostr，表示接入成功，否则接入失败
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
    }

    /**
     * 请求校验与处理
     *
     * @param request
     * @param response
     * @throws Exception
     * @Title requestRrocessing
     * @Description 微信消息接入接口
     */
    @ResponseBody()
    @RequestMapping(value = "/accessWechat", method = RequestMethod.POST)
    public void requestRrocessing(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 接收参数：微信加密签名、时间戳、随机数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");

        PrintWriter out = response.getWriter();
        // 请求校验
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            // 调用服务类接收处理请求
            String respXml = CoreService.processRequest(request);
            out.print(respXml);
        }
        out.close();
    }

    /**
     * @Author 温枝达
     * @Email alabadazi@gmail.com
     * @Date 2017/3/14 13:35
     * @Description oauth2授权
     */
    @RequestMapping(value = "/authorization", method = RequestMethod.GET)
    public void authorization(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        // 用户同意授权后，能够获取到code
        String code = request.getParameter("code");
        String redirect = request.getParameter("redirect");
        String redirectUrl = "";

        // 用户同意授权
        if (!"authdeny".equals(code)) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken("wxebe0dfbffbab06bb", "2dcdf31f6f1ff8f04427d3f11c40e196", code);

            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();

            // 用户标识
            String openId = weixinOauth2Token.getOpenId();

            // 获取用户信息 scope 为 snsapi_userinfo才需要做一下的操作
            SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);

            // 实现用户数据插入或保存（业务不多直接写controller）
            UUser user = userService.findUserByOpenId(openId);
            if (null != user) { // 用户已存在，做一次更新操作
                user.setLastLoginTime(new Date());
                user.setUpdateTime(new Date());
                user.setHeadImgUrl(snsUserInfo.getHeadImgUrl());
                user.setCity(snsUserInfo.getCity());
                user.setProvince(snsUserInfo.getProvince());
                user.setCountry(snsUserInfo.getCountry());
                user.setNickname(snsUserInfo.getNickname());
                user.setSex(snsUserInfo.getSex());
                userService.updateByOpenIdSelective(user);
            } else { // 用户初次到访，保存到数据库
                user = new UUser();
                user.setOpenId(snsUserInfo.getOpenId());
                user.setHeadImgUrl(snsUserInfo.getHeadImgUrl());
                user.setCity(snsUserInfo.getCity());
                user.setProvince(snsUserInfo.getProvince());
                user.setCountry(snsUserInfo.getCountry());
                user.setNickname(snsUserInfo.getNickname());
                user.setSex(snsUserInfo.getSex());
                user.setPswd("c25baf3538db9cd490736598f88d2ad8"); // 密码默认123456
                user.setScore(0); //
                user.setCreateTime(new Date());
                user.setUpdateTime(new Date());
                user.setLastLoginTime(new Date());
                user.setStatus((long)1);
                userService.insertSelective(user);
            }

            if (null != redirect && !redirect.isEmpty()) {
                // 跳转到 m.liangzehe.com/login?openid=xxx&redirect=xxx
                redirectUrl = "http://m.liangzehe.com/login?openid=" + snsUserInfo.getOpenId() + "&redirect=" + redirect;
            } else {
                // 跳转到首页：m.liangzehe.com/
                redirectUrl = "http://m.liangzehe.com";
            }
        }
        response.sendRedirect(redirectUrl);
    }


    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public void auth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String redirect = request.getParameter("redirect");

        // 授权接口url
        final String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxebe0dfbffbab06bb&redirect_uri=" +
                UrlEncodeManager.urlEncodeUTF8("http://lbd-qa.linesum.com/authorization.shtml?redirect=" + redirect) +
                "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

        response.sendRedirect(requestUrl);
//        CommonUtil.httpsRequest(requestUrl, "GET", null);
    }


    /**
     * 统一下单
     */
    @RequestMapping(value = "/unifiedOrder")
    @ResponseBody
    public Map<String, Object> unifiedOrder(HttpServletRequest request) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String openId = request.getParameter("open_id");
        String orderId = request.getParameter("order_id");
        String type = request.getParameter("type");
        LoggerUtils.debug(WechatController.class, "订单类型：" + type);
        try {
            int typeInt = Integer.parseInt(type);
            if (1 != typeInt && 2 != typeInt) {
                resultMap.put("code", 500);
                resultMap.put("msg", "请传入正确的订单类型 -- 1：菜品；2：佛具");
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("code", 500);
            resultMap.put("msg", "请传入正确的订单类型 -- 1：菜品；2：佛具");
            return resultMap;
        }

        // 调用统一下单service
        String prepayId = payService.unifiedOrder(openId, orderId, type, request.getRemoteAddr());
        if (!PayUtils.isEmpty(prepayId)) {
            String timeStamp = PayUtils.getTimeStamp();// 当前时间戳
            String nonceStr = PayUtils.getRandomStr(20);// 不长于32位的随机字符串

            SortedMap<String, Object> signMap = new TreeMap<String, Object>();// 自然升序map
            signMap.put("appId", WechatConstants.APP_ID);
            signMap.put("package", prepayId);
            signMap.put("timeStamp", timeStamp);
            signMap.put("nonceStr", nonceStr);
            signMap.put("signType", "MD5");

            //

            resultMap.put("code", 200);
            resultMap.put("msg", "验证成功");
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("appId", WechatConstants.APP_ID);
            result.put("timeStamp", timeStamp);
            result.put("nonceStr", nonceStr);
            result.put("package", prepayId);
            result.put("signType", "MD5");
            result.put("paySign", PayUtils.getSign(signMap));// 获取签名

            resultMap.put("result", result);
            return resultMap;
        }

        resultMap.put("code", 500);
        resultMap.put("msg", "统一下单获取数据出错，请刷新后再试！");
        return resultMap;
    }


    /**
     * 支付完成回调地址
     */
    @RequestMapping(value = "/wxpayCallback.shtml", method = RequestMethod.POST)
    public void callBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream is = request.getInputStream();
        HashMap<String, String> map = new HashMap<String, String>();
        LoggerUtils.fmtDebug(getClass(), "微信回调函数");
        // 1、读取传入信息并转换为map
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(is);
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        String payType = "";
        String memberId = "";
        Element root = document.getRootElement();
        List<Element> list = root.elements();
        for (Element e : list) {
            if (e.getName().trim().equals("payType")) {
                payType = e.getText().trim();
            } else if (e.getName().trim().equals("memberId")) {
                memberId = e.getText().trim();
            } else {
                map.put(e.getName().trim(), e.getText().trim());
            }
        }
        is.close();
        // 2、克隆传入的信息并进行验签
        HashMap<String, String> signMap = (HashMap<String, String>) map.clone();
        signMap.remove("sign");
        LoggerUtils.fmtDebug(getClass(), "验证签名: [%s]", map.toString());
        String key = WechatConstants.KEY;
        String sign = CheckUtil.signature(signMap, key);
        if (!sign.equals(map.get("sign"))) {
            LoggerUtils.fmtDebug(getClass(), "微信支付回调函数：验签错误");
            return;
        }
        // 信息处理
        String result_code = map.get("result_code");
        try {

            if ("SUCCESS".equals(result_code)) {
                //由于微信后台会同时回调多次，所以需要做防止重复提交操作的判断
                //此处放防止重复提交操作

            } else if ("FAIL".equals(result_code)) {

            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            //进行业务逻辑操作
            String outTradeNo = map.get("out_trade_no");

            LoggerUtils.fmtDebug(getClass(), "out_trade_no: [%s]", outTradeNo);

            payService.updateOrderStatus(outTradeNo);

        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtils.fmtDebug(getClass(), "回调用户中心错误");
        }


        // 返回信息，防止微信重复发送报文
        String result = "<xml>"
                + "<return_code><![CDATA[SUCCESS]]></return_code>"
                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml>";
        PrintWriter out = new PrintWriter(response.getOutputStream());
        out.print(result);
        out.flush();
        out.close();

    }


}
