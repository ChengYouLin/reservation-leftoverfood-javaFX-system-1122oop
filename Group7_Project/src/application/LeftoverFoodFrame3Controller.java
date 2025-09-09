package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import backEnd.LeftoverFood;
import backEnd.Login;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LeftoverFoodFrame3Controller {

	@FXML
	private Label userIDLabel;
	@FXML
	private Label setUpTime;
	@FXML
	private Label deleteTime;

	private Stage stage;
	private Scene scene;

	public void frame1Btn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LeftoverFoodFrame1.fxml"));
		Parent root = loader.load();
		LeftoverFoodFrame1Controller LeftoverFoodFrameController = loader.getController();

		LeftoverFoodFrameController.frame1Btn(event);

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}

	@FXML
	public void frame2Btn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LeftoverFoodFrame2.fxml"));
		Parent root = loader.load();
		LeftoverFoodFrame2Controller LeftoverFoodFrameController = loader.getController();

		LeftoverFoodFrameController.frame2Btn(event);

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}

	@FXML
	public void frame3Btn(ActionEvent event) throws IOException {
		LeftoverFood lFood = new LeftoverFood(SharedData.ID);
		lFood.refreshInfo();

		userIDLabel.setText(lFood.getID() + " / " + lFood.getMealName());
		setUpTime.setText(lFood.getSetUpTime());
		deleteTime.setText(lFood.getDeleteTime());

	}

	@FXML
	public void userSwitchBtn(ActionEvent event) throws IOException {

		Login login = new Login();
		if (login.checkID(SharedData.ID)) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame4.fxml"));
			Parent root = loader.load();
			UserFrame4Controller userFrameController = loader.getController();

			userFrameController.frame4Btn(event);

			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("本帳號屬於一次性帳號，無法做切換。\n亦將於建立時間後兩小時自動刪除，且無法重複登入。");
			alert.showAndWait();
		}

	}

	@FXML
	public void leftoverFoodSwitchBtn(ActionEvent event) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("提示");
		alert.setHeaderText(null);
		alert.setContentText("本帳號屬於一次性帳號，無法做切換。\n亦將於建立時間後兩小時自動刪除，且無法重複登入。");
		alert.showAndWait();
	}

	@FXML
	public void deleteThisIDBtn(ActionEvent event) throws IOException {
		LeftoverFood lFood = new LeftoverFood(SharedData.ID);
		lFood.refreshInfo();
		lFood.checkAllDBTime();
		lFood.deleteID();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFrame.fxml"));
		Parent root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

	}
}
