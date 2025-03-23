package cn.edu.sdcet.api.Controller;

import cn.edu.sdcet.api.Service.GroupService;
import cn.edu.sdcet.api.Entity.*;
import cn.edu.sdcet.api.Entity.Package;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/api/group")
public class GroupController {

	@Resource
	GroupService groupService;

	/**
	 * 创建分组
	 */
	@PostMapping(value = "",produces = "application/json")
	@ResponseBody
	public JSONObject addGroup(@RequestBody Group name, HttpSession session) {
		Package bean = Tool.getPackage();
		User user = Tool.getUser(session);
		log.info("收到创建分组 {} 请求", name.getName());
		if (name.Null()) {
			bean.setMsg("输入错误");
			return bean.toJSON();
		}
		if (groupService.addGroup(user,name.getName())) {
			bean.setCode(0);
			bean.setMsg("创建成功");
			log.info("创建分组 {} 成功", name.getName());
		} else {
			bean.setMsg("创建失败");
			log.info("创建分组 {} 失败", name.getName());
		}
		return bean.toJSON();
	}

	/**
	 * 删除分组
	 */
	@DeleteMapping("/{id}")
	@ResponseBody
	public JSONObject deleteGroup(@PathVariable int id, HttpSession session) {
		User user = Tool.getUser(session);
		Package bean = Tool.getPackage();
		log.info("收到删除分组 {} 请求", id);
		if (groupService.deleteGroup(id, user)) {
			bean.setCode(0);
			bean.setMsg("删除成功");
			log.info("删除分组 {} 成功", id);
		} else {
			bean.setMsg("删除失败");
			log.info("删除分组 {} 失败", id);
		}
		return bean.toJSON();
	}

	/**
	 * 修改分组
	 */
	@PatchMapping(value = "/{id}",produces = "application/json")
	@ResponseBody
	public JSONObject modifyName(@PathVariable int id, @RequestBody Group group, HttpSession session) {
		Package bean = Tool.getPackage();
		User user = Tool.getUser(session);
		log.info("收到修改分组 {} 名称请求新名称 {}", id, group.getName());
		if (group.Null()) {
			bean.setMsg("输入错误");
			return bean.toJSON();
		}
		JSONObject object = groupService.modifyName(id, group.getName(), user);
		if (object != null) {
			bean.setCode(0);
			bean.setMsg("修改成功");
			bean.newData(object);
			log.info("修改分组 {} 名称成功", id);
		} else {
			bean.setMsg("修改失败");
			log.info("修改分组 {} 名称失败", id);
		}
		return bean.toJSON();
	}

	/**
	 * 查询分组
	 */
	@GetMapping(value = "",produces = "application/json")
	@ResponseBody
	public JSONObject AllGroup(HttpSession session) {
		Package bean = Tool.getPackage();
		User user = Tool.getUser(session);
		log.info("收到查询用户全部分组请求 用户:{}", user.getUsername());
		JSONArray objects = groupService.AllGroup(user);
		if (objects != null) {
			bean.setCode(0);
			bean.newData(objects);
			log.info("查询用户全部分组成功 用户:{}", user.getUsername());
		} else {
			bean.setCode(404);
			bean.setMsg("未找到");
		}
		return bean.toJSON();
	}

}
