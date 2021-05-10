package com.iVoting.dto;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class UserDTO {
	private String userId;

	private String account;

	private String password;

	private String passwordHint;

	private String name;

	private String status;

	private Date joinDate;

	private Date modifyDate;

	private String modifyMan;

	private String roleId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId( String roleId ) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId( String userId ) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount( String account ) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword( String password ) {
		this.password = password;
	}

	public String getPasswordHint() {
		return passwordHint;
	}

	public void setPasswordHint( String passwordHint ) {
		this.passwordHint = passwordHint;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus( String status ) {
		this.status = status;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate( Date joinDate ) {
		this.joinDate = joinDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate( Date modifyDate ) {
		this.modifyDate = modifyDate;
	}

	public String getModifyMan() {
		return modifyMan;
	}

	public void setModifyMan( String modifyMan ) {
		this.modifyMan = modifyMan;
	}

	// ALT + SHIFT + S + S
	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", account=" + account + ", password=" + password + ", passwordHint=" + passwordHint + ", name=" + name + ", status=" + status + ", joinDate=" + joinDate + ", modifyDate=" + modifyDate + ", modifyMan=" + modifyMan + "]";
	}

}
