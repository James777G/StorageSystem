package org.maven.apache.mapper;

import org.maven.apache.regulatory.Regulatory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("regulatoryMapper")
public interface RegulatoryMapper {

    List<Regulatory> selectAll();

    void addNewRegulatory(Regulatory regulatory);

    void deleteRegulatory(String itemName);

    Regulatory selectByName(String name);
}
