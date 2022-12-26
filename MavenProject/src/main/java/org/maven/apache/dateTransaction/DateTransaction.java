package org.maven.apache.dateTransaction;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class DateTransaction {
    private Integer ItemID;
    private String ItemName;
    private Integer AddUnit;
    private Integer RemoveUnit;
    private Integer CurrentUnit;
    private String RecordTime;




    public Integer getCurrentUnit() {

        if(CurrentUnit==null){
            CurrentUnit=0;
        }


        return CurrentUnit;
    }

    /**
     * input should be the calculation of original currentUnit + addUnit-removeUnit
     * @param currentUnit
     */
    public void setCurrentUnit(Integer currentUnit) {
        if(currentUnit<0){
            System.out.println("Wrong current unit less than 0 in datetransaction.Datetransaction file");
        }
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

    public String getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(String recordTime) {
        RecordTime = recordTime;
    }
}
