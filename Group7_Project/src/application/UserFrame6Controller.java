package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;

import backEnd.ShopCart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserFrame6Controller {

	@FXML
	private TableView<ShopCart> tableView;
	@FXML
	private Label shopNameLabel;
	@FXML
	private TableColumn<ShopCart, String> mealNameTable;
	@FXML
	private TableColumn<ShopCart, String> quanTable;
	@FXML
	private TableColumn<ShopCart, Integer> totalPriceTable;
	@FXML
	private TableColumn<ShopCart, ShopCart> orderModifyBtnTable;
	@FXML
	private Label totalQuanLabel;
	@FXML
	private Label totalPriceLabel;

	private ArrayList<ShopCart> shopCart = new ArrayList<>();

	private String shopID = "";
	private String shopName;

	private Stage stage;
	private Scene scene;

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

	@FXML
	public void backPageBtn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame5.fxml"));
		Parent root = loader.load();
		UserFrame5Controller userFrameController = loader.getController();
		userFrameController.backInitialize(shopID, shopCart);
		;
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}

	public void initialize(String shopID, ArrayList<ShopCart> shopCart, String shopName) {

		this.shopName = shopName;
		this.shopID = shopID;
		this.shopCart = shopCart;

		shopNameLabel.setText(shopName);

		mealNameTable.setCellValueFactory(new PropertyValueFactory<>("name"));
		quanTable.setCellValueFactory(new PropertyValueFactory<>("quan"));
		totalPriceTable.setCellValueFactory(new PropertyValueFactory<>("price"));

		addButtonToTable();

		// 處理標籤和總數量總價格
		int total = 0;
		int totalQuan = 0;
		String numericString = "";

		for (int i = 0; i < shopCart.size(); i++) {
			total = total + shopCart.get(i).getPrice();
			try {
				numericString = shopCart.get(i).getQuan().replaceAll("[^0-9]", "");
				totalQuan = totalQuan + Integer.parseInt(numericString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		totalQuanLabel.setText("共" + String.valueOf(totalQuan) + "個");
		totalPriceLabel.setText("$ " + String.valueOf(total));

		ObservableList<ShopCart> cData = FXCollections.observableArrayList(shopCart);
		tableView.setItems(cData);
	}

	private void addButtonToTable() {
		orderModifyBtnTable.setCellFactory(param -> new TableCell<ShopCart, ShopCart>() {
			final Button btn = new Button("刪除");

			@Override
			protected void updateItem(ShopCart item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {
					btn.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							ShopCart shopCartItem = getTableView().getItems().get(getIndex());
							getTableView().getItems().remove(shopCartItem);
							shopCart.remove(shopCartItem);

							initialize(shopID, shopCart, shopName);
						}
					});
					setGraphic(btn);
					setText(null);
				}
			}
		});
	}

	@FXML
	public void contiBtn(ActionEvent event) throws IOException {
		backPageBtn(event);
	}

	@FXML
	public void checkBtn(ActionEvent event) throws IOException {
		try {

			if (shopCart.isEmpty()) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("警告");
				alert.setHeaderText(null);
				alert.setContentText("你的購物車目前是空的！請選擇繼續訂餐！");

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
