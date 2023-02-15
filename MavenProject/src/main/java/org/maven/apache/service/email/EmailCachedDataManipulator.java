package org.maven.apache.service.email;

import org.maven.apache.email.Email;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("emailDataManipulator")
public class EmailCachedDataManipulator implements EmailCachedDataManipulationService {


    @Override
    public List<List<Email>> getPagedCacheList(List<Email> cachedList, int pageSize) {
        List<List<Email>> pagedCachedList = new ArrayList<>();
        int pageNumber = cachedList.size() / pageSize;
        for (int i = 0; i < pageNumber; i++) {
            List<Email> itemList = new ArrayList<>(4);
            for (int j = 0; j < pageSize; j++) {
                itemList.add(cachedList.get((i * pageSize) + j));
            }
            pagedCachedList.add(i, itemList);
        }
        if (cachedList.size() % pageSize != 0) {
            List<Email> itemList = new ArrayList<>();
            for (int i = 0; i < cachedList.size() % pageSize; i++) {
                itemList.add(i, cachedList.get(cachedList.size() - cachedList.size() % pageSize + i));
            }
            pagedCachedList.add(itemList);
        }
        return pagedCachedList;
    }
}
