package org.maven.apache.utils;



import org.maven.apache.transaction.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionCachedUtils {
    public enum listType {
        RESTOCK_Amount_ASC_4,RESTOCK_Amount_DESC_4,RESTOCK_DATE_ASC_4,RESTOCK_DATE_DESC_4,
        TAKEN_Amount_ASC_4,TAKEN_Amount_DESC_4,TAKEN_DATE_ASC_4,TAKEN_DATE_DESC_4,
        AMOUNT_ASC_4, AMOUNT_DESC_4, AMOUNT_ASC_7, AMOUNT_DESC_7,
        DATE_ASC_4, DATE_DESC_4, DATE_ASC_7, DATE_DESC_7
    }

    private static Map<listType, List<List<Transaction>>> transactionMap = new HashMap<listType, List<List<Transaction>>>();

    public static void putLists(listType type, List<List<Transaction>> lists) {
        transactionMap.put(type, lists);
    }

    public static List<List<Transaction>> getLists(listType type) {
        return transactionMap.get(type);
    }





}
