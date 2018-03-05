package com.dm.srv.system;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.BaseDto;
import com.dm.dto.RoleDto;
import com.dm.dto.SysLogDto;
import com.dm.dto.UserDeptRoleDto;
import com.dm.dto.UserRoleDto;
import com.dm.entity.UserEntity;

public interface StaffManageSrv {

	/**
	 * 获取所有角色
	 * @return
	 */
	List<RoleDto> getRoles();

	/**
	 * 获取人员列表信息
	 * @param userName
	 * @param userId
	 * @param emailAddress
	 * @param roleId
	 * @return
	 */
	List<UserRoleDto> getUserList(String userName,String userId,String emailAddress,String roleId,int start,int length);

	/**
	 * 获取员工总数据
	 */
	int getStaffCount(String userName,String userId,String emailAddress,String roleId);

	/**
	 * 获取角色信息
	 * @return
	 */
	UserDeptRoleDto getStaffInfo(String userId);

	/**
	 * 获取role信息
	 * @return
	 */
	List<UserDeptRoleDto> getRoleInfo(String userId);

	/**
	 * 获取角色部门信息
	 * @return
	 */
	List<UserDeptRoleDto> getDeptInfo(String userId);

	/**
	 * 新增用户信息与插入部门信息
	 * @return
	 */
	BaseDto addUser(UserDeptRoleDto userDeptRoleDto,String loginId,String dept,String Flg);

	/**
	 *修改用户信息
	 * @return
	 */
	BaseDto updateUser(UserDeptRoleDto userDeptRoleDto,String loginId,String dept,String Flg);

	/**
	 *删除所属部门
	 * @return
	 */
	BaseDto deleteDept(String userId,String deptId);

	/**
	 *删除所在行用户信息
	 * @return
	 */
	BaseDto deleteFlg(String userId,String loginId);

	/**
	 *批量删除选中行用户信息
	 * @return
	 */
	BaseDto deleteAllFlg(@Param("userId")String userId,@Param("loginId") String loginId);

	/**
	 *获取系统日志列表
	 * @return
	 */
	List<SysLogDto> getLog(SysLogDto system,int start,int length);
	/**
	 * 获取数据总条数
	 */
	int getTatal(SysLogDto system);
}
