package backEnd;

import java.util.ArrayList;

public class Register implements CheckAccount {

	private String ID;
	private String password;
	private char type;
	protected ConnectDB connectDB;

	public Register(String ID, String password, char type) {
		this.ID = ID;
		this.password = password;
		this.type = type;
		this.connectDB = new ConnectDB();
	}

	public boolean checkID(String ID) {
		String query = "SELECT ID FROM `Account` WHERE ID = '" + ID + "'";
		ArrayList<Object> allID = connectDB.connectDB(query, "ID");

		for (Object a : allID) {
			if (a.equals(ID)) {
				return false;
			}
		}
		this.ID = ID;
		return true;
	}

	public boolean checkPassword(String password) {
		this.password = password;
		return true;
	}

	public String getID() {
		return ID;
	}

	public char getType() {
		return type;
	}

	public boolean insertAccountDB() {
		boolean result = false;
		String query = "INSERT INTO `Account` (ID, Password, Type) " + "VALUES ('" + ID + "','" + password + "','"
				+ type + "')";
		result = connectDB.insertDeleteDB(query);
		return result;
	}
}