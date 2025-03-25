package cn.edu.sdcet.api.Controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Random;

@Aspect
@Slf4j
@Component
public class AOPController {
	@Resource
	Random random;

	@Around(value = "execution(* cn.edu.sdcet.api.Controller.*.*(..))")
	public Object poitCut(ProceedingJoinPoint joinPoint) throws Throwable {
		int i = random.nextInt(100000);
		if (i < 10000) i+=10000;
		String name = joinPoint.getSignature().getName() + "-" + i;
		log.info("请求 :{} 方法收到请求", name);
		Object[] args = joinPoint.getArgs();
		log.info("参数 :{} 共{}项 {}", name, args.length , args);
		Object object = joinPoint.proceed();
		if (object != null) {
			log.info("返回 :{} 内容 : {}", name, object);
		}
		log.info("结束 :{} 方法执行结束", name);
		return object;
	}
}
