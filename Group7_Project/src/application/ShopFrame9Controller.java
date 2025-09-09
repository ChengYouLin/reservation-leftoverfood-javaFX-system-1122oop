package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import backEnd.OrderItem;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ShopFrame9Controller {
	@FXML
	private Label pickUpNumLabel;

	private OrderItem item;
	private Stage stage;
	private Scene scene;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void initialize(OrderItem item) {
		this.item = item;
		pickUpNumLabel.setText(item.getPickUpNum());
	}

	@FXML
	public void checkBtn(ActionEvent event) throws IOException {
		item.setSomeInfo("State", "已完成");

		stage.close();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame3.fxml"));
		Parent root = loader.load();
		ShopFrame3Controller shopFrameController = loader.getController();

		shopFrameController.frame3Btn(event);

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

	}

	@FXML
	public void cancelBtn(ActionEvent event) {
		closeBtn(event);
	}

	@FXML
	public void closeBtn(ActionEvent event) {
		stage.close();
	}
}
