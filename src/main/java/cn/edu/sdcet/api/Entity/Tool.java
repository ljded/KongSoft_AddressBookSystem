package cn.edu.sdcet.api.Entity;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class Tool {

	public static Package getPackage() {
		return new Package();
	}

	public static User getUser(HttpSession session) {
		return (User) session.getAttribute("user");
	}

}
