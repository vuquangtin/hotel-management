package com.gsmart.ui.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.util.StringConverter;

public class FxDatePickerConverter extends StringConverter<LocalDate>
{
	// Default Date Pattern
	private String pattern = "MM/dd/yyyy";
	// The Date Time Converter
	private DateTimeFormatter dtFormatter;
	
	public FxDatePickerConverter() 
	{
		dtFormatter = DateTimeFormatter.ofPattern(pattern);
	}
	
	public FxDatePickerConverter(String pattern) 
	{
		this.pattern = pattern;
		dtFormatter = DateTimeFormatter.ofPattern(pattern);
	}
	
	// Change String to LocalDate
	public LocalDate fromString(String text) 
	{
		LocalDate date = null;
		
		if (text != null && !text.trim().isEmpty()) 
		{
			date = LocalDate.parse(text, dtFormatter);
		}
	
		return date;
	}
	
	// Change LocalDate to String
	@Override
	public String toString(LocalDate date) 
	{
		String text = null;
		
		if (date != null) 
		{
			text = dtFormatter.format(date);
		}
	
		return text;
	}
}
