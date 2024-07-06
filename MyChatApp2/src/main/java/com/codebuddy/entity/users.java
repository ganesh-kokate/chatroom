package com.codebuddy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class users {
	
	@Id
	@Column(name = "username", nullable = false)	
	private String username;
	
	private String password;
	
	private String  role; 
	
	private String mailid;
	
	private boolean enable;
	
	private String verificationcode;
	
	public String getMailid() {
		return mailid;
	}


	public void setMailid(String mailid) {
		this.mailid = mailid;
	}


	
	

	public users() {
		super();
	}
	

	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isEnable() {
		return enable;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
	}


	public String getVerificationcode() {
		return verificationcode;
	}


	public void setVerificationcode(String verificationcode) {
		this.verificationcode = verificationcode;
	}


	@Override
	public String toString() {
		return "users [username=" + username + ", password=" + password + ", role=" + role + ", mailid=" + mailid
				+ ", enable=" + enable + ", verificationcode=" + verificationcode + "]";
	}
	
	
		

}
