package org.maven.apache.service.message;


import org.maven.apache.item.Item;
import org.maven.apache.mapper.MessageMapper;
import org.maven.apache.message.Message;
import org.maven.apache.service.item.ItemDataManipulationService;
import org.maven.apache.service.transaction.CachedTransactionDataListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个class中的方法是给controller用
 */
@Service("CachedMessageService")
public class CachedMessageServiceProvider implements CachedMessageService{
@Resource
private MessageService messageService;
@Resource
private MessageManipulationService messageManipulationService;
@Resource
private CachedMessageDataListService cachedMessageDataListService;
@Resource
private MessageMapper messageMapper;


    /**
     * This method updates all the cached Message data currently stored
     * <p>
     * 1. This method is highly time-consuming, thus should be delegated to loading animation.
     * 2. This method should be called whenever there is a change to database, thus should be
     * included in add, delete and update methods.
     * </p>
     */
    @Override
    public void updateAllCachedMessageData() {
        cachedMessageDataListService.updateAllLists(messageMapper,messageManipulationService);
    }

    /**
     * This method adds a new Message to the database.
     * <p>
     * 1. This method will call updateAllCachedMessageData to retrieve the latest data after
     * the insertion of data is completed.
     * 2. This method is highly time-consuming, thus should be delegated to loading animation.
     * 3. This method might throw exceptions thus should be surrounded by try/catch for further
     * operations (Some fields are left blank accidentally).
     * </p>
     *
     * @param message
     */
    @Override
    public void addNewMessage(Message message) {

    }

    /**
     * This method deletes an existing Message from the database.
     * <p>
     * 1. This method will call updateAllCachedStaffData to retrieve the latest data after
     * the insertion of data is completed.
     * 2. This method is highly time-consuming, thus should be delegated to loading animation.
     * </p>
     *
     * @param id message ID is unique
     */
    @Override
    public void deleteMessageById(int id) {

    }

    /**
     * This method updates an existing Message in the database.
     * <p>
     * 1. This method will call updateAllCachedStaffData to retrieve the latest data after
     * the insertion of data is completed.
     * 2. This method is highly time-consuming, thus should be delegated to loading animation.
     * 3. This method might throw exceptions thus should be surrounded by try/catch for further
     * operations. (Some fields are left blank accidentally)
     * </p>
     *
     * @param message encapsulated message object to be updated with desired attributes
     */
    @Override
    public void updateMessage(Message message) {

    }

    /**
     * This method updates the star message in the database
     * <p>
     * *     1. This method will call updateAllCachedStaffData to retrieve the latest data after
     * *     the insertion of data is completed.
     * *     2. This method is highly time-consuming, thus should be delegated to loading animation.
     * *     3. This method might throw exceptions thus should be surrounded by try/catch for further
     * *     operations. (Some fields are left blank accidentally)
     * * </p>
     *
     * @param id
     */
    @Override
    public void starMessage(int id) {

    }



}
