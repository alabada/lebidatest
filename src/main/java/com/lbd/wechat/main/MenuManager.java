package com.lbd.wechat.main;

import com.lbd.wechat.menu.*;
import com.lbd.wechat.pojo.Token;
import com.lbd.wechat.util.CommonUtil;
import com.lbd.wechat.util.MenuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义菜单结构
 *
 * @author wenzhida
 */
public class MenuManager {

    private static Logger log = LoggerFactory.getLogger(MenuManager.class);

    /**
     * 定义菜单结构
     *
     * @return
     */
    private static Menu getMenu() {

//        ViewButton btnTestAuth = new ViewButton();
//        btnTestAuth.setName("测试授权");
//        btnTestAuth.setType("view");
//        btnTestAuth.setUrl("http://lbd-qa.linesum.com/auth.shtml?redirect=xxx");

        // 我的快递菜单
        ViewButton btn11 = new ViewButton();
        btn11.setName("取快递");
        btn11.setType("view");
        btn11.setUrl("https://www.baidu.com/");

        ViewButton btn12 = new ViewButton();
        btn12.setName("物流查询");
        btn12.setType("view");
        btn12.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxebe0dfbffbab06bb&redirect_uri=http%3A%2F%2Flbd-qa.linesum.com%2Fauthorization.shtml&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

        ViewButton btn13 = new ViewButton();
        btn13.setName("手机绑定");
        btn13.setType("view");
        btn13.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxebe0dfbffbab06bb&redirect_uri=http%3A%2F%2Flbd-qa.linesum.com%2Fauthorization.shtml&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

        // 快递小哥菜单
        ViewButton btn21 = new ViewButton();
        btn21.setName("账号注册");
        btn21.setType("view");
        btn21.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxebe0dfbffbab06bb&redirect_uri=http%3A%2F%2Flbd-qa.linesum.com%2Fauthorization.shtml&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

        ViewButton btn22 = new ViewButton();
        btn22.setName("个人中心");
        btn22.setType("view");
        btn22.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxebe0dfbffbab06bb&redirect_uri=http%3A%2F%2Flbd-qa.linesum.com%2Fauthorization.shtml&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

        ViewButton btn23 = new ViewButton();
        btn23.setName("投建查询");
        btn23.setType("view");
        btn23.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxebe0dfbffbab06bb&redirect_uri=http%3A%2F%2Flbd-qa.linesum.com%2Fauthorization.shtml&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

        // 我的乐必达菜单
        ViewButton btn31 = new ViewButton();
        btn31.setName("个人中心");
        btn31.setType("view");
        btn31.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxebe0dfbffbab06bb&redirect_uri=http%3A%2F%2Flbd-qa.linesum.com%2Fauthorization.shtml&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

        ViewButton btn32 = new ViewButton();
        btn32.setName("快件派发");
        btn32.setType("view");
        btn32.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxebe0dfbffbab06bb&redirect_uri=http%3A%2F%2Flbd-qa.linesum.com%2Fauthorization.shtml&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

        ViewButton btn33 = new ViewButton();
        btn33.setName("快件中心");
        btn33.setType("view");
        btn33.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxebe0dfbffbab06bb&redirect_uri=http%3A%2F%2Flbd-qa.linesum.com%2Fauthorization.shtml&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

//        ViewButton btn14 = new ViewButton();
//        btn14.setName("demo");
//        btn14.setType("view");
//        btn14.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxebe0dfbffbab06bb&redirect_uri=http%3A%2F%2Flbd-qa.linesum.com%2Fauthorization.shtml&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");



        //
        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("我的快递");
        mainBtn1.setSubButton(new Button[]{btn11,btn12,btn13});

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("快递小哥");
        mainBtn2.setSubButton(new Button[]{btn21,btn22,btn23});

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("我的");
        mainBtn3.setSubButton(new Button[]{btn31,btn32,btn33});

        Menu menu = new Menu();
        menu.setButton(new Button[]{mainBtn1});

        return menu;
    }

    public static void main(String[] args) {
        // 第三方用户唯一凭证
        String appId = "wxb63e6e0f22d99f6f";
        // 第三方用户唯一凭证密钥
        String appSecret = "4016f84cd6264a176742587eb93e4dc4";

        // 调用接口获取凭证
        Token token = CommonUtil.getToken(appId, appSecret);

        if (null != token) {
            // 创建菜单
            boolean result = MenuUtil.createMenu(getMenu(), token.getAccessToken());

            // 判断菜单创建结果
            if (result) {
                log.info("菜单创建成功!");
            } else {
                log.info("菜单创建失败!");
            }
        }
    }
}
