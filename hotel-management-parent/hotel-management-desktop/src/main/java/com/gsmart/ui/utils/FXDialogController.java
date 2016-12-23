package com.gsmart.ui.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.gsmart.ui.controls.FXDateTimePicker;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Pair;

public class FXDialogController {

	private static void setDialogIcon(Dialog<?> dialog) {
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(
				FXDialogController.class.getResource("/com/gsmart/ui/components/images/logo.png").toString()));
	}

	/**
	 * Used for show exception to screen by dialog.
	 * <p>
	 * 
	 * @param ex
	 *            - Exception occur.
	 */
	public static void showErrorDialog(Exception ex) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception Dialog");
		alert.setHeaderText("Look, an Exception Dialog");
		alert.setContentText(ex.getMessage());

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);
		setDialogIcon(alert);

		alert.showAndWait();
	}

	public static void showInfomationDialog(String title, String headerText, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(message);
		setDialogIcon(alert);

		alert.showAndWait();
	}

	public static void showWarningDialog(String title, String headerText, String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(message);
		setDialogIcon(alert);

		alert.showAndWait();
	}

	public static void showErrorDialog(String title, String headerText, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(message);
		setDialogIcon(alert);

		alert.showAndWait();
	}

	/**
	 * 
	 * @param data
	 *            : data for selection combo box.
	 * @param title
	 *            : Title of dialog.
	 * @param headerText
	 *            : Header text of dialog
	 * @param message
	 *            : Content message
	 * @param defauleText
	 *            : Default text show on dialog.
	 * 
	 * @return Optional<?> contain user selection.
	 * 
	 *         Example. <code>
	 * Optional<String> result;
	 * 
	 * result.ifPresent(letter -> System.out.println("Your choice: " + letter));
	 * </code>
	 */
	public static Optional<?> showChoiceDialog(List<?> data, String title, String headerText, String message,
			String defauleText) {

		ChoiceDialog<?> dialog = new ChoiceDialog<>(defauleText, data);

		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(message);
		setDialogIcon(dialog);

		// Traditional way to get the response value.
		Optional<?> result = dialog.showAndWait();
		return result;
	}

	/**
	 * 
	 * @param title
	 *            : Title of dialog.
	 * @param headerText
	 *            : Header text of dialog
	 * @param message
	 *            : Content message
	 * @param defauleText
	 *            : Default text show on dialog.
	 * 
	 * @return String - string user entry.
	 * 
	 */
	public static String showTextInputDialog(String title, String headerText, String message, String defaultValue) {
		if (defaultValue == null)
			defaultValue = "";

		TextInputDialog dialog = new TextInputDialog(defaultValue);
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(message);
		setDialogIcon(dialog);

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			return result.get();
		}

		return "";
	}

	/**
	 * 
	 * @param title
	 *            : Title of dialog.
	 * @param headerText
	 *            : Header text of dialog
	 * @param message
	 *            : Content message
	 * @param defauleText
	 *            : Default text show on dialog.
	 * 
	 * @return boolean - true if user press OK button and false if user cancel
	 *         of close dialog.
	 * 
	 */
	public static boolean showConfirmationDialog(String title, String headerText, String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(message);
		setDialogIcon(alert);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}

	public static Optional<Pair<Date, Date>> showDateTimePickterDialog(String title, String headerText, String message) {
		Dialog<Pair<Date, Date>> dialog = new Dialog<>();
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(message);

		// Set the icon (must be included in the project).
		dialog.setGraphic(new ImageView(FXDialogController.class.getResource("/com/gsmart/ui/components/images/date-time-picker.png").toString()));
		
		// Set the button types.
		ButtonType submitButtonType = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

		FXDateTimePicker dateTimePicker = new FXDateTimePicker(false, "From date", "Time", "To date", "Time");
		dialog.getDialogPane().setContent(dateTimePicker);
		
		// Convert the result to a date date -pair when the submit button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == submitButtonType) {
		        return new Pair<>(dateTimePicker.getFirstDate(), dateTimePicker.getSecondDate());
		    }
		    return null;
		});
		
		return dialog.showAndWait();
	}

}
