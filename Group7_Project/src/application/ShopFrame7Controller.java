package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import backEnd.Meal;
import backEnd.Menu;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

public class ShopFrame7Controller {

	@FXML
	private TextField mealNameField;
	@FXML
	private TextField appointmentQuanField;
	@FXML
	private TextField unitField;
	@FXML
	private TextField unitPriceField;
	@FXML
	private TextField noteField;
	@FXML
	private ChoiceBox<String> stateChoice;

	private Stage stage;
	private Scene scene;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void initialize() {
		stateChoice.getItems().clear();
		stateChoice.getItems().addAll("販售中", "停售中", "刪除");
	}

	@FXML
	public void checkBtn(ActionEvent event) throws IOException {

		String name = mealNameField.getText();
		String quan = appointmentQuanField.getText();
		String unit = unitField.getText();
		String unitPrice = unitPriceField.getText();
		String note = noteField.getText();
		String state = stateChoice.getValue();

		// 確認餐點名稱有沒有重複
		Menu menu = new Menu();
		boolean result = false;
		ArrayList<Meal> all = menu.refreshInfo(SharedData.ID);
		for (Meal s : all) {
			if (s.getMealName().equals(name)) {
				result = true;
				break;
			}
		}

		if (result) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("餐點名稱不能重複。");
			alert.showAndWait();
		} else if (unit == null || unit == " ") {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("請輸入清楚量詞");
			alert.showAndWait();
		} else if (state == " " || state == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("請選擇你要更新的狀態。");
			alert.showAndWait();

		} else {
			try {
				int numQuan = Integer.parseInt(quan);
				int numUnitPrice = Integer.parseInt(unitPrice);

				Meal meal = new Meal(SharedData.ID, name, numQuan, unit, numUnitPrice, note);

				meal.setSomeInfo("State", state);

				FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame1.fxml"));
				Parent root = loader.load();
				ShopFrame1Controller shopFrameController = loader.getController();

				shopFrameController.frame1Btn(event);
				stage.close();

				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);

			} catch (NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("錯誤訊息");
				alert.setHeaderText(null);
				alert.setContentText("請確定預約數量與價格皆為數字");

				alert.showAndWait();
			}
		}

	}

	@FXML
	public void closeBtn(ActionEvent event) {
		stage.close();
	}
}
