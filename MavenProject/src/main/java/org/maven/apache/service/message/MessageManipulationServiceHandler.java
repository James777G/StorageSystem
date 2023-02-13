package org.maven.apache.service.message;

import org.maven.apache.message.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("messageManipulationService")
public class MessageManipulationServiceHandler implements MessageManipulationService{

    /**
     * This method separated all the data into list of list with each list containing a maximum
     * of five elements.
     *
     * @param cachedList the presorted list
     * @param pageSize
     * @return a sorted list with each index representing a page.
     */
    @Override
    public List<List<Message>> getPagedCacheList(List<Message> cachedList, int pageSize) {
        List<List<Message>> pagedCachedList = new ArrayList<>();
        int pageNumber = cachedList.size() / pageSize;
        for (int i = 0; i < pageNumber; i++) {
            List<Message> messageList = new ArrayList<>(4);
            for (int j = 0; j < pageSize; j++) {
                messageList.add(cachedList.get((i * pageSize) + j));
            }
            pagedCachedList.add(i, messageList);
        }
        if (cachedList.size() % pageSize != 0) {
            List<Message> messageList = new ArrayList<>();
            for (int i = 0; i < cachedList.size() % pageSize; i++) {
                messageList.add(i, cachedList.get(cachedList.size() - cachedList.size() % pageSize + i));
            }
            pagedCachedList.add(messageList);
        }
        return pagedCachedList;
    }
}
