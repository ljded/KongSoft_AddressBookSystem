package cn.edu.sdcet.api.Entity;

import cn.edu.sdcet.api.Mapper.UserMI;
import com.alibaba.fastjson2.JSONObject;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@SessionScope
@Component
public class User {
	private int userid;
	@Size(min = 1, message = "用户名不能为空")
	private String username;
	@Size(min = 1, message = "密码不能为空")
	private String password;

	//新密码
	public boolean NewPassword(String oldPass, String newPass, UserMI userMI) {
		if (PassWordCompare(oldPass) && !PassWordCompare(newPass)) {
			return userMI.updateUserPass(userid, newPass) != 0;
		}
		return false;
	}

	private boolean PassWordCompare(String pass) {
		return password.equals(pass);
	}

	public JSONObject getUserNameObj() {
		JSONObject json = new JSONObject();
		json.put("username", username);
		return json;
	}

	public boolean Null() {
		return username.isEmpty() || password.isEmpty();
	}
}
