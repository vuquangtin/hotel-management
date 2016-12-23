package com.gsmart.ui.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.gsmart.repository.OrdersRepository;
import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.utils.FXDialogController;
import com.gsmart.ui.utils.ReportController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.util.Pair;

public class ReportManagedController implements DialogController {
	@Autowired
	private OrdersRepository orderRepository;

	@SuppressWarnings("unused")
	private FXMLDialog fXMLDialog;

	@Override
	public void setDialog(FXMLDialog dialog) {
		this.fXMLDialog = dialog;
	}

	@FXML
	public void renderDailyReport(ActionEvent event) {
		ReportController.printDailyReport(orderRepository);
	}

	@FXML
	public void renderAllOrdersReport(ActionEvent event) {
		ReportController.printAllOrderReport(orderRepository);
	}

	@FXML
	public void renderOrderReportByDate(ActionEvent event) {
		Optional<Pair<Date, Date>> result = FXDialogController.showDateTimePickterDialog(
				"Select Date For Generating Report", "Please selected two date time correct.", "Notice");
		
		//Check whether user selected date time or not.
		result.ifPresent(selectedData -> {
			// From Date =  selectedData.getKey() , To Date = selectedData.getValue() 
			ReportController.printOrdersReportByDate(orderRepository, selectedData.getKey(), selectedData.getValue());
		});

	}

}
