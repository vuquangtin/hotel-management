package com.gsmart.ui.controller;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsmart.model.Orders;
import com.gsmart.model.Room;
import com.gsmart.model.RoomCategory;
import com.gsmart.repository.RoomCategoryRepository;
import com.gsmart.repository.RoomRepository;
import com.gsmart.service.RoomService;
import com.gsmart.ui.components.FXMLDialog;

import application.ApplicationConfiguration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

@Component
public class HomeController implements DialogController{
	
	@Autowired
	private ApplicationConfiguration applicationConfiguration;
	
	@Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private RoomCategoryRepository roomCategoryRepository;
    
    @Autowired
    private RoomService roomService;
    
//    public HomeController(ApplicationConfiguration applicationConfiguration) {
//		this.applicationConfiguration = applicationConfiguration;
//	}
    
	@FXML
	public void clicked(ActionEvent event) {
		System.out.println("Say Hello World From Main Form !!!");
		roomService.SearchRoom();
		
		Orders order = new Orders();
    	order.setCreatedAt(new Date());
    	order.setCustomerName("Nguyen Huu Quyen");
    	
    	HashSet<Orders> orders = new HashSet<Orders>();
    	orders.add(order);
    	
    	RoomCategory roomCategory = new RoomCategory();
    	roomCategory.setName("Phong Loai A");
    	
    	roomCategoryRepository.save(roomCategory);
    	
    	Room room = new Room();
    	room.setName("A123");
    	room.setListOrders(orders);
    	room.setRoomCategory(roomCategory);
    	
    	roomRepository.save(room);
    	
    	for(Room item : roomRepository.findAll()) {
    		System.out.println(item.getName());
    	}
    	
    	applicationConfiguration.orderRoomDialog().show();
	}

	@Override
	public void setDialog(FXMLDialog dialog) {
		// TODO Auto-generated method stub
		
	}
}
