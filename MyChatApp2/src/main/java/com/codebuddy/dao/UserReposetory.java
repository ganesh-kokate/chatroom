package com.codebuddy.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import com.codebuddy.entity.users;


public interface UserReposetory extends JpaRepository<users, String> {

	public users findUserByUsername(String username);

	public users findByverificationcode(String verificationcode);
	
}
