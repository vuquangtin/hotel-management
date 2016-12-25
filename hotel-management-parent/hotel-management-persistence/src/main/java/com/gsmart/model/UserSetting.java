package com.gsmart.model;

public class UserSetting {
	private String hotelName;
	private String hotelAddress;
	private String hotelTelephone;
	private String locale;
	private String hotelDescription;
	
	public UserSetting() {
		
	}
	
	public String getHotelName() {
		return hotelName;
	}
	public String getHotelAddress() {
		return hotelAddress;
	}
	public String getHotelTelephone() {
		return hotelTelephone;
	}
	public String getLocale() {
		return locale;
	}
	public String getHotelDescription() {
		return hotelDescription;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	public void setHotelTelephone(String hotelTelephone) {
		this.hotelTelephone = hotelTelephone;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public void setHotelDescription(String hotelDescription) {
		this.hotelDescription = hotelDescription;
	}
}
