package com.codebuddy.service;

import com.codebuddy.entity.users;

public interface UserServices {
	
	public users saveuser(users user,String url);
	
	public void sendEmail(users user,String path);
	
	public boolean verifyAccount(String verificationcode);
	
	public void sendotp(users user,String otp);
	
	public void savepass(users user,String pass);

}
