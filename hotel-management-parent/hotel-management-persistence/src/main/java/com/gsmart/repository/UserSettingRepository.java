package com.gsmart.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsmart.model.UserSetting;

public class UserSettingRepository {
	private final static String rootResourcePath = System.getProperty("user.home") + File.separator + "Hotel Management Application";
	// Data is stored in resources folder.
	private final static String userSettingFilePath = rootResourcePath + File.separator + "settings" + File.separator + "UserSetting.json";
	private final ObjectMapper mapper = new ObjectMapper();
	
	private int numberTimeTryingToCheck = 0;

	protected void handleNotFoundUserSettingFile(UserSetting userSetting) {
		System.err.println("Has error when try to save user setting file.");
		
		Path settingDirectory = Paths.get(String.valueOf(rootResourcePath + "/" + "settings"));
		Path userSettingPath = Paths.get(String.valueOf(userSettingFilePath));
		
		File directory = new File(settingDirectory.toAbsolutePath().toString());
		
		//The key important is , Java Need have parent directory first.
	    if (!directory.getParentFile().exists()){
	    	directory.getParentFile().mkdir();
	    }
	    
	    //Second, if settings directory not exist, we must create it.
	    if (!directory.exists()){
	    	directory.mkdir();
	    }
		
	    //Thirst,if file is opening by other process just notify for use.
	    if(Files.isExecutable(userSettingPath)) {
	    	System.err.println(userSettingFilePath + " Is open by other process, please check again !.");
	    } else {
	    	File file = new File(userSettingPath.toAbsolutePath().toString());
		    if (!file.exists()){
		    	try {
					file.createNewFile();
				} catch (IOException e) {
					System.err.println("Cannot re-saving user setting files : -> Because " + e.getMessage());
				}
		    }
	    }
	    
	    //we must stop loop if error can not resolve.
	    numberTimeTryingToCheck ++;
	    
	    if(numberTimeTryingToCheck <= 3) {
	    	//Delay 3 seconds.
	    	try {
				TimeUnit.SECONDS.sleep(3);
				saveUserSetting(userSetting);
			} catch (InterruptedException e) {
				System.err.println("Cannot re-saving user setting files");
			}
	    };
	    
	}

	protected void handleErrorWhenSavingFile(Exception e) {
		if (e instanceof JsonParseException) {
			System.err.println("---- > Error When Parsing User Setting Data");
		}

		if (e instanceof JsonMappingException) {
			System.err.println("---- > Error When Mapping User Setting Data");
		}
	}

	public void handleJsonSavingException(Exception e) {
		System.err.println(e.getMessage());
	}

	protected UserSetting getDefaultUserSetting() {
		UserSetting userSetting = new UserSetting();

		userSetting.setHotelName("Your Hotel Name");
		userSetting.setHotelAddress("Your Address");
		userSetting.setHotelDescription("Describe something");
		userSetting.setLocale("en_EN");

		return userSetting;
	}

	public UserSetting getUserSetting() {
		Path userSettingPath = Paths.get(String.valueOf(userSettingFilePath));
		
		try {
			File file = new File(userSettingPath.toAbsolutePath().toString());
			
			//If user setting file not found. Throw exception and return defaule setting.
			if(!file.exists())
				throw new IOException();
			
			return mapper.readValue(file, UserSetting.class);
			
		} catch (JsonParseException | JsonMappingException e) {
			handleErrorWhenSavingFile(e);
		} catch (IOException e) {
			System.err.println("Not found user setting files.");
		}

		// If has error return default user setting.
		return getDefaultUserSetting();
	}

	public void saveUserSetting(UserSetting userSetting) {
		if (userSetting == null)
			return;

		try {
			File file = Paths.get(userSettingFilePath).toFile();
			if (!file.exists())
				file.createNewFile();

			mapper.writeValue(file, userSetting);
		} catch (IOException e) {
			handleNotFoundUserSettingFile(userSetting);
		}
	}
}
