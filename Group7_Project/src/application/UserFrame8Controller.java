package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import backEnd.OrderItem;
import backEnd.Shop;
import backEnd.ShopCart;
import backEnd.User;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class UserFrame8Controller {

	@FXML
	private Label orderMeal1Label;
	@FXML
	private Label orderMeal2Label;
	@FXML
	private Label orderMeal3Label;
	@FXML
	private Label orderMeal1QuanLabel;
	@FXML
	private Label orderMeal2QuanLabel;
	@FXML
	private Label orderMeal3QuanLabel;
	@FXML
	private Label orderMeal1PriceLabel;
	@FXML
	private Label orderMeal2PriceLabel;
	@FXML
	private Label orderMeal3PriceLabel;
	@FXML
	private Label totalQuanLabel;
	@FXML
	private Label totalPeiceLabel;
	@FXML
	private Label orderUserNameLabel;
	@FXML
	private Label orderShopNameLabel;
	@FXML
	private TextField hour;
	@FXML
	private TextField min;

	private ArrayList<ShopCart> shopCart;

	private String shopID = "";
	private int totalPrice = 0;
	private int totalQuan = 0;

	private Stage stage;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void initialize(String shopID, ArrayList<ShopCart> shopCart) {
		this.shopID = shopID;
		this.shopCart = shopCart;

		String numericString = "";

		for (int i = 0; i < shopCart.size(); i++) {
			totalPrice = totalPrice + shopCart.get(i).getPrice();
			try {
				numericString = shopCart.get(i).getQuan().replaceAll("[^0-9]", "");
				totalQuan = totalQuan + Integer.parseInt(numericString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		totalQuanLabel.setText(String.valueOf(totalQuan));
		totalPeiceLabel.setText("$ " + String.valueOf(totalPrice));

		for (int i = 0; i < 3; i++) {
			if (i < shopCart.size() && shopCart.get(i) != null) { // 檢查索引是否在範圍內並且元素不為空
				switch (i) {
				case 0:
					orderMeal1Label.setText(shopCart.get(i).getName());
					orderMeal1QuanLabel.setText(shopCart.get(i).getQuan());
					orderMeal1PriceLabel.setText(String.valueOf(shopCart.get(i).getPrice()));
					break;
				case 1:
					orderMeal2Label.setText(shopCart.get(i).getName());
					orderMeal2QuanLabel.setText(shopCart.get(i).getQuan());
					orderMeal2PriceLabel.setText(String.valueOf(shopCart.get(i).getPrice()));
					break;
				case 2:
					orderMeal3Label.setText(shopCart.get(i).getName());
					orderMeal3QuanLabel.setText(shopCart.get(i).getQuan());
					orderMeal3PriceLabel.setText(String.valueOf(shopCart.get(i).getPrice()));
					break;
				}
			} else {
				switch (i) {
				case 0:
					orderMeal1Label.setText("");
					orderMeal1QuanLabel.setText("");
					orderMeal1PriceLabel.setText("");
					break;
				case 1:
					orderMeal2Label.setText("");
					orderMeal2QuanLabel.setText("");
					orderMeal2PriceLabel.setText("");
					break;
				case 2:
					orderMeal3Label.setText("");
					orderMeal3QuanLabel.setText("");
					orderMeal3PriceLabel.setText("");
					break;
				}
			}
		}

		User user = new User(SharedData.ID);
		user.refreshInfo();

		Shop shop = new Shop(shopID);
		shop.refreshInfo();

		orderUserNameLabel.setText(user.getName());
		orderShopNameLabel.setText(shop.getShop() + " \n " + shop.getTelephone());
	}

	@FXML
	public void backPageBtn(ActionEvent event) throws IOException {
		stage.close();
	}

	// 送出訂單！把所有餐點名稱改成,分開
	@FXML
	public void checkBtn(ActionEvent event) throws IOException {

		String mealName = "";
		String quanAll = "";

		for (int i = 0; i < shopCart.size(); i++) {
			if (i == shopCart.size() - 1) {
				mealName = mealName + shopCart.get(i).getName();
				quanAll = quanAll + shopCart.get(i).getQuan();
			} else {
				mealName = mealName + shopCart.get(i).getName() + ",";
				quanAll = quanAll + shopCart.get(i).getQuan() + ",";
			}
		}

		String hours = hour.getText();
		String mins = min.getText();

		if (hours.length() < 2 || mins.length() < 2) {
			System.out.println("Attempting to show alert...");

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText(
					"所有時間欄位都需要填寫兩位數字。\n例如：下午兩點五分，請填寫：「14:05」\n注意：如果時間填錯，系統可能會自動判斷為未取餐訂單\n導致使用者被增加一次未取餐紀錄。");
			alert.showAndWait();
		} else {
			String note = "";
			String pickUpTime = hours + ":" + mins;
			OrderItem newOrder = new OrderItem(SharedData.ID, shopID, mealName, quanAll, totalPrice, note, pickUpTime);

			FXMLLoader loader = new FXMLLoader(getClass().getResource("UserFrame9.fxml"));
			Parent root = loader.load();

			UserFrame9Controller userFrameController = loader.getController();
			userFrameController.initialize(String.valueOf(totalQuan), String.valueOf(totalPrice),
					newOrder.getPickUpNum(), pickUpTime);
			userFrameController.setPrimaryStage((Stage) ((Node) event.getSource()).getScene().getWindow());

			Stage newStage = new Stage();
			newStage.setTitle("訂單編號");
			newStage.setScene(new Scene(root));

			userFrameController.setStage(newStage);

			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(((Node) event.getSource()).getScene().getWindow());

			newStage.showAndWait();
			this.stage.close();

		}

	}
}
