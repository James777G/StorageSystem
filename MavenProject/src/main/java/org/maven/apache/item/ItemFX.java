package org.maven.apache.item;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class ItemFX extends RecursiveTreeObject<ItemFX> {

	private StringProperty ID;

	private StringProperty name;

	private StringProperty unit;

	private StringProperty description;

	protected ItemFX(StringProperty iD, StringProperty name, StringProperty unit, StringProperty description) {
		ID = iD;
		this.name = name;
		this.unit = unit;
		this.description = description;
	}

	public StringProperty getID() {
		return ID;
	}

	public void setID(StringProperty iD) {
		ID = iD;
	}

	public StringProperty getName() {
		return name;
	}

	public void setName(StringProperty name) {
		this.name = name;
	}

	public StringProperty getUnit() {
		return unit;
	}

	public void setUnit(StringProperty unit) {
		this.unit = unit;
	}

	public StringProperty getDescription() {
		return description;
	}

	public void setDescription(StringProperty description) {
		this.description = description;
	}

}
