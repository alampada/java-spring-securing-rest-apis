package io.jzheaux.springsecurity.resolutions;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@SpringBootApplication
public class ResolutionsApplication extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(authz -> authz
				.mvcMatchers(HttpMethod.GET, "/resolutions", "/resolution/**").hasAuthority("resolution:read")
				.anyRequest().hasAuthority("resolution:write"))
				.httpBasic(basic -> {})
				.csrf().disable();
	}

	@Bean
	public  UserDetailsService userDetailsService(UserRepository users) {
		return new UserRepositoryUserDetailsService(users);
	}

//	@Bean
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource);

//		return new InMemoryUserDetailsManager(
//				org.springframework.security.core.userdetails.User
//						.withUsername("user")
//						.password("{bcrypt}$2a$10$M/UszZrlEu7OjsLwVM15iOjvFWYOIiwrdx7KHiQIBSkrbeXdMotq2")
//						.authorities("resolution:read")
//						.build());
//	}

	public static void main(String[] args) {
		SpringApplication.run(ResolutionsApplication.class, args);
	}

}
