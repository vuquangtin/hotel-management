package com.gsmart.ui.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.gsmart.model.Orders;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ReportController {
	
	private static ImageIcon getLogoIcon() {
		return new ImageIcon(ReportController.class.getResource("/com/gsmart/ui/components/images/logo.png").toExternalForm());
	}
	
	/**
	 * Used for show window print invoice of order.
	 * <p>
	 * @param orders - Order object used for generate invoice.
	 */
	public static void printInvoice(Orders order) {
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/reports/orders/payment-sheet.jasper",
					null, new JRBeanArrayDataSource(new Orders[] {order}));
			
			//Set theme window.
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			
			JasperViewer viewer = new JasperViewer(jasperPrint, false);
			
			viewer.setIconImage(getLogoIcon().getImage());
			viewer.setTitle("Order Payment Sheet");
			viewer.setVisible(true);
			
		} catch (JRException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			FXDialogController.showErrorDialog(e);
		}
	}
	
	/**
	 * Used for show print window for all orders.
	 * <p>
	 * @param orders
	 */
	public static void printOrdersReport(List<Orders> orders) {
		try {
			JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(orders, false);
			
			//Set theme window.
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ItemDataSource", itemsJRBean);

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					"src/main/resources/reports/orders/all-order-report.jasper", parameters, new JREmptyDataSource());	
			JasperViewer viewer = new JasperViewer(jasperPrint, false);
			
			viewer.setIconImage(getLogoIcon().getImage());
			viewer.setTitle("All Orders");
			viewer.setVisible(true);
			
		} catch (JRException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			FXDialogController.showErrorDialog(e);
		}
	}
}
