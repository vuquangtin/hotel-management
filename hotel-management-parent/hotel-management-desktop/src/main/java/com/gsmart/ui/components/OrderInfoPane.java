package com.gsmart.ui.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.gsmart.model.Orders;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class OrderInfoPane extends GridPane{
	private GridPane column1;
	private GridPane column2;
	
	private TextField nameTxt = new TextField();
	private TextField addressTxt = new TextField();
	private TextField telephoneTxt = new TextField();
	private TextField idTxt = new TextField();
	private TextField fromDateTxt = new TextField();
	private TextField toDateTxt = new TextField();
	private TextField genderTxt = new TextField();
	
	private Label nameLbl = new Label("Name");
	private Label addressLbl = new Label("Address");
	private Label telephoneLbl = new Label("Telephone");
	private Label idLbl = new Label("ID");
	private Label fromDateLbl = new Label("From Date");
	private Label toDateLbl = new Label("To Date");
	private Label genderLbl = new Label("Gender");
	
	
	public OrderInfoPane () {
		super();
		setPadding(new Insets(5));

		renderColumn1();
		renderColumn2();
	}
	
	public void setOrderInfomation(Orders order) {
		this.nameTxt.setText(getValidString(order.getCustomerName()));
		this.addressTxt.setText(getValidString(order.getCustomerAddress()));
		this.telephoneTxt.setText(getValidString(order.getCustomerTelephone()));
		this.idTxt.setText(getValidString(order.getCustomerId()));
		this.fromDateTxt.setText(getValidStringDateTime(order.getCreatedAt()));
		this.toDateTxt.setText(getValidStringDateTime(order.getCheckOutAt()));
	}
	
	public String getValidString(String value) {
		if(value.isEmpty() || value == null) return "";
		return value;
	}
	
	public String getValidStringDateTime(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if(date != null) return dateFormat.format(date);
		return "";
	}
	
	public void renderColumn1 () {
		column1 = new GridPane();
		column1.setPadding(new Insets(5));
		
		addNodeToGridPane(column1 , getRow(nameLbl, nameTxt) , 0 , 0);
		addNodeToGridPane(column1 , getRow(addressLbl, addressTxt) , 0 , 1);
		addNodeToGridPane(column1 , getRow(telephoneLbl, telephoneTxt) , 0 , 2);
		addNodeToGridPane(column1 , getRow(idLbl, idTxt) , 0 , 3);
			
		setConstraints(column1, 0, 0);
		getChildren().add(column1);
	}
	
	
	public void renderColumn2 () {
		column2 = new GridPane();
		column2.setPadding(new Insets(5));
			
		addNodeToGridPane(column2 , getRow(fromDateLbl, fromDateTxt) , 0 , 0);
		addNodeToGridPane(column2 , getRow(toDateLbl, toDateTxt) , 0 , 1);
		addNodeToGridPane(column2 , getRow(genderLbl, genderTxt) , 0 , 2);
		
		setConstraints(column2, 1, 0);
		getChildren().add(column2);
	}
	
	@SuppressWarnings("static-access")
	public void addNodeToGridPane(GridPane gridPane , HBox hbox , int columnIndex , int rowIndex) {
		gridPane.setConstraints(hbox, columnIndex, rowIndex);
		gridPane.getChildren().add(hbox);
	}
	
	public HBox getRow(Label label , TextField textField) {
		textField.setEditable(false);
		HBox hb = new HBox();
		label.getStyleClass().add("control-label");
		label.setPrefSize(100, 10);
		hb.getChildren().addAll(label, textField);
		return hb;
	}
	
	
	/**
	 * Used for render form fields name for multiple languages.
	 * <P>
	 * 
	 * @param resource
	 *            - Resource Bundle file
	 */
	public void initFormFieldName(ResourceBundle resource) {
		nameLbl.setText(resource.getString("UIControls.OrderInfoPane.Fields.Name"));
		addressLbl.setText(resource.getString("UIControls.OrderInfoPane.Fields.Address"));
		telephoneLbl.setText(resource.getString("UIControls.OrderInfoPane.Fields.Telephone"));
		idLbl.setText(resource.getString("UIControls.OrderInfoPane.Fields.ID"));
		fromDateLbl.setText(resource.getString("UIControls.OrderInfoPane.Fields.FromDate"));
		toDateLbl.setText(resource.getString("UIControls.OrderInfoPane.Fields.ToDate"));
		genderLbl.setText(resource.getString("UIControls.OrderInfoPane.Fields.Gender"));
	}
	
}
