package com.codebuddy.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codebuddy.dao.UserReposetory;
import com.codebuddy.entity.users;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServicesImpl implements UserServices{
	
	
		@Autowired
		private UserReposetory userReposetory;
		
		@Autowired
		private PasswordEncoder passwordEncoder;
		
		@Autowired
		public JavaMailSender mailSender;

		@Override
		public users saveuser(users user,String url) {
			
			String password=passwordEncoder.encode(user.getPassword());
			user.setPassword(password);
			user.setMailid(user.getMailid());
			user.setEnable(false);
			user.setVerificationcode(UUID.randomUUID().toString());
			user.setRole("user");
		users xuser	=userReposetory.save(user);
		
		if (xuser != null) 
		{
			sendEmail(xuser, url);
		}
		return xuser;
		
		
		}

		@Override
		public void sendEmail(users user, String url) {
			 
			String from = "codinglesson20@gmail.com";
			String to = user.getMailid();
			String subject = "Account Verfication";
			String content = "Dear [[name]],<br>" + "Please click the link below to verify:<br>"
					+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "team ChatRoom";
			
			try {

				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message);

				helper.setFrom(from, "Chat Room");
				helper.setTo(to);
				helper.setSubject(subject);

				content = content.replace("[[name]]", user.getUsername());
				
				String siteUrl = url + "/verify?code=" + user.getVerificationcode();

				System.out.println(siteUrl);

				content = content.replace("[[URL]]", siteUrl);

				helper.setText(content, true);

				mailSender.send(message);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		@Override
		public boolean verifyAccount(String verificationcode) {
			users user = userReposetory.findByverificationcode(verificationcode);

			if (user == null) {
				return false;
			} else {

				user.setEnable(true);
				user.setVerificationcode(null);

				userReposetory.save(user);

				return true;
			}
		}

		@Override
		public void sendotp(users user, String otp) {
			String from = "codinglesson20@gmail.com";
			String to = user.getMailid();
			String subject = "Password Change Request by User";
			String content="Dear [[name]],<br> Here is your OTP:<br>" + otp;
					
			try {

				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message);

				helper.setFrom(from, "ChatRoom");
				helper.setTo(to);
				helper.setSubject(subject);

				content = content.replace("[[name]]", user.getUsername());

				helper.setText(content, true);

				mailSender.send(message);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		@Override
		public void savepass(users user, String pass) {
			
			String password=passwordEncoder.encode(pass);
			user.setPassword(password);
			userReposetory.save(user);
			
			String from = "codinglesson20@gmail.com";
			String to = user.getMailid();
			String subject = "Password Changed";
			String content="Dear [[name]],<br> Your Password is changed successfully:<br>";
			
			
			try {

				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message);

				helper.setFrom(from, "ChatRoom");
				helper.setTo(to);
				helper.setSubject(subject);

				content = content.replace("[[name]]", user.getUsername());

				helper.setText(content, true);

				mailSender.send(message);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		

}
