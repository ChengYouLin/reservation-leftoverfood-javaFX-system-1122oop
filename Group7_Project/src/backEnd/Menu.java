package backEnd;

import java.util.ArrayList;

public class Menu extends ConnectDB {

	public Menu() {
	}

	public ArrayList<Meal> refreshInfo(String shopID) {
		ArrayList<Meal> allMeal = new ArrayList<>();

		try {
			String query = "SELECT * FROM Menu WHERE ID = '" + shopID + "'";
			System.out.println("Executing query: " + query);
			ArrayList<ArrayList<Object>> result = super.fetchAll(query);

			for (ArrayList<Object> row : result) {
				String ID = (String) row.get(0);
				String mealName = (String) row.get(1);
				int appointmentQuantity = (int) row.get(2);
				String unit = (String) row.get(3);
				int unitPrice = (int) row.get(4);
				String shopNote = (String) row.get(5);
				String state = (String) row.get(6);

				Meal meal = new Meal(ID, mealName, appointmentQuantity, unit, unitPrice, shopNote, state);
				allMeal.add(meal);
				System.out.println("Added Meal: " + mealName + ", " + unitPrice + ", " + unit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return allMeal;
	}

	public boolean insertMenuDB(String ID, String mealName, int appointmentQuantity, String unit, int unitPrice,
			String shopNote) {
		String state = "販售中";
		String query = "INSERT INTO Menu (ID, MealName, AppointmentQuantity, Unit, UnitPrice, ShopNote, State) VALUES ('"
				+ ID + "', '" + mealName + "', " + appointmentQuantity + ", '" + unit + "', " + unitPrice + ", '"
				+ shopNote + "', '" + state + "')";
		return super.insertDeleteDB(query);
	}

	public boolean insertMenuDB(String ID, String mealName, int appointmentQuantity) {
		String state = "販售中";
		String query = "INSERT INTO Menu (ID, MealName, AppointmentQuantity, Unit, UnitPrice, ShopNote, State) VALUES ('"
				+ ID + "', '" + mealName + "', " + appointmentQuantity + ", '份', 0, '', '" + state + "')";
		return super.insertDeleteDB(query);
	}
}
