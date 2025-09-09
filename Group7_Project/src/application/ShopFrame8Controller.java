package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import backEnd.OrderItem;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

public class ShopFrame8Controller {

	@FXML
	private Label pickUpNumLabel;
	@FXML
	private ChoiceBox<String> stateChoiceBox;
	@FXML
	private TextArea noteField;

	private OrderItem item;
	private Stage stage;
	private Scene scene;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void initialize(OrderItem item) {
		this.item = item;
		pickUpNumLabel.setText(item.getPickUpNum());
		stateChoiceBox.getItems().addAll("準備中", "待取餐", "已完成", "取消");
	}

	@FXML
	public void checkBtn(ActionEvent event) throws IOException {

		String result = stateChoiceBox.getValue();

		if (result == "" || result == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("請選擇你要更新的狀態。");
			alert.showAndWait();
		} else {
			item.setSomeInfo("State", result);
		}

		stage.close();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame2.fxml"));
		Parent root = loader.load();
		ShopFrame2Controller shopFrameController = loader.getController();

		shopFrameController.frame2Btn(event);

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

	}

	@FXML
	public void closeBtn(ActionEvent event) {
		stage.close();
	}
}
