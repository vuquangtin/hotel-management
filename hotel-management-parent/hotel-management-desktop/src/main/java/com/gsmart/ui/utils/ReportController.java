package com.gsmart.ui.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.data.jpa.domain.Specification;

import com.gsmart.model.Orders;
import com.gsmart.repository.OrdersRepository;
import com.gsmart.repository.specification.OrderReportSpecification;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ReportController {

	private static ImageIcon getLogoIcon() {
		return new ImageIcon(
				ReportController.class.getResource("/com/gsmart/ui/components/images/date-time-picker.png").toString());
	}

	/**
	 * Used for show window print invoice of order.
	 * <p>
	 * 
	 * @param orders
	 *            - Order object used for generate invoice.
	 */
	public static void printInvoice(Orders order) {
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					"src/main/resources/reports/orders/payment-sheet.jasper", null,
					new JRBeanArrayDataSource(new Orders[] { order }));

			// Set theme window.
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			JasperViewer viewer = new JasperViewer(jasperPrint, false);

			viewer.setIconImage(getLogoIcon().getImage());
			viewer.setTitle("Order Payment Sheet");
			viewer.setVisible(true);

		} catch (JRException | ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			FXDialogController.showErrorDialog(e);
		}
	}

	/**
	 * Used for show print window for all orders.
	 * <p>
	 * 
	 * @param orders
	 */
	public static void printDailyReport(OrdersRepository ordersRepository) {
		printOrdersReportByDate(ordersRepository, null, null);
	}

	/**
	 * Print all order have in database.
	 * <p>
	 * 
	 * @param ordersRepository
	 */
	public static void printAllOrderReport(OrdersRepository ordersRepository) {
		try {
			JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(ordersRepository.findAll(), false);

			// Set theme window.
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("OrderItemDataSource", itemsJRBean);
			parameters.put("employeeName", "Nguy\u1EC5n H\u1EEFu Quy\u1EC1n");

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					"src/main/resources/reports/orders/all-orders-report.jasper", parameters, new JREmptyDataSource());
			JasperViewer viewer = new JasperViewer(jasperPrint, false);

			viewer.setIconImage(getLogoIcon().getImage());
			viewer.setTitle("All Orders");
			viewer.setVisible(true);

		} catch (JRException | ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			FXDialogController.showErrorDialog(e);
		}
	}

	public static void printOrdersReportByDate(OrdersRepository ordersRepository, Date fromDate, Date toDate) {
		try {
			Specification<Orders> specification = null;

			// If both from date and to date equal null, it mean we need show
			// daily report.
			if (fromDate == null && toDate == null) {
				toDate = new Date();
				fromDate = new Date();

				specification = OrderReportSpecification.getDailyReportSpecification();
			} else {
				specification = OrderReportSpecification.getOrderReportByDateSpecification(fromDate, toDate);
			}

			JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(
					ordersRepository.findAll(specification), false);

			// Set theme window.
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("OrderItemDataSource", itemsJRBean);
			parameters.put("fromDate", fromDate);
			parameters.put("toDate", toDate);
			parameters.put("employeeName", "Nguy\u1EC5n H\u1EEFu Quy\u1EC1n");

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					"src/main/resources/reports/orders/order-report-by-date.jasper", parameters,
					new JREmptyDataSource());
			JasperViewer viewer = new JasperViewer(jasperPrint, false);

			viewer.setIconImage(getLogoIcon().getImage());
			viewer.setTitle("All Orders");
			viewer.setVisible(true);

		} catch (JRException | ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			FXDialogController.showErrorDialog(e);
		}
	}
}
