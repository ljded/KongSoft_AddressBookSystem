package cn.edu.sdcet.api.Controller;

import cn.edu.sdcet.api.Entity.Package;
import cn.edu.sdcet.api.Entity.Tool;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorController {

	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public JSONObject error(MethodArgumentNotValidException e) {
		Package bean = Tool.getPackage();
		bean.setCode(400);
		String str = e.getMessage();
		int start = str.indexOf("]; default message [") + 20;
		int end = str.indexOf("]]");
		str = str.substring(start, end);
		if (str.length() > 15) {
			start = str.indexOf("]; default message [") + 20;
			str = str.substring(start);
		}
		bean.setMsg(str);
		return bean.toJSON();
	}
}
