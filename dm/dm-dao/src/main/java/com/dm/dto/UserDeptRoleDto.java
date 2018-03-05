package com.dm.dto;

public class UserDeptRoleDto extends BaseDto{

	/*
	 * 用户id
	 */
	private String userId;
	/*
	 * 域账号
	 */
	private String userCode;
	/*
	 * 用户名
	 */
	private String userName;
	/*
	 * 邮箱
	 */
	private String email;
	/*
	 * 角色Id
	 */
	private int roleId;
	/*
	 * 角色名称
	 */
	private String roleName;
	/*
	 * 部门id
	 */
	private String deptId;
	/*
	 * 部门名
	 */
	private String deptName;
	/*
	 * 是否负责人
	 */
	private String leaderFlg;

	/*
	 * 部门id拼接String
	 */
	private String dept;
	/*
	 * 是否负责人拼接String
	 */
	private String Flg;


	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getFlg() {
		return Flg;
	}
	public void setFlg(String flg) {
		Flg = flg;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getLeaderFlg() {
		return leaderFlg;
	}
	public void setLeaderFlg(String leaderFlg) {
		this.leaderFlg = leaderFlg;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


}
