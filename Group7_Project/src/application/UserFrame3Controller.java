package application;

import backEnd.LeftoverFood;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

public class UserFrame3Controller {

	private Stage stage;
	private Scene scene;

	@FXML
	private TextField leftNameField;
	@FXML
	private TextField appointmentField;
	@FXML
	private TextField addressField;
	@FXML
	private TextField noteField;

	@FXML
	public void frame1Btn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame1.fxml"));
		Parent root = loader.load();
		UserFrame1Controller userFrameController = loader.getController();

		userFrameController.frame1Btn(event);

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}

	@FXML
	public void frame2Btn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame2.fxml"));
		Parent root = loader.load();
		UserFrame2Controller userFrame2Controller = loader.getController();

		userFrame2Controller.frame2Btn(event);

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}

	@FXML
	public void frame3Btn(ActionEvent event) throws IOException {

		LeftoverFood lFood = new LeftoverFood(SharedData.ID);

		if (lFood.checkIDExist()) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LeftoverFoodFrame1.fxml"));
			Parent root = loader.load();
			LeftoverFoodFrame1Controller frameController = loader.getController();

			frameController.frame1Btn(event);

			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
		}
	}

	public boolean checkLFoodExist() {

		LeftoverFood lFood = new LeftoverFood(SharedData.ID);

		if (lFood.checkIDExist()) {
			return true;
		}
		return false;
	}

	@FXML
	public void frame4Btn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame4.fxml"));
		Parent root = loader.load();
		UserFrame4Controller userFrameController = loader.getController();

		userFrameController.frame4Btn(event);

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}

	@FXML
	public void checkBtn(ActionEvent event) throws IOException {

		LeftoverFood lFood = new LeftoverFood(SharedData.ID);

		String name = leftNameField.getText();
		String appointment = appointmentField.getText();
		String address = addressField.getText();

		int numAppointment = 0;

		if (name.length() < 2 || name.length() > 20) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("請說明清楚餐點的名稱20字以內");
			alert.showAndWait();
		} else if (address.length() < 1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("請填寫清楚詳細位置地點。");
			alert.showAndWait();
		} else {
			try {
				numAppointment = Integer.parseInt(appointment);

				if (lFood.initializeInfo(name, numAppointment, address)) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("LeftoverFoodFrame1.fxml"));
					Parent root = loader.load();
					LeftoverFoodFrame1Controller frameController = loader.getController();

					frameController.frame1Btn(event);

					stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					scene = new Scene(root);
					stage.setScene(scene);
				}

			} catch (NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("錯誤訊息");
				alert.setHeaderText(null);
				alert.setContentText("請輸入數字");

				alert.showAndWait();
			}
		}

	}
}
