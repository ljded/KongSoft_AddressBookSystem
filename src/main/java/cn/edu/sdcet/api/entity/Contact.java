package cn.edu.sdcet.api.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Data
@Component
public class Contact {
	@ExcelProperty(value = "姓名")
	private String name;
	@ExcelIgnore
	private int ID;
	@ColumnWidth(30)
	@ExcelProperty(value = "手机号码")
	private String phone;
	@ColumnWidth(50)
	@ExcelProperty(value = "电子邮箱")
	private String email;
	@ExcelProperty(value = "备注")
	private String comment;
	@ExcelIgnore
	private int userId;
	@ExcelIgnore
	private Integer group_id;
	@ExcelIgnore
	private Integer groupId;
	@ExcelIgnore
	private Date CreatedAt;
	@ExcelIgnore
	private Date UpdatedAt;
	@ExcelIgnore
	private Date DeletedAt;
	@ExcelProperty(value = "分组名称")
	private String groupName;
	@ExcelIgnore
	private JSONObject object = null;

	public JSONObject toJsonLong(){
		object = new JSONObject();
		object.put("ID", ID);
		object.put("CreatedAt", CreatedAt);
		object.put("UpdatedAt", UpdatedAt);
		object.put("DeletedAt", DeletedAt);
		return toJsonShort();
	}

	public JSONObject toJsonShort(){
		if(object == null){
			object = new JSONObject();
		}
		object.put("name", name);
		object.put("phone", phone);
		object.put("email", email);
		object.put("comment", Objects.requireNonNullElse(comment, ""));
		object.put("user_id", userId);
		object.put("group_id", groupId);
		return object;
	}
}
