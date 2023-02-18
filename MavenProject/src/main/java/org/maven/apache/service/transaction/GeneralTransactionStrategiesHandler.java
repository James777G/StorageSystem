package org.maven.apache.service.transaction;

import jakarta.annotation.Resource;
import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.exception.NegativeDataException;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.transaction.Transaction;
import org.springframework.stereotype.Service;

@Service("strategiesHandler")
public class GeneralTransactionStrategiesHandler implements GeneralTransactionStrategiesService {

    @Resource
    private AbstractTransactionStrategy warehouseStrategy;

    @Resource
    private AbstractTransactionStrategy cargoDataStrategy;

    @Resource
    private AbstractTransactionStrategy alertStrategy;

    @Resource
    private AbstractTransactionStrategy appPageControllerSynchronizer;

    @Resource
    private AbstractTransactionStrategy transactionPageControllerSynchronizer;

    @Override
    public void doStrategies(ItemMapper itemMapper, TransactionMapper transactionMapper, Transaction transaction) throws DataNotFoundException, NegativeDataException {
        cargoDataStrategy.doStrategy(itemMapper, transactionMapper, transaction);
        warehouseStrategy.doStrategy(itemMapper, transactionMapper, transaction);
        alertStrategy.doStrategy(itemMapper, transactionMapper, transaction);
    }

    @Override
    public void doStrategies(ItemMapper itemMapper, TransactionMapper transactionMapper, int id) throws NegativeDataException {
        cargoDataStrategy.doStrategy(itemMapper, transactionMapper, id);
        warehouseStrategy.doStrategy(itemMapper, transactionMapper, id);
        alertStrategy.doStrategy(itemMapper, transactionMapper, id);

    }

    @Override
    public void doPostStrategies(ItemMapper itemMapper, TransactionMapper transactionMapper, Transaction transaction) throws DataNotFoundException, NegativeDataException {
        appPageControllerSynchronizer.doStrategy(itemMapper, transactionMapper, transaction);
        transactionPageControllerSynchronizer.doStrategy(itemMapper, transactionMapper, transaction);
    }

    @Override
    public void doPostStrategies(ItemMapper itemMapper, TransactionMapper transactionMapper, int id) throws NegativeDataException {
        appPageControllerSynchronizer.doStrategy(itemMapper, transactionMapper, id);
        transactionPageControllerSynchronizer.doStrategy(itemMapper, transactionMapper, id);
    }
}
