package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;

import backEnd.Order;
import backEnd.OrderItem;
import backEnd.User;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserFrame4Controller {

	private Stage stage;
	private Scene scene;

	@FXML
	private Label userNameLabel;
	@FXML
	private Label notPickTimeLabel;
	@FXML
	private Label orderLabel;
	@FXML
	private Label userNoteLabel;

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

		User user = new User(SharedData.ID);
		user.refreshInfo();

		userNameLabel.setText(user.getName());
		notPickTimeLabel.setText(String.valueOf(user.getNotPickUPTime()));

		Order order = new Order();
		ArrayList<OrderItem> orders = order.userRefreshInfo(SharedData.ID);

		orderLabel.setText("已經累積" + orders.size() + "筆");

	}

	@FXML
	public void setUserInfoBtn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame7.fxml"));
		Parent root = loader.load();
		UserFrame7Controller userFrame7Controller = loader.getController();

		userFrame7Controller.initilize();

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

	}

	@FXML
	public void userSwitchBtn(ActionEvent event) throws IOException {

		frame1Btn(event);
	}

	@FXML
	public void leftoverFoodSwitchBtn(ActionEvent event) throws IOException {

		frame3Btn(event);
	}
}
