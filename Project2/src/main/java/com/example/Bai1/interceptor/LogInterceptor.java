package com.example.Bai1.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
	@Override// trước khi gọi đến một phương thức
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("INTERCEPTOR");
		log.info(request.getRemoteAddr());
		log.info(request.getRequestURI());
		return true; // cho đi tiếp
	}
	
	@Override //hoàn thiện request và trước khi gửi đến client
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("Done Request");
	}
}
