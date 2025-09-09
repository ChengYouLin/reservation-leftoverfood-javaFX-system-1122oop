package application;

import java.io.IOException;

import backEnd.Register;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

public class RegisterFrameController {
	@FXML
	private TextField accountField;
	@FXML
	private PasswordField passwordField;

	private Stage stage;
	private Scene scene;
	private String ID;

	public boolean checkIDPassword(char type) {
		boolean result = false;

		this.ID = accountField.getText();
		String password = passwordField.getText();

		if (ID.length() > 20 || ID.length() < 8) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("帳號長度請介於8~20位數。");
			alert.showAndWait();

			accountField.clear();
			passwordField.clear();

			return result;
		}

		if (password.length() > 20 || password.length() < 8) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("密碼長度請介於8~20位數。");
			alert.showAndWait();

			accountField.clear();
			passwordField.clear();

			return result;
		}

		Register account = new Register(ID, password, type);

		if (account.checkID(ID) && account.checkPassword(password)) {
			SharedData.ID = ID;
			SharedData.password = password;
			return result = true;
		} else {
			// 跳出警告視窗，該帳號已經有人使用過！
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("註冊失敗");
			alert.setHeaderText(null);
			alert.setContentText("該帳號已經有人使用！請重新建立。");
			alert.showAndWait();

			// 清除原本填寫的帳號密碼
			accountField.clear();
			passwordField.clear();
		}

		return result;
	}

	@FXML
	public void shopBtn(ActionEvent event) throws IOException {
		if (checkIDPassword('C')) {
			Parent root = FXMLLoader.load(getClass().getResource("ShopRegister.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
		}
	}

	@FXML
	public void userBtn(ActionEvent event) throws IOException {
		if (checkIDPassword('U')) {
			Parent root = FXMLLoader.load(getClass().getResource("UserRegister.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
		}

	}
}
