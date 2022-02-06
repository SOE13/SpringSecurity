package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SpringSecurityConfigure extends WebSecurityConfigurerAdapter{
	

	@Autowired
	private BCryptPasswordEncoder encode;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").hasAnyRole("Teacher","Student")
					.antMatchers("/teacher/**").hasRole("Teacher")
					.antMatchers("/student/**").hasAnyRole("Teacher","Student")
					.and().formLogin().and().logout();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(encode)
		.withUser("admin").password(encode.encode("admin")).roles("Teacher");
		auth.inMemoryAuthentication().passwordEncoder(encode)
		.withUser("student").password(encode.encode("student")).roles("Student");
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	public static void main(String[] args) {
		BCryptPasswordEncoder en=new BCryptPasswordEncoder();
		System.out.println(en.encode("admin"));
	}
	

}
