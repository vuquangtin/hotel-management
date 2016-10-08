package com.gsmart.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsmart.model.Room;
import com.gsmart.model.util.RoomUtils;
import com.gsmart.repository.RoomCategoryRepository;
import com.gsmart.repository.RoomRepository;
import com.gsmart.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	RoomCategoryRepository roomCategoryRepository;

	@Autowired
	RoomRepository roomRepository;

	@Override
	public void SearchRoom() {
		// TODO Auto-generated method stub
		System.out.println("Searching room by service...");
	}

	@Override
	public List<Room> findRoomByDate(Date dateIn, Date checkOutDate) {
		List<Room> rooms = roomRepository.findAll();
		Collections.sort(rooms, RoomUtils.getSortByFreeTimeComparator());
		
		return null;
	}

}
