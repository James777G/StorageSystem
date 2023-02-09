package org.maven.apache.service.transaction;

import jakarta.annotation.Resource;
import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.exception.NegativeDataException;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.transaction.Transaction;
import org.springframework.stereotype.Service;

@Service("strategiesHandler")
public class GeneralTransactionStrategiesHandler implements GeneralTransactionStrategiesService{

    @Resource
    private AbstractTransactionStrategy warehouseStrategy;

    @Resource
    private AbstractTransactionStrategy cargoDataStrategy;

    @Override
    public void doStrategies(ItemMapper itemMapper, TransactionMapper transactionMapper, Transaction transaction) throws DataNotFoundException, NegativeDataException {
        cargoDataStrategy.doStrategy(itemMapper, transactionMapper, transaction);
        warehouseStrategy.doStrategy(itemMapper, transactionMapper, transaction);
    }

    @Override
    public void doStrategies(ItemMapper itemMapper, TransactionMapper transactionMapper, int id) throws NegativeDataException {
        cargoDataStrategy.doStrategy(itemMapper, transactionMapper, id);
        warehouseStrategy.doStrategy(itemMapper, transactionMapper, id);
    }
}
