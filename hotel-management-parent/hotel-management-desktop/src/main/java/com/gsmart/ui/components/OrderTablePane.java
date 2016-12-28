package com.gsmart.ui.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import org.springframework.data.jpa.domain.Specifications;

import com.gsmart.model.Orders;
import com.gsmart.model.Room;
import com.gsmart.model.RoomCategory;
import com.gsmart.repository.OrdersRepository;
import com.gsmart.repository.specification.OrdersSpecification;
import com.gsmart.ui.controller.HomeController;
import com.gsmart.ui.utils.FxDatePickerConverter;
import com.gsmart.ui.utils.JavaFXUtils;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class OrderTablePane extends VBox {

	private HomeController homeController;
	private OrdersRepository ordersRepository;

	private TextField searchTextField = new TextField();

	private ToggleGroup group = new ToggleGroup();
	private RadioButton customerNameRadioBtn = new RadioButton("By name");
	private RadioButton roomNameRadioBtn = new RadioButton("By room");
	private RadioButton customerIdRadioBtn = new RadioButton("By ID");

	private DatePicker fromDate = new DatePicker();
	private DatePicker toDate = new DatePicker();
	private ComboBox<RoomCategory> roomType = new ComboBox<RoomCategory>();

	private TableView<Orders> table = new TableView<Orders>();

	private Button searchButton = new Button("Search");

	public OrderTablePane() {
		super();
		getStyleClass().add("order-table-pane");
		getChildren().add(getTopBar());
		getChildren().add(getTable());

		initSearchButtonListener();
		setPadding(new Insets(5));
	}

	public HBox getTopBar() {
		HBox hb = new HBox();
		VBox left = new VBox();
		VBox right = new VBox();

		// == Initialization Left Pane === //
		left.setPadding(new Insets(20, 0, 0, 0));

		searchTextField.setPromptText("Search ...");
		left.getChildren().add(searchTextField);

		customerNameRadioBtn.setToggleGroup(group);
		roomNameRadioBtn.setToggleGroup(group);
		customerIdRadioBtn.setToggleGroup(group);

		customerIdRadioBtn.setUserData("SEARCH_BY_ID");
		roomNameRadioBtn.setUserData("SEARCH_BY_ROOM_NAME");
		customerNameRadioBtn.setUserData("SEARCH_BY_NAME");

		customerNameRadioBtn.setSelected(true);

		HBox toggleRadioBtn = new HBox();
		toggleRadioBtn.getChildren().add(customerNameRadioBtn);
		toggleRadioBtn.getChildren().add(roomNameRadioBtn);
		toggleRadioBtn.getChildren().add(customerIdRadioBtn);

		left.getChildren().add(toggleRadioBtn);
		// == Initialization Left Pane === //

		// == Initialization Right Pane === //

		HBox rightTop = new HBox();
		rightTop.setPadding(new Insets(10));
		rightTop.setSpacing(20);

		// Date picker
		VBox vb = new VBox();
		vb.setPadding(new Insets(2));
		vb.setSpacing(2);

		fromDate.setPrefWidth(120);
		toDate.setPrefWidth(120);

		FxDatePickerConverter converter = new FxDatePickerConverter();
		fromDate.setConverter(converter);
		toDate.setConverter(converter);
		fromDate.setPromptText("From date");
		toDate.setPromptText("To date");

		vb.getChildren().add(fromDate);
		vb.getChildren().add(toDate);

		rightTop.getChildren().add(vb);
		// Date picker

		roomType.setPromptText("All room type");
		rightTop.getChildren().add(roomType);

		searchButton.getStyleClass().add("button-raised");
		searchButton.setDefaultButton(true);
		searchButton.setMnemonicParsing(false);
		searchButton.setPadding(new Insets(5));

		Text materialDesignIcon = GlyphsDude.createIcon(MaterialDesignIcon.MAGNIFY, "2.5em");
		materialDesignIcon.setFill(Color.WHITE);
		searchButton.setGraphic(materialDesignIcon);

		rightTop.getChildren().add(searchButton);
		right.getChildren().add(rightTop);
		// == Initialization Right Pane === //

		hb.getChildren().add(left);
		hb.getChildren().add(right);

		return hb;
	}

	public void initSearchButtonListener() {
		searchButton.setOnAction((event) -> {
			search();
		});
	}

	public void search() {
		Orders orderSampler = new Orders();
		orderSampler.setRoom(new Room());

		switch (group.getSelectedToggle().getUserData().toString()) {
		case "SEARCH_BY_ID": {
			orderSampler.setCustomerId(JavaFXUtils.getTextFieldValue(searchTextField));
			break;
		}

		case "SEARCH_BY_NAME": {
			orderSampler.setCustomerName(JavaFXUtils.getTextFieldValue(searchTextField));
			break;
		}

		case "SEARCH_BY_ROOM_NAME": {
			orderSampler.getRoom().setName(JavaFXUtils.getTextFieldValue(searchTextField));
			break;
		}
		}

		orderSampler.setCreatedAt(JavaFXUtils.getDatePickerValue(fromDate));
		orderSampler.setCheckOutAt(JavaFXUtils.getDatePickerValue(toDate));
		
		System.out.println("Order Sampler Create At : " + orderSampler.getCreatedAt() + " And Check out at " + orderSampler.getCheckOutAt());
		orderSampler.getRoom().setRoomCategory(roomType.getValue());

		OrdersSpecification ordersSpecification = new OrdersSpecification(orderSampler);
		table.setItems(FXCollections
				.observableArrayList(getOrdersRepository().findAll(Specifications.where(ordersSpecification))));
	}

	@SuppressWarnings("unchecked")
	public TableView<Orders> getTable() {

		TableColumn<Orders, String> indexCol = new TableColumn<Orders, String>("ID");
		TableColumn<Orders, Text> statusCol = new TableColumn<Orders, Text>("Status");
		TableColumn<Orders, String> customerNameCol = new TableColumn<Orders, String>("Customer Name");
		TableColumn<Orders, String> roomNameCol = new TableColumn<Orders, String>("Room Name");
		TableColumn<Orders, String> fromDateCol = new TableColumn<Orders, String>("From Date");
		TableColumn<Orders, String> toDateCol = new TableColumn<Orders, String>("To Date");
		TableColumn<Orders, Integer> dateNumberCol = new TableColumn<Orders, Integer>("Date Number");

		indexCol.setCellValueFactory(new PropertyValueFactory<Orders, String>("id"));
		customerNameCol.setCellValueFactory(new PropertyValueFactory<Orders, String>("customerName"));
		roomNameCol.setCellValueFactory(new PropertyValueFactory<Orders, String>("roomName"));

		statusCol.setCellValueFactory(row -> {
			Text text = null;
			switch (row.getValue().getStatus()) {
			case 0: {
				text = GlyphsDude.createIcon(MaterialDesignIcon.CLOCK, "1em");
				text.setFill(Color.GRAY);
				break;
			}
			case 1: {
				text = GlyphsDude.createIcon(MaterialDesignIcon.KEY_VARIANT, "1em");
				text.setFill(Color.GREEN);
				break;
			}
			case 2: {
				text = GlyphsDude.createIcon(MaterialDesignIcon.CHECKBOX_MARKED, "1em");
				text.setFill(Color.BLUEVIOLET);
				break;
			}
			}
			text.setText(row.getValue().getRoomStatus());
			return new SimpleObjectProperty<Text>(text);
		});

		fromDateCol.setCellValueFactory(row -> {
			SimpleStringProperty property = new SimpleStringProperty();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			property.setValue(dateFormat.format(row.getValue().getCreatedAt()));
			return property;
		});

		toDateCol.setCellValueFactory(row -> {
			SimpleStringProperty property = new SimpleStringProperty();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			property.setValue(dateFormat.format(row.getValue().getCheckOutAt()));
			return property;
		});

		dateNumberCol.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("numberDate"));

		table.getColumns().addAll(indexCol, statusCol, customerNameCol, roomNameCol, fromDateCol, toDateCol,
				dateNumberCol);

		table.getSelectionModel().selectedIndexProperty().addListener((object) -> {
			Orders orderSelected = table.getSelectionModel().getSelectedItem();
			if (orderSelected != null) {
				getController().getOrderInfoPane().setOrderInfomation(orderSelected);
				getController().getCalculatorPane().setCalculatorInformation(orderSelected);
				getController().getRoomInfoPane().setRoomInformation(orderSelected);
				getController().updateReceiveRoomButton(orderSelected);
			}
		});

		table.setMaxHeight(200);
		return table;
	}

	public Orders getSeletedOrder() {
		return table.getSelectionModel().getSelectedItem();
	}

	public TableView<Orders> getTableView() {
		return this.table;
	}

	public void setController(HomeController homeController) {
		this.homeController = homeController;
	}

	public HomeController getController() {
		return this.homeController;
	}

	public ComboBox<RoomCategory> getRoomType() {
		return roomType;
	}

	public OrdersRepository getOrdersRepository() {
		return ordersRepository;
	}

	public void setOrdersRepository(OrdersRepository ordersRepository) {
		this.ordersRepository = ordersRepository;
	}
	
	/**
	 * Used for set column name for table.
	 * 
	 * <p>
	 * @param resources : ResourceBundle of application.
	 */
	public void setColumnLabel(ResourceBundle resources) {
		
		//Notice: Order of columns label is very important.
		String[] columnLabels = { 
				"UIControls.OrderTablePane.Index", "UIControls.OrderTablePane.OrderStatus",
				"UIControls.OrderTablePane.CustomerName", "UIControls.OrderTablePane.RoomName",
				"UIControls.OrderTablePane.FromDate", "UIControls.OrderTablePane.ToDate",
				"UIControls.OrderTablePane.NumbersDate", 
		};
		
		for (int i = 0; i <= 6; i++) {
			table.getColumns().get(i).setText(resources.getString(columnLabels[i]));
		}
		
		//Update form fields.
		customerIdRadioBtn.setText(resources.getString("UIControls.OrderTablePane.Forms.ByID"));
		roomNameRadioBtn.setText(resources.getString("UIControls.OrderTablePane.Forms.ByRoom"));
		customerNameRadioBtn.setText(resources.getString("UIControls.OrderTablePane.Forms.ByName"));
		searchTextField.setPromptText(resources.getString("UIControls.OrderTablePane.Forms.SearchPromptText"));
		roomType.setPromptText(resources.getString("UIControls.OrderTablePane.Forms.RoomTypePromptText"));
		fromDate.setPromptText(resources.getString("UIControls.OrderTablePane.Forms.FromDate"));
		toDate.setPromptText(resources.getString("UIControls.OrderTablePane.Forms.ToDate"));
		searchButton.setText(resources.getString("UIControls.OrderTablePane.Forms.SearchButton"));
		
	}
}
