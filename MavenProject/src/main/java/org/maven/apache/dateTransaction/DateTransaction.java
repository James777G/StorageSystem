package org.maven.apache.dateTransaction;

import java.sql.Timestamp;

public class DateTransaction {
    private Integer ItemID;
    private String ItemName;
    private Integer AddUnit;
    private Integer RemoveUnit;
    private Integer CurrentUnit;
    private Timestamp RecordTime;
    public Integer getCurrentUnit() {
        return CurrentUnit;
    }

    public void setCurrentUnit(Integer currentUnit) {
        CurrentUnit = currentUnit;
    }

    public Integer getItemID() {
        return ItemID;
    }

    public void setItemID(Integer itemID) {
        ItemID = itemID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public Integer getAddUnit() {
        return AddUnit;
    }

    public void setAddUnit(Integer addUnit) {
        AddUnit = addUnit;
    }

    public Integer getRemoveUnit() {
        return RemoveUnit;
    }

    public void setRemoveUnit(Integer removeUnit) {
        RemoveUnit = removeUnit;
    }

    public Timestamp getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        RecordTime = recordTime;
    }
}
