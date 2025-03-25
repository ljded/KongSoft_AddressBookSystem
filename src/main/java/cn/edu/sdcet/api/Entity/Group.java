package cn.edu.sdcet.api.Entity;

import com.alibaba.fastjson2.JSONObject;
import jakarta.validation.constraints.Size;
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
	@Size(min = 1, message = "分组名称不能为空")
	private String Name;
	private int UserID;

	public boolean Null() {
		return Name.isEmpty();
	}

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
