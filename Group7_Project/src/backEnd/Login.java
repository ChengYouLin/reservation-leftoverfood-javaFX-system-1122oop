package backEnd;

import java.util.ArrayList;

public class Login implements CheckAccount {

	private String ID;
	private ConnectDB connectDB;

	public Login() {
		this.connectDB = new ConnectDB();
	}

	public boolean checkID(String ID) {
		String query = "SELECT ID FROM `Account` WHERE ID = '" + ID + "'";
		ArrayList<Object> allID = connectDB.connectDB(query, "ID");
		for (Object a : allID) {
			if (a.equals(ID)) {
				this.ID = ID;
				return true;
			}
		}
		return false;
	}

	public boolean checkPassword(String password) {
		String query = "SELECT Password FROM `Account` WHERE Password = '" + password + "'";
		ArrayList<Object> allPassword = connectDB.connectDB(query, "Password");
		for (Object a : allPassword) {
			if (a.equals(password)) {
				return true;
			}
		}
		return false;
	}

	public char getType() {
		String query = "SELECT Type FROM `Account` WHERE ID = '" + ID + "'";
		ArrayList<Object> allInfo = connectDB.connectDB(query, "Type");
		if (!allInfo.isEmpty() && allInfo.get(0) != null) {
			return ((String) allInfo.get(0)).charAt(0);
		}
		return ' ';
	}

	public boolean setPassword(String orginalPassword, String newPassword) {
		boolean result = false;
		if (checkPassword(orginalPassword)) {
			String query = "UPDATE `Account` SET password = '" + newPassword + "' WHERE Id = '" + ID + "'";
			result = connectDB.insertDeleteDB(query);
			return result;
		}
		return result;
	}

}
