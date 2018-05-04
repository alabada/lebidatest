package com.lbd.wechat.constant;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/3/31 21:32
 * @Description 微信相关常量
 */
public class WechatConstants {

    /**
     * appId 第三方用户唯一凭证
     */
    public static final String APP_ID = "wxebe0dfbffbab06bb";

    /**
     * appSecret 第三方用户唯一凭证密钥
     */
    public static final String APP_SECRET = "2dcdf31f6f1ff8f04427d3f11c40e196";

    /**
     * 微信支付商户号
     */
    public static final String MCH_ID = "1420444802";

    /**
     * 微信支付API秘钥 liangzehe20170429liangqiangnings
     */
//    public static final String KEY = "QBmgXTJP7QDTudt42qN4Jy2zuc2BvfYh";
    public static final String KEY = "liangzehe20170429liangqiangnings";

    /**
     * 微信交易类型:公众号支付
     */
    public static final String TRADE_TYPE_JSAPI = "JSAPI";

    /**
     * WEB
     */
    public static final String WEB = "WEB";

    /**
     * 返回成功字符串
     */
    public static final String RETURN_SUCCESS = "SUCCESS";

    /**
     * 支付地址(包涵回调地址)
     */
    public static final String PAY_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2bef02f0ed84edfc&redirect_uri=http%3a%2f%2fwxpay.pes-soft.com%2fwxpay%2fm%2fweChat%2funifiedOrder&response_type=code&scope=snsapi_base#wechat_redirect";

    /**
     * 微信统一下单url
     */
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 微信申请退款url
     */
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 微信支付通知url
     */
    public static final String NOTIFY_URL = "http://lbd-qa.linesum.com/wxpayCallback.shtml";

    /**
     * 证书位置
     */
    public static final String CERT_PATH = "H:/Ws/pes-wxpay/src/main/webapp/cert/apiclient_cert.p12";


    /**
     * 图片资源地址
     */
    public static final String PIC_DIR_WIN = "C:/home/qishon/filesystem/uploadFiles" + "/thumbMedia/liangzehe.png";

    public static final String PIC_DIR_LINUX = "/home/qishon/filesystem/uploadFiles" + "/thumbMedia/liangzehe.png";

    public static final String PIC_DIR_MAC_LOCAL = "/Users/zhida.wen/res/img/filesystem/uploadFiles" + "/thumbMedia/liangzehe.png";

}
