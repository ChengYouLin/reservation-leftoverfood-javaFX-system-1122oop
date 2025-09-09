package backEnd;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class OrderItem extends Order {

	private String userID, shopID, mealName, userNote, pickUpNum, shopName, quantity;
	private String state = "準備中";
	private int totalPrice;
	private String pickUpTime;
	private String[] infoName = { "UserID", "ShopID", "PickUpNumber", "MealName", "Quantity", "TotalPrice", "Note",
			"PickUpTime", "State" };

	// 刷新所有訂單資料使用
	public OrderItem(String userID, String shopID, String pickUpNum, String mealName, String quantity, int totalPrice,
			String userNote, String pickUpTime, String state) {
		this.userID = userID;
		this.shopID = shopID;
		this.pickUpNum = pickUpNum;
		this.mealName = mealName;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.userNote = userNote;
		this.pickUpTime = pickUpTime;
		this.state = state;

		String query = "SELECT Shop FROM `ShopInformation` WHERE ID = '" + shopID + "'";
		ArrayList<Object> results = super.connectDB(query, "Shop");
		if (results.isEmpty()) {
			this.shopName = mealName;
		} else {
			this.shopName = (String) results.get(0);
		}

	}

	// 使用者新增商家訂單使用
	public OrderItem(String userID, String shopID, String mealName, String quantity, int totalPrice, String note,
			String pickUpTime) {
		this.userID = userID;
		this.shopID = shopID;
		this.pickUpNum = generatePickUpNum(shopID);
		this.mealName = mealName;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.userNote = note;
		this.pickUpTime = pickUpTime;

		// 輸入進資料庫
		insertOrderToDB();
	}

	// 新增剩食訂單使用
	public OrderItem(String userID, String shopID, String mealName, String quantity, String pickUpTime) {
		this.userID = userID;
		this.shopID = shopID;
		this.pickUpNum = generatePickUpNum(shopID);
		this.mealName = mealName;
		this.quantity = quantity;
		this.pickUpTime = pickUpTime;

		// 輸入進資料庫
		insertOrderToDB(true);
	}

	// 使用者刪除訂單
	public boolean deleteOrder() {
		boolean result = false;
		String query = "DELETE FROM `Orders` WHERE PickUpNumber = '" + pickUpNum + "'";
		System.out.println(query);
		result = super.insertDeleteDB(query);
		return result;
	}

	// 設定某資料
	public boolean setSomeInfo(String infoName, Object newInfo) {
		boolean result = false;
		String goalName = "";

		for (String s : this.infoName) {
			if (s.equalsIgnoreCase(infoName)) {
				goalName = s;
				break;
			}
		}

		String query = "UPDATE `Orders` SET `" + goalName + "` = '" + newInfo + "' WHERE `PickUpNumber` = '" + pickUpNum
				+ "'";
		result = super.insertDeleteDB(query);
		return result;
	}

	public String getUserID() {
		return userID;
	}

	public String getShopID() {
		return shopID;
	}

	public String generatePickUpNum(String shopID) {
		String result;
		int totalCol = 0;

		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
		String formattedDate = formatter.format(now);

		System.out.println(formattedDate);

		String query = "SELECT * FROM `Orders`";
		ArrayList<Object> results = super.connectDB(query, "PickUpNumber");

		if (formattedDate.charAt(0) == '0') {
			String nweFormattedDate = formattedDate.substring(1, 4);

			for (int i = 0; i < results.size(); i++) {
				String result4 = (String.valueOf(results.get(i)).substring(0, 3));
				System.out.println(result4);

				if (result4.equals(nweFormattedDate)) {
					totalCol = totalCol + 1;
				}
			}

		} else {

			for (int i = 0; i < results.size(); i++) {
				String result4 = (String.valueOf(results.get(i)).substring(0, 4));
				System.out.println(result4);

				if (result4.equals(formattedDate)) {
					totalCol = totalCol + 1;
				}
			}
		}

		for (Object obj : results) {
			if (obj instanceof String && ((String) obj).startsWith(formattedDate)) {
				totalCol++;
			}
		}

		int randomNum = (int) (Math.random() * 100);
		result = String.format("%s%03d%02d", formattedDate, totalCol, randomNum);

		return result;
	}

	public String getPickUpNum() {
		return pickUpNum;
	}

	public String getMealName() {
		return mealName;
	}

	public String getQuantity() {
		return quantity;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public String getUserNote() {
		return userNote;
	}

	public String getPickUpTime() {
		return pickUpTime;
	}

	public String getState() {
		return state;
	}

	public String getShopName() {
		return shopName;
	}

	// 輸入訂單至資料庫(一般訂單)
	private void insertOrderToDB() {
		String query = "INSERT INTO `Orders` (UserID, ShopID, PickUpNumber, MealName, Quantity, TotalPrice, Note, PickUpTime, State) "
				+ "VALUES ('" + userID + "','" + shopID + "','" + pickUpNum + "','" + mealName + "','" + quantity + "',"
				+ totalPrice + ",'" + userNote + "','" + pickUpTime + "','" + state + "')";
		super.insertDeleteDB(query);
	}

	// 輸入訂單至資料庫(剩食訂單)
	private void insertOrderToDB(boolean isSurplus) {
		int price = isSurplus ? 0 : totalPrice;
		String query = "INSERT INTO `Orders` (UserID, ShopID, PickUpNumber, MealName, Quantity, TotalPrice, Note, PickUpTime, State) "
				+ "VALUES ('" + userID + "','" + shopID + "','" + pickUpNum + "','" + mealName + "','" + quantity + "',"
				+ price + ",' ','" + pickUpTime + "','" + state + "')";
		super.insertDeleteDB(query);
	}
}