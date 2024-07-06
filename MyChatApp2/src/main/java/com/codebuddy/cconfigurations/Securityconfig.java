package com.codebuddy.cconfigurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.AuthorizeHttpRequestsDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.catalina.User;
@Configuration
@EnableWebSecurity
public class Securityconfig {

    @Autowired
    private DataSource dataSource;
        
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("codinglesson20@gmail.com");
        mailSender.setPassword("sbwl nruu baax efxh");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); 

        mailSender.setJavaMailProperties(props);
        return mailSender;
    }
    
    @Bean
    public UserDetailsService getUserDetailsService() {
		return new CustemUserDetailService();
		
	}
    
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
    	
    	DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    	daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
    	daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    	return daoAuthenticationProvider;
    	
		
	}

    //@Autowired
    //public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
      //  auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
   // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http .csrf().disable()//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())//.csrf().disable()
		  //.and()  
		 .authorizeRequests().requestMatchers("/hello").authenticated()
	            //.anyRequest().authenticated() 
	            .and()
	        .formLogin()
	            .loginPage("/login2").loginProcessingUrl("/login") 
	            .defaultSuccessUrl("/hello")
	            .permitAll() 
	            .and()
	            .httpBasic()
	            .and()
	            .logout()
	            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	            .logoutSuccessUrl("/login2")
	            .deleteCookies("JSESSIONID", "XSRF-TOKEN");
	           // .and()
	           // .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

           
        return http.build();
    }
}


