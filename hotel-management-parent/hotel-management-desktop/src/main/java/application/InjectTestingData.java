package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import com.gsmart.model.Orders;
import com.gsmart.model.Room;
import com.gsmart.model.RoomCategory;
import com.gsmart.repository.OrdersRepository;
import com.gsmart.repository.RoomCategoryRepository;
import com.gsmart.repository.RoomRepository;
import com.gsmart.repository.specification.OrdersSpecification;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class InjectTestingData {
	@Autowired
	private RoomCategoryRepository roomCategoryRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private OrdersRepository ordersRepository;

	public InjectTestingData() {

	}

	public void doInject() {
		RoomCategory roomCategory = new RoomCategory();
		roomCategory.setName("Room Type A");
		roomCategory.setPrice(450.0);
		roomCategory.setCapacity(2);
		roomCategoryRepository.save(roomCategory);
		// =============Data 1 ==============//

		Orders order_1 = new Orders();
		order_1.setCustomerName("Nguyen Huu Quyen");
		order_1.setCustomerAddress("Da Nang, Viet Nam");
		order_1.setCustomerTelephone("0511 456 789");
		order_1.setCreatedAt(new Date());
		order_1.setCheckOutAt(new Date());
		order_1.setGender((byte) 1);
		order_1.setPromotion(0.1);
		order_1.setPrepay(250.0);
		order_1.setCustomerId("468312187956");

		HashSet<Orders> listOrder_1 = new HashSet<Orders>();
		listOrder_1.add(order_1);

		Room room_1 = new Room();
		room_1.setName("A123");
		room_1.setListOrders(listOrder_1);
		room_1.setRoomCategory(roomCategory);
		room_1.setAcreage("45");
		order_1.setRoom(room_1);
		order_1.setTotalPrice(order_1.getRoom().getRoomCategory().getPrice());

		// =============Data 2 ==============//
		Orders order_2 = new Orders();
		order_2.setCustomerName("Hoang Bui Ngoc Quy");
		order_2.setCustomerAddress("Da Nang, Viet Nam");
		order_2.setCustomerTelephone("0511 897 456");
		order_2.setCreatedAt(new Date());
		order_2.setCheckOutAt(new Date());
		order_2.setGender((byte) 1);
		order_2.setPromotion(0.1);
		order_2.setPrepay(200.0);
		order_2.setCustomerId("68235412356");

		HashSet<Orders> listOrder_2 = new HashSet<Orders>();
		listOrder_2.add(order_2);

		Room room_2 = new Room();
		room_2.setName("B456");
		room_2.setListOrders(listOrder_2);
		room_2.setRoomCategory(roomCategory);
		room_2.setAcreage("45");
		order_2.setRoom(room_2);
		order_2.setTotalPrice(order_2.getRoom().getRoomCategory().getPrice());

		roomRepository.save(room_1);
		roomRepository.save(room_2);

		System.out.println("Inject Testing Data Completed !!");

		testFindByNameWithSpecification();
		testJasperReportForOrder(ordersRepository.findAll());
	}

	public void testFindByNameWithSpecification() {
		System.out.println("TESTING SEARCHING METHOD .......");

		Orders exampler = new Orders();
		// exampler.setCustomerName("Nguyen Huu Quyen");

		Room room = new Room();
		// room.setName("B456");

		room.setRoomCategory(roomCategoryRepository.findOne(1));
		exampler.setRoom(room);

		OrdersSpecification ordersSpecification = new OrdersSpecification(exampler);

		List<Orders> orders = ordersRepository.findAll(Specifications.where(ordersSpecification));
		for (Orders item : orders) {
			System.out.println("SEARCHING RESULT ---- " + "ORDER ID : " + item.getId() + " CUSTOMER NAME : "
					+ item.getCustomerName() + " CREATED AT : " + item.getCreatedAt());
		}
	}

	public void testReport() {
		List<Orders> orders = new ArrayList<>();
		Orders order = new Orders();
		order.setCreatedAt(new Date());
		order.setCheckOutAt(new Date());
		order.setCustomerName("Nguy\u1EC5n H\u1EEFu Quy\u1EC1n 123 L\u00E1 l\u00E0 l\u00E1 l\u00E0 ~~");
		order.setRoom(new Room("A123"));
		order.setId(1);

		orders.add(order);

		testJasperReportForOrder(orders);

	}

	public void testJasperReportForOrder(List<Orders> orders) {
		System.out.println("TESTING Generated Orders Report .......");

		try {
			/* User home directory location */
			String userHomeDirectory = System.getProperty("user.home");
			/* Output file location */
			String outputFile = userHomeDirectory + File.separatorChar + "All-Orders-HotelManagement.pdf";
			
			/* Convert List to JRBeanCollectionDataSource */
			JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(orders , false);

			/* Map to hold Jasper report Parameters */
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ItemDataSource", itemsJRBean);

			/*
			 * Using compiled version(.jasper) of Jasper report to generate PDF
			 */
			JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/reports/orders/all-order-report.jasper",
					parameters, new JREmptyDataSource());
			
			/* outputStream to create PDF */
			OutputStream outputStream = new FileOutputStream(new File(outputFile));

			/* Write content to PDF file */
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

			System.out.println("Orders Report File Generated");

		} catch (JRException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		InjectTestingData app = new InjectTestingData();
		app.testReport();
	}
}
