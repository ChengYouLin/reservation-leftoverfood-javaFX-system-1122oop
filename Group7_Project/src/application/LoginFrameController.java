package application;

import backEnd.LeftoverFood;
import backEnd.Login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class LoginFrameController {

	@FXML
	private Button Btn1;

	private Stage stage;
	private Scene scene;

	private Login login;

	@FXML
	private TextField accountField;
	@FXML
	private PasswordField passwordField;

	@FXML
	public void registerBtn(ActionEvent event) throws IOException {

		// 切換到註冊頁面
		Parent root = FXMLLoader.load(getClass().getResource("RegisterFrame.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

	}

	@FXML
	public void loginBtn(ActionEvent event) throws IOException {

		login = new Login();

		String ID = accountField.getText();
		String password = passwordField.getText();
		char type;

		if (login.checkID(ID) && login.checkPassword(password)) {

			type = login.getType();

			if (type == 'C') {

				// 傳入帳號
				SharedData.ID = ID;

				// 切換到該商家的頁面
				FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame1.fxml"));
				Parent root = loader.load();
				ShopFrame1Controller shopFrameController = loader.getController();

				shopFrameController.frame1Btn(event);

				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);

			} else if (type == 'U') {

				SharedData.ID = ID;
				LeftoverFood lFood = new LeftoverFood();
				lFood.checkAllDBTime();

				// 切換到一般使用者的頁面
				FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame1.fxml"));
				Parent root = loader.load();
				UserFrame1Controller userFrameController = loader.getController();

				userFrameController.frame1Btn(event);

				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);

			} else {
				// 從系統端發現問題是無法順利判斷
				System.out.println("inLoginFrameControllerClass, 無法順利判斷帳號種類");
			}

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("登入失敗");
			alert.setHeaderText(null);
			alert.setContentText("帳號或密碼有錯誤！");
			alert.showAndWait();

			// 清除原本填寫的帳號密碼
			accountField.clear();
			passwordField.clear();
		}

	}

	@FXML
	public void leftoverFoodBtn(ActionEvent event) throws IOException {

		// 切換到剩食註冊頁面
		Parent root = FXMLLoader.load(getClass().getResource("LeftoverFoodRegister.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}
}
