package com.lbd.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/2/19 14:43
 * @Description 基实体类
 */
public class BaseModel implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
