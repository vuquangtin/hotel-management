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
import com.gsmart.repository.OrdersRepository;
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
	
	@Autowired
	OrdersRepository ordersRepository;

	@Override
	public void SearchRoom() {
		System.out.println("Searching room by service...");
	}

	private boolean isFreeBetween(Orders order1, Orders order2, Date dateIn, Date checkOutDate) {
		if(order1.getCheckOutAt().before(dateIn)) {
			System.out.println("Criterial 1 correct !");
		}else {
			System.out.println("Criterial 1 failed !");
			System.out.println("Order 2 has time | " + order2.getCreatedAt() + " Check Out Time Require is | " + checkOutDate);
		}
		
		if(order2.getCreatedAt().after(checkOutDate)) {
			System.out.println("Criterial 2 correct !");
		} else {
			System.out.println("Criterial 2 failed !");
			System.out.println("Order 2 has time | " + order2.getCreatedAt() + " Check Out Time Require is | " + checkOutDate);
		}
		
		if (order1.getCheckOutAt().before(dateIn) && order2.getCreatedAt().after(checkOutDate)) {
			return true;
		}
		return false;
	}

	private boolean isEndedOrder(Orders order, Date currentTime) {
		return order.getCheckOutAt().before(currentTime);
	}

	@Override
	public List<SearchRoomResult> findRoomByDate(Date dateIn, Date checkOutDate) {
		List<Room> rooms = roomRepository.findAll();
		Collections.sort(rooms, RoomUtils.getSortByFreeTimeComparator());
		List<SearchRoomResult> searchRoomResults = new ArrayList<SearchRoomResult>();

		// Contain last order of each room.
		List<Orders> lastOrder = new ArrayList<Orders>();
		Date currentTime = new Date();
		int numberOrderValid = 0;

		for (Room room : rooms) {
			Iterator<Orders> orders = ordersRepository.findAllByRoomOrderByCreatedAtAsc(room).iterator();
			numberOrderValid = 0;
			
			while (orders.hasNext()) {
				Orders firstOrder = orders.next();
				// It will get next item until has valid order.
				// Valid order is.
				// 1) Has checkout time before dateIn. 
				// 2) Not ended.
				System.out.println("Fist Order Is " + firstOrder.getCustomerName());
				while ((firstOrder.getCheckOutAt().after(dateIn) || isEndedOrder(firstOrder, currentTime))
						&& orders.hasNext()){
					firstOrder = orders.next();
				}
					
				numberOrderValid ++;
				System.out.println("Valid First Order Of Room " + room.getName() + " Customer name " + firstOrder.getCustomerName());
				
				Orders secondOrder = null;
				//Check if is last order.
				if (orders.hasNext()) {
					secondOrder = orders.next();
					
					System.out.println("Valid Second Order Of Room " + room.getName() + " Customer name " + secondOrder.getCustomerName());
					//If free time between two order is valid with input.	
					if (isFreeBetween(firstOrder, secondOrder, dateIn, checkOutDate)) {
						System.out.println("Add to between " + firstOrder.getCustomerName() + " And " + secondOrder.getCustomerName());
						searchRoomResults.add(new SearchRoomResult(room, firstOrder.getCheckOutAt()));
					}

				} else {
					System.out.println("Set Orders of Room " + room.getName() + " is empty");
					//Offset used for mark correct index of room.
					lastOrder.add(firstOrder);
				}
			}
			//If no valid order , add null to that offset.
			if(numberOrderValid == 0) lastOrder.add(null);
		}
		
		// Completed add to between free time, next try add to last.
		for (int index = 0; index < lastOrder.size(); index++) {
			try {
				//Because some room is don't have any order valid, therefore not exist last order with that offset.
				//It will throw array index exception, If that happened we just need to book room at the moment.
				//else we try to add to time when last order is completed.
				searchRoomResults.add(new SearchRoomResult(rooms.get(index), lastOrder.get(index).getCheckOutAt()));
				System.out.println("Add to Ended Last Order Of Room " + rooms.get(index).getName());
			} catch (Exception ex) {
				// If room don't have any order try book room at the moment.
				searchRoomResults.add(new SearchRoomResult(rooms.get(index), new Date()));
				System.out.println("Add to Current Time Of Room " + rooms.get(index).getName());
			}
		}

		return searchRoomResults;
	}

}
