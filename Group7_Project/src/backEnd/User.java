package backEnd;

import java.util.ArrayList;

public class User extends ConnectDB {
	private String ID, depart, name, email, cellphone;
	private int notPickUpTime;
	private String[] infoName = { "Department", "Name", "Cellphone", "Email", "NotPickUpTime" };

	public User(String ID) {
		this.ID = ID;
	}

	public ArrayList<Shop> getAllShop() {
		ArrayList<Shop> allShop = new ArrayList<>();

		String query = "SELECT ID FROM `ShopInformation`";
		System.out.println("Executing query: " + query);
		ArrayList<Object> allID = super.connectDB(query, "ID");

		for (Object id : allID) {
			Shop shop = new Shop(id.toString());
			shop.refreshInfo();
			if (getNotPickUPTime() <= shop.getNotPickUpTime()) {
				allShop.add(shop);
			}
		}

		return allShop;
	}

	public ArrayList<LeftoverFood> getAllLFood() {
		ArrayList<LeftoverFood> allLFood = new ArrayList<>();

		String query = "SELECT ID FROM `LeftoverFood`";
		System.out.println("Executing query: " + query);
		ArrayList<Object> allID = super.connectDB(query, "ID");

		for (Object id : allID) {
			LeftoverFood lFood = new LeftoverFood(id.toString());
			lFood.refreshInfo();
			allLFood.add(lFood);
		}
		return allLFood;
	}

	public void refreshInfo() {
		ArrayList<Object> allInfo = new ArrayList<>();

		for (int i = 0; i < infoName.length; i++) {
			String query = "SELECT " + infoName[i] + " FROM `UserInformation` WHERE ID = '" + ID + "'";
			System.out.println("Executing query: " + query);

			ArrayList<Object> result = super.connectDB(query, infoName[i]);

			if (result != null && !result.isEmpty()) {
				allInfo.add(result.get(0));
			} else {
				throw new RuntimeException("No data found for: " + infoName[i]);
			}
		}

		if (allInfo.size() >= 5) {
			try {
				this.depart = (String) allInfo.get(0);
				this.name = (String) allInfo.get(1);
				this.cellphone = (String) allInfo.get(2);
				this.email = (String) allInfo.get(3);
				this.notPickUpTime = (Integer) allInfo.get(4);
			} catch (ClassCastException e) {
				System.out.println("Type mismatch in fetched data: " + e.getMessage());
				throw new RuntimeException("Type mismatch in fetched data: " + e.getMessage());
			}
		} else {
			throw new RuntimeException("Incomplete data fetched from database");
		}
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

		String query = "UPDATE `UserInformation` SET " + goalName + " = '" + newInfo + "' WHERE ID = '" + ID + "'";
		System.out.println("Executing update query: " + query);
		result = super.insertDeleteDB(query);
		return result;
	}

	public String getID() {
		return ID;
	}

	public String getDepart() {
		return depart;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getCellphone() {
		return cellphone;
	}

	public int getNotPickUPTime() {
		return notPickUpTime;
	}
}