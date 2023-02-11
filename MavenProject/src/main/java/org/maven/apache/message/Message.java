package org.maven.apache.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private int MessageID;
    private String StaffName;
    private String Category;
    private String Information;
    private Date MessageTime;
    private int Star;

    public int getMessageID() {
        return MessageID;
    }

    public void setMessageID(int messageID) {
        MessageID = messageID;
    }

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public Date getMessageTime() {
        return MessageTime;
    }

    public void setMessageTime(Date messageTime) {
        MessageTime = messageTime;
    }

    public int getStar() {
        return Star;
    }

    public void setStar(int star) {
        Star = star;
    }
}
