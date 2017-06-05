package com.kedang.fenxiao.aop;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kedang.fenxiao.util.perf.Profiler;


/***
 * Aspect that file event for user feed and system message
 */
@Aspect
public class ProfilerAspect {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ProfilerAspect.class);

	
//	@Around("(within(com.xuexin.pay..*) || within(com.leftbeach.gx.api.rest..*) || within(com.leftbeach.gx.api.service..*)) && execution(public * *(..))")
	@Around("within(com.kedang.fenxiao..*) && execution(public * *(..))")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		try{
//			logger.info("---------------------EventAspect doBasicProfiling : " + pjp);
			Profiler.enter(pjp.getTarget().getClass().getSimpleName() + " # " + pjp.getSignature().getName());
			return pjp.proceed();
		}finally {
			Profiler.release();
		}

    }


}

