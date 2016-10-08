package com.gsmart.service;

import java.util.Date;
import java.util.List;

import com.gsmart.model.Room;

public interface RoomService {
	public void SearchRoom() ;
	public List<Room> findRoomByDate(Date dateIn, Date checkOutDate);
}
