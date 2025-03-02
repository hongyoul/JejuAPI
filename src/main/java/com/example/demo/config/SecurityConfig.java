package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.service.MemberService;
import com.example.demo.service.MemberServiceImpl;
import com.example.demo.security.ApiCheckFilter;
import com.example.demo.security.ApiLoginFilter;
import com.example.demo.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// API Check 필터에서 인증객체를 생성할 때 사용됨
		@Bean
		UserDetailsService customUserDetailsService() {
			return new UserDetailsServiceImpl();
		}
		
		// 회원가입시 패스워드를 암호화할때, 로그인시 패스워드를 대조할 때 사용됨
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		// 데이터베이스에서 회원 정보를 조회하는 서비스
		@Bean
		public MemberService memberService() {
			return new MemberServiceImpl();
		}

		// 필터를 빈으로 등록하면, 자동으로 필터 체인에 추가됨
		@Bean
		public ApiCheckFilter apiCheckFilter() {
			return new ApiCheckFilter(customUserDetailsService());
		}

		@SuppressWarnings("removal")
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		        // ✅ CSRF 비활성화 (POST 요청 차단 방지)
		        http.csrf().disable();

		        // ✅ 세션 사용 안 함
		        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		        // ✅ 인증 없이 접근 가능한 경로 (로그인, 회원가입, 상품 조회, 게시판 조회)
		        http.authorizeHttpRequests()
		            .requestMatchers("/login", "/register").permitAll()

		            // ✅ 로그인한 사용자만 접근 가능 (마이페이지, 리뷰 작성 등)
		            .requestMatchers("/member/*", "/board/*", "/Comment/*").authenticated()
		            
		            // ✅ 그 외 모든 요청은 허용 (테스트 중에만 사용, 이후 삭제 가능)
		            .anyRequest().permitAll();

		        /* API 로그인 필터 */
		        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		        authenticationManagerBuilder.userDetailsService(customUserDetailsService())
		                                    .passwordEncoder(passwordEncoder());

		        // 인증 매니저 생성
		        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		        http.authenticationManager(authenticationManager);

		        // ApiLoginFilter 생성 및 등록
		        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/login", memberService());
		        apiLoginFilter.setAuthenticationManager(authenticationManager);

		        // apiLoginFilter가 UsernamePasswordFilter보다 먼저 실행되도록 설정
		        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);

		        /* API 체크 필터 */
		        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);

		        return http.build();
		   
		}	

	}
