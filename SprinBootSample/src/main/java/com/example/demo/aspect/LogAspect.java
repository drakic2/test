package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {
	
	/**
	 * Log output before executing the service 
	 * Target: [UserService] is included in the class name
	 * */
	@Before("execution(* *..*.*UserService.*(..))")
	public void startLog(JoinPoint jp) {
		log.info("Method start: " + jp.getSignature());
	}
	
	/**
	 * Log output after executing the service 
	 * Target: [UserService] is included in the class name
	 * */
	@After("execution(* *..*.*UserService.*(..))")
	public void endLog(JoinPoint jp) {
		log.info("Method end: " + jp.getSignature());
	}
	
	
	/**
	 * Log output before and after the controller is executed
	 * */
	//@Around("bean(*Controller)")
	//@Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	@Around("@within(org.springframework.stereotype.Controller)")
	public Object startLog(ProceedingJoinPoint jp) throws Throwable {
		
		//output start log
		log.info("Method start: " + jp.getSignature());
		

		try {
			//method execution
			Object result = jp.proceed();
			
			//output end log
			log.info("Method end: " + jp.getSignature());

			//return the execution result to the caller
			return result;
			
		}catch (Exception e) {
			// output error log
			log.error("Method abend: " + jp.getSignature());
			
			//rethrow error
			throw e;
		}
		
		
	}

}
