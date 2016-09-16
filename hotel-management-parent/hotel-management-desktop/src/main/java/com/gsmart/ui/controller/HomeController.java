package com.gsmart.ui.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.gsmart.model.Orders;
import com.gsmart.model.Room;
import com.gsmart.model.RoomCategory;
import com.gsmart.repository.RoomCategoryRepository;
import com.gsmart.repository.RoomRepository;
import com.gsmart.service.RoomService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class HomeController {
	
	private static ConfigurableApplicationContext applicationContext;
	
	@Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private RoomCategoryRepository roomCategoryRepository;
    
    @Autowired
    private RoomService roomService;
    
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
    	
    
//    	Stage stage = new Stage();
//    	try {
//    		FXMLLoader loader = new FXMLLoader();
//    		loader.setControllerFactory(applicationContext::getBean);
//    		System.out.println(applicationContext);
//			Parent root = loader.load(getClass().getResource("/com/gsmart/ui/components/Test2.fxml"));
//			Scene scene = new Scene(root, 400, 400);
//			stage.setScene(scene);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        stage.show();
	}
}
