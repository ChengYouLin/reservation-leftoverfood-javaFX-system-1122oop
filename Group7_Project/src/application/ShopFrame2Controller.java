package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;

import backEnd.Order;
import backEnd.OrderItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShopFrame2Controller {

	@FXML
	private TableView<OrderItem> tableView;
	@FXML
	private TableColumn<OrderItem, String> pickUpNumTable;
	@FXML
	private TableColumn<OrderItem, String> infoTable;
	@FXML
	private TableColumn<OrderItem, OrderItem> stateBtnTable;

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

		Order order = new Order();

		ArrayList<OrderItem> all = order.refreshInfo(SharedData.ID);

		all = order.checkState(all);

		pickUpNumTable.setCellValueFactory(new PropertyValueFactory<>("pickUpNum"));

		infoTable.setCellValueFactory(cellData -> {
			SimpleStringProperty property = new SimpleStringProperty();
			OrderItem item = cellData.getValue();

			String[] mealName = item.getMealName().split(",");
			String[] quantity = item.getQuantity().split(",");

			String result = "";
			for (int i = 0; i < mealName.length; i++) {
				result = result + mealName[i] + "   " + quantity[i] + "\n";
			}
			result = result + "\n總金額: $ " + String.valueOf(item.getTotalPrice());

			property.set(result);
			return property;
		});

		stateBtnTable.setCellFactory(param -> new TableCell<OrderItem, OrderItem>() {
			final Button btn = new Button("修改內容");

			@Override
			public void updateItem(OrderItem item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {
					btn.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							OrderItem item = getTableView().getItems().get(getIndex());

							FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame8.fxml"));
							Parent root;
							try {
								root = loader.load();
								ShopFrame8Controller shopFrameController = loader.getController();
								shopFrameController.initialize(item);

								Stage newStage = new Stage();
								newStage.setTitle("餐點內容修改");
								newStage.setScene(new Scene(root));

								shopFrameController.setStage(newStage);

								newStage.initModality(Modality.APPLICATION_MODAL);
								newStage.initOwner(((Node) event.getSource()).getScene().getWindow());

								newStage.showAndWait();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					setGraphic(btn);
					setText(null);
				}
			}
		});

		ObservableList<OrderItem> oData = FXCollections.observableArrayList(all);
		tableView.setItems(oData);

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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame4.fxml"));
		Parent root = loader.load();
		ShopFrame4Controller shopFrameController = loader.getController();
		shopFrameController.frame4Btn(event);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}
}
