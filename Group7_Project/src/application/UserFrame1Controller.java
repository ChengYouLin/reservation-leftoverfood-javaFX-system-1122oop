package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import backEnd.LeftoverFood;
import backEnd.Shop;
import backEnd.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class UserFrame1Controller {

	private Stage stage;
	private Scene scene;

	@FXML
	private TableView<LeftoverFood> leftoverFoodTable;
	@FXML
	private TableColumn<LeftoverFood, String> leftFoodNameTable;
	@FXML
	private TableColumn<LeftoverFood, Integer> leftNameQuanTable;
	@FXML
	private TableColumn<LeftoverFood, String> leftFoodAddresssTable;
	@FXML
	private TableColumn<LeftoverFood, LeftoverFood> leftFoodBtnTable;

	@FXML
	private TableView<Shop> shopTable;
	@FXML
	private TableColumn<Shop, String> shopNameTable;
	@FXML
	private TableColumn<Shop, String> timeTable;
	@FXML
	private TableColumn<Shop, String> shopAddressTable;
	@FXML
	private TableColumn<Shop, Shop> bookBtnTable;

	@FXML
	public void frame1Btn(ActionEvent event) {
		User user = new User(SharedData.ID);
		user.refreshInfo();

		ArrayList<Shop> all = user.getAllShop();
		Iterator<Shop> iterator = all.iterator();

		while (iterator.hasNext()) {
			Shop s = iterator.next();
			System.out.println("into: " + s.getShop());
			System.out.println("shop: " + s.getNotPickUpTime());
			System.out.println("user: " + user.getNotPickUPTime());

			if (s.getNotPickUpTime() <= user.getNotPickUPTime()) {
				System.out.println("Delete: " + s.getShop());
				iterator.remove();
			}
		}

		shopNameTable.setCellValueFactory(new PropertyValueFactory<>("shop"));

		timeTable.setCellValueFactory(cellData -> {
			SimpleStringProperty property = new SimpleStringProperty();
			Shop shop = cellData.getValue();

			String workingTime = shop.getWorkingTimeStart() + " ~ " + shop.getWorkingTimeEnd();

			property.set(workingTime);
			return property;
		});

		shopAddressTable.setCellValueFactory(cellData -> {
			SimpleStringProperty property = new SimpleStringProperty();
			String original = cellData.getValue().getAddress();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < original.length(); i++) {
				if (i > 0 && i % 8 == 0) {
					builder.append('\n');
				}
				builder.append(original.charAt(i));
			}
			property.set(builder.toString());
			return property;
		});

		bookBtnTable.setCellFactory(param -> new TableCell<Shop, Shop>() {
			final Button btn = new Button("立即訂餐");

			@Override
			public void updateItem(Shop item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {
					btn.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							Shop shop = getTableView().getItems().get(getIndex());
							System.out.println("Booking at " + shop.getShop());

							FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame5.fxml"));
							Parent root;
							try {
								root = loader.load();
								UserFrame5Controller userFrameController = loader.getController();

								userFrameController.initialize(shop.getID());

								stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
								scene = new Scene(root);
								stage.setScene(scene);
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

		ObservableList<Shop> sData = FXCollections.observableArrayList(all);
		shopTable.setItems(sData);

		leftFoodNameTable.setCellValueFactory(new PropertyValueFactory<>("mealName"));
		leftNameQuanTable.setCellValueFactory(new PropertyValueFactory<>("appointmentQuantity"));
		leftFoodAddresssTable.setCellValueFactory(new PropertyValueFactory<>("address"));

		leftFoodBtnTable.setCellFactory(param -> new TableCell<LeftoverFood, LeftoverFood>() {
			final Button btn = new Button("立即預約");

			@Override
			protected void updateItem(LeftoverFood item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {
					btn.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							LeftoverFood leftoverFood = getTableView().getItems().get(getIndex());
							System.out.println("Booking " + leftoverFood.getMealName());

							try {
								FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame10.fxml"));
								Parent root = loader.load();

								UserFrame10Controller userFrameController = loader.getController();
								userFrameController.initialize(leftoverFood);

								Stage newStage = new Stage();
								newStage.setTitle("確認訂單");
								newStage.setScene(new Scene(root));

								userFrameController.setStage(newStage);

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

		ObservableList<LeftoverFood> lData = FXCollections.observableArrayList(user.getAllLFood());
		leftoverFoodTable.setItems(lData);
	}

	@FXML
	public void frame2Btn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame2.fxml"));
		Parent root = loader.load();
		UserFrame2Controller userFrame2Controller = loader.getController();

		userFrame2Controller.frame2Btn(event);

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
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