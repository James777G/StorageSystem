package org.maven.apache.service.text;

public abstract sealed class AbstractMessageTextService permits TransactionBasedTextService, StaffBasedTextService, CargoBasedTextService{

    public abstract String getHeaderText(String id, String name);
}
