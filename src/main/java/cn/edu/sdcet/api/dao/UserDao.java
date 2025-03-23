package cn.edu.sdcet.api.dao;

import cn.edu.sdcet.api.Mapper.UserMI;
import cn.edu.sdcet.api.entity.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

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


	/**
	 * 修改密码
	 */
	public boolean NewPassword(User user, String oldpass, String newpass) {
		if (oldpass == null || newpass == null) {
			System.out.println("数据为空");
			return false;
		}
		return user.NewPassword(oldpass, newpass, userMI);
	}
}
