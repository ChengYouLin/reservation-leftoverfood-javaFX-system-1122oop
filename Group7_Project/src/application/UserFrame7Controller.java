package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

import backEnd.User;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class UserFrame7Controller {

	private Stage stage;
	private Scene scene;

	@FXML
	private Label userNameLabel;
	@FXML
	private TextField depField;
	@FXML
	private TextField nameField;
	@FXML
	private TextField phoneField;
	@FXML
	private TextField addressField;

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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame3.fxml"));
		Parent root = loader.load();
		UserFrame3Controller userFrameController = loader.getController();

		userFrameController.frame3Btn(event);
		boolean result = userFrameController.checkLFoodExist();

		if (!result) {
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
		}
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

	public void initilize() {
		User user = new User(SharedData.ID);
		user.refreshInfo();
		userNameLabel.setText(user.getName());
	}

	@FXML
	public void checkBtn(ActionEvent event) throws IOException {

		User user = new User(SharedData.ID);

		user.refreshInfo();

		userNameLabel.setText(user.getName());

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
			user.setSomeInfo("Name", name);
			user.setSomeInfo("Department", dep);
			user.setSomeInfo("Cellphone", phone);
			user.setSomeInfo("Email", address);

			FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame4.fxml"));
			Parent root = loader.load();
			UserFrame4Controller userFrameController = loader.getController();

			userFrameController.frame4Btn(event);

			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
		}

	}
}
