package com.gsmart.model.util;

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import com.gsmart.model.Orders;
import com.gsmart.model.Room;

public class RoomUtils {
	public static long getTotalFreeTimeByOrders(Room room) {
		Date currentTime = new Date();
		long result = 0L;
		Iterator<Orders> orders = room.getListOrders().iterator();
		Orders orderItem;

		while (orders.hasNext()) {
			orderItem = orders.next();

			if (orderItem.getCheckOutAt().after(currentTime)) {
				result += (orderItem.getCheckOutAt().getTime() - orderItem.getCreatedAt().getTime());
			}
			// If on the last order.
			if (!orders.hasNext()) {
				result = (orderItem.getCheckOutAt().getTime() - currentTime.getTime()) - result;
			}
		}

		return result;
	}

	public static Comparator<Room> getSortByFreeTimeComparator() {
		return new Comparator<Room>() {
			@Override
			public int compare(Room room1, Room room2) {
				long freeTimeRoom1 = RoomUtils.getTotalFreeTimeByOrders(room1);
				long freeTimeRoom2 = RoomUtils.getTotalFreeTimeByOrders(room2);
				return (freeTimeRoom1 > freeTimeRoom2) ? 1 : (freeTimeRoom1 < freeTimeRoom2) ? -1 : 0;
			}
		};
	}
}
