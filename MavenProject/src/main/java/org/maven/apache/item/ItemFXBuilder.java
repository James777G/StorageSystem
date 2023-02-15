package org.maven.apache.item;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个 Class 是专门用来做 ItemFX 这个 Class 的
 *
 * @author james
 */


public class ItemFXBuilder {

    /**
     * 建造一个单一的 ITEMFX
     *
     * @param item
     * @return
     */
    public static ItemFX build(Item item) {
        return new ItemFX(new SimpleStringProperty(String.valueOf(item.getItemID())), new SimpleStringProperty(item.getItemName()),
                new SimpleStringProperty(String.valueOf(item.getUnit())), new SimpleStringProperty(item.getDescription()));
    }

    /**
     * 建造一个List 的 ITEMFX
     *
     * @param items
     * @return
     */
    public static List<ItemFX> buildList(List<Item> items) {
        List<ItemFX> result = new ArrayList<ItemFX>();

        for (Item item : items) {
            result.add(ItemFXBuilder.build(item));
        }

        return result;
    }

}
