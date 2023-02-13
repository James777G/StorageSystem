package org.maven.apache.service.regulatory;

import jakarta.annotation.Resource;
import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.RegulatoryMapper;
import org.maven.apache.regulatory.Regulatory;
import org.maven.apache.utils.RegulatoryCachedUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("regulatoryService")
public class RegulatoryDAOServiceHandler implements RegulatoryService{

    @Resource
    private RegulatoryMapper regulatoryMapper;

    @Resource
    private RegulatoryDataManipulationService regulatoryDataManipulator;

    @Resource
    private NewRegulatoryAddStrategies regulatoryAddStrategies;

    @Resource
    private ItemMapper itemMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void updateAllRegulatoryData() {
        RegulatoryCachedUtils.putLists(RegulatoryCachedUtils.listType.ALL,
                regulatoryDataManipulator.getPagedCacheList(regulatoryMapper.selectAll(), 3));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewRegulatory(Regulatory regulatory) throws DataNotFoundException {
        regulatoryAddStrategies.proceed(regulatory, itemMapper, regulatoryMapper);
        updateAllRegulatoryData();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRegulatory(String itemName) {
        regulatoryMapper.deleteRegulatory(itemName);
        updateAllRegulatoryData();
    }
}
