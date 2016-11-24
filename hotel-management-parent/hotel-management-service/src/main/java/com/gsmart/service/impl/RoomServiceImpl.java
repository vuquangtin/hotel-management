package com.gsmart.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

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

	private boolean isFreeBetween(Orders order1, Orders order2, Date dateIn, Date checkOutDate) {
		if (order1.getCheckOutAt().before(dateIn) && order2.getCreatedAt().after(checkOutDate)) {
			return true;
		}
		return false;
	}

	private boolean isEndedOrder(Orders order) {
		return order.getCheckOutAt().after(new Date());
	}

	@Override
	public List<SearchRoomResult> findRoomByDate(Date dateIn, Date checkOutDate) {
		List<Room> rooms = roomRepository.findAll();
		Collections.sort(rooms, RoomUtils.getSortByFreeTimeComparator());
		List<SearchRoomResult> searchRoomResults = new ArrayList<SearchRoomResult>();
		
		//@lastOrder : contain last order of each room.
		List<Orders> lastOrder = new ArrayList<Orders>();
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(dateIn);
		calendar.setTimeZone(TimeZone.getTimeZone("GTM+7:00"));
		
		SimpleDateFormat format = new SimpleDateFormat("mm:MM:yyyy hh:mm:ss");
				
		
		System.out.println("TIME-IN : " + format.format(calendar.getTime()));
		
		int offset = 0;
		

		for (Room room : rooms) {
			Iterator<Orders> orders = room.getListOrders().iterator();

			while (orders.hasNext()) {
				Orders firstOrder = orders.next();
				
				while ((firstOrder.getCheckOutAt().after(dateIn) || isEndedOrder(firstOrder)) && orders.hasNext())
					firstOrder = orders.next();

				Orders secondOrder = null;

				if (orders.hasNext()) {
					secondOrder = orders.next();

					if (isFreeBetween(firstOrder, secondOrder, dateIn, checkOutDate)) {
						searchRoomResults.add(new SearchRoomResult(room, firstOrder.getCheckOutAt()));
					}
				} else {
					lastOrder.add(offset, firstOrder);
				}
				offset++;
			}
		}

		// complete add to between free time, try add to last
		if (searchRoomResults.size() == 0) {
			for (int index = 0; index < offset; index++) {
				try {
					searchRoomResults.add(new SearchRoomResult(rooms.get(index), lastOrder.get(index).getCheckOutAt()));
				} catch (Exception ex) {
					// Book room at the moment
					searchRoomResults.add(new SearchRoomResult(rooms.get(index), new Date()));
				}

			}
		}

		return searchRoomResults;
	}

}
