package application;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.UIManager;

import org.apache.commons.lang.RandomStringUtils;
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
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;

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

		//testFindByNameWithSpecification();
		//testJasperReportForOrder(ordersRepository.findAll());
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
		
		for(int i = 1 ; i <= 5 ; i++) {
			Orders order = new Orders();
			order.setId(i);
			order.setCreatedAt(new Date());
			order.setCheckOutAt(new Date());
			order.setPaidAt(new Date());
			order.setCustomerName("Nguy\u1EC5n H\u1EEFu Quy\u1EC1n");
			order.setRoom(new Room("A123", new RoomCategory("Phòng đơn")));
			order.setPromotion(0.1);
			order.setTotalPrice(100000.0);
			order.setPaymentPrice(100000.0*(1 - 0.1 ));
			orders.add(order);
		}
		

		//testCreateOrderPaymentSheet(orders.get(0));
		testJasperReportForOrder(orders);

	}

	public void testCreateOrderPaymentSheet(Orders order) {
		System.out.println("TESTING Orders Payment Sheet File Generated .......");

		try {
			/* User home directory location */
			String userHomeDirectory = System.getProperty("user.home");
			/* Output file location */
			String outputFile = userHomeDirectory + File.separatorChar + "order-payment-HotelManagement.docx";

			/* Map to hold Jasper report Parameters */
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("SomeParameter", "Some thing here");

			/*
			 * Using compiled version(.jasper) of Jasper report to generate PDF
			 */
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					"src/main/resources/reports/orders/payment-sheet.jasper", parameters,
					new JRBeanArrayDataSource(new Orders[] { order }));

			JRDocxExporter export = new JRDocxExporter();
			export.setExporterInput(new SimpleExporterInput(jasperPrint));
			export.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(outputFile)));

			SimpleDocxReportConfiguration config = new SimpleDocxReportConfiguration();
			export.setConfiguration(config);

			export.exportReport();

			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception e) {
					
			}
				
			JasperViewer viewer = new JasperViewer(jasperPrint, false);
			viewer.setVisible(true);
			viewer.setTitle("Order Payment Sheet");

			

			System.out.println("Orders Payment Sheet File Generated");

		} catch (JRException ex) {
			ex.printStackTrace();
		}
	}

	public void testJasperReportForOrder(List<Orders> orders) {
		System.out.println("TESTING Generated Orders Report .......");

		try {
			/* User home directory location */
			//String userHomeDirectory = System.getProperty("user.home");
			/* Output file location */
			//String outputFile = userHomeDirectory + File.separatorChar + "All-Orders-HotelManagement.pdf";

			/* Convert List to JRBeanCollectionDataSource */
			JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(orders, false);

			/* Map to hold Jasper report Parameters */
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("OrderItemDataSource", itemsJRBean);
			parameters.put("fromDate", new Date());
			parameters.put("toDate", new Date());
			parameters.put("employeeName", "Nguy\u1EC5n H\u1EEFu Quy\u1EC1n");

			/*
			 * Using compiled version(.jasper) of Jasper report to generate PDF
			 */
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					"src/main/resources/reports/orders/all-orders-report.jasper", parameters, new JREmptyDataSource());
			
			
			JasperViewer viewer = new JasperViewer(jasperPrint, false);
			viewer.setVisible(true);
			viewer.setTitle("Order Payment Sheet");

//			/* outputStream to create PDF */
//			OutputStream outputStream = new FileOutputStream(new File(outputFile));
//
//			/* Write content to PDF file */
//			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

			System.out.println("Orders Report File Generated");

		} catch (JRException ex) {
			ex.printStackTrace();
		}
	}
	
	public void preparedDataForTestQuickSearchRoom() throws ParseException {
		System.out.println("preparedDataForTestQuickSearchRoom ---- STARTED ");
		
		RoomCategory roomCategory = new RoomCategory();
		roomCategory.setName("Room Type A");
		roomCategory.setPrice(450.0);
		roomCategory.setCapacity(2);
		roomCategoryRepository.save(roomCategory);
		
		Room room_1 = new Room();
		room_1.setName("A123");
		room_1.setRoomCategory(roomCategory);
		room_1.setAcreage("45");
		
		
		Room room_2 = new Room();
		room_2.setName("B456");
		room_2.setRoomCategory(roomCategory);
		room_2.setAcreage("45");
		
		Orders T1 = getSampleOrder("Nguyễn Hữu Quyền" , "28/12/2016 17:17","29/12/2016 17:17");
		T1.setRoom(room_1);
		
		Orders T2 = getSampleOrder("Hoàng Bùi Ngọc Quý" , "28/12/2016 12:12","29/12/2016 12:12");
		T2.setRoom(room_2);
		
		Orders T3 = getSampleOrder("Nguyễn Văn A" , "31/12/2016 17:17","3/1/2017 17:17");
		T3.setRoom(room_1);
		
		Orders T4 = getSampleOrder("Trần Văn B" , "4/1/2017 12:12","7/1/2017 17:17");
		T4.setRoom(room_2);
		
		HashSet<Orders> listOrder_1 = new HashSet<Orders>();
		listOrder_1.add(T1);
		listOrder_1.add(T3);
		
		HashSet<Orders> listOrder_2 = new HashSet<Orders>();
		listOrder_2.add(T2);
		listOrder_2.add(T4);
		
		room_1.setListOrders(listOrder_1);
		room_2.setListOrders(listOrder_2);
		
		roomRepository.save(room_1);
		roomRepository.save(room_2);
		
		ordersRepository.save(T1);
		ordersRepository.save(T2);
		ordersRepository.save(T3);
		ordersRepository.save(T4);
		
		System.out.println("preparedDataForTestQuickSearchRoom ---- DONE ");
	}
	
	/**
	 * Used for get random valid order.
	 * <p>
	 * @param name
	 * @param timeIn
	 * @param timeCheckOut
	 * @return
	 * @throws ParseException
	 */
	private Orders getSampleOrder(String name , String timeIn , String timeCheckOut) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Orders O = new Orders();
		O.setPrepay(0.0);
		O.setPromotion(0.0);
		O.setCustomerName(name);
		O.setCustomerId(RandomStringUtils.randomAlphanumeric(20).toUpperCase());
		O.setCustomerAddress(RandomStringUtils.randomAlphanumeric(20).toUpperCase());
		O.setCustomerTelephone(RandomStringUtils.randomAlphanumeric(20).toUpperCase());
		O.setCreatedAt(format.parse(timeIn));
		O.setCheckOutAt(format.parse(timeCheckOut));
		return O;
	}

	public static void main(String[] args) {
		InjectTestingData app = new InjectTestingData();
		app.testReport();
	}
}
