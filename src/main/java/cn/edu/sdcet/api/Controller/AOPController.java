package cn.edu.sdcet.api.Controller;

import cn.edu.sdcet.api.Entity.Tool;
import cn.edu.sdcet.api.Entity.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.SessionException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Random;

@Aspect
@Slf4j
@Component
public class AOPController {
	@Resource
	Random random;

	String[] whiteList = new String[] {"session","register","exit","SessionError","error"};

	@Around(value = "execution(* cn.edu.sdcet.api.Controller.*.*(..))")
	public Object poitCut(ProceedingJoinPoint joinPoint) throws Throwable {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		// 从请求中获取 Session
		HttpSession session = request.getSession(false);


		int i = random.nextInt(100000);
		if (i < 10000) i+=10000;
		String name = joinPoint.getSignature().getName() + "-" + i;
		log.info("请求 :{} 方法收到请求", name);
		Object[] args = joinPoint.getArgs();
		log.info("参数 :{} 共{}项 {}", name, args.length , args);


		//判断调用方法是否在白名单
		String gname = joinPoint.getSignature().getName();
		boolean white = true;
		for (String s : whiteList) {
			if (gname.equals(s)) {
				white = false;
				break;
			}
		}
		if (white) {
			boolean flag = false;
			User user = Tool.getUser(session);
			if (user != null){
				flag = true;
			}
			if (!flag){
				log.info("错误 :{} 方法无 Session 传入", name);
				throw new SessionException("",null,null);
			}
		}


		Object object = joinPoint.proceed();
		if (object != null) {
			log.info("返回 :{} 内容 : {}", name, object);
		}
		log.info("结束 :{} 方法执行结束", name);
		return object;
	}
}
