package com.gsmart.service;

import java.util.Date;
import java.util.List;

import com.gsmart.service.model.SearchRoomResult;

public interface RoomService {
	public void SearchRoom() ;
	public List<SearchRoomResult> findRoomByDate(Date dateIn, Date checkOutDate);
}
