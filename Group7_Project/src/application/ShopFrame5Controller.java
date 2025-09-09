package application;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import backEnd.Meal;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class ShopFrame5Controller {
	@FXML
	private Label mealNameLabel;
	@FXML
	private ChoiceBox<String> stateChoiceBox;

	private Stage stage;
	private Scene scene;
	private Meal meal;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void initialize(Meal meal) {
		stateChoiceBox.getItems().addAll("販售中", "停售中", "刪除");
		this.meal = meal;

		mealNameLabel.setText(meal.getMealName());
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
		} else if (result == "刪除") {
			meal.deleteMeal(SharedData.ID, meal.getMealName());

		} else {
			meal.setSomeInfo("State", result);
		}

		stage.close();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame1.fxml"));
		Parent root = loader.load();
		ShopFrame1Controller shopFrameController = loader.getController();

		shopFrameController.frame1Btn(event);

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

	}

	@FXML
	public void closeBtn(ActionEvent event) {
		stage.close();
	}
}
