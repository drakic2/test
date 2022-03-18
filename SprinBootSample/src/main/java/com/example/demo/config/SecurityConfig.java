package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder encoder;

	
	/**Set a target out-of-security */
	@Override
	public void configure(WebSecurity web) throws Exception {
		//Do not apply security
		web
			.ignoring()
				.antMatchers("/webjars/**")
				.antMatchers("/css/**")
				.antMatchers("/js/**")
				.antMatchers("/h2-console/**");

	}
	
	/** Various security settings */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Set of login unnecessary page
		http
			.authorizeRequests()
				.antMatchers("/login").permitAll() //Direct link OK
				.antMatchers("/user/signup").permitAll() //Direct link OK
				.antMatchers("/user/signup/rest").permitAll() //Direct link OK
				.antMatchers("/admin").hasAuthority("ROLE_ADMIN") //Authority control
				.anyRequest().authenticated(); //Otherwise direct link NG
		
		//Login process
		http
			.formLogin()
				.loginProcessingUrl("/login") //Login process path
				.loginPage("/login") //specify login page
				.failureUrl("/login?error") //Transition destination when login fails
				.usernameParameter("userId") //Login page user ID
				.passwordParameter("password") //Login page password
				.defaultSuccessUrl("/user/list", true); //Transition destination after success
		
		//Logout process
		http
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout");
		
		//Disable CSRF measures (temporary)
		//http.csrf().disable();
	}
	
	/** Authentification settings */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// In-memory authentication
		/*auth
			.inMemoryAuthentication()
				.withUser("user") //add user
				.password(encoder.encode("user"))
				.roles("GENERAL")
			.and()
				.withUser("admin") //add admin
				.password(encoder.encode("admin"))
				.roles("ADMIN");
				*/
		
		//User data authentication
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(encoder);
	
		
	}
	
	
	
}
