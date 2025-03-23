package cn.edu.sdcet.api.dao;

import cn.edu.sdcet.api.Mapper.GroupMI;
import cn.edu.sdcet.api.entity.Group;
import cn.edu.sdcet.api.entity.User;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class GroupDao {

	@Resource
	private GroupMI groupMI;

	/**
	 * 创建分组
	 */
	public boolean addGroup(User user, String name) {
		return groupMI.addGroup(name,user.getUserid(),new Date()) != 0;
	}

	/**
	 * 删除分组
	 */
	public boolean deleteGroup(int gid) {
		return groupMI.deleteGroup(gid) != 0;
	}

	/**
	 * 修改分组名称
	 */
	public JSONObject modifyName(int gid, String newName) {
		if (groupMI.updateGroup(newName,gid) != 0){
			return groupMI.selectGroup(gid).toJson();
		} else {
			return null;
		}
	}

	/**
	 * 获取用户全部分组
	 */
	public JSONArray AllGroup(User user) {
		JSONArray objects = new JSONArray();
		List<Group> groups = groupMI.selectAllGroup(user.getUserid());
		for (Group group : groups) {
			objects.add(group.toJson());
		}
		return objects;
	}

}
