package org.maven.apache.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Transaction implements Serializable {

    private int ID;
    private String ItemName;
    private String StaffName;
    private String Status;
    private int Unit;
    private String TransactionTime;
    private String Purpose;

    public int getID() {return ID;}

    public void setID(int ID) {this.ID = ID;}

    public String getItemName() {return ItemName;}

    public void setItemName(String itemName) {ItemName = itemName;}

    public String getStaffName() {return StaffName;}

    public void setStaffName(String staffName) {StaffName = staffName;}

    public String getStatus() {return Status;}

    public void setStatus(String status) {Status = status;}

    public int getUnit() {return Unit;}

    public void setUnit(int unit) {Unit = unit;}

    public String getTransactionTime() {return TransactionTime;}

    public void setTransactionTime(String transactionTime) {TransactionTime = transactionTime;}

    public String getPurpose() {return Purpose;}

    public void setPurpose(String purpose) {Purpose = purpose;}
}
