package org.maven.apache.item;


import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@EqualsAndHashCode
public class Item implements Serializable {
    private Integer ItemID;
    private String ItemName;
    private Integer Unit;
    private String Description = "";

    @Override
    public String toString() {
        return "Item [ItemID=" + ItemID + ", ItemName=" + ItemName + ", Unit=" + Unit + ", Description=" + Description
                + "]";
    }

    public Integer getItemID() {
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

    public Integer getUnit() {
        return Unit;
    }

    public void setUnit(int unit) {
        Unit = unit;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


}
