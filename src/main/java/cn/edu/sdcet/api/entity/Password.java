package cn.edu.sdcet.api.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Password {
	private String oldPassword;
	private String newPassword;
}
