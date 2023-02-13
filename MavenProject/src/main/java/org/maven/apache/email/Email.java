package org.maven.apache.email;

import lombok.Data;

import java.io.Serializable;

@Data
public class Email implements Serializable {

    private int ID;
    private String emailAddress;
}
