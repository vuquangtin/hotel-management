package com.gsmart.ui.controller;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.gsmart.model.Orders;
import com.gsmart.repository.OrdersRepository;
import com.gsmart.repository.RoomCategoryRepository;
import com.gsmart.repository.specification.ValidOrderSpecification;
import com.gsmart.service.RoomService;
import com.gsmart.ui.components.CalculatePane;
import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.components.OrderInfoPane;
import com.gsmart.ui.components.OrderTablePane;
import com.gsmart.ui.components.RoomInfoPane;
import com.gsmart.ui.utils.FXDialogController;

import application.ApplicationConfiguration;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class HomeController implements DialogController, Initializable {
	private FXMLDialog dialog;

	@Autowired
	CalculatePane cp;

	@Autowired
	private ApplicationConfiguration applicationConfiguration;

	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private RoomCategoryRepository roomCategoryRepository;

	@Autowired
	private RoomService roomService;

	@FXML
	VBox content;

	@FXML
	OrderInfoPane orderInfoPane;

	@FXML
	OrderTablePane orderTablePane;

	@FXML
	CalculatePane calculatorPane;

	@FXML
	RoomInfoPane roomInfoPane;

	@FXML
	Button receiveRoomBtn;

	@FXML
	Button removeRoom;

	private ValidOrderSpecification validOrderSpec = new ValidOrderSpecification();

	@FXML
	Label orderInfoPaneTitle;

	@FXML
	Button homeBtn;

	@FXML
	Button reportBtn;

	@FXML
	Button orderRoom;

	@FXML
	Button settingBtn;

	@FXML
	Button orderRoomBtn;

	@FXML
	Button saveOrderBtn;

	@FXML
	Button removeOrderBtn;

	@FXML Button editOrderBtn;

	/**
	 * Load again data for table, it will refresh content for table.
	 */
	public void updateOrderTable() {
		if (orderTablePane != null) {
			// Set date for table.
			orderTablePane.getTableView()
					.setItems(FXCollections.observableArrayList(ordersRepository.findAll(validOrderSpec)));

			// Set data for combo box.
			orderTablePane.getRoomType().setItems(FXCollections.observableArrayList(roomCategoryRepository.findAll()));
			orderTablePane.setController(this);
			orderTablePane.setOrdersRepository(this.ordersRepository);
		}
	}

	/**
	 * Using for injecting Spring Bean to UI Components.
	 */
	public void injectionBeanForComponents() {
		if (calculatorPane != null) {
			calculatorPane.setOrderRepository(ordersRepository);
			calculatorPane.setHomeController(this);
		}
	}

	@FXML
	public void openEditOrderStage(ActionEvent event) {
		roomService.SearchRoom();
		if (orderTablePane.getSeletedOrder() == null)
			return;

		applicationConfiguration.orderRoomDialog().show();
		// Before open order room stage, we need call controller and setup data
		applicationConfiguration.orderRoomController().setOrderInformation(orderTablePane.getSeletedOrder());

	}

	public void closeDialog() {
		dialog.close();
	}

	@Override
	public void setDialog(FXMLDialog dialog) {
		this.dialog = dialog;
	}

	@FXML
	public void removeOrder() {
		Orders orders = orderTablePane.getSeletedOrder();

		if (orders != null) {
			// Check whether user was confirmed this action.
			boolean isComfirm = FXDialogController.showConfirmationDialog("Remove Order",
					"Are you sure to remove this order ?", "Of customer " + orders.getCustomerName());

			if (isComfirm) {
				// Set status equal -1 mean this order will hidden.
				orders.setStatus(-1);
				ordersRepository.save(orders);
				updateOrderTable();
			}
		}
	}

	@FXML
	public void openOrderRoomStage(ActionEvent event) {
		applicationConfiguration.orderRoomDialog().show();
		// After showing, we need update UI components view.
		applicationConfiguration.orderRoomController().preparedForCreateNewOne();
	}

	public CalculatePane getCalculatorPane() {
		return calculatorPane;
	}

	public RoomInfoPane getRoomInfoPane() {
		return roomInfoPane;
	}

	public OrderInfoPane getOrderInfoPane() {
		return orderInfoPane;
	}

	public void updateReceiveRoomButton(Orders order) {
		if (order.getStatus() == 1 || order.getStatus() == 2)
			this.receiveRoomBtn.setVisible(false);
		else
			this.receiveRoomBtn.setVisible(true);
	}

	@FXML
	public void receiveRoom(ActionEvent event) {
		Orders order = orderTablePane.getSeletedOrder();

		// Check whether user was confirmed this action.
		boolean isComfirm = FXDialogController.showConfirmationDialog("Receive Room",
				"Are you sure to Receive this room ?", "For customer " + order.getCustomerName());

		if (isComfirm) {
			order.setStatus(1);
			ordersRepository.save(order);
			updateOrderTable();
			this.receiveRoomBtn.setVisible(false);
		}
	}

	@FXML
	public void openReportManagedDialog(ActionEvent event) {
		applicationConfiguration.reportSelectionManagedDialog().show();
	}

	/**
	 * Task for render multiple languages.
	 */
	public void multipleLanguagRender(ResourceBundle resources) {
		// Set column label for multiple languages.
		if (orderTablePane != null)
			orderTablePane.setColumnLabel(resources);

		// Initial multiple language.
		if (calculatorPane != null)
			calculatorPane.initFormFieldName(resources);

		// Initial multiple language.
		if (roomInfoPane != null)
			roomInfoPane.initFormFieldName(resources);

		// Initial multiple language.
		if (orderInfoPane != null) {
			orderInfoPaneTitle.setText(resources.getString("UIControls.OrderInfoPane"));
			orderInfoPane.initFormFieldName(resources);
		}

		// Change home components text.
		homeBtn.setText(resources.getString("HomeStage.Buttons.HomeButton"));
		reportBtn.setText(resources.getString("HomeStage.Buttons.ReportButton"));
		orderRoomBtn.setText(resources.getString("HomeStage.Buttons.OrderRoomButton"));
		settingBtn.setText(resources.getString("HomeStage.Buttons.SettingButton"));
		
		editOrderBtn.setText(resources.getString("UIControls.OrderInfoPane.Button.EditButton"));
		removeOrderBtn.setText(resources.getString("UIControls.OrderInfoPane.Button.RemoveButton"));
		saveOrderBtn.setText(resources.getString("UIControls.OrderInfoPane.Button.SaveButton"));
	}

	/**
	 * Used for change and update locale of application.
	 * <p>
	 * 
	 * @param newLocale
	 *            - new locale selected by user.
	 */
	public void changeLanguage(String newLocale) {
		if (newLocale == null | newLocale.equalsIgnoreCase(""))
			return;

		ResourceBundle bundle = ResourceBundle.getBundle("com.gsmart.ui.components.locale.messages",
				new Locale(newLocale));

		// Call method update again all components.
		multipleLanguagRender(bundle);
	}

	/**
	 * Called after JavaFX initial completed UI components.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		injectionBeanForComponents();
		updateOrderTable();
		multipleLanguagRender(resources);
	}

	@FXML
	public void openSettingStage() {
		applicationConfiguration.SettingStageDialog().show();
		// Get data and binding to form.
		applicationConfiguration.SettingStageController().setHomeController(this);
		applicationConfiguration.SettingStageController().preparedSettingStage();
	}

}
