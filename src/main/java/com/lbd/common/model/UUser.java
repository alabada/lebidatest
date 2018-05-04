package com.lbd.common.model;

import java.io.Serializable;
import java.util.Date;

import net.sf.json.JSONObject;

/**
 * @Author 温枝达
 * @Date 2017/2/7 17:04
 * @Description
 */
public class UUser implements Serializable {

    private static final long serialVersionUID = 1L;

    //0:禁止登录
    public static final Long _0 = new Long(0);

    //1:有效
    public static final Long _1 = new Long(1);

    private Long id;

    // 用户标识
    private String openId;

    /**
     * 昵称
     */
    private String nickname;

    // 性别（1是男性，2是女性，0是未知）
    private int sex;

    // 省份
    private String province;

    // 城市
    private String city;

    // 国家
    private String country;

    // 用户头像连接
    private String headImgUrl;

    /**
     * 邮箱 | 登录帐号
     */
    private String email;

    /**
     * 密码 transient关键字阻止该字段被序列化
     */
    private transient String pswd;

    // 用户手机号码
    private String mobile;

    // 用户绑定qq号码
    private String qq;

    // 积分
    private Integer score;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 1:有效，0:禁止登录
     */
    private Long status;


    public UUser() {
    }

    public UUser(UUser user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.pswd = user.getPswd();
        this.createTime = user.getCreateTime();
        this.lastLoginTime = user.getLastLoginTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String toString() {
        return JSONObject.fromObject(this).toString();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}