package cn.edu.sdcet.api.Controller;

import cn.edu.sdcet.api.Entity.Tool;
import cn.edu.sdcet.api.Entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

	@GetMapping(value = {"/retrun;","/retrun"})
	public ModelAndView exit(HttpSession session) {
		User user = Tool.getUser(session);
		if (user != null) {
			session.removeAttribute("user");
		}
		return new ModelAndView("index");
	}
}
