package com.dm.srv.impl.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.dao.StaffManageDao;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.RoleDto;
import com.dm.dto.SysLogDto;
import com.dm.dto.UserDeptRoleDto;
import com.dm.dto.UserRoleDto;
import com.dm.entity.UserEntity;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.srv.system.StaffManageSrv;
import com.dm.tool.Constant;

@Service
public class StaffManageSrvImpl extends BaseSrvImp implements StaffManageSrv {

	@Autowired
	private StaffManageDao sMDao;

	@Override
	public List<RoleDto> getRoles() {
		return sMDao.getRoles();
	}

	//获取员工信息列表
	@Override
	public List<UserRoleDto> getUserList(String userName, String userId, String emailAddress, String roleId,int start,int length) {

		return sMDao.getUserList(userName, userId, emailAddress, roleId,start,length);
	}

	//获取员工总数据
	public int getStaffCount(String userName, String userId, String emailAddress, String roleId) {
		return sMDao.getStaffCount(userName, userId, emailAddress, roleId);
	}

	//获取角色信息
	@Override
	public UserDeptRoleDto getStaffInfo(String userId) {
		return sMDao.getStaffInfo(userId);
	}

	//获取role信息
	@Override
	public List<UserDeptRoleDto> getRoleInfo(String userId) {
		return sMDao.getRoleInfo(userId);
	}

	//获取角色部门信息
	@Override
	public List<UserDeptRoleDto> getDeptInfo(String userId) {
		return sMDao.getDeptInfo(userId);
	}

	//新增用户信息
	@Override
	public BaseDto addUser(UserDeptRoleDto userDeptRoleDto,String loginId,String dept,String Flg) {
		BaseDto baseDto = new BaseDto();
		//获取用户信息最后一条数据 ，取得id
		UserDeptRoleDto user =sMDao.getFinal();
		//转换int类型
		String use = user.getUserId();
		int userI =Integer.parseInt(use);
		//自增长
		int userIi =userI+1;
		String userId = String.valueOf(userIi);
		//将userId放入对象中
		userDeptRoleDto.setUserId(userId);
		//传入对象，新增用户信息
		int add = sMDao.addUser(userDeptRoleDto,loginId);
		if (add>0) {
			//用户信息新增成功
			baseDto.setBizCode(BizCode.BIZ_CODE_INSERT_SUCCESS);
		}else {
			//用户信息新增失败
			baseDto.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
		}

		//删除用户角色
		int del = sMDao.delRole(userId);
		String[] roleValue = userDeptRoleDto.getRoleName().split(",");
		//创建一个集合对象
		List<UserDeptRoleDto> rolelist =  new ArrayList<>();
		//遍历值
		for(int q=0;q<roleValue.length;q++) {
			UserDeptRoleDto role = new UserDeptRoleDto();
			//向对象中写入数据
			role.setRoleName(roleValue[q]);
			role.setUserId(userId);
			//将数据放入集合
			rolelist.add(role);
		}
        if (rolelist.size()>0) {
        	//添加用户角色
			int addrole = sMDao.addRole(rolelist,loginId);
			if (addrole>0) {
				//角色添加成功
				baseDto.setBizCode(BizCode.BIZ_CODE_INSERT_SUCCESS);
			}else {
				//角色添加失败，参数错误
				baseDto.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
			}
		}
		//当存在添加部门时
		if (!dept.equals("")) {
		//当前部门行的数组
		String[] deptValue = dept.split(",");
		//部门对应的是否为负责人
		String[] leaderFlgValue = Flg.split(",");
		//创建一个集合对象
		List<UserDeptRoleDto> addlist =  new ArrayList<>();
		//遍历值
		for(int i=0;i<deptValue.length;i++) {
			UserDeptRoleDto newuser = new UserDeptRoleDto();
			//向对象中写入数据
			newuser.setDeptId(deptValue[i]);
			newuser.setLeaderFlg(leaderFlgValue[i]);
			newuser.setUserId(userId);
			//将数据放入集合中
			addlist.add(newuser);
		}
		if (addlist.size()>0) {
			//批量插入部门信息
			int adddept = sMDao.addDept(addlist, loginId);
			if (adddept>0) {
				baseDto.setBizCode(BizCode.BIZ_CODE_INSERT_SUCCESS);
			}else {
				baseDto.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
			}
		}
	 }
		return baseDto;
	}

