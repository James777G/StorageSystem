package org.maven.apache.service.item;

import org.maven.apache.item.Item;
import org.maven.apache.staff.Staff;

import java.util.List;

public interface ItemDataManipulationService {

    /**
     * This method separated all the data into list of list with each list containing a maximum
     * of seven elements.
     * @param cachedList the presorted list
     * @return a sorted list with each index representing a page.
     */
    List<List<Item>> getPagedCacheList(List<Item> cachedList, int pageSize);
}
