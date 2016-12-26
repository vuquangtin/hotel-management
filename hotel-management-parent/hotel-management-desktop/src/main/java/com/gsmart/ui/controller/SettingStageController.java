package com.gsmart.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.gsmart.model.UserSetting;
import com.gsmart.repository.UserSettingRepository;
import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.controls.FXTextField;

import application.ApplicationSetting;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

public class SettingStageController implements DialogController, Initializable {
	private FXMLDialog dialog;

	@Autowired
	HomeController homeController;

	@FXML
	FXTextField hotelNameTxt;
	@FXML
	FXTextField addressTxt;
	@FXML
	TextArea descriptionTextArea;

	@FXML
	RadioButton englishRadioBtn;

	@FXML
	RadioButton vietnameseRadioBtn;

	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}

	@Override
	public void setDialog(FXMLDialog dialog) {
		this.dialog = dialog;
	}

	public void preparedSettingStage() {
		UserSetting userSetting = ApplicationSetting.getUserSetting();
		hotelNameTxt.setText(userSetting.getHotelName());
		addressTxt.setText(userSetting.getHotelAddress());
		descriptionTextArea.setText(userSetting.getHotelDescription());
		
		//Bind selected language.
		if (userSetting.getLocale() == null)
			englishRadioBtn.setSelected(true);
		else {
			switch (userSetting.getLocale()) {
			case "vi_VN": {
				vietnameseRadioBtn.setSelected(true);
				break;
			}
			case "en_EN": {
				englishRadioBtn.setSelected(true);
				break;
			}
			default: {
				englishRadioBtn.setSelected(true);
			}
			}
		}

	}

	private void resetAllField() {
		hotelNameTxt.setText("");
		addressTxt.setText("");
		descriptionTextArea.setText("");
	}

	@FXML
	public void submitUserSetting() {
		UserSetting userSetting = new UserSetting();
		userSetting.setHotelName(hotelNameTxt.getText());
		userSetting.setHotelAddress(addressTxt.getText());
		userSetting.setHotelDescription(descriptionTextArea.getText());
		
		if(vietnameseRadioBtn.isSelected()) 
			userSetting.setLocale((String) vietnameseRadioBtn.getUserData());
		else 
			userSetting.setLocale((String) englishRadioBtn.getUserData());
		
		// Saving user setting.
		ApplicationSetting.setUserSetting(userSetting);
		// Save to file.
		UserSettingRepository userSettingRepository = new UserSettingRepository();
		userSettingRepository.saveUserSetting(userSetting);
		
		// Should reset form when close.
		resetAllField();
		
		// Update new language.
		homeController.changeLanguage(userSetting.getLocale());
		this.dialog.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
