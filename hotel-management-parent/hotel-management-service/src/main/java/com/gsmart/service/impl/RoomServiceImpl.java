package com.gsmart.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

		for (Room room : rooms) {

			List<Orders> roomOrderItems = ordersRepository.findAllByRoomOrderByCreatedAtAsc(room);
			int i = 0;
			// If Room don't have any order.
			if (roomOrderItems.size() == 0)
				lastOrder.add(null);
			else {
				// Check add to before first order if can.
				if (roomOrderItems.get(0).getCreatedAt().after(currentTime) && dateIn.after(currentTime)
						&& checkOutDate.before(roomOrderItems.get(0).getCreatedAt()))
					searchRoomResults.add(new SearchRoomResult(room, dateIn));
			}

			while (i < roomOrderItems.size()) {
				while ((roomOrderItems.get(i).getCheckOutAt().after(dateIn)
						|| isEndedOrder(roomOrderItems.get(i), currentTime))) {
					if (i < roomOrderItems.size() - 1)
						i++;
					else
						break;
				}
				// If current index is last order.
				if (i == roomOrderItems.size() - 1) {
					// If last order still active then add to last orders list.
					if (roomOrderItems.get(i).getCheckOutAt().after(currentTime))
						lastOrder.add(roomOrderItems.get(i));
					else
						lastOrder.add(null);
					
					break;
				} else {
					if (isFreeBetween(roomOrderItems.get(i), roomOrderItems.get(i + 1), dateIn, checkOutDate)) {
						searchRoomResults.add(new SearchRoomResult(room, roomOrderItems.get(i).getCheckOutAt()));
					}
					i++;
					continue;
				}
			}

		}
		
		//Try to add when last order was completed.
		for (int i = 0; i < lastOrder.size(); i++) {
			if (lastOrder.get(i) != null) {
				// If last order is valid, try to add when last order completed.
				searchRoomResults.add(new SearchRoomResult(rooms.get(i), lastOrder.get(i).getCheckOutAt()));
			} else {
				// It mean current room is free.
				searchRoomResults.add(new SearchRoomResult(rooms.get(i), new Date()));
			}
		}

		return searchRoomResults;
	}

}
