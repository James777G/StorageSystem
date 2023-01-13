package org.maven.apache.staff;

import lombok.Data;

@Data
public class Staff {

    private int staffID;
    private enum status{
        ACTIVE,
        INACTIVE
    }

    private String staffName;

    private String otherInfo;


}
