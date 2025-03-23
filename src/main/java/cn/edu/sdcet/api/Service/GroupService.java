package cn.edu.sdcet.api.Service;

import cn.edu.sdcet.api.Mapper.GroupMI;
import cn.edu.sdcet.api.Entity.Group;
import cn.edu.sdcet.api.Entity.User;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GroupService {

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
	public boolean deleteGroup(int gid, User user) {
		return groupMI.deleteGroup(gid, user.getUserid()) != 0;
	}

	/**
	 * 修改分组名称
	 */
	public JSONObject modifyName(int gid, String newName, User user) {
		if (groupMI.updateGroup(newName,gid, user.getUserid()) != 0){
			List<Group> groups = groupMI.selectGroup(user.getUserid(), gid);
			if (groups.size() == 1) {
				return groups.getFirst().toJson();
			}
		}
		return null;
	}

	/**
	 * 获取用户全部分组
	 */
	public JSONArray AllGroup(User user) {
		JSONArray objects = new JSONArray();
		List<Group> groups = groupMI.selectGroup(user.getUserid(),0);
		for (Group group : groups) {
			objects.add(group.toJson());
		}
		return objects;
	}

}
