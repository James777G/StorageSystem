package org.maven.apache.service.regulatory;

import jakarta.annotation.Resource;
import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.RegulatoryMapper;
import org.maven.apache.regulatory.Regulatory;
import org.maven.apache.service.transaction.AbstractTransactionStrategy;
import org.springframework.stereotype.Service;

@Service("regulatoryAddStrategies")
public class NewRegulatoryAddStrategiesHandler implements NewRegulatoryAddStrategies{

    @Resource
    private AbstractRegulatoryStrategy regulatoryMailingStrategy;

    @Resource
    private AbstractRegulatoryStrategy regulatoryItemStrategy;

    @Override
    public void proceed(Regulatory regulatory, ItemMapper itemMapper, RegulatoryMapper regulatoryMapper) throws DataNotFoundException {
        boolean b = regulatoryItemStrategy.doStrategy(regulatory, itemMapper, regulatoryMapper);
        if(b){
            regulatoryMailingStrategy.doStrategy(regulatory, itemMapper, regulatoryMapper);
        }
    }
}
