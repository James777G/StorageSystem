package org.maven.apache.service.regulatory;

import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.RegulatoryMapper;
import org.maven.apache.regulatory.Regulatory;

public abstract sealed class AbstractRegulatoryStrategy permits ItemRegulatoryStrategy, RegulatoryMailingStrategy{

    public abstract boolean doStrategy(Regulatory regulatory, ItemMapper itemMapper, RegulatoryMapper regulatoryMapper) throws DataNotFoundException;
}
