package com.lbd.common.dao;

import java.util.List;
import java.util.Map;

import com.lbd.common.model.UUser;
import com.lbd.permission.bo.URoleBo;

public interface UUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UUser record);

    int insertSelective(UUser record);

    UUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UUser record);

    int updateByPrimaryKey(UUser record);

    int updateByOpenIdSelective(UUser record);

    int updateByOpenId(UUser record);

    int updateScore(Integer score);

	UUser login(Map<String, Object> map);

	UUser findUserByEmail(String email);

	UUser findUserByOpenId(String openId);

	List<URoleBo> selectRoleByUserId(Long id);

	Integer selectUserIdByOpenId(String openId);

}