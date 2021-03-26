package io.jzheaux.springsecurity.resolutions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class ResolutionsApplication extends WebSecurityConfigurerAdapter {

	@Autowired
	UserRepositoryJwtAuthenticationConverter authenticationConverter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(authz -> authz
						.anyRequest().authenticated())
				.httpBasic(basic -> {})
				.oauth2ResourceServer(
						oauth2 -> oauth2.jwt().jwtAuthenticationConverter(this.authenticationConverter))
				.csrf().disable()
				.cors(cors -> {});
	}

//	@Bean
//	JwtAuthenticationConverter jwtAuthenticationConverter() {
//		JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
//		JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
//		authoritiesConverter.setAuthorityPrefix("");
//		authenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
//		return authenticationConverter;
//	}

	@Bean
	WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.maxAge(0)
						.allowedOrigins("http://localhost:4000")
						.allowedMethods("HEAD")
						.allowedHeaders("Authorization");
			}
		};
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
