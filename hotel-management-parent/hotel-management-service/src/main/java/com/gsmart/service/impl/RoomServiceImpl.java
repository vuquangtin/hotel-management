package com.gsmart.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsmart.model.Orders;
import com.gsmart.model.Room;
import com.gsmart.model.util.RoomUtils;
import com.gsmart.repository.RoomCategoryRepository;
import com.gsmart.repository.RoomRepository;
import com.gsmart.service.RoomService;
import com.gsmart.service.model.SearchRoomResult;

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
	
	private boolean isFreeBetween(Orders order1 , Orders order2, Date dateIn , Date checkOutDate) {
		if(order1.getCheckOutAt().before(dateIn) && order2.getCreatedAt().after(checkOutDate)) {
			return true;
		}
		return false;
	}
	
	private boolean isNotEndedOrder(Orders order) {
		return order.getCheckOutAt().after(new Date());
	}

	@Override
	public List<SearchRoomResult> findRoomByDate(Date dateIn, Date checkOutDate) {
		List<Room> rooms = roomRepository.findAll();
		Collections.sort(rooms, RoomUtils.getSortByFreeTimeComparator());
		List<SearchRoomResult> searchRoomResults = new ArrayList<SearchRoomResult>();
		List<Orders> lastOrder = new ArrayList<Orders>();
		
		for(Room room : rooms) {
			Iterator<Orders> orders = room.getListOrders().iterator();
			while(orders.hasNext()) {
				Orders firstOrder = orders.next();
				while(isNotEndedOrder(firstOrder) && orders.hasNext()) firstOrder = orders.next();
				
				Orders secondOrder = null;
				
				if(orders.hasNext()) {
					secondOrder = orders.next();
					
					if(isFreeBetween(firstOrder, secondOrder, dateIn, checkOutDate)) {
						searchRoomResults.add(new SearchRoomResult(room, firstOrder.getCheckOutAt()));
					}
				} else {
					lastOrder.add(firstOrder);
				}
			}
		}
		
		//It mean can't add to between free time, try add to last
		if(searchRoomResults.size() == 0) {
			for(int index = 0 ; index < rooms.size() ; index ++) {
				if(lastOrder.get(index) != null) {
					searchRoomResults.add(new SearchRoomResult(rooms.get(index), lastOrder.get(index).getCheckOutAt()));
				} else {
					//Book room at the moment
					searchRoomResults.add(new SearchRoomResult(rooms.get(index), new Date()));
				}
				
			}
		}
		
		return searchRoomResults;
	}

}
