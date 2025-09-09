package backEnd;

public class UserRegister extends Register {

	private String department;
	private String name;
	private String cellphone;
	private String email;

	public UserRegister(String ID, String password) {
		super(ID, password, 'U');

	}

	public void setInfo(String dp, String name, String phone, String mail) {
		this.department = dp;
		this.name = name;
		this.cellphone = phone;
		this.email = mail;
	}

	public boolean insertUserInfoDB() {
		boolean result = false;
		String query = "INSERT INTO `UserInformation` (ID, Department, Name, Cellphone, Email, NotPickUpTime) "
                + "VALUES ('" + super.getID() + "','" + department + "','" + name + "','" + cellphone + "','" + email + "', 0)";
		result = super.connectDB.insertDeleteDB(query);
		return result;
	}

}