	//修改用户信息
	@Override
	public BaseDto updateUser(UserDeptRoleDto userDeptRoleDto, String loginId,String dept,String Flg) {
		BaseDto baseDto = new BaseDto();
		//修改用户信息
		int update = sMDao.updateUser(userDeptRoleDto, loginId);
		if (update>0) {
			//用户信息修改成功
			baseDto.setBizCode(BizCode.BIZ_CODE_UPDATE_SUCCESS);
		}else {
			//用户信息修改失败
			baseDto.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
		}
		String userId = userDeptRoleDto.getUserId();
		//删除用户角色
		int del = sMDao.delRole(userId);
		String[] roleValue = userDeptRoleDto.getRoleName().split(",");
		//创建一个集合对象
		List<UserDeptRoleDto> rolelist =  new ArrayList<>();
		//遍历值
		for(int q=0;q<roleValue.length;q++) {
			UserDeptRoleDto role = new UserDeptRoleDto();
			//向对象中写入数据
			role.setRoleName(roleValue[q]);
			role.setUserId(userId);
			//将数据放入集合
			rolelist.add(role);
		}
        if (rolelist.size()>0) {
        	//添加用户角色
			int addrole = sMDao.addRole(rolelist,loginId);
			if (addrole>0) {
				//角色添加成功
				baseDto.setBizCode(BizCode.BIZ_CODE_UPDATE_SUCCESS);
			}else {
				//角色添加失败，参数错误
				baseDto.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
			}
		}
		//当存在添加部门时
		if (!dept.equals("")) {
			//当前部门行的数组
			String[] deptValue = dept.split(",");
			//部门对应的是否为负责人
			String[] leaderFlgValue = Flg.split(",");
			//创建一个集合对象
			List<UserDeptRoleDto> addlist =  new ArrayList<>();
			//遍历值
			for(int i=0;i<deptValue.length;i++) {
				UserDeptRoleDto newuser = new UserDeptRoleDto();
				//向对象中写入数据
				newuser.setDeptId(deptValue[i]);
				newuser.setLeaderFlg(leaderFlgValue[i]);
				newuser.setUserId(userDeptRoleDto.getUserId());
				//将数据放入集合中
				addlist.add(newuser);
			}
			if (addlist.size()>0) {
				//删除部门信息
				for(int a = 0; a<addlist.size(); a++) {
					int deleteDept = sMDao.deleteDept(userId, addlist.get(a).getDeptId());
				}
				//批量插入部门信息
				int adddept = sMDao.addDept(addlist, loginId);
				if (adddept>0) {
					//部门信息插入成功，这边为修改信息部分使用更新成功Msg
					baseDto.setBizCode(BizCode.BIZ_CODE_UPDATE_SUCCESS);
				}else {
					//参数错误，信息插入失败
					baseDto.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
				}
			}
		}


		return baseDto;
	}

	//删除所属部门
	@Override
	public BaseDto deleteDept(String userId,String deptId) {
		BaseDto baseDto = new BaseDto();
		//删除所属部门
		int delete = sMDao.deleteDept(userId,deptId);
		if (delete>0) {
			baseDto.setBizCode(BizCode.BIZ_CODE_DELETE_SUCCESS);
		}else {
			baseDto.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
		}
		return baseDto;
	}

	//删除所在行的用户信息
	@Override
	public BaseDto deleteFlg(String userId, String loginId) {
		BaseDto base = new BaseDto();
		//删除所在行的用户信息
		int delete = sMDao.deleteFlg(userId, loginId);
		if(delete>0) {
			//信息删除成功(将flg=1改为flag=0)
			base.setBizCode(BizCode.BIZ_CODE_DELETE_SUCCESS);
		}else {
			//删除失败
			base.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
		}
		return base;
	}

	//批量删除选中行的用户信息
	@Override
	public BaseDto deleteAllFlg(String userId,String loginId) {
		BaseDto base = new BaseDto();
		 //下拉框查出的类型数组
		String[] userIdValue=   userId.split(",");
		String userID ="";
		for(int i=0;i<userIdValue.length;i++ ) {
			userID = userIdValue[i];
			//执行删除操作
			int delet = sMDao.deleteFlg(userID, loginId);
			if(delet>0) {
				base.setBizCode(BizCode.BIZ_CODE_DELETE_SUCCESS);
			}else {
				base.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
			}
		}
		return base;
	}
	//获取系统日志列表
	@Override
	public List<SysLogDto> getLog(SysLogDto system,int start,int length) {
		List<SysLogDto> list = sMDao.getLog(system,start,length);
		return list;
	}

	//获取数据总条数
	@Override
	public int getTatal(SysLogDto system) {
		int count = sMDao.getTatal(system);
		return count;
	}

}
