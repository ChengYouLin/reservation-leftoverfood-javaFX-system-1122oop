package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import backEnd.Meal;
import backEnd.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LeftoverFoodFrame1Controller {

	@FXML
	private TableView<Meal> tableView;
	@FXML
	private TableColumn<Meal, String> mealNameTable;
	@FXML
	private TableColumn<Meal, Integer> quanTable;
	@FXML
	private TableColumn<Meal, Meal> modifyBtnTable;
	@FXML
	private TableColumn<Meal, Meal> stateBtnTable;

	private Stage stage;
	private Scene scene;

	public void frame1Btn(ActionEvent event) throws IOException {
		Menu menu = new Menu();

		mealNameTable.setCellValueFactory(new PropertyValueFactory<>("mealName"));
		quanTable.setCellValueFactory(new PropertyValueFactory<>("appointmentQuantity"));

		modifyBtnTable.setCellFactory(param -> new TableCell<Meal, Meal>() {
			final Button btn = new Button("修改內容");

			@Override
			public void updateItem(Meal item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {
					btn.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							Meal meal = getTableView().getItems().get(getIndex());

							FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame6.fxml"));
							Parent root;
							try {
								root = loader.load();
								ShopFrame6Controller shopFrameController = loader.getController();
								shopFrameController.initialize(meal);

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

		stateBtnTable.setCellFactory(param -> new TableCell<Meal, Meal>() {
			final Button btn = new Button("狀態更改");

			@Override
			public void updateItem(Meal item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {
					btn.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							Meal meal = getTableView().getItems().get(getIndex());

							FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame5.fxml"));
							Parent root;
							try {
								root = loader.load();
								ShopFrame5Controller shopFrameController = loader.getController();
								shopFrameController.initialize(meal);

								Stage newStage = new Stage();
								newStage.setTitle("狀態更改");
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

		ObservableList<Meal> mData = FXCollections.observableArrayList(menu.refreshInfo(SharedData.ID));
		tableView.setItems(mData);

	}

	@FXML
	public void frame2Btn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LeftoverFoodFrame2.fxml"));
		Parent root = loader.load();
		LeftoverFoodFrame2Controller LeftoverFoodFrameController = loader.getController();

		LeftoverFoodFrameController.frame2Btn(event);

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}

	@FXML
	public void frame3Btn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LeftoverFoodFrame3.fxml"));
		Parent root = loader.load();
		LeftoverFoodFrame3Controller LeftoverFoodFrameController = loader.getController();

		LeftoverFoodFrameController.frame3Btn(event);

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}

	@FXML
	public void createMealBtn(ActionEvent event) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("提示");
		alert.setHeaderText(null);
		alert.setContentText("目前剩食無法新增品項！");
		alert.showAndWait();
	}
}
