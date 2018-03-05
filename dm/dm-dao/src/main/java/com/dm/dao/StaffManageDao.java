package com.dm.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.ParmMstDto;
import com.dm.dto.RoleDto;
import com.dm.dto.SysLogDto;
import com.dm.dto.UserDeptRoleDto;
import com.dm.dto.UserRoleDto;
import com.dm.entity.UserEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：人员维护
 */
public interface StaffManageDao {

	/**
	 * 获取所有角色
	 * @return
	 */
	List<RoleDto> getRoles();

	/**
	 * 获取人员列表
	 * @param userName
	 * @param userId
	 * @param emailAddress
	 * @param roleId
	 * @return
	 */
	List<UserRoleDto> getUserList(@Param("userName")String userName, @Param("userId")String userId, @Param("emailAddress")String emailAddress, @Param("roleId")String roleId,@Param("start")int start,@Param("length") int length);

	/**
	 * 获取人员总数据
	 */
	int getStaffCount(@Param("userName")String userName, @Param("userId")String userId, @Param("emailAddress")String emailAddress, @Param("roleId")String roleId);

	/**
	 * 获取角色信息
	 * @param userId 用户id
	 * @return UserDeptRoleDto
	 */
	UserDeptRoleDto getStaffInfo(@Param("userId")String userId);

	/**
	 * 获取role信息
	 * @param userId 用户id
	 * @return list
	 */
	List<UserDeptRoleDto> getRoleInfo(@Param("userId")String userId);

	/**
	 * 获取角色部门信息
	 * @param userId 用户id
	 * @return list
	 */
	List<UserDeptRoleDto> getDeptInfo(@Param("userId")String userId);

	/**
	 * 获取最后一条数据
	 * @return UserDeptRoleDto
	 */
	UserDeptRoleDto getFinal();

	/**
	 * 新增用户信息
	 * @param UserDeptRoleDto
	 * @param loginId   登录人id
	 * @return
	 */
	int addUser(@Param("user")UserDeptRoleDto userDeptRoleDto,@Param("loginId")String loginId);

	/**
	 * 追加行部门信息插入
	 * @param addlist   存放部门信息的list
	 * @param loginId  登录人id
	 * @return
	 */
	int addDept(@Param("list") List<UserDeptRoleDto> addlist,@Param("loginId") String loginId);

	/**
	 * 修改用户信息
	 * @param userDeptRoleDto
	 * @param loginId   登录人id
	 * @return
	 */
	int updateUser(@Param("use")UserDeptRoleDto userDeptRoleDto,@Param("loginId")String loginId);

	/**
	 * 删除所属部门
	 * @param  userId  用户id
	 * @param  deptId  部门id
	 * @return
	 */
	int deleteDept(@Param("userId")String userId,@Param("deptId")String deptId);

	/**
	 * 添加用户与角色
	 * @param  userId  用户id
	 * @param  roleId   角色id
	 * @param  loginiId  登录人id
	 * @return
	 */
	int addRole(@Param("list") List<UserDeptRoleDto>rolelist,@Param("loginId")String loginId);

	/**
	 * 删除用户与角色
	 * @param  userId  用户id
	 * @return
	 */
	int delRole(@Param("userId")String userId);

	/**
	 * 单行删除用户信息
	 * @param  userId  用户id
	 * @param  loginiId  登录人id
	 * @return
	 */
	int deleteFlg(@Param("userId")String userId,@Param("loginId")String  loginId);

	/**
	 * 获取系统日志列表
	 * @param sysLogDto
	 * @return list
	 */
	List<SysLogDto> getLog(@Param("sys")SysLogDto system,@Param("start")int start,@Param("length") int length);

	/**
	 * 获取数据总条数
	 */
	int getTatal(SysLogDto system);
}
