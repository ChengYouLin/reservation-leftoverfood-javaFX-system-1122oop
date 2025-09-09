package backEnd;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LeftoverFood extends ConnectDB {

	private String ID, mealName, address, deleteTime;
	private int appointmentQuantity;
	private String setUpTime;
	private String[] infoName = { "ID", "MealName", "AppointmentQuantity", "Address", "SetUpTime" };

	public LeftoverFood() {
	}

	public LeftoverFood(String ID) {
		this.ID = ID;
	}

	public boolean checkIDExist() {
		String query = "SELECT ID FROM `LeftoverFood` WHERE ID = '" + ID + "'";
		ArrayList<Object> allID = super.connectDB(query, "ID");
		for (Object a : allID) {
			if (a.equals(ID)) {
				return true;
			}
		}
		return false;
	}

	public boolean initializeInfo(String mealName, int appointmentQuantity, String address) {
		this.mealName = mealName;
		this.appointmentQuantity = appointmentQuantity;
		this.address = address;

		// 額外處理時間
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.setUpTime = formatter.format(now);

		String query = "INSERT INTO `LeftoverFood` (`ID`, `MealName`, `AppointmentQuantity`, `Address`, `SetUpTime`) "
				+ "VALUES ('" + this.ID + "','" + this.mealName + "'," + this.appointmentQuantity + ",'" + this.address
				+ "','" + this.setUpTime + "')";

		Meal firstMeal = new Meal(ID, mealName, appointmentQuantity);

		return super.insertDeleteDB(query);

	}

	public boolean deleteID() {
		String query = "DELETE FROM `LeftoverFood` WHERE `ID` = '" + ID + "'";
		boolean result = super.insertDeleteDB(query);

		String query2 = "DELETE FROM `Menu` WHERE `ID` = '" + ID + "'";
		result &= super.insertDeleteDB(query2);

		Order order = new Order();
		ArrayList<OrderItem> o = order.refreshInfo(ID);

		for (OrderItem i : o) {
			i.deleteOrder();
		}

		return result;
	}

	public boolean setSomeInfo(String infoName, Object newInfo) {
		for (String s : this.infoName) {
			if (s.equalsIgnoreCase(infoName)) {
				String query = "UPDATE `LeftoverFood` SET `" + s + "` = '" + newInfo + "' WHERE `ID` = '" + ID + "'";
				return super.insertDeleteDB(query);
			}
		}
		return false;
	}

	public void refreshInfo() {
		ArrayList<Object> allInfo = new ArrayList<>();

		for (String s : infoName) {
			String query = "SELECT `" + s + "` FROM `LeftoverFood` WHERE `ID` = '" + ID + "'";
			ArrayList<Object> all = super.connectDB(query, s);
			if (!all.isEmpty()) {
				allInfo.add(all.get(0));
			} else {
				allInfo.add(null);
			}
		}

		if (!allInfo.isEmpty() && allInfo.size() == infoName.length) {
			this.ID = (String) allInfo.get(0);
			this.mealName = (String) allInfo.get(1);
			this.appointmentQuantity = (int) allInfo.get(2);
			this.address = (String) allInfo.get(3);
			this.setUpTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp) allInfo.get(4));

			Object timestamp = allInfo.get(4);
			LocalDateTime localDateTime = ((Timestamp) timestamp).toLocalDateTime();
			LocalDateTime newLocalDateTime = localDateTime.plusHours(2);
			this.deleteTime = newLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		} else {
			throw new RuntimeException("Failed to refresh information from the database.");
		}
	}

	public boolean checkSetUpTime() {
		boolean result = false;
		Date current = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String userInput = getSetUpTime();
			Date userTime = format.parse(userInput);
			long differenceInHours = (current.getTime() - userTime.getTime()) / (1000 * 60 * 60);
			if (differenceInHours > 2) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void checkAllDBTime() {
		String query = "SELECT ID FROM `LeftoverFood`";
		ArrayList<Object> allID = super.connectDB(query, "ID");
		for (int i = 0; i < allID.size(); i++) {
			LeftoverFood leftoverFood = new LeftoverFood(allID.get(i).toString());
			leftoverFood.refreshInfo();
			System.err.println(allID.get(i).toString());
			if (leftoverFood.checkSetUpTime()) {
				leftoverFood.deleteID();
			}
		}
	}

	public String getID() {
		return ID;
	}

	public String getMealName() {
		return mealName;
	}

	public String getAddress() {
		return address;
	}

	public int getAppointmentQuantity() {
		return appointmentQuantity;
	}

	public String getSetUpTime() {
		return setUpTime;
	}

	public String getDeleteTime() {
		return deleteTime;
	}
}