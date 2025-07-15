package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class ExceptionAspect {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

	// 攔截 controller 下所有方法
	@Pointcut("execution(* com.example.demo.controller..*(..))")
	public void controllerMethods() {
	}

	// 當 method 拋出 Exception 時觸發 handleControllerException
	// JoinPoint 提供當前「目標方法」的資訊，例如：方法名稱、參數列表、目標物件（實例）、呼叫目標方法的上下文等
	@AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
	public void handleControllerException(JoinPoint joinpoint, Exception ex) {
		logger.error("[ExceptionAspect] 方法 {} 發生異常: {}", joinpoint.getSignature().toShortString(), ex.getMessage(), ex);
	}
}
