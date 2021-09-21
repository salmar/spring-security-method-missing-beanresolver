package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;

import com.example.demo.SpringSecurityMethodMissingBeanResolverApplicationTests.AppConfig.SecuredClass;

class SpringSecurityMethodMissingBeanResolverApplicationTests {

	@Nested
    @SpringBootTest(classes = {AppConfig.class, GlobalMethodSecurityConfig.class})
    class GlobalMethodSecurityTest {
		
		@Test
		@WithMockUser("sergi")
		void test(@Autowired SecuredClass securedClass) {
			assertThrows(AccessDeniedException.class, () -> securedClass.secureMethod());
		}
	}
	
	@Nested
    @SpringBootTest(classes = {AppConfig.class, MethodSecurityConfig.class})
    class MethodSecurityTest {
		
		@Test
		@WithMockUser("sergi")
		void test(@Autowired SecuredClass securedClass) {
			assertThrows(AccessDeniedException.class, () -> securedClass.secureMethod());
		}
	}
	
	
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	static class GlobalMethodSecurityConfig {}
	
	@Configuration
	@EnableMethodSecurity
	static class MethodSecurityConfig {}
	
	@Configuration
	static class AppConfig {
		@Component
		class SecuredClass {
			
			@PreAuthorize("@customAuthorizer.auth(authentication)")
			public String secureMethod() {
				return "granted";
			}
		}
		
		@Component("customAuthorizer")
		class CustomAuthorizer {
			
			public boolean auth(Authentication authentication) {
				return false;
			}
		}
	}
	
}
