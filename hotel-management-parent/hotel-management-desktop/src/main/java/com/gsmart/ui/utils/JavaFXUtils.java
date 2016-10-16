package com.gsmart.ui.utils;

import java.util.Date;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class JavaFXUtils {	
	
	public JavaFXUtils() {
		
	}
	public static String getTextFieldValue(TextField textField) {
		if (textField.getText() == null)
			return "";
		return textField.getText();
	}

	public static Date getDatePickerValue(DatePicker datePicker) {
		if (datePicker.getValue() != null)
			return DateUtils.asDate(datePicker.getValue());
		return null;
	}
}
