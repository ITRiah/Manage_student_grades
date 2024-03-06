package com.example.Bai1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.Bai1.interceptor.LogInterceptor;

@SpringBootApplication
@EnableJpaAuditing // Kết hợp EntityListener
@EnableScheduling // lên lịch job
@EnableCaching

public class Bai1Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Bai1Application.class, args);
	}
	
	//Đăng ký interceptor
	@Autowired
	LogInterceptor logInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor);
	}
}
