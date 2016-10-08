package com.gsmart.service.model;

import java.util.Date;

import com.gsmart.model.Room;

public class SearchRoomResult {
	private Room room;
	private Date dateIn;
	
	public SearchRoomResult(Room room, Date dateIn) {
		super();
		this.room = room;
		this.dateIn = dateIn;
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
