package com.gsmart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsmart.repository.RoomCategoryRepository;
import com.gsmart.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {
	
	@Autowired
	RoomCategoryRepository roomCategoryRepository;

	@Override
	public void SearchRoom() {
		// TODO Auto-generated method stub
		System.out.println("Searching room by service...");
	}
	
}
