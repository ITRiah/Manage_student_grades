package com.example.Bai1.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LogAOP {
	@Before("execution(* com.example.Bai1.Service.DepartmentService.getById(*))")	
	public void getDepartmentId(JoinPoint joinPoint) {
		int id = (Integer) joinPoint.getArgs()[0]; // 0 là vị trí của tham số.
		log.info("Join Point " + id);
	}
}
