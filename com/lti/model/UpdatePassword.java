package com.lti.model;

import com.lti.dao.PasswordEncryption;
/**
 * it is used to update the password
 * @author 10649718
 *
 */
public class UpdatePassword {
	private String OldPassword;
	private String NewPassword;
	private String ConfirmPassword;

	public String getOldPassword() {
		System.out.println("Old Password Encryption "+PasswordEncryption.Encrypt(OldPassword));
		return PasswordEncryption.Encrypt(OldPassword);
	}

	public void setOldPassword(String oldPassword) {
		OldPassword = oldPassword;
	}

	public String getConfirmPassword() {
		return ConfirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		ConfirmPassword = confirmPassword;
	}

	public String getNewPassword() {
		return PasswordEncryption.Encrypt(NewPassword);
	}

	public void setNewPassword(String newPassword) {
		NewPassword = newPassword;
	}
}
