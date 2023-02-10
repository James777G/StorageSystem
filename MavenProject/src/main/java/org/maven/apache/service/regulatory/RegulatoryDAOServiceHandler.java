package org.maven.apache.service.regulatory;

import jakarta.annotation.Resource;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.RegulatoryMapper;
import org.maven.apache.regulatory.Regulatory;
import org.maven.apache.utils.RegulatoryCachedUtils;
import org.springframework.stereotype.Service;

@Service("regulatoryService")
public class RegulatoryDAOServiceHandler implements RegulatoryService{

    @Resource
    private RegulatoryMapper regulatoryMapper;

    @Resource
    private RegulatoryDataManipulationService regulatoryDataManipulator;

    @Resource
    private ItemMapper itemMapper;

    @Override
    public void updateAllRegulatoryData() {
        RegulatoryCachedUtils.putLists(RegulatoryCachedUtils.listType.ALL,
                regulatoryDataManipulator.getPagedCacheList(regulatoryMapper.selectAll(), 3));
    }

    @Override
    public void addNewRegulatory(Regulatory regulatory) {

    }

    @Override
    public void deleteRegulatory(String itemName) {

    }
}
