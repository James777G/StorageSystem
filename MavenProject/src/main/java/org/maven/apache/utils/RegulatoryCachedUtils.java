package org.maven.apache.utils;

import org.maven.apache.email.Email;
import org.maven.apache.regulatory.Regulatory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegulatoryCachedUtils {

    public enum listType{
        ALL
    }

    private static final Map<listType, List<List<Regulatory>>> regulatoryMap = new HashMap<>();

    public static void putLists(listType type, List<List<Regulatory>> lists) {
        regulatoryMap.put(type, lists);
    }

    public static List<List<Regulatory>> getLists(listType type) {
        return regulatoryMap.get(type);
    }
}
