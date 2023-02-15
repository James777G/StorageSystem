package org.maven.apache.service.transaction;

import org.maven.apache.mapper.TransactionMapper;

public interface CachedTransactionDataListService {
    void updateAllLists(TransactionMapper transactionMapper, CachedManipulationService cachedManipulationService);
}
