package application;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsmart.model.Orders;
import com.gsmart.model.Room;
import com.gsmart.model.RoomCategory;
import com.gsmart.repository.RoomCategoryRepository;
import com.gsmart.repository.RoomRepository;


@Component
public class InjectTestingData {
	@Autowired
	private RoomCategoryRepository roomCategoryRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	public InjectTestingData() {
		
	}
	
	public void doInject() {
		RoomCategory roomCategory = new RoomCategory();
		roomCategory.setName("Room Type A");
		roomCategory.setPrice(450.0);
		
		roomCategoryRepository.save(roomCategory);
		//=============Data 1 ==============//
		
		
		Orders order_1 = new Orders();
		order_1.setCustomerName("Nguyen Huu Quyen");
		order_1.setCustomerAddress("Da Nang, Viet Nam");
		order_1.setCustomerTelephone("0511 456 789");
		order_1.setCreatedAt(new Date());
		
		HashSet<Orders> listOrder_1 = new HashSet<Orders>();
		listOrder_1.add(order_1);
		
		Room room_1 = new Room();
		room_1.setName("A123");
		room_1.setListOrders(listOrder_1);
		room_1.setRoomCategory(roomCategory);
		
		//=============Data 2 ==============//
		Orders order_2 = new Orders();
		order_2.setCustomerName("Hoang Bui Ngoc Quy");
		order_2.setCustomerAddress("Da Nang, Viet Nam");
		order_2.setCustomerTelephone("0511 897 456");
		order_2.setCreatedAt(new Date());
		
		HashSet<Orders> listOrder_2 = new HashSet<Orders>();
		listOrder_2.add(order_2);
		
		Room room_2 = new Room();
		room_2.setName("B456");
		room_2.setListOrders(listOrder_2);
		room_2.setRoomCategory(roomCategory);
		
		roomRepository.save(room_1);
		roomRepository.save(room_2);
		
		System.out.println("Inject Testing Data Completed !!");
	}
}
