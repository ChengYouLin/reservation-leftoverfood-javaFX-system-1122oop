package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import backEnd.Shop;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ShopFrame4Controller {

	@FXML
	private Label userNameLabel;
	@FXML
	private Label notPickTimeLabel;
	@FXML
	private Label workingWeekLabel;
	@FXML
	private Label workngTimeLabel;
	@FXML
	private Label operatingTimeLabel;
	@FXML
	private Label nowWorkingStateLabel;

	private Stage stage;
	private Scene scene;

	@FXML
	public void frame1Btn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame1.fxml"));
		Parent root = loader.load();
		ShopFrame1Controller shopFrameController = loader.getController();
		shopFrameController.frame1Btn(event);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}

	@FXML
	public void frame2Btn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame2.fxml"));
		Parent root = loader.load();
		ShopFrame2Controller shopFrame2Controller = loader.getController();
		shopFrame2Controller.frame2Btn(event);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}

	@FXML
	public void frame3Btn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame3.fxml"));
		Parent root = loader.load();
		ShopFrame3Controller shopFrameController = loader.getController();
		shopFrameController.frame3Btn(event);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}

	@FXML
	public void frame4Btn(ActionEvent event) {

		Shop shop = new Shop(SharedData.ID);
		shop.refreshInfo();

		userNameLabel.setText(shop.getShop());
		notPickTimeLabel.setText(String.valueOf(shop.getNotPickUpTime()));

		String week = shop.getWorkingWeek();
		String result = "";
		if (week.charAt(0) == '1') {
			result = result + "一. ";
		}
		if (week.charAt(1) == '1') {
			result = result + "二. ";
		}
		if (week.charAt(2) == '1') {
			result = result + "三. ";
		}
		if (week.charAt(3) == '1') {
			result = result + "四. ";
		}
		if (week.charAt(4) == '1') {
			result = result + "五. ";
		}
		if (week.charAt(5) == '1') {
			result = result + "六. ";
		}
		if (week.charAt(6) == '1') {
			result = result + "日. ";
		}

		workingWeekLabel.setText(result);

		LocalTime now = LocalTime.now();
		String startTimeStr = shop.getOperatingTimeStart();
		String endTimeStr = shop.getOperatingTimeEnd();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		LocalTime startTime = LocalTime.parse(startTimeStr, formatter);
		LocalTime endTime = LocalTime.parse(endTimeStr, formatter);

		if (now.isAfter(startTime) && now.isBefore(endTime)) {
			nowWorkingStateLabel.setText("開放預約中");
		} else {
			nowWorkingStateLabel.setText("停止預約中");
		}

		workngTimeLabel.setText(shop.getWorkingTimeStart() + " ~ " + shop.getWorkingTimeEnd());
		operatingTimeLabel.setText(shop.getOperatingTimeStart() + " ~ " + shop.getOperatingTimeEnd());

	}

	@FXML
	public void setShopInfoBtn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame10.fxml"));
		Parent root = loader.load();
		ShopFrame10Controller shopFrameController = loader.getController();
		shopFrameController.initialize();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

	}

	@FXML
	public void shopSwitchBtn(ActionEvent event) throws IOException {

		frame1Btn(event);
	}

	@FXML
	public void leftoverFoodSwitchBtn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LeftoverFoodRegister.fxml"));
		Parent root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}
}
