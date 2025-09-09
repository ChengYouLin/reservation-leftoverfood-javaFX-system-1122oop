package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import backEnd.LeftoverFood;
import backEnd.Order;
import backEnd.OrderItem;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserFrame10Controller {
	@FXML
	private Label lFoodName;
	@FXML
	private Label quanLabel;
	@FXML
	private Label addressLabel;
	@FXML
	private Label pickTimeLabel;
	@FXML
	private Label appointmentLabel;

	private LeftoverFood lFood;

	// 現在剩下的可訂購數量
	private int nowQuan;
	private String pickUpTime;

	private Stage stage;
	private Scene scene;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void initialize(LeftoverFood lFood) {
		this.lFood = lFood;
		lFood.refreshInfo();

		lFoodName.setText(this.lFood.getMealName());

		Order order = new Order();
		ArrayList<OrderItem> allOrder = order.refreshInfo(lFood.getID());

		int total = lFood.getAppointmentQuantity();
		for (OrderItem i : allOrder) {
			String number = extractLeadingNumber(i.getQuantity());
			total = total - Integer.parseInt(number);
		}
		this.nowQuan = total;
		quanLabel.setText(String.valueOf(total));
		addressLabel.setText(this.lFood.getAddress());

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nowPlus15Minutes = now.plusMinutes(15);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String formattedTime = nowPlus15Minutes.format(formatter);

		pickUpTime = formattedTime;
		pickTimeLabel.setText("你需於「 " + formattedTime + "」完成取餐");
	}

	public static String extractLeadingNumber(String str) {
		String regex = "^\\d+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);

		if (matcher.find()) {
			return matcher.group();
		} else {
			return "";
		}
	}

	@FXML
	public void closeBtn(ActionEvent event) throws IOException {
		stage.close();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame1.fxml"));
		Parent root = loader.load();
		UserFrame1Controller userFrameController = loader.getController();
		userFrameController.frame1Btn(event);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

	}

	@FXML
	public void upBtn(ActionEvent event) {
		int num;
		num = Integer.parseInt(appointmentLabel.getText());
		System.out.println(num);

		num = num + 1;

		if (num < nowQuan) {
			appointmentLabel.setText(String.valueOf(num));
		}

	}

	@FXML
	public void downBtn(ActionEvent event) {

		int num;

		num = Integer.parseInt(appointmentLabel.getText());
		System.out.println(num);

		if (num > 0) {
			num = num - 1;

			appointmentLabel.setText(String.valueOf(num));
		}

	}

	@FXML
	public void checkBtn(ActionEvent event) throws IOException {

		OrderItem newOrder = new OrderItem(SharedData.ID, lFood.getID(), lFood.getMealName(),
				appointmentLabel.getText(), pickUpTime);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame9.fxml"));
		Parent root = loader.load();

		UserFrame9Controller userFrameController = loader.getController();
		userFrameController.initialize(appointmentLabel.getText(), "0", newOrder.getPickUpNum(), pickUpTime);

		Stage newStage = new Stage();
		newStage.setTitle("訂單編號");
		newStage.setScene(new Scene(root));

		userFrameController.setStage(newStage);
		userFrameController.setPrimaryStage(newStage);

		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.initOwner(((Node) event.getSource()).getScene().getWindow());

		newStage.showAndWait();

		stage.close();
	}
}
