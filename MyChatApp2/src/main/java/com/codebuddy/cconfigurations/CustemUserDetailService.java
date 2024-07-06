package com.codebuddy.cconfigurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.codebuddy.dao.UserReposetory;
import com.codebuddy.entity.users;

@Component
public class CustemUserDetailService implements UserDetailsService{

	@Autowired
	private UserReposetory userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		users user =userRepo.findUserByUsername(username);
		
		if (user==null) {
			throw new UsernameNotFoundException("user not found");
		}
		else {
			return new CustemUser(user);
		}
		//return null;
	}

}
