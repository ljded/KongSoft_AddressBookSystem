package cn.edu.sdcet.api.Entity;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Password {
	@Size(min = 1, message = "旧密码不能为空")
	private String oldPassword;
	@Size(min = 1, message = "新密码不能为空")
	private String newPassword;

	public boolean Null() {
		return oldPassword.isEmpty() || newPassword.isEmpty();
	}
}
