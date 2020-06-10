package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.demo.service.UserService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserService userService;
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	RoleHierarchy roleHierarchy() {
	    RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
	    hierarchy.setHierarchy("ROLE_admin > ROLE_user");
	    return hierarchy;
	}


	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/js/**", "/css/**","/images/**");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests()
	    		.antMatchers("/admin/**").hasRole("admin")
	    		.antMatchers("/user/**").hasRole("user")
	            .anyRequest().authenticated()
	            .and()
	            .formLogin()
	            .loginPage("/login.html")
	            .defaultSuccessUrl("/index")	       
	            .permitAll()
	            .and()
	            .logout()
	            .logoutUrl("/logout")
	            .permitAll()
	            .and()
	            .rememberMe()
	            .and()
	            .csrf().disable();
	}
	
	
}
