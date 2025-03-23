package cn.edu.sdcet.api.Entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Password {
	private String oldPassword;
	private String newPassword;

	public boolean Null() {
		return oldPassword.isEmpty() || newPassword.isEmpty();
	}
}
