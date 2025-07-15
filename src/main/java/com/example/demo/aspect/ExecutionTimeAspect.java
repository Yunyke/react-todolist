package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {
	
	/*
	 * AOP（面向切面編程）是一種將非核心但經常重複出現的業務邏輯，從核心業務代碼中分離出來的設計方式。
	 * 這些重複邏輯會被獨立建立為所謂的「切面」（Aspect），
	 * 例如日誌記錄（logging）、權限驗證（authorization）、例外處理、事務管理等。
	 * 這些功能雖然不直接影響業務邏輯本身，卻廣泛存在於多個業務流程中。透過 AOP，可以將這些「橫切關注點」統一管理，
	 * 提升系統的可維護性與可讀性，並減少重複程式碼。
	 */


	
	@Around("execution(* com.example.demo.controller..*(..))")
	public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		
		Object result = joinPoint.proceed();
		
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		
		String methodName = joinPoint.getSignature().toShortString();
		System.out.println("⏱️ 方法 " + methodName + " 執行時間: " + duration + " ms");
		
		return result;
	}
}
