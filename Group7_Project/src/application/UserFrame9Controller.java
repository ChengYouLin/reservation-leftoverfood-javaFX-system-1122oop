package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserFrame9Controller {

	@FXML
	private Label totalQuanLabel;
	@FXML
	private Label totalPriceLabel;
	@FXML
	private Label pickUpTimeLabel;
	@FXML
	private Label pickUpNumLabel;

	private Stage stage;
    private Stage primaryStage; 

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void initialize(String quan, String price, String pickUpNum, String pickUpTime) {

		totalQuanLabel.setText(quan);
		totalPriceLabel.setText("$ " + price);
		pickUpTimeLabel.setText(pickUpTime);
		pickUpNumLabel.setText(pickUpNum);

	}

	
	@FXML
	public void checkBtn(ActionEvent event) throws IOException {

		this.stage.close();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame2.fxml"));
		Parent root = loader.load();
		UserFrame2Controller userFrame2Controller = loader.getController();
		userFrame2Controller.frame2Btn(event);
        primaryStage.getScene().setRoot(root);


	}

	public void setPrimaryStage(Stage window) {
		primaryStage = window;
	}
}
