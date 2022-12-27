package org.maven.apache.service.item;

import org.apache.ibatis.annotations.Param;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;

import java.util.List;

public interface ItemService {
    /**
     * This method adds a new item to the item table
     * @param item an instance of Item
     */
    void addNewItem(Item item);

    /**
     * This method returns a list of Item that is currently in Item Table
     * @return a list of items
     */
    List<Item> selectAll();

    /**
     * This method returns an item that has the same ID value in the table
     * @param id integer ItemID
     * @return an instance of Item
     */
    Item selectById(int id);

    /**
     * This method returns a list of item selected by its ItemName and unit
     * @param itemName Supports Fuzzy Search
     * @param unit The minimum number of units for filtering
     * @return a list of item
     */
    List<Item> selectByCondition(String itemName, int unit);

    /**
     * updates the value of an item in ItemTable based on their ID values
     * @param item an instance of Item
     * @return an integer showing how many number of rows in the table are affected
     */
    int update(Item item);

    /**
     * deletes an item in the item table by its id
     * @param id an integer ItemID
     */
    void deleteById(int id);

    /**
     * deletes a set of items based on their ID values
     * @param ids an array of IDs
     */
    void deleteByIds(int[] ids);

}
