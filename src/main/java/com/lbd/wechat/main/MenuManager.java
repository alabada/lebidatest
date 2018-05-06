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


        ViewButton btn11 = new ViewButton();
        btn11.setName("乐必达");
        btn11.setType("view");
        btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxebe0dfbffbab06bb&redirect_uri=http%3A%2F%2Flbd-qa.linesum.com%2Fauthorization.shtml&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

//        ViewButton btn14 = new ViewButton();
//        btn14.setName("乐必达");
//        btn14.setType("view");
//        btn14.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxebe0dfbffbab06bb&redirect_uri=http%3A%2F%2Flbd-qa.linesum.com%2Fauthorization.shtml&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");



        //
        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("乐必达");
        mainBtn1.setSubButton(new Button[]{btn11});

//        ComplexButton mainBtn2 = new ComplexButton();
//        mainBtn2.setName("购物");
//        mainBtn2.setSub_button(new Button[]{btn21, btn22, btn23, btn24, btn25});
//
//        ComplexButton mainBtn3 = new ComplexButton();
//        mainBtn3.setName("网页游戏");
//        mainBtn3.setSub_button(new Button[]{btn31, btn32});

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
