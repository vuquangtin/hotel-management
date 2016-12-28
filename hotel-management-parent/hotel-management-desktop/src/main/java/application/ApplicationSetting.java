package application;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.gsmart.model.UserSetting;

@Component
public class ApplicationSetting {
	
	private static UserSetting userSetting;
	private static ReloadableResourceBundleMessageSource resourceBundleMessageSource;
	private static LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
	
	public ApplicationSetting() {
		
	}
	
	public static void setMessageSource(ReloadableResourceBundleMessageSource resourceBundleMessageSource) {
		ApplicationSetting.resourceBundleMessageSource = resourceBundleMessageSource;
	}
	
	public static ReloadableResourceBundleMessageSource getMessageSource() {
		if(resourceBundleMessageSource == null) {
			String locale = getUserSetting().getLocale();
			if(locale == null || locale.isEmpty())
				locale = "en_US";
			
			resourceBundleMessageSource= new ReloadableResourceBundleMessageSource();
			resourceBundleMessageSource.setBasename("classpath:validation-messages/ValidationMessages_" + locale);
			resourceBundleMessageSource.setCacheSeconds(10); // reload messages every 10 seconds
			resourceBundleMessageSource.setDefaultEncoding("UTF-8");
		}
		
		return resourceBundleMessageSource;
	}
	
	public static void setUserSetting(UserSetting userSetting) {
		ApplicationSetting.userSetting = userSetting;
	}
	
	public static UserSetting getUserSetting() {
		return userSetting;
	}
	
	public static void changeLanguage(String lang) {
		//Must set message source to NULL for get new instance for next time used.
		setMessageSource(null);
	}
	
	public static Validator getValidator() {
		// Should check new message source for each time.
		localValidatorFactoryBean.setValidationMessageSource(getMessageSource());
		localValidatorFactoryBean.afterPropertiesSet();
		
		return localValidatorFactoryBean;
	}
	
	
}
