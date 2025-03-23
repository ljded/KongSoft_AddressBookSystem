package cn.edu.sdcet.api.entity;

import cn.edu.sdcet.api.Mapper.UserMI;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@SessionScope
@Component
public class User {
	private int userid;
	private String username;
	private String password;

	//新密码
	public boolean NewPassword(String oldpass, String newpass, UserMI userMI) {
		if (Passwordcompare(oldpass) || Passwordcompare(newpass)) {
			return userMI.updateUserpass(userid, newpass) != 0;
		}
		return false;
	}

	private boolean Passwordcompare(String pass) {
		return !password.equals(pass);
	}

	public JSONObject getUserNameObj() {
		JSONObject json = new JSONObject();
		json.put("username", username);
		return json;
	}
}
