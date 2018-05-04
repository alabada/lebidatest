package com.lbd.user.service;

import java.util.List;
import java.util.Map;

import com.lbd.user.bo.UserBo;
import org.springframework.ui.ModelMap;

import com.lbd.common.model.UUser;
import com.lbd.core.mybatis.page.Pagination;
import com.lbd.permission.bo.URoleBo;
import com.lbd.permission.bo.UserRoleAllocationBo;

public interface UUserService {

	int deleteByPrimaryKey(Long id);

	UUser insert(UUser record);

    UUser insertSelective(UUser record);

    UUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UUser record);

    int updateByPrimaryKey(UUser record);

    int updateByOpenIdSelective(UUser record);

    int updateByOpenId(UUser record);
    
    UUser login(String email ,String pswd);

	UUser findUserByEmail(String email);

	UUser findUserByOpenId(String openId);

	Pagination<UserBo> findByPage(Map<String, Object> resultMap, Integer pageNo,
								  Integer pageSize);

	Map<String, Object> deleteUserById(String ids);

	Map<String, Object> updateForbidUserById(Long id, Long status);

	Pagination<UserRoleAllocationBo> findUserAndRole(ModelMap modelMap,
			Integer pageNo, Integer pageSize);

	List<URoleBo> selectRoleByUserId(Long id);

	Map<String, Object> addRole2User(Long userId, String ids);

	Map<String, Object> deleteRoleByUserIds(String userIds);


}
