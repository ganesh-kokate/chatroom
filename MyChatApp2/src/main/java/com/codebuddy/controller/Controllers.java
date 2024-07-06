package com.codebuddy.controller;

import java.lang.ProcessBuilder.Redirect;
import java.net.URI;
import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codebuddy.dao.UserReposetory;
import com.codebuddy.entity.users;
import com.codebuddy.service.UserServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
public class Controllers {
	
	@Autowired
	UserServices userServices;
	
	@Autowired
	private UserReposetory userRepo;
	
	
	@GetMapping("/hello")
	public String name(Principal p, Model m) {
		if (p != null) {
			String username = p.getName();
			 if (username != null) {
			users user = userRepo.findUserByUsername(username);
			System.out.println(user);
			m.addAttribute("user", user);
			 }
			 else {
			        // Handle null username, e.g., set a default message
			        m.addAttribute("user", "Not logged in");
			    }
			
		}
		
		
		
		return "NewFile";
	}
	
	@GetMapping("/byy")
	public String name3() {
		return "by";
	}
	
	@GetMapping("/login2")
	public String name2() {
		return "login2";
	}
	
	@GetMapping("/signup")
	public String name4() {
		return "signUP";
	}
	
	@PostMapping("/saveuser")
	public String saveuser(@ModelAttribute users usr,HttpSession session ,Model m,HttpServletRequest request) {
		System.out.println(usr);
		
		String url = request.getRequestURL().toString();
		url = url.replace(request.getServletPath(), "");
		
		
		
	users u=userServices.saveuser(usr,url);
		
	if(u!=null)
	{
		System.out.println("Success"+usr);
		session.setAttribute("currentuser", u);
		m.addAttribute(u);
	}
	else {
		System.out.println("Fail");
	}
		
		
		return "redirect:/login2";
	}
	
	@GetMapping("/verify")
	public String verify(@Param("code") String code, Model m) {
		
		boolean f = userServices.verifyAccount(code);

		if (f) {
			m.addAttribute("msg", "Sucessfully your account is verified");
		} else {
			m.addAttribute("msg", "may be your vefication code is incorrect or already veified ");
		}

		return "message";
		
	}
	
	@PostMapping("/forgot")
	public String forgot()
	{
		return "forgot";
	}
	
	@PostMapping("/sendotp")
	public String sendOtp(@RequestParam("username") String username,RedirectAttributes redirectAttributes,HttpSession session) {
	    
		
		users user = userRepo.findUserByUsername(username);
		String verificationcode=UUID.randomUUID().toString();
		user.setVerificationcode(verificationcode);
		user	=userRepo.save(user);
		
		userServices.sendotp(user, verificationcode);
		
		System.out.println(user);
		
		
		//RedirectAttributes redirectAttributes ;
		
		redirectAttributes.addFlashAttribute("user", user);
	    redirectAttributes.addFlashAttribute("verificationcode", verificationcode);
		
	    //String redirectUrl = "/newpass"; 
	    session.setAttribute("username", username);
	    return "redirect:/newpass";
	}
	
	@GetMapping("/newpass")
	public String passchange(@ModelAttribute("user") users user,
            @ModelAttribute("verificationcode") String verificationcode,RedirectAttributes redirectAttributes) {
				
		
		
	   
		return "changepass";
	}
	
	
	@PostMapping("/finalchange")
	public String finalchange(@RequestParam("otp") String otp,@RequestParam("pass") String pass,HttpSession session)
	{
		
		String username=(String) session.getAttribute("username");
		users user = userRepo.findUserByUsername(username);
		//System.out.println(username);
		String str1=otp;
		String str2=user.getVerificationcode();
		if(str1.equals(str2))
		{
			userServices.savepass(user, pass);
		}
		else {
			System.out.println("wrong otp");
		}
		System.out.println("final change"+user.getVerificationcode());
		System.out.println("otp change "+otp);
		return "login2";
	}
	

}
