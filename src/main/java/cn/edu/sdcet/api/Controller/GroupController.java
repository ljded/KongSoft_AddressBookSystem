package cn.edu.sdcet.api.Controller;

import cn.edu.sdcet.api.Service.GroupService;
import cn.edu.sdcet.api.Entity.*;
import cn.edu.sdcet.api.Entity.Package;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@RequestMapping("/api/group")
public class GroupController {

	@Resource
	GroupService groupService;

	/**
	 * 创建分组
	 */
	@PostMapping(value = "",produces = "application/json")
	@ResponseBody
	public JSONObject addGroup(@RequestBody @Valid Group name, HttpSession session) {
		Package bean = Tool.getPackage();
		User user = Tool.getUser(session);
		if (groupService.addGroup(user,name.getName())) {
			bean.setCode(0);
			bean.setMsg("创建成功");
		} else {
			bean.setMsg("创建失败");
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
		if (groupService.deleteGroup(id, user)) {
			bean.setCode(0);
			bean.setMsg("删除成功");
		} else {
			bean.setMsg("删除失败");
		}
		return bean.toJSON();
	}

	/**
	 * 修改分组
	 */
	@PatchMapping(value = "/{id}",produces = "application/json")
	@ResponseBody
	public JSONObject modifyName(@PathVariable int id, @RequestBody @Valid Group group, HttpSession session) {
		Package bean = Tool.getPackage();
		User user = Tool.getUser(session);
		JSONObject object = groupService.modifyName(id, group.getName(), user);
		if (object != null) {
			bean.setCode(0);
			bean.setMsg("修改成功");
			bean.newData(object);
		} else {
			bean.setMsg("修改失败");
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
		JSONArray objects = groupService.AllGroup(user);
		if (objects != null) {
			bean.setCode(0);
			bean.newData(objects);
		} else {
			bean.setCode(404);
			bean.setMsg("未找到");
		}
		return bean.toJSON();
	}

}
