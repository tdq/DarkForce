package com.darkforce.meta;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.darkforce.components.Component;

@Aspect
public class SessionIdAspect {
	private String sessionId;
	
	@After("execute(void Application.setSessionId(String))")
	public void getSessionFromApplication(JoinPoint point) {
		Object[] args = point.getArgs();
		this.sessionId = (String)args[0];
	}
	
	@Before("execute(Component.new(..))")
	public void setSessionIdToComponent(JoinPoint point) {
		Component component = (Component)point.getThis();
		
		component.setSessionId(sessionId);
	}
}
