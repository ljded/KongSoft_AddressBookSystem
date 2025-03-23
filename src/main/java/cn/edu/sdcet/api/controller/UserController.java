package cn.edu.sdcet.api.controller;

import cn.edu.sdcet.api.entity.Package;
import cn.edu.sdcet.api.entity.Password;
import cn.edu.sdcet.api.dao.UserDao;
import cn.edu.sdcet.api.entity.Tool;
import cn.edu.sdcet.api.entity.User;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserController {

	@Resource
	UserDao userDao;

	/**
	 * 用户注册
	 */
	@PostMapping(value = "", produces = "application/json")
	@ResponseBody
	public JSONObject register(@RequestBody User nuser) {
		Package bean = Tool.getPackage();
		if (userDao.registered(nuser.getUsername(), nuser.getPassword())) {
			bean.setCode(0);
			bean.setMsg("注册成功");
		} else {
			bean.setMsg("注册失败");
		}
		return bean.toJSON();
	}

	/**
	 * 用户登录
	 */
	@PostMapping(value = "/session", produces = "application/json")
	@ResponseBody
	public JSONObject session(@RequestBody User nuser, HttpSession session) {
		Package bean = Tool.getPackage();
		User user = userDao.login(nuser.getUsername(), nuser.getPassword());
		if (user != null) {
			bean.setCode(0);
			bean.setMsg("登录成功");
			session.setAttribute("user", user);
		} else {
			bean.setCode(404);
			bean.setMsg("登录失败");
		}
		return bean.toJSON();
	}

	/**
	 * 获取用户信息
	 */
	@GetMapping(value = "/info", produces = "application/json")
	@ResponseBody
	public JSONObject info(HttpSession session) {
		User user = Tool.getUser(session);
		Package bean = Tool.getPackage();
		if (user != null) {
			bean.setCode(0);
			bean.newData(user.getUserNameObj());
		} else {
			bean.setCode(404);
			bean.setMsg("未登录");
		}
		return bean.toJSON();
	}

	/**
	 * 修改密码
	 */
	@PatchMapping(path = "/password", produces = "application/json")
	@ResponseBody
	public JSONObject NewPassword(@RequestBody Password password, HttpSession session) {
		User user = Tool.getUser(session);
		Package bean = Tool.getPackage();
		if (userDao.NewPassword(user, password.getOldPassword(), password.getNewPassword())) {
			bean.setCode(0);
			bean.setMsg("密码修改成功");
		} else {
			bean.setMsg("密码修改失败");
		}
		return bean.toJSON();
	}

}
