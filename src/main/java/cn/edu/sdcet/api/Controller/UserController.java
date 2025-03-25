package cn.edu.sdcet.api.Controller;

import cn.edu.sdcet.api.Entity.Package;
import cn.edu.sdcet.api.Entity.Password;
import cn.edu.sdcet.api.Service.UserService;
import cn.edu.sdcet.api.Entity.Tool;
import cn.edu.sdcet.api.Entity.User;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@RequestMapping("/api/user")
public class UserController {

	@Resource
	UserService userService;

	/**
	 * 用户注册
	 */
	@PostMapping(value = "", produces = "application/json")
	@ResponseBody
	public JSONObject register(@RequestBody @Valid User nuser) {
		Package bean = Tool.getPackage();
		if (nuser.Null()) {
			bean.setCode(400);
			bean.setMsg("输入错误");
			return bean.toJSON();
		}  else if (userService.UserDuplicate(nuser.getUsername())) {
			bean.setCode(400);
			bean.setMsg("用户名重复");
			return bean.toJSON();
		}
		if (userService.registered(nuser.getUsername(), nuser.getPassword())) {
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
	public JSONObject session(@RequestBody @Valid User nuser, HttpSession session) {
		Package bean = Tool.getPackage();
		User user = userService.login(nuser.getUsername(), nuser.getPassword());
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
	public JSONObject NewPassword(@RequestBody @Valid Password password, HttpSession session) {
		User user = Tool.getUser(session);
		Package bean = Tool.getPackage();
		if (userService.NewPassword(user, password.getOldPassword(), password.getNewPassword())) {
			bean.setCode(0);
			bean.setMsg("密码修改成功");
		} else {
			bean.setMsg("密码修改失败");
		}
		return bean.toJSON();
	}

}
