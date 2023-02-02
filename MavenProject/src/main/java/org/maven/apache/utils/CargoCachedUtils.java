package org.maven.apache.utils;

import org.maven.apache.item.Item;
import org.maven.apache.staff.Staff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CargoCachedUtils {

    public enum listType{
        ALL
    }

    private static final Map<listType, List<List<Item>>> itemMap = new HashMap<>();

    public static void putLists(listType type, List<List<Item>> lists) {
        itemMap.put(type, lists);
    }

    public static List<List<Item>> getLists(listType type) {
        return itemMap.get(type);
    }
}
