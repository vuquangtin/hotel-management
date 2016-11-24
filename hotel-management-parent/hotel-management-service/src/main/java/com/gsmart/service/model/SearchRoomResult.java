package com.gsmart.service.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.gsmart.model.Room;

public class SearchRoomResult {
	private Room room;
	private Date dateIn;
	
	private final Calendar calendar = GregorianCalendar.getInstance();
	
	public SearchRoomResult(Room room, Date dateIn) {
		super();
		this.room = room;
		this.dateIn = dateIn;
	}
	
	public String getName() {
		return room != null ? room.getName() : "";
	}
	
	public Date getEndedTime() {
		return dateIn != null ? dateIn : null;
	}
	
	public String getDistance() {
		Date currentTime = new Date();
		if(dateIn != null) {
			calendar.setTimeInMillis(currentTime.getTime() - dateIn.getTime());
			return calendar.get(Calendar.DATE) + " Date " + calendar.get(Calendar.HOUR) + " Hour " + calendar.get(Calendar.MINUTE) + " Minute ";
		}
		if(dateIn.equals(currentTime)) {
			return "Now";
		}
		return "";
	}
	
	public Room getRoom() {
		return room;
	}
	public Date getDateIn() {
		return dateIn;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}
}
