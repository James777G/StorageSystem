package org.maven.apache.service.message;

import org.maven.apache.message.Message;

import java.util.List;

/**
 * 这个借口就是提供方法把list转为list《list》
 */
public interface MessageManipulationService {
    /**
     * This method separated all the data into list of list with each list containing a maximum
     * of five elements.
     *
     * @param cachedList the presorted list
     * @return a sorted list with each index representing a page.
     */
    List<List<Message>> getPagedCacheList(List<Message> cachedList, int pageSize);
}
