package application;

import java.io.IOException;

import backEnd.UserRegister;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class UserRegisterController {
	@FXML
	private TextField nameField;
	@FXML
	private TextField depField;
	@FXML
	private TextField addressField;
	@FXML
	private TextField phoneField;

	private Stage stage;
	private Scene scene;

	@FXML
	public void checkBtn(ActionEvent event) throws IOException {

		UserRegister user = new UserRegister(SharedData.ID, SharedData.password);

		String name = nameField.getText();
		String dep = depField.getText();
		String address = addressField.getText();
		String phone = phoneField.getText();

		if (name.length() > 5 || name.length() < 2) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("名字文字長度須小於五個字。");
			alert.showAndWait();
		} else if (dep.length() > 10 || dep.length() < 3) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("系所文字長度須小於十個字。");
			alert.showAndWait();
		} else if (address.length() > 30 || address.length() < 8) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("請輸入有效的電子郵件。");
			alert.showAndWait();
		} else if (phone.length() != 10) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("請輸入有效的手機號碼。");
			alert.showAndWait();
		} else {
			user.setInfo(dep, name, phone, address);

			if (user.insertAccountDB()) {
				if (user.insertUserInfoDB()) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame1.fxml"));
					Parent root = loader.load();
					UserFrame1Controller userFrameController = loader.getController();

					userFrameController.frame1Btn(event);

					stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					scene = new Scene(root);
					stage.setScene(scene);
				} else {
					System.out.println("inUserRegisterControllerClass, 無法順利存入「使用者資訊」的表格中！");
				}
			} else {
				System.out.println("inUserRegisterControllerClass, 無法順利存入「帳號」的表格中！");
			}
		}

	}
}
