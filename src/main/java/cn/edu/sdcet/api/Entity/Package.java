package cn.edu.sdcet.api.Entity;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Package {
	int code = 1;
	String msg = null;
	JSONArray ArrayData = null;
	JSONObject ObjectData = null;

	public void newData(JSONObject data) {
		this.ObjectData = data;
	}

	public void newData(JSONArray data) {
		this.ArrayData = data;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("code", code);
		if (msg == null) {
			msg = "";
		}
		json.put("msg", msg);
		if (ArrayData != null) {
			json.put("data", ArrayData);
		} else {
			if (ObjectData == null) {
				ObjectData = new JSONObject();
			}
			json.put("data", ObjectData);
		}
		return json;
	}

}
