package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

	
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
