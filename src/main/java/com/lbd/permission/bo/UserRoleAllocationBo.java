package com.lbd.permission.bo;

import java.io.Serializable;

import com.lbd.common.model.UUser;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/2/8 19:38
 * @Description 用户角色分配 查询列表BO
 */
public class UserRoleAllocationBo extends UUser implements Serializable {
    private static final long serialVersionUID = 1L;

    //Role Name列转行，以,分割
    private String roleNames;
    //Role Id列转行，以‘,’分割
    private String roleIds;

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

}
