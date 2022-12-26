package org.maven.apache.service.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ItemServiceProvider implements ItemService{

    private ItemMapper itemMapper;

    public ItemMapper getItemMapper() {
        return itemMapper;
    }

    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    /**
     * This method adds a new item to the item table
     *
     * @param item an instance of Item
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewItem(Item item) {
        itemMapper.add(item);
    }

    /**
     * This method returns a list of Item that is currently in Item Table
     *
     * @return a list of items
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Item> selectAll() {
        return itemMapper.selectAll();
    }

    /**
     * This method returns an item that has the same ID value in the table
     *
     * @param id integer ItemID
     * @return an instance of Item
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public Item selectById(int id) {
        return itemMapper.selectById(id);
    }

    /**
     * This method returns a list of item selected by its ItemName and unit
     *
     * @param itemName Supports Fuzzy Search
     * @param unit     The minimum number of units for filtering
     * @return a list of item
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public List<Item> selectByCondition(String itemName, int unit) {
        return itemMapper.selectByCondition(itemName, unit);
    }

    /**
     * updates the value of an item in ItemTable based on their ID values
     *
     * @param item an instance of Item
     * @return an integer showing how many number of rows in the table are affected
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int update(Item item) {
        return itemMapper.update(item);
    }

    /**
     * deletes an item in the item table by its id
     *
     * @param id an integer ItemID
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteById(int id) {
        itemMapper.deleteById(id);
    }

    /**
     * deletes a set of items based on their ID values
     *
     * @param ids an array of IDs
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteByIds(int[] ids) {
        itemMapper.deleteByIds(ids);
    }

    @Override
    public void fixIdGapInside() {
        itemMapper.fixIdGapInside();
    }



}
