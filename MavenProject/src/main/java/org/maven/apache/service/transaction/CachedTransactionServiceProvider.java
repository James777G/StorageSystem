package org.maven.apache.service.transaction;

import jakarta.annotation.Resource;
import lombok.Data;
import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.exception.NegativeDataException;
import org.maven.apache.exception.Warning;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.service.item.CachedItemService;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.utils.TransactionCachedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("cachedTransactionService")
@Data
public class CachedTransactionServiceProvider implements CachedTransactionService {

    @Autowired
    @SuppressWarnings("all")
    private TransactionMapper transactionMapper;

    @Resource
    private CachedManipulationService cachedManipulationService;

    @Resource
    private GeneralTransactionStrategiesService strategiesHandler;

    @Resource
    private ItemMapper itemMapper;

    @Resource
    private CachedItemService cachedItemService;

    @Resource
    private CachedTransactionDataListService cachedTransactionListService;


    /**
     * This method updates all the cached transaction data currently stored
     * <p>
     * 1. This method is highly time-consuming, thus should be delegated to loading animation.
     * 2. This method should be called whenever there is a change to database, thus should be
     * included in add, delete and update methods.
     * </p>
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void updateAllCachedTransactionData() {
        cachedTransactionListService.updateAllLists(transactionMapper, cachedManipulationService);
    }


    /**
     * This method adds a new transaction to the database.
     * <p>
     * 1. This method will call updateAllCachedTransactionData to retrieve the latest data after
     * the insertion of data is completed.
     * 2. This method is highly time-consuming, thus should be delegated to loading animation.
     * 3. This method might throw exceptions thus should be surrounded by try/catch for further
     * operations (Some fields are left blank accidentally).
     * </p>
     *
     * @param transaction encapsulated transaction to be added
     */
    @Warning(Warning.WarningType.DEBUG)
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewTransaction(Transaction transaction) throws DataNotFoundException, NegativeDataException {
        transactionMapper.addNewTransaction(transaction);
        updateAllCachedTransactionData();
        strategiesHandler.doStrategies(itemMapper, transactionMapper, transaction);
    }
    /**
     * This method deletes an existing transaction from the database.
     * <p>
     * 1. This method will call updateAllCachedTransactionData to retrieve the latest data after
     * the insertion of data is completed.
     * 2. This method is highly time-consuming, thus should be delegated to loading animation.
     * </p>
     *
     * @param id Transaction ID which is unique
     */
    @Warning(Warning.WarningType.DEBUG)
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteTransactionById(int id) throws NegativeDataException {
        transactionMapper.deleteTransactionById(id);
        updateAllCachedTransactionData();
        strategiesHandler.doStrategies(itemMapper,transactionMapper,id);
    }

    /**
     * This method updates an existing transaction in the database.
     * <p>
     * 1. This method will call updateAllCachedTransactionData to retrieve the latest data after
     * the insertion of data is completed.
     * 2. This method is highly time-consuming, thus should be delegated to loading animation.
     * 3. This method might throw exceptions thus should be surrounded by try/catch for further
     * operations. (Some fields are left blank accidentally)
     * </p>
     *
     * @param transaction encapsulated transaction object to be updated with desired attributes
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateTransaction(Transaction transaction) {
        transactionMapper.updateTransaction(transaction);
        updateAllCachedTransactionData();
    }

    /**
     * this method should be use after using delete by id
     */
    @Override
    public void IdGapInside() {
        transactionMapper.IdGapInside();
    }


}
