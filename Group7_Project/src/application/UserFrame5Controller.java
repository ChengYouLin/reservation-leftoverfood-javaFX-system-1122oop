package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import backEnd.Meal;
import backEnd.Menu;
import backEnd.Shop;
import backEnd.ShopCart;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class UserFrame5Controller {

	@FXML
	private Label shopNameLabel;

	@FXML
	private TableView<Meal> tableView;

	@FXML
	private TableColumn<Meal, String> mealNameTable;
	@FXML
	private TableColumn<Meal, String> unitAndUnitPriceTable;
	@FXML
	private TableColumn<Meal, Void> quanTable;
	@FXML
	private TableColumn<Meal, Integer> totalPriceTable;
	@FXML
	private TableColumn<Meal, Void> orderCheckBtnTable;

	private Stage stage;
	private Scene scene;
	private ObservableList<Meal> data;
	private String shopID;

	private String shopName;

	private ArrayList<ShopCart> shopCart = new ArrayList<>();

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

	// 從frame6回來
	public void backInitialize(String shopID, ArrayList<ShopCart> cart) {

		this.shopCart = cart;

		initialize(shopID);
	}

	public void initialize(String shopID) {
		this.shopID = shopID;

		Shop shop = new Shop(shopID);
		shop.refreshInfo();
		shopName = shop.getShop();

		shopNameLabel.setText(shop.getShop());

		Menu menu = new Menu();
		ArrayList<Meal> mealList = menu.refreshInfo(shopID);

		Iterator<Meal> iterator = mealList.iterator();
		while (iterator.hasNext()) {
			Meal meal = iterator.next();
			System.out.println(meal.getMealName());

			if (!meal.getState().equals("販售中")) {
				iterator.remove();
			}
		}

		data = FXCollections.observableArrayList(mealList);

		// 初始化數據
		mealNameTable.setCellValueFactory(new PropertyValueFactory<>("mealName"));
		unitAndUnitPriceTable.setCellValueFactory(cellData -> {
			SimpleStringProperty property = new SimpleStringProperty();
			Meal meal = cellData.getValue();
			String result = meal.getUnitPrice() + " / " + meal.getUnit();
			property.set(result);
			return property;
		});

		quanTable.setCellFactory(col -> new TableCell<Meal, Void>() {
			private final Label quantityLabel = new Label();
			private final Button incrementButton = new Button("+");
			private final Button decrementButton = new Button("-");
			private final HBox hbox = new HBox(5, decrementButton, quantityLabel, incrementButton);

			{
				incrementButton.setOnAction(event -> {
					int quantity = Integer.parseInt(quantityLabel.getText());
					quantity++;
					quantityLabel.setText(String.valueOf(quantity));
					updateMealQuantity(getIndex(), quantity);
				});

				decrementButton.setOnAction(event -> {
					int quantity = Integer.parseInt(quantityLabel.getText());
					if (quantity > 0) {
						quantity--;
						quantityLabel.setText(String.valueOf(quantity));
						updateMealQuantity(getIndex(), quantity);
					}
				});

				hbox.setAlignment(Pos.CENTER);
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					Meal meal = getTableView().getItems().get(getIndex());
					quantityLabel.setText(String.valueOf(meal.getQuantity()));
					setGraphic(hbox);
				}
			}

			private void updateMealQuantity(int index, int quantity) {
				Meal meal = getTableView().getItems().get(index);
				meal.setQuantity(quantity);
				meal.setTotalPrice(meal.getUnitPrice() * quantity);
				tableView.refresh();
			}
		});

		totalPriceTable.setCellValueFactory(cellData -> {
			Meal meal = cellData.getValue();
			return new SimpleIntegerProperty(meal.getTotalPrice()).asObject();
		});

		totalPriceTable.setCellFactory(col -> new TableCell<Meal, Integer>() {
			@Override
			protected void updateItem(Integer item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(new Label(item.toString()));
				}
			}
		});

		orderCheckBtnTable.setCellFactory(col -> new TableCell<Meal, Void>() {
			private final Button btn = new Button("Order");

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(btn);
					btn.setOnAction(event -> {

						// 從console 顯示預定的餐點
						Meal meal = getTableView().getItems().get(getIndex());
						int quantity = meal.getQuantity();
						if (quantity == 0) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("提醒");
							alert.setHeaderText(null);
							alert.setContentText("品項數量為「0」，不可加入到購物車中唷！");

							alert.showAndWait();
						} else {

							int totalPrice = meal.getUnitPrice() * quantity;
							System.out.println("Ordered " + meal.getMealName() + " x " + quantity + " for total price: "
									+ totalPrice);

							ShopCart orderItem = new ShopCart(meal.getMealName(), quantity + meal.getUnit(),
									totalPrice);
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("確認");
							alert.setHeaderText(null);
							alert.setContentText("成功加入購物車！");

							alert.showAndWait();
							shopCart.add(orderItem);
						}

					});
				}
			}
		});

		tableView.setItems(data);
	}

	@FXML
	public void backPageBtn(ActionEvent event) throws IOException {
		frame1Btn(event);
	}

	@FXML
	public void shopcartBtn(ActionEvent event) throws IOException {
		checkBtn(event);
	}

	@FXML
	public void checkBtn(ActionEvent event) throws IOException {

		if (shopCart.isEmpty()) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("你的購物車目前是空的！");

			alert.showAndWait();

		} else {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame6.fxml"));
			Parent root = loader.load();
			UserFrame6Controller userFrameController = loader.getController();
			userFrameController.initialize(shopID, shopCart, shopName);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
		}
	}

	@FXML
	public void straightCheckBtn(ActionEvent event) {
		try {

			if (shopCart.isEmpty()) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("警告");
				alert.setHeaderText(null);
				alert.setContentText("你的購物車目前是空的！");

				alert.showAndWait();

			} else {

				FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame8.fxml"));
				Parent root = loader.load();

				UserFrame8Controller userFrameController = loader.getController();
				userFrameController.initialize(shopID, shopCart);

				Stage newStage = new Stage();
				newStage.setTitle("確認訂單");
				newStage.setScene(new Scene(root));
				userFrameController.setStage(newStage);

				newStage.initModality(Modality.APPLICATION_MODAL);
				newStage.initOwner(((Node) event.getSource()).getScene().getWindow());

				newStage.showAndWait();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
