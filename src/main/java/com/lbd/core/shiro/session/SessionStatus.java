package com.lbd.core.shiro.session;

import java.io.Serializable;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/2/8 19:32
 * @Description Session 状态
 */
public class SessionStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    //是否踢出 true:有效，false：踢出。
    private Boolean onlineStatus = Boolean.TRUE;


    public Boolean isOnlineStatus() {
        return onlineStatus;
    }

    public Boolean getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }


}
