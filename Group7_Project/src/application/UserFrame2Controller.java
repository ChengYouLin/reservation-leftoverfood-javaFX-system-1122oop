package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import backEnd.Order;
import backEnd.OrderItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;

public class UserFrame2Controller {

	private Stage stage;
	private Scene scene;

	@FXML
	private TableView<OrderItem> orderTable;
	@FXML
	private TableColumn<OrderItem, String> shopNameTable;
	@FXML
	private TableColumn<OrderItem, Integer> totalTable;
	@FXML
	private TableColumn<OrderItem, String> pickUpTimeTable;
	@FXML
	private TableColumn<OrderItem, String> pickUpNumTable;
	@FXML
	private TableColumn<OrderItem, String> stateTable;

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
	public void frame2Btn(ActionEvent event) {

		Order order = new Order();

		shopNameTable.setCellValueFactory(new PropertyValueFactory<>("shopName"));
		totalTable.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
		pickUpTimeTable.setCellValueFactory(new PropertyValueFactory<>("pickUpTime"));
		pickUpNumTable.setCellValueFactory(new PropertyValueFactory<>("pickUpNum"));
		stateTable.setCellValueFactory(new PropertyValueFactory<>("state"));

		ObservableList<OrderItem> oData = FXCollections.observableArrayList(order.userRefreshInfo(SharedData.ID));
		orderTable.setItems(oData);

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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame4.fxml"));
		Parent root = loader.load();
		UserFrame4Controller userFrameController = loader.getController();

		userFrameController.frame4Btn(event);

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}
}
