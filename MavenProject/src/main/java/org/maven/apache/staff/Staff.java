package org.maven.apache.staff;

import lombok.Data;

@Data
public class Staff {

    enum Status{
        ACTIVE,
        INACTIVE
    }
    private int staffID;

    private Status status;


    private String staffName;

    private String otherInfo;


}
