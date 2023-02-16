package org.maven.apache.service.regulatory;

import org.maven.apache.regulatory.Regulatory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("regulatoryDataManipulator")
public class RegulatoryCachedDataManipulator implements RegulatoryDataManipulationService {

    @Override
    public List<List<Regulatory>> getPagedCacheList(List<Regulatory> cachedList, int pageSize) {
        List<List<Regulatory>> pagedCachedList = new ArrayList<>();
        int pageNumber = cachedList.size() / pageSize;
        for (int i = 0; i < pageNumber; i++) {
            List<Regulatory> itemList = new ArrayList<>(4);
            for (int j = 0; j < pageSize; j++) {
                itemList.add(cachedList.get((i * pageSize) + j));
            }
            pagedCachedList.add(i, itemList);
        }
        if (cachedList.size() % pageSize != 0) {
            List<Regulatory> itemList = new ArrayList<>();
            for (int i = 0; i < cachedList.size() % pageSize; i++) {
                itemList.add(i, cachedList.get(cachedList.size() - cachedList.size() % pageSize + i));
            }
            pagedCachedList.add(itemList);
        }
        return pagedCachedList;
    }
}
