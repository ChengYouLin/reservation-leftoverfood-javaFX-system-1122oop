package backEnd;

public class Meal extends Menu {

	private String ID, mealName, unit, shopNote, state;
	private int appointmentQuantity, unitPrice;
	private String[] infoName = { "ID", "MealName", "AppointmentQuantity", "Unit", "UnitPrice", "ShopNote", "State" };
	private int totalPrice, quantity;

	// 刷新資料使用
	public Meal(String ID, String mealName, int appointmentQuantity, String unit, int unitPrice, String shopNote,
			String state) {
		this.ID = ID;
		this.mealName = mealName;
		this.appointmentQuantity = appointmentQuantity;
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.shopNote = shopNote;
		this.state = state;
	}

	// 商家新增菜單
	public Meal(String ID, String mealName, int appointmentQuantity, String unit, int unitPrice, String shopNote) {
		this.ID = ID;
		this.mealName = mealName;
		this.appointmentQuantity = appointmentQuantity;
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.shopNote = shopNote;

		super.insertMenuDB(ID, mealName, appointmentQuantity, unit, unitPrice, shopNote);
	}

	// 剩食新增菜單
	public Meal(String ID, String mealName, int appointmentQuantity) {
		this.ID = ID;
		this.mealName = mealName;
		this.appointmentQuantity = appointmentQuantity;
		this.unit = "份";
		this.unitPrice = 0;
		this.shopNote = "";

		super.insertMenuDB(ID, mealName, appointmentQuantity);
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

		String query = "UPDATE Menu SET " + goalName + " = '" + newInfo + "' WHERE Id = '" + ID + "' AND MealName = '"
				+ mealName + "'";
		result = super.insertDeleteDB(query);
		return result;
	}

	// 主要給剩食刪掉所有該帳號有的餐點
	// 不確定有沒有用到
	public boolean deleteMeal(String ID) {
		boolean result = false;
		String query = "DELETE FROM 'Menu' WHERE ID =" + ID;
		result = super.insertDeleteDB(query);

		return result;
	}

	// 一般刪除餐點
	public boolean deleteMeal(String ID, String mealName) {
		boolean result = false;
		String query = "DELETE FROM Menu WHERE ID ='" + ID + "' AND MealName = '" + mealName + "'";
		System.out.println(query);
		result = super.insertDeleteDB(query);

		return result;
	}

	public String getID() {
		return ID;
	}

	public String getMealName() {
		return mealName;
	}

	public String getUnit() {
		return unit;
	}

	public String getShopNote() {
		return shopNote;
	}

	public String getState() {
		return state;
	}

	public int getAppointmentQuantity() {
		return appointmentQuantity;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	// 前端即時更新使用！
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	// 前端即時更新使用！
	public int getTotalPrice() {
		return totalPrice;
	}

	// 前端即時更新使用！
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// 前端即時更新使用！
	public int getQuantity() {
		return quantity;
	}
}
