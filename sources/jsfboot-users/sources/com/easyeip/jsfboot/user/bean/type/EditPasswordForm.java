package com.easyeip.jsfboot.user.bean.type;

import com.easyeip.jsfboot.user.type.AccountEntity;

public class EditPasswordForm {
	
	private AccountEntity account;
	private String oldPassword;
	private String newPassword1;
	private String newPassword2;

	public EditPasswordForm(AccountEntity account) {
		this.account = account;
	}
	
	public AccountEntity getAccountEntry(){
		return account;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword1() {
		return newPassword1;
	}

	public void setNewPassword1(String newPassword1) {
		this.newPassword1 = newPassword1;
	}

	public String getNewPassword2() {
		return newPassword2;
	}

	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}

}
