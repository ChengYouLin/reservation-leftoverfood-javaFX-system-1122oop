package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

import backEnd.Shop;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;

public class ShopFrame10Controller {
	@FXML
	private Label userNameLabel;
	@FXML
	private TextField shopNameField;
	@FXML
	private TextField addressField;
	@FXML
	private TextField phoneField;
	@FXML
	private TextField mailField;
	@FXML
	private CheckBox mon;
	@FXML
	private CheckBox tue;
	@FXML
	private CheckBox wed;
	@FXML
	private CheckBox thu;
	@FXML
	private CheckBox fri;
	@FXML
	private CheckBox sat;
	@FXML
	private CheckBox sun;
	@FXML
	private TextField workingBeginHour;
	@FXML
	private TextField workingBeginMin;
	@FXML
	private TextField workingEndHour;
	@FXML
	private TextField workingEndMin;
	@FXML
	private TextField operatingBeginHour;
	@FXML
	private TextField operatingBeginMin;
	@FXML
	private TextField operatingEndHour;
	@FXML
	private TextField operatingEndMin;
	@FXML
	private TextField notPickUpTime;

	private Stage stage;
	private Scene scene;
	private Shop shop;

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
	public void frame4Btn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame4.fxml"));
		Parent root = loader.load();
		UserFrame4Controller userFrameController = loader.getController();
		userFrameController.frame4Btn(event);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}

	public void initialize() {
		Shop shop = new Shop(SharedData.ID);
		this.shop = shop;
		shop.refreshInfo();
		userNameLabel.setText(shop.getShop());
	}

	@FXML
	public void checkBtn(ActionEvent event) {
		String name = shopNameField.getText();
		String address = addressField.getText();
		String mail = mailField.getText();
		String phone = phoneField.getText();
		String beginM = workingBeginMin.getText();
		String beginH = workingBeginHour.getText();
		String endH = workingEndHour.getText();
		String endM = workingEndMin.getText();
		String bookBeginM = operatingBeginMin.getText();
		String bookBeginH = operatingBeginHour.getText();
		String bookEndH = operatingEndHour.getText();
		String bookEndM = operatingEndMin.getText();
		String notPickUpTimes = notPickUpTime.getText();
		int numNotPickUpTime = 0;

		String workingWeek = "";

		if (mon.isSelected()) {
			workingWeek = workingWeek + "1";
		} else {
			workingWeek = workingWeek + "0";
		}

		if (tue.isSelected()) {
			workingWeek = workingWeek + "1";
		} else {
			workingWeek = workingWeek + "0";
		}

		if (wed.isSelected()) {
			workingWeek = workingWeek + "1";
		} else {
			workingWeek = workingWeek + "0";
		}

		if (thu.isSelected()) {
			workingWeek = workingWeek + "1";
		} else {
			workingWeek = workingWeek + "0";
		}

		if (fri.isSelected()) {
			workingWeek = workingWeek + "1";
		} else {
			workingWeek = workingWeek + "0";
		}

		if (sat.isSelected()) {
			workingWeek = workingWeek + "1";
		} else {
			workingWeek = workingWeek + "0";
		}

		if (sun.isSelected()) {
			workingWeek = workingWeek + "1";
		} else {
			workingWeek = workingWeek + "0";
		}

		if (name.length() > 20 || name.length() < 1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("請填寫清楚店家名稱。");
			alert.showAndWait();
		} else if (address.length() < 1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("請填寫清楚店家地址。");
			alert.showAndWait();
		} else if (mail.length() > 30 || mail.length() < 8) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("請輸入有效的電子郵件。");
			alert.showAndWait();
		} else if (phone.length() < 8 || phone.length() > 10) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("請輸入有效的電話號碼（須為8~10碼）。");
			alert.showAndWait();
		} else if (beginM.length() != 2 || beginH.length() != 2 || endH.length() != 2 || endM.length() != 2
				|| bookBeginM.length() != 2 || bookBeginH.length() != 2 || bookEndH.length() != 2
				|| bookEndM.length() != 2) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("所有時間欄位都需要填寫兩位數字。\n例如：下午兩點五分，請填寫：「14:05」\n注意：如果時間填錯，使用者將無法從主頁中看見你的餐廳。");
			alert.showAndWait();
		} else if (mon.isSelected() == false && tue.isSelected() == false && wed.isSelected() == false
				&& thu.isSelected() == false && fri.isSelected() == false && sun.isSelected() == false
				&& sat.isSelected() == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("每週至少營業一天。");
			alert.showAndWait();
		} else {

			try {
				numNotPickUpTime = Integer.parseInt(notPickUpTimes);

				String workingTimeStart = beginH + ":" + beginM;
				String workingTimeEnd = endH + ":" + endM;
				String operatingTimeStart = bookBeginH + ":" + bookBeginM;
				String operatingTimeEnd = bookEndH + ":" + bookEndM;

				shop.setSomeInfo("Shop", name);
				shop.setSomeInfo("Address", address);
				shop.setSomeInfo("Telephone", phone);
				shop.setSomeInfo("Email", mail);
				shop.setSomeInfo("WorkingWeek", workingWeek);
				shop.setSomeInfo("WorkingTimeStart", workingTimeStart);
				shop.setSomeInfo("WorkingTimeEnd", workingTimeEnd);
				shop.setSomeInfo("OperatingTimeStart", operatingTimeStart);
				shop.setSomeInfo("OperatingTimeEnd", operatingTimeEnd);
				shop.setSomeInfo("NotPickUpTime", numNotPickUpTime);
				shop.setSomeInfo("WorkingTimeStart", workingTimeStart);
				shop.setSomeInfo("WorkingTimeStart", workingTimeStart);

				FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame4.fxml"));
				Parent root = loader.load();
				UserFrame4Controller userFrameController = loader.getController();
				userFrameController.frame4Btn(event);
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);

			} catch (NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("錯誤訊息");
				alert.setHeaderText(null);
				alert.setContentText("請輸入數字");

				alert.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
