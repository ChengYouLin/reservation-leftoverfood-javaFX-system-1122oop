package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.util.regex.Matcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import backEnd.LeftoverFood;
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
import javafx.scene.control.Label;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LeftoverFoodFrame2Controller {

	@FXML
	private TableView<OrderItem> tableView;
	@FXML
	private Label remainQuanLabel;
	@FXML
	private Spinner<Integer> switchQuan;
	@FXML
	private TableColumn<OrderItem, String> pickUpNum;
	@FXML
	private TableColumn<OrderItem, String> quanTable;
	@FXML
	private TableColumn<OrderItem, OrderItem> stateBtnTable;
	@FXML
	private Button checkButton;

	private Stage stage;
	private Scene scene;

	@FXML
	public void initialize() {
		initializeSpinner();
	}

	private void initializeSpinner() {
		// 初始化 Spinner，設置初始值為 0
		SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
				0, Integer.MAX_VALUE, 0);
		switchQuan.setValueFactory(valueFactory);

		switchQuan.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue < 0) {
				switchQuan.getValueFactory().setValue(0);
			}
		});
	}

	@FXML
	public void frame1Btn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LeftoverFoodFrame1.fxml"));
		Parent root = loader.load();
		LeftoverFoodFrame1Controller LeftoverFoodFrameController = loader.getController();

		LeftoverFoodFrameController.frame1Btn(event);

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
	}

	public void initializeLabel() {

		LeftoverFood lFood = new LeftoverFood(SharedData.ID);
		lFood.refreshInfo();

		int total = lFood.getAppointmentQuantity();

		Order order = new Order();
		ArrayList<OrderItem> orders = order.refreshInfo(SharedData.ID);

		for (OrderItem i : orders) {
			String number = extractLeadingNumber(i.getQuantity());
			total = total - Integer.parseInt(number);
		}

		remainQuanLabel.setText(String.valueOf(total));

	}

	public static String extractLeadingNumber(String str) {
		String regex = "^\\d+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);

		if (matcher.find()) {
			return matcher.group();
		} else {
			return "";
		}
	}

	@FXML
	public void frame2Btn(ActionEvent event) throws IOException {

		initializeSpinner();
		initializeLabel();

		Order order = new Order();

		ArrayList<OrderItem> orders = order.refreshInfo(SharedData.ID);

		Iterator<OrderItem> iterator = orders.iterator();
		while (iterator.hasNext()) {
			OrderItem i = iterator.next();
			if (!i.getState().equals("準備中") && !i.getState().equals("待取餐")) {
				System.out.println("Delete: " + i.getPickUpNum() + ", " + i.getState());
				iterator.remove();
			}
		}

		pickUpNum.setCellValueFactory(new PropertyValueFactory<>("pickUpNum"));

		quanTable.setCellValueFactory(cellData -> {
			SimpleStringProperty property = new SimpleStringProperty();
			OrderItem item = cellData.getValue();

			String result = "總數量: " + item.getQuantity();

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

							FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopFrame9.fxml"));
							Parent root;
							try {
								root = loader.load();
								ShopFrame9Controller shopFrameController = loader.getController();
								shopFrameController.initialize(item);

								Stage newStage = new Stage();
								newStage.setTitle("取餐！");
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

		ObservableList<OrderItem> oData = FXCollections.observableArrayList(orders);
		tableView.setItems(oData);
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
	public void checkBtn(ActionEvent event) {
		int value = switchQuan.getValue();

		LeftoverFood lFood = new LeftoverFood(SharedData.ID);
		lFood.refreshInfo();

		String quan = String.valueOf(value) + "個";
		OrderItem lFoodOrder = new OrderItem(SharedData.ID, SharedData.ID, lFood.getMealName(), quan, "XX:XX");
		lFoodOrder.setSomeInfo("State", "已完成");

		if (value == 0) {
			System.err.println("LFoodFrame2 error: Value cannot be zero.");
		} else {
			System.out.println("Current value: " + value);
		}
		initializeSpinner();
		initializeLabel();
	}
}