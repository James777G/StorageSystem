package org.maven.apache.item;


public class Item {
	private int ItemID;
	private String ItemName;
	private int Unit;

	@Override
	public String toString() {
		return "Item [ItemID=" + ItemID + ", ItemName=" + ItemName + ", Unit=" + Unit + "]";
	}
	public int getItemID() {
		return ItemID;
	}
	public void setItemID(int itemID) {
		ItemID = itemID;
	}
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	public int getUnit() {
		return Unit;
	}
	public void setUnit(int unit) {
		Unit = unit;
	}
	
	
}
