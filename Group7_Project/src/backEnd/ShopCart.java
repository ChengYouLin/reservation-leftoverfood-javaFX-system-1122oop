package backEnd;

public class ShopCart {

	private String name;
	private String quan;
	private int price;

	public ShopCart(String n, String q, int p) {
		this.name = n;
		this.quan = q;
		this.price = p;
	}

	public String getName() {
		return name;
	}

	public String getQuan() {
		return quan;
	}

	public int getPrice() {
		return price;
	}
}
