package com.kedang.fenxiao.util.perf;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * User: skyrain
 * Date: 13-8-29
 * Time: 下午4:34
 */
@Aspect
public class ProfilerAspect {
//	private static Logger logger = LoggerFactory.getLogger(ProfilerAspect.class);


	@Around("within(com.xuexin.pay..*) && execution(public * *(..))")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		try{
//			logger.info("---------------------EventAspect doBasicProfiling : " + pjp);
			Profiler.enter(pjp.getTarget().getClass().getSimpleName()+" # "+pjp.getSignature().getName());
			return pjp.proceed();
		}finally {
			Profiler.release();
		}

	}
}
