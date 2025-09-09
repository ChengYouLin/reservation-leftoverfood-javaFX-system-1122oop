package backEnd;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Order extends ConnectDB {

	public Order() {
	}

	public ArrayList<OrderItem> refreshInfo(String shopID) {
		ArrayList<OrderItem> allOrder = new ArrayList<>();

		try {
			String query = "SELECT * FROM Orders WHERE ShopID = '" + shopID + "'";
			System.out.println("Executing query: " + query);
			ArrayList<ArrayList<Object>> result = super.fetchAll(query);

			for (ArrayList<Object> row : result) {
				String userID = (String) row.get(0);
				String shopID2 = (String) row.get(1);
				String pickUpNum = String.valueOf(row.get(2));
				String mealName = (String) row.get(3);
				String quantity = (String) row.get(4);
				int totalPrice = (int) row.get(5);
				String userNote = (String) row.get(6);
				String pickUpTime = (String) row.get(7);
				String state = (String) row.get(8);

				OrderItem order = new OrderItem(userID, shopID2, pickUpNum, mealName, quantity, totalPrice, userNote,
						pickUpTime, state);
				allOrder.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return allOrder;
	}



	public ArrayList<OrderItem> userRefreshInfo(String user) {
		ArrayList<OrderItem> allOrder = new ArrayList<>();

		try {
			String query = "SELECT * FROM Orders WHERE UserID = '" + user + "'";
			System.out.println("Executing query: " + query); // 输出查询语句
			ArrayList<ArrayList<Object>> result = super.fetchAll(query);

			for (ArrayList<Object> row : result) {
				String userID = (String) row.get(0);
				String shopID2 = (String) row.get(1);
				String pickUpNum = String.valueOf(row.get(2));
				String mealName = (String) row.get(3);
				String quantity = (String) row.get(4);
				int totalPrice = (int) row.get(5);
				String userNote = (String) row.get(6);
				String pickUpTime = (String) row.get(7);
				String state = (String) row.get(8);

				OrderItem order = new OrderItem(userID, shopID2, pickUpNum, mealName, quantity, totalPrice, userNote,
						pickUpTime, state);
				allOrder.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return allOrder;
	}

	public ArrayList<OrderItem> checkState(ArrayList<OrderItem> all) {
		System.out.println("inOrderCheckState");

		ArrayList<OrderItem> alls = all;

		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
		String formattedDate = formatter.format(now);

		if (formattedDate.charAt(0) == '0') {
			String nweFormattedDate = formattedDate.substring(1, 4);

			for (int i = all.size() - 1; i >= 0; i--) {
				String result4 = (String.valueOf(all.get(i).getPickUpNum()).substring(0, 3));
				System.out.println(result4);

				if (result4.equals(nweFormattedDate) == false) {
					System.out.println("Delete: " + all.get(i).getPickUpNum());

					User user = new User(all.get(i).getUserID());
					user.refreshInfo();

					int originate = user.getNotPickUPTime();
					user.setSomeInfo("NotPickUpTime", originate + 1);
					all.get(i).deleteOrder();
					all.remove(i);
				}
			}

		} else {

			for (int i = all.size() - 1; i >= 0; i--) {
				String result4 = (String.valueOf(all.get(i).getPickUpNum()).substring(0, 4));
				System.out.println(result4);

				if (result4.equals(formattedDate) == false) {
					System.out.println("Delete: " + all.get(i).getPickUpNum());

					User user = new User(all.get(i).getUserID());
					user.refreshInfo();

					int originate = user.getNotPickUPTime();
					user.setSomeInfo("NotPickUpTime", originate + 1);
					all.get(i).deleteOrder();
					all.remove(i);
				}
			}
		}

		alls = all;
		return alls;
	}

	// 一般
	public boolean insertOrderDB(String userID, String shopID, String pickUpNum, String mealName, String quantity,
			int totalPrice, String note, String pickUpTime) {
		boolean result = false;

		String state = "準備中";
		String query = "INSERT INTO `Orders` (UserID, ShopID, PickUpNumber, MealName, Quantity, TotalPrice, Note, PickUpTime, State) "
				+ "VALUES ('" + userID + "','" + shopID + "','" + pickUpNum + "','" + mealName + "'," + quantity + ","
				+ totalPrice + ",'" + note + "','" + pickUpTime + "','" + state + "')";
		result = super.insertDeleteDB(query);
		return result;
	}

	// 剩食
	public boolean insertOrderDB(String userID, String shopID, String pickUpNum, String mealName, String quantity,
			String pickUpTime) {
		boolean result = false;

		Timestamp timestamp = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parsedDate = dateFormat.parse(pickUpTime);
			timestamp = new Timestamp(parsedDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String state = "準備中";
		String query = "INSERT INTO `Orders` (UserID, ShopID, PickUpNumber, MealName, Quantity, TotalPrice, Note, PickUpTime, State) "
				+ "VALUES ('" + userID + "','" + shopID + "','" + pickUpNum + "','" + mealName + "'," + quantity
				+ ", 0, '','" + timestamp + "','" + state + "')";
		result = super.insertDeleteDB(query);
		return result;
	}

	public String generatePickUpNum(String shopID) throws SQLException {
		String result;
		int totalCol = 0;

		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
		String formattedDate = formatter.format(today);

		String query = "SELECT * FROM `ShopInformation` WHERE ShopID = '" + shopID + "'";
		ArrayList<Object> results = super.connectDB(query, "PickUpNumber");

		for (Object obj : results) {
			if (((String) obj).startsWith(formattedDate)) {
				totalCol++;
			}
		}

		int randomNum = (int) (Math.random() * 100);
		result = String.format("%s%03d%02d", formattedDate, totalCol, randomNum);

		return result;
	}
}