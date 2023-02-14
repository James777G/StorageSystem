package org.maven.apache.service.item;

import org.maven.apache.item.Item;

import java.lang.reflect.InvocationTargetException;

public interface CachedItemService {

    /**
     * This method updates all the cached item data currently stored
     * <p>
     *     1. This method is highly time-consuming, thus should be delegated to loading animation.
     *     2. This method should be called whenever there is a change to database, thus should be
     *     included in add, delete and update methods.
     * </p>
     */
    void updateAllCachedItemData();


    /**
     * This method adds a new item to the database.
     * <p>
     *     1. This method will call updateAllCachedItemData to retrieve the latest data after
     *     the insertion of data is completed.
     *     2. This method is highly time-consuming, thus should be delegated to loading animation.
     *     3. This method might throw exceptions thus should be surrounded by try/catch for further
     *     operations (Some fields are left blank accidentally).
     * </p>
     * @param item encapsulated item to be added
     */
    void addNewItem(Item item);


    /**
     * This method deletes an existing item from the database.
     * <p>
     *     1. This method will call updateAllCachedStaffData to retrieve the latest data after
     *     the insertion of data is completed.
     *     2. This method is highly time-consuming, thus should be delegated to loading animation.
     * </p>
     * @param id Staff ID which is unique
     */
    void deleteItemById(int id);

    /**
     * This method updates an existing item in the database.
     * <p>
     *     1. This method will call updateAllCachedStaffData to retrieve the latest data after
     *     the insertion of data is completed.
     *     2. This method is highly time-consuming, thus should be delegated to loading animation.
     *     3. This method might throw exceptions thus should be surrounded by try/catch for further
     *     operations. (Some fields are left blank accidentally)
     * </p>
     * @param item encapsulated item object to be updated with desired attributes
     */
    void updateItem(Item item) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;
}
