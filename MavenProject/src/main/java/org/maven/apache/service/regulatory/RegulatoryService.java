package org.maven.apache.service.regulatory;

import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.regulatory.Regulatory;

public interface RegulatoryService {

    void updateAllRegulatoryData();

    void addNewRegulatory(Regulatory regulatory) throws DataNotFoundException;

    void deleteRegulatory(String itemName);

}
