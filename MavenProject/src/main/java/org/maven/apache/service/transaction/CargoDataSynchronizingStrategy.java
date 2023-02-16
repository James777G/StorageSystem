package org.maven.apache.service.transaction;

import jakarta.annotation.Resource;
import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.exception.NegativeDataException;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.service.item.CachedItemService;
import org.maven.apache.transaction.Transaction;
import org.springframework.stereotype.Service;

@Service("cargoDataStrategy")
public final class CargoDataSynchronizingStrategy extends AbstractTransactionStrategy {

    @Resource
    private CachedItemService cachedItemService;

    @Override
    public void doStrategy(ItemMapper itemMapper, TransactionMapper transactionMapper, Transaction transaction) throws DataNotFoundException, NegativeDataException {
        if ("RESTOCK".equals(transaction.getStatus())) {
            doIncreaseStrategyInAdd(itemMapper, transactionMapper, transaction);
        } else {
            doDecreaseStrategyInAdd(itemMapper, transactionMapper, transaction);
        }
    }

    @Override
    public void doStrategy(ItemMapper itemMapper, TransactionMapper transactionMapper, int id) throws NegativeDataException {
        if ("RESTOCK".equals(transactionMapper.selectById(id).getStatus())) {
            doDecreaseStrategyInDelete(itemMapper, transactionMapper, id);
        } else {
            doIncreaseStrategyInDelete(itemMapper, transactionMapper, id);
        }
    }

    private void doIncreaseStrategyInDelete(ItemMapper itemMapper, TransactionMapper transactionMapper, int id) {
        // try to find the item involved

        Item item = null;
        Transaction transaction = transactionMapper.selectById(id);
        try {
            item = itemMapper.selectByItemName(transaction.getItemName());
        } catch (Exception ignored) {
            return;
        }

        // increase item unit
        if (item != null) {
            int previousUnit = item.getUnit();
            int currentUnit = previousUnit + transaction.getUnit();
            item.setUnit(currentUnit);
            itemMapper.update(item);
            cachedItemService.updateAllCachedItemData();
        }

    }

    private void doDecreaseStrategyInDelete(ItemMapper itemMapper, TransactionMapper transactionMapper, int id) throws NegativeDataException {
        // try to find the item involved

        Item item = null;
        Transaction transaction = transactionMapper.selectById(id);
        try {
            item = itemMapper.selectByItemName(transaction.getItemName());
        } catch (Exception ignored) {
            return;
        }

        // decrease item unit
        if (item != null) {
            int previousUnit = item.getUnit();
            int currentUnit = previousUnit - transaction.getUnit();
            if (currentUnit >= 0) {
                item.setUnit(currentUnit);
                itemMapper.update(item);
                cachedItemService.updateAllCachedItemData();
            } else {
                throw new NegativeDataException("Negative Data Value");
            }
        }
    }


    private void doIncreaseStrategyInAdd(ItemMapper itemMapper, TransactionMapper transactionMapper, Transaction transaction) throws DataNotFoundException {
        // find item which is involved in this transaction

        Item item;
        try {
            item = itemMapper.selectByItemName(transaction.getItemName());
        } catch (Exception e) {
            throw new DataNotFoundException("Item with this item name does not exist in the database");
        }

        if(item == null){
            throw new DataNotFoundException("Item with this item name does not exist in the database");
        }

        // item amount should increase
        int previousUnit = item.getUnit();
        int currentUnit = previousUnit + transaction.getUnit();
        item.setUnit(currentUnit);

        itemMapper.update(item);
        cachedItemService.updateAllCachedItemData();

    }

    private void doDecreaseStrategyInAdd(ItemMapper itemMapper, TransactionMapper transactionMapper, Transaction transaction) throws DataNotFoundException, NegativeDataException {
        // find item which is involved in this transaction

        Item item = null;
        try {
            item = itemMapper.selectByItemName(transaction.getItemName());
        } catch (Exception e) {
            throw new DataNotFoundException("Item with this item name does not exist in the database");
        }

        if(item == null){
            throw new DataNotFoundException("Item with this item name does not exist in the database");
        }

        // item amount should decrease
        int previousUnit = item.getUnit();
        int currentUnit = previousUnit - transaction.getUnit();
        if (currentUnit >= 0) {
            item.setUnit(currentUnit);
            itemMapper.update(item);
            cachedItemService.updateAllCachedItemData();
        } else {
            throw new NegativeDataException("Negative Item Data");
        }

    }
}
