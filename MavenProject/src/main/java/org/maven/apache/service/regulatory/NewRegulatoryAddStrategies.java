package org.maven.apache.service.regulatory;

import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.RegulatoryMapper;
import org.maven.apache.regulatory.Regulatory;

public interface NewRegulatoryAddStrategies {

    void proceed(Regulatory regulatory, ItemMapper itemMapper, RegulatoryMapper regulatoryMapper);
}
