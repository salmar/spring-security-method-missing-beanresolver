package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Sample project that shows how the {@link PreAuthorizeAuthorizationManager} (& friends)
 * can't access a bean in a Spring-EL expression due to missing {@link BeanResolver} in
 * the {@link DefaultMethodSecurityExpressionHandler}
 * 
 * @see SpringSecurityMethodMissingBeanResolverApplicationTests
 */
@SpringBootApplication
public class SpringSecurityMethodMissingBeanResolverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityMethodMissingBeanResolverApplication.class, args);
	}
}