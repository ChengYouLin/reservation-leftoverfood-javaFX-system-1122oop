package application;

import backEnd.Login;
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

public class LeftoverFoodRegisterController {

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
	public void backBtn(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("LoginFrame.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}

	@FXML
	public void checkBtn(ActionEvent event) throws IOException {

		int number = (int) (Math.random() * 90000000) + 10000000; // 這將確保數字始終是八位數
		String ID = String.valueOf(number);

		Login login = new Login();

		if (login.checkID(ID) == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("錯誤");
			alert.setHeaderText(null);
			alert.setContentText("請重新建立一次！");
			alert.showAndWait();
		} else {

			String name = leftNameField.getText();
			String appointment = appointmentField.getText();
			String address = addressField.getText();
//			String note = noteField.getText();

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

					LeftoverFood lFood = new LeftoverFood(ID);
					if (lFood.checkIDExist() == false) {
						if (lFood.initializeInfo(name, numAppointment, address)) {

							SharedData.ID = ID;

							FXMLLoader loader = new FXMLLoader(getClass().getResource("LeftoverFoodFrame2.fxml"));
							Parent root = loader.load();
							LeftoverFoodFrame2Controller frameController = loader.getController();

							frameController.frame2Btn(event);

							stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
							scene = new Scene(root);
							stage.setScene(scene);

						}
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
}
