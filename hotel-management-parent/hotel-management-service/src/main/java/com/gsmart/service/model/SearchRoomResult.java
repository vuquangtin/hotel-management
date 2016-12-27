package com.gsmart.service.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.gsmart.model.Room;

public class SearchRoomResult implements Comparable<SearchRoomResult> {
	private Room room;
	private Date dateIn;

	private final Calendar calendar = GregorianCalendar.getInstance();

	public SearchRoomResult(Room room, Date dateIn) {
		super();
		this.room = room;
		this.dateIn = dateIn;
	}

	public String getName() {
		return room != null ? room.getName() : "";
	}

	public Date getEndedTime() {
		return room != null ? dateIn : null;
	}

	public String getDistance() {
		Date currentTime = new Date();
		Calendar current = Calendar.getInstance();
		Calendar timeIn = Calendar.getInstance();
		timeIn.setTime(dateIn);

		// Calculate the difference in millisecond between two dates
		long diffInMilis = Math.abs(timeIn.getTimeInMillis() - current.getTimeInMillis());

		/*
		 * Now we have difference between two date in form of mill-second we can
		 * easily convert it Minute / Hour / Days by dividing the difference
		 * with appropriate value. 1 Second : 1000 mill-second 1 Hour : 60 *
		 * 1000 millisecond 1 Day : 24 * 60 * 1000 mill-second
		 */

		long minute = diffInMilis / (60 * 1000);
		long days = minute / (24 * 60);
		long hours = (minute % (24 * 60)) / 60;
		long minutes = ((minute % (24 * 60)) % 60);

		if (dateIn != null) {
			calendar.setTimeInMillis(Math.abs(currentTime.getTime() - dateIn.getTime()));
			return days + " Date " + hours + " Hour " + minutes + " Minute ";
		}
		//If customer can book this room less than 1 hour.
		if (days == 0 && hours == 0 ) {
			return "Now";
		}
		
		return "";
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

	@Override
	public int compareTo(SearchRoomResult o) {
		if (this.getEndedTime().after(o.getEndedTime()))
			return 1;
		if (this.getEndedTime().before(o.getEndedTime()))
			return -1;
		return 0;
	}
}
