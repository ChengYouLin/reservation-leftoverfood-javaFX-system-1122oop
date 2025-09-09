package backEnd;

import java.sql.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ShopRegister extends Register {

	private String shop, address, email, workingWeek, telephone;
	private int notPickUpTime;
	private String workingTimeStart, workingTimeEnd;
	private String operatingTimeStart, operatingTimeEnd;

	public ShopRegister(String ID, String password) {
		super(ID, password, 'C');
	}

	public void setInfo(String shop, String address, String email, String workingWeek, String telephone,
			int notPickUpTime, String workingTimeStart, String workingTimeEnd, String operatingTimeStart,
			String operatingTimeEnd) {

		this.shop = shop;
		this.address = address;
		this.email = email;
		this.workingWeek = workingWeek;
		this.telephone = telephone;

		this.workingTimeStart = workingTimeStart;
		this.workingTimeEnd = workingTimeEnd;
		this.operatingTimeStart = operatingTimeStart;
		this.operatingTimeEnd = operatingTimeEnd;
		this.notPickUpTime = notPickUpTime;
	}

	public boolean insertShopInfoDB() {
		boolean result = false;

		Time timeWorkingTimeStart = null;
		Time timeWorkingTimeEnd = null;
		Time timeOperatingTimeStart = null;
		Time timeOperatingTimeEnd = null;

		try {
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

			long msWorkingTimeStart = timeFormat.parse(workingTimeStart).getTime();
			timeWorkingTimeStart = new Time(msWorkingTimeStart);

			long msWorkingTimeEnd = timeFormat.parse(workingTimeEnd).getTime();
			timeWorkingTimeEnd = new Time(msWorkingTimeEnd);

			long msOperatingTimeStart = timeFormat.parse(operatingTimeStart).getTime();
			timeOperatingTimeStart = new Time(msOperatingTimeStart);

			long msOperatingTimeEnd = timeFormat.parse(operatingTimeEnd).getTime();
			timeOperatingTimeEnd = new Time(msOperatingTimeEnd);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		String query = "INSERT INTO ShopInformation (ID, Shop, Address, Telephone, Email, WorkingWeek, WorkingTimeStart, WorkingTimeEnd, OperatingTimeStart, OperatingTimeEnd, NotPickUpTime) "
				+ "VALUES ('" + super.getID() + "','" + shop + "','" + address + "','" + telephone + "','" + email
				+ "','" + workingWeek + "','" + timeWorkingTimeStart + "','" + timeWorkingTimeEnd + "','"
				+ timeOperatingTimeStart + "','" + timeOperatingTimeEnd + "','" + notPickUpTime + "')";
		result = super.connectDB.insertDeleteDB(query);
		return result;
	}

}
