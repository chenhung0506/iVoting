package com.iVoting.dto;

public class LmsApiKeysVO {
	private String sourceName; // 屬於哪個系統Ex:NextWms

	private String userName; // 打對方api需要的帳號

	private String password; // 打對方api需要的密碼

	private String apiDesc; // api描述

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName( String sourceName ) {
		this.sourceName = sourceName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName( String userName ) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword( String password ) {
		this.password = password;
	}

	public String getApiDesc() {
		return apiDesc;
	}

	public void setApiDesc( String apiDesc ) {
		this.apiDesc = apiDesc;
	}
}
