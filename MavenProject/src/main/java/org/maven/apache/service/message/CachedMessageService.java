package org.maven.apache.service.message;

import org.maven.apache.message.Message;

public interface CachedMessageService {

    /**
     * This method updates all the cached Message data currently stored
     * <p>
     * 1. This method is highly time-consuming, thus should be delegated to loading animation.
     * 2. This method should be called whenever there is a change to database, thus should be
     * included in add, delete and update methods.
     * </p>
     */
    void updateAllCachedMessageData();


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
     * @param
     */
    void addNewMessage(Message message);


    /**
     * This method deletes an existing message from the database.
     * <p>
     * 1. This method will call updateAllCachedStaffData to retrieve the latest data after
     * the insertion of data is completed.
     * 2. This method is highly time-consuming, thus should be delegated to loading animation.
     * </p>
     *
     * @param id message ID is unique
     */
    void deleteMessageById(int id);

    /**
     * This method updates an existing message in the database.
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
    void updateMessage(Message message);

    /**
     * This method updates the star message in the database
     * <p>
     * *     1. This method will call updateAllCachedStaffData to retrieve the latest data after
     * *     the insertion of data is completed.
     * *     2. This method is highly time-consuming, thus should be delegated to loading animation.
     * *     3. This method might throw exceptions thus should be surrounded by try/catch for further
     * *     operations. (Some fields are left blank accidentally)
     * * </p>
     */
    void starMessage(int id);
}
