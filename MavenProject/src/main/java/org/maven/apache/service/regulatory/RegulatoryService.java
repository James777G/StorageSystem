package org.maven.apache.service.regulatory;

import org.maven.apache.regulatory.Regulatory;

public interface RegulatoryService {

    void updateAllRegulatoryData();

    void addNewRegulatory(Regulatory regulatory);

    void deleteRegulatory(String itemName);

}
