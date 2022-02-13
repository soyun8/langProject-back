package com.main.spring.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/** CORS
 * 	BackEnd API 서버와 Web FrontEnd 서버를 각각 구성할경우 CORS 설정을 해야한다
 * 
 * */
@Configuration
public class CorsConfig {

	  @Bean
	   public CorsFilter corsFilter() {
	      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	      CorsConfiguration config = new CorsConfiguration();
	      config.setAllowCredentials(true); 	// 내 서버가 응답을 할 때 , json을 자바스크립트에서 처리 할 수 있게 할지를 설정하는 것
	      config.addAllowedOrigin("*");			// 모든 ip에 응답 허용
	      config.addAllowedHeader("*");			// 모든 헤더의 응답 허용
	      config.addAllowedMethod("*");			// 모든 http method 허용

	      source.registerCorsConfiguration("/**", config);
	      return new CorsFilter(source);
	   }
}
