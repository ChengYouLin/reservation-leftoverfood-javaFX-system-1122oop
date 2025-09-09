package backEnd;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.sql.Time;

public class Shop extends ConnectDB {

	private String ID, shop, address, email, telephone, workingWeek;
	private int notPickUpTime;
	private String workingTimeStart, workingTimeEnd;
	private String operatingTimeStart, operatingTimeEnd;
	private String[] infoName = { "Shop", "Address", "Telephone", "Email", "WorkingWeek", "WorkingTimeStart",
			"WorkingTimeEnd", "OperatingTimeStart", "OperatingTimeEnd", "NotPickUpTime" };

	public Shop(String ID) {
		this.ID = ID;
	}

	public void refreshInfo() {
		ArrayList<Object> allInfo = new ArrayList<>();

		for (int i = 0; i < infoName.length; i++) {
			String query = "SELECT " + infoName[i] + " FROM ShopInformation WHERE ID = '" + ID + "'";
			ArrayList<Object> result = super.connectDB(query, infoName[i]);
			if (result != null && !result.isEmpty()) {
				allInfo.add(result.get(0));
			} else {
				throw new RuntimeException("No data found for: " + infoName[i]);
			}
			if (i == infoName.length - 1) {
				break;
			}
		}
		this.shop = (String) allInfo.get(0);
		this.address = (String) allInfo.get(1);
		this.telephone = (String) allInfo.get(2);
		this.email = (String) allInfo.get(3);
		this.workingWeek = (String) allInfo.get(4);

		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

		Time time = (Time) allInfo.get(5);
		this.workingTimeStart = dateFormat.format(time);

		time = (Time) allInfo.get(6);
		this.workingTimeEnd = dateFormat.format(time);

		time = (Time) allInfo.get(7);
		this.operatingTimeStart = dateFormat.format(time);

		time = (Time) allInfo.get(8);
		this.operatingTimeEnd = dateFormat.format(time);
		this.notPickUpTime = (Integer) allInfo.get(9);
	}

	public boolean setSomeInfo(String infoName, Object newInfo) {
		boolean result = false;
		String goalName = "";

		for (String s : this.infoName) {
			if (s.equalsIgnoreCase(infoName)) {
				goalName = s;
				break;
			}
		}

		String query = "UPDATE ShopInformation SET " + goalName + " = '" + newInfo + "' WHERE ID = '" + ID + "'";
		result = super.insertDeleteDB(query);
		return result;
	}

	public String getID() {
		return ID;
	}

	public String getShop() {
		return shop;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getWorkingWeek() {
		return workingWeek;
	}

	public int getNotPickUpTime() {
		return notPickUpTime;
	}

	public String getWorkingTimeStart() {
		return workingTimeStart;
	}

	public String getWorkingTimeEnd() {
		return workingTimeEnd;
	}

	public String getOperatingTimeStart() {
		return operatingTimeStart;
	}

	public String getOperatingTimeEnd() {
		return operatingTimeEnd;
	}

}