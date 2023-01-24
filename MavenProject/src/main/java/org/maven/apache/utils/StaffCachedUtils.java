package org.maven.apache.utils;

import org.maven.apache.staff.Staff;
import org.maven.apache.transaction.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffCachedUtils {

    public enum listType {
        ALL, ACTIVE, INACTIVE
    }

    private static Map<listType, List<List<Staff>>> staffMap = new HashMap<listType, List<List<Staff>>>();

    public static void putLists(listType type, List<List<Staff>> lists) {
        staffMap.put(type, lists);
    }

    public static List<List<Staff>> getLists(listType type) {
        return staffMap.get(type);
    }
}
