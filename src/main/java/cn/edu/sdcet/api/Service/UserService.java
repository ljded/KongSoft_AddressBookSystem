package cn.edu.sdcet.api.Service;

import cn.edu.sdcet.api.Mapper.UserMI;
import cn.edu.sdcet.api.Entity.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Resource
	private UserMI userMI;

	/**
	 * 创建用户
	 */
	public boolean registered(String username, String password) {
		return userMI.addUser(username, password) != 0;
	}

	/**
	 * 登录
	 */
	public User login(String username, String password) {
		return userMI.loginUser(username, password);
	}

	public boolean UserDuplicate(String username) {
		User user = userMI.UserDuplicate(username);
		return user != null;
	}


	/**
	 * 修改密码
	 */
	public boolean NewPassword(User user, String oldPass, String newPass) {
		if (oldPass == null || newPass == null) {
			System.out.println("数据为空");
			return false;
		}
		return user.NewPassword(oldPass, newPass, userMI);
	}
}
