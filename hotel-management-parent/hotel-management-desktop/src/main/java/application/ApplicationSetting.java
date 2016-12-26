package application;

import org.springframework.stereotype.Component;

import com.gsmart.model.UserSetting;

@Component
public class ApplicationSetting {
	public static UserSetting userSetting;
	
	public ApplicationSetting() {
		
	}
	
	public static void setUserSetting(UserSetting userSetting) {
		ApplicationSetting.userSetting = userSetting;
	}
	
	public static UserSetting getUserSetting() {
		return userSetting;
	}
}
