package com.gsmart.business;

import com.gsmart.business.exceptions.OrderInformationNotValid;
import com.gsmart.model.Orders;

public class OrderCalculateBusiness {
	
	/**
	 * Calculate total price by total hours.
	 * <p>
	 * @param orders
	 * @return
	 * @throws OrderInformationNotValid
	 */
	public static Double calculateTotalPriceOfOrder(Orders orders) {
		
		if(orders.getCreatedAt() == null || orders.getCheckOutAt() == null)
			return 0.0;
		
		if(orders.getPromotion() == null)
			orders.setPromotion(0.0);
		
		if(orders.getPrepay() == null)
			orders.setPrepay(0.0);
		
		long diffInMilis = Math.abs(orders.getCheckOutAt().getTime() - orders.getCreatedAt().getTime());
		
		long minute = diffInMilis / (60 * 1000);
		long days = minute / (24 * 60);
		long hours = (minute % (24 * 60)) / 60;
		long minutes = ((minute % (24 * 60)) % 60);
		
		if(minutes >= 45) {
			hours += 1;
		}
		
		Double pricePerHour = orders.getRoom().getRoomCategory().getPrice();
		
		return (days * 24.0 + hours ) * pricePerHour;
	}
}
