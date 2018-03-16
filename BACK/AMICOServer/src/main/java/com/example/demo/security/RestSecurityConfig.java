package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public UserRepositoryAuthenticationProvider userRepoAuthProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.antMatcher("/api/**");

		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/logIn").authenticated();

		// URLs that need authentication to access to it

		// Users
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/users/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("USER", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN");
		// Courses (PENDING)
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/courses/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/courses/user/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/courses/admin/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/courses/**").hasRole("USER");
		// Moodle
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/moodle/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/moodle/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/moodle/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/moodle/**").hasRole("USER");
		// Subjets (PENDING)
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/subjets/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/subjets/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/subjets/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/subjets/**").hasRole("USER");

		// Other URLs can be accessed without authentication
		http.authorizeRequests().anyRequest().permitAll();

		// Disable CSRF protection (it is difficult to implement with ng2)
		http.csrf().disable();

		// Use Http Basic Authentication
		http.httpBasic();

		// Do not redirect when logout
		http.logout().logoutSuccessHandler((rq, rs, a) -> {
		});
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// Database authentication provider
		auth.authenticationProvider(userRepoAuthProvider);
	}
}
