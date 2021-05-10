package com.iVoting.constants;

public class Protocol {

	//table employee filed
	public static final String EMPLOYEE_ID = "EMP_ID";
	public static final String EMPLOYEE_NAME = "EMP_NAME";
	public static final String EMPLOYEE_PASSWORD = "EMP_PASSWORD";
	public static final String EMPLOYEE_MEMO = "EMP_MEMO";
	public static final String EMPLOYEE_JOIN_DATE = "JOIN_DATE";
	public static final String EMPLOYEE_MODIFY_DATE = "MODIFY_DATE";
	
	//message
	public static final String MESSAGE = "message";
	public static final String MESSAGE_UPDATE_SUCCESS = "資料更新成功";
	public static final String MESSAGE_DELETE_SUCCESS = "資料刪除成功";
	public static final String MESSAGE_ADD_SUCCESS = "資料新增成功";
	public static final String MESSAGE_SELECT_SUCCESS = "資料選取成功";
	
	public static final String MESSAGE_ADD_FAIL = "資料庫新增失敗";
	public static final String MESSAGE_SELECT_FAIL = "資料庫選取失敗";
	public static final String MESSAGE_UPDATE_FAIL = "資料庫新增失敗，ID須為唯一";
	public static final String MESSAGE_DELETE_FAIL = "資料庫刪除失敗";
	//error
	public static final String ERROR = "error";	
	public static final String ERROR_ID_PARSING = "ID格式輸入錯誤";
	public static final String ERROR_NAME_PARSING = "姓名格式輸入錯誤";
	public static final String ERROR_PASSWORD_PARSING = "密碼格式輸入錯誤";
	public static final String ERROR_DATE_PARSING = "日期格式輸入錯誤";
	public static final String ERROR_FIELD_NULL = "資料不齊全";
	public static final String ERROR_DEPULICATE = "重複的ID";
	
	public static final String CONNECTION_CLOSE_SUCCESS = "資料關閉成功";
	public static final String CONNECTION_CLOSE_FAIL = "資料關閉失敗";
	public static final String PREPARESTAMENT_CLOSE_SUCCESS = "PREPARESTAMENT關閉成功";
	public static final String PREPARESTAMENT_CLOSE_FAIL = "PREPARESTAMENT關閉失敗";
}
