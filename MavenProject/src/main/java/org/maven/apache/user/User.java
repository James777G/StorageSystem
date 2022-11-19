package org.maven.apache.user;

public class User {
	private Integer ID;
	private String Name;
	private String Price;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	@Override
	public String toString() {
		return "User [ID=" + ID + ", Name=" + Name + ", Price=" + Price + "]";
	}
}
