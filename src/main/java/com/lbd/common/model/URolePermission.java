package com.lbd.common.model;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/2/8 15:38
 * @Description 中间表
 */
public class URolePermission implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * {@link URole.id}
     */
    private Long rid;
    /**
     * {@link UPermission.id}
     */
    private Long pid;

    public URolePermission() {
    }

    public URolePermission(Long rid, Long pid) {
        this.rid = rid;
        this.pid = pid;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}