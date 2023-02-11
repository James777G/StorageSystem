package org.maven.apache.utils;

import org.maven.apache.email.Email;
import org.maven.apache.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailCachedUtils {

    public enum listType{
        ALL
    }

    private static final Map<listType, List<List<Email>>> emailMap = new HashMap<>();

    public static void putLists(listType type, List<List<Email>> lists) {
        emailMap.put(type, lists);
    }

    public static List<List<Email>> getLists(listType type) {
        return emailMap.get(type);
    }
}
