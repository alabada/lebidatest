package com.lbd.user.controller;

import com.lbd.common.controller.BaseController;
import com.lbd.common.model.UUser;
import com.lbd.common.utils.LoggerUtils;
import com.lbd.common.utils.StringUtils;
import com.lbd.common.utils.VerifyCodeUtils;
import com.lbd.core.shiro.token.manager.TokenManager;
import com.lbd.user.manager.UserManager;
import com.lbd.user.service.UUserService;
import net.sf.json.JSONObject;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @Author 温枝达
 * @Date 2017/2/7 15:36
 * @Description 用户登录相关，不需要做登录限制。scope注解表示每次接收一个请求创建一个action对象，若为单例模式，则多请求共用同一个action。注册时，若没有这个注解，注册完成后，下一个请求再注册一次，action里会保留上一次注册的信息。
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("u")
public class UserLoginController extends BaseController {

    @Resource
    UUserService userService;

    /**
     * 登录跳转
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("user/login");
    }

    /**
     * 注册跳转
     *
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public ModelAndView register() {
        return new ModelAndView("user/register");
    }

    /**
     * 注册 && 登录
     *
     * @param vcode  验证码
     * @param entity UUser实体
     * @return
     */
    @RequestMapping(value = "subRegister", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> subRegister(String vcode, UUser entity) {
        resultMap.put("status", 400);
        if (!VerifyCodeUtils.verifyCode(vcode)) {
            resultMap.put("message", "验证码不正确！");
            return resultMap;
        }
        String email = entity.getEmail();

        UUser user = userService.findUserByEmail(email);
        if (null != user) {
            resultMap.put("message", "帐号|Email已经存在！");
            return resultMap;
        }
        Date date = new Date();
        entity.setCreateTime(date);
        entity.setLastLoginTime(date);
        //把密码md5
        entity = UserManager.md5Pswd(entity);
        //设置有效
        entity.setStatus(UUser._1);

        entity = userService.insert(entity);
        LoggerUtils.fmtDebug(getClass(), "注册插入完毕！", JSONObject.fromObject(entity).toString());
        entity = TokenManager.login(entity, Boolean.TRUE);
        LoggerUtils.fmtDebug(getClass(), "注册后，登录完毕！", JSONObject.fromObject(entity).toString());
        resultMap.put("message", "注册成功！");
        resultMap.put("status", 200);
        return resultMap;
    }

    /**
     * 登录提交
     *
     * @param entity     登录的UUser
     * @param rememberMe 是否记住
     * @param request    request，用来取登录之前Url地址，用来登录后跳转到没有登录之前的页面。
     * @return
     */
    @RequestMapping(value = "submitLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submitLogin(UUser entity, Boolean rememberMe, HttpServletRequest request) {

        try {
            entity = TokenManager.login(entity, rememberMe);
            resultMap.put("status", 200);
            resultMap.put("message", "登录成功");

            SavedRequest savedRequest = WebUtils.getSavedRequest(request); // shiro 获取登录之前的地址
            String url = null;
            if (null != savedRequest) {
                url = savedRequest.getRequestUrl();
            }

            /**
             * 我们平常用的获取上一个请求的方式，在Session不一致的情况下是获取不到的
             * String url = (String) request.getAttribute(WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE);
             */
            LoggerUtils.fmtDebug(getClass(), "获取登录之前的URL:[%s]", url);
            // 如果登录之前没有地址，那么就跳转到首页。
            if (StringUtils.isBlank(url)) {
                url = request.getContextPath() + "/user/index.shtml";
            }
            //跳转地址
            resultMap.put("back_url", url);
        } catch (DisabledAccountException e) {
            resultMap.put("status", 500);
            resultMap.put("message", "帐号已经禁用。");
        } catch (Exception e) {
            resultMap.put("status", 500);
            resultMap.put("message", "帐号或密码错误");
        }

        return resultMap;
    }

    /**
     * 退出
     *
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> logout() {
        try {
            TokenManager.logout();
            resultMap.put("status", 200);
        } catch (Exception e) {
            resultMap.put("status", 500);
            logger.error("errorMessage:" + e.getMessage());
            LoggerUtils.fmtError(getClass(), e, "退出出现错误，%s。", e.getMessage());
        }
        return resultMap;
    }
}
