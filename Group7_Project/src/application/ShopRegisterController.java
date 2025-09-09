package application;

import java.io.IOException;

import backEnd.ShopRegister;
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
import javafx.scene.control.CheckBox;

public class ShopRegisterController {
	@FXML
	private TextField shopNameField;
	@FXML
	private TextField addressField;
	@FXML
	private TextField mailAddress;
	@FXML
	private TextField phoneAddress;
	@FXML
	private CheckBox mon;
	@FXML
	private CheckBox tue;
	@FXML
	private CheckBox thu;
	@FXML
	private CheckBox fri;
	@FXML
	private CheckBox wed;
	@FXML
	private CheckBox sat;
	@FXML
	private CheckBox sun;
	@FXML
	private TextField beginMin;
	@FXML
	private TextField beginHour;
	@FXML
	private TextField endHour;
	@FXML
	private TextField endMin;
	@FXML
	private TextField bookBeginMin;
	@FXML
	private TextField bookBeginHour;
	@FXML
	private TextField bookEndHour;
	@FXML
	private TextField bookEndMin;
	@FXML
	private TextField notPickUpTimes;

	private Stage stage;
	private Scene scene;

	@FXML
	public void checkBtn(ActionEvent event) throws IOException {

		ShopRegister shop = new ShopRegister(SharedData.ID, SharedData.password);

		String name = shopNameField.getText();
		String address = addressField.getText();
		String mail = mailAddress.getText();
		String phone = phoneAddress.getText();
		String beginM = beginMin.getText();
		String beginH = beginHour.getText();
		String endH = endHour.getText();
		String endM = endMin.getText();
		String bookBeginM = bookBeginMin.getText();
		String bookBeginH = bookBeginHour.getText();
		String bookEndH = bookEndHour.getText();
		String bookEndM = bookEndMin.getText();
		String notPickUpTime = notPickUpTimes.getText();
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
				numNotPickUpTime = Integer.parseInt(notPickUpTime);

				String workingTimeStart = beginH + ":" + beginM;
				String workingTimeEnd = endH + ":" + endM;
				String operatingTimeStart = bookBeginH + ":" + bookBeginM;
				String operatingTimeEnd = bookEndH + ":" + bookEndM;

				shop.setInfo(name, address, mail, workingWeek, phone, numNotPickUpTime, workingTimeStart,
						workingTimeEnd, operatingTimeStart, operatingTimeEnd);

				if (shop.insertAccountDB()) {
					if (shop.insertShopInfoDB()) {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame1.fxml"));
						Parent root = loader.load();
						ShopFrame1Controller shopFrameController = loader.getController();

						shopFrameController.frame1Btn(event);

						stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						scene = new Scene(root);
						stage.setScene(scene);
					} else {
						System.out.println("inUserRegisterControllerClass, 無法順利存入「商家資訊」的表格中！");
					}
				} else {
					System.out.println("inUserRegisterControllerClass, 無法順利存入「帳號」的表格中！");
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
