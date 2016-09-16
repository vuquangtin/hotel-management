package application;


import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import com.gsmart.config.ConfigurationDAO;
import com.gsmart.model.Orders;
import com.gsmart.model.Room;
import com.gsmart.model.RoomCategory;
import com.gsmart.repository.RoomCategoryRepository;
import com.gsmart.repository.RoomRepository;

@SpringBootApplication
@ContextConfiguration(classes = {ConfigurationDAO.class})
@ComponentScan(basePackages = {"com.gsmart"})
@EnableAutoConfiguration
public class HelloJpaApplication implements CommandLineRunner {

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private RoomCategoryRepository roomCategoryRepository;
    
  
    public static void main(String[] args) {
        SpringApplication.run(HelloJpaApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
    	
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
    		System.out.println(item);
    	}
    }
}