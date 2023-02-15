package org.maven.apache.utils;

import org.maven.apache.message.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageCachedUtils {
    public enum listType {
        STAR_MESSAGE, All_MESSAGE
    }

    private static final Map<MessageCachedUtils.listType, List<List<Message>>> messageMap = new HashMap<>();

    public static void putLists(MessageCachedUtils.listType type, List<List<Message>> lists) {
        messageMap.put(type, lists);
    }

    public static List<List<Message>> getLists(MessageCachedUtils.listType type) {
        return messageMap.get(type);
    }
}
