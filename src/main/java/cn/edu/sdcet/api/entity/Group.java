package cn.edu.sdcet.api.entity;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Group {
	private int groupId;
	private Date CreatedAt;
	private Date UpdatedAt;
	private Date DeletedAt;
	private String Name;
	private int UserID;

	public JSONObject toJson(){
		JSONObject object = new JSONObject();
		object.put("ID", groupId);
		object.put("CreatedAt", CreatedAt);
		object.put("UpdatedAt", UpdatedAt);
		object.put("DeletedAt", DeletedAt);
		object.put("Name", Name);
		object.put("UserID", UserID);
		return object;
	}
}
