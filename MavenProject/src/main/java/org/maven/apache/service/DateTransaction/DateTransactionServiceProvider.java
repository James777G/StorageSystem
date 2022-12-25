package org.maven.apache.service.DateTransaction;

import org.maven.apache.dateTransaction.DateTransaction;
import org.maven.apache.mapper.DateTransactionMapper;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class DateTransactionServiceProvider implements DateTransactionService {

    private DateTransactionMapper DateTransactionMapper;

    public DateTransactionMapper getItemMapper() {
        return DateTransactionMapper;
    }

    public void setDateTransactionMapper(DateTransactionMapper DateTransactionMapper) {
        this.DateTransactionMapper = DateTransactionMapper;
    }

    @Override
    public List<DateTransaction> selectAll() {
        return DateTransactionMapper.selectAll();
    }

    @Override
    public void deleteById(int id) {
        DateTransactionMapper.deleteById(id);
    }

    @Override
    public void addTransaction(DateTransaction dateTransaction) {
        DateTransactionMapper.addTransaction(dateTransaction);
    }

    @Override
    public void addUnitNumber(int updateNumber,int id) {
    DateTransactionMapper.addUnitNumber(updateNumber,id);
    }

    @Override
    public void removeUnitNumber(int number,int id) {
DateTransactionMapper.removeUnitNumber(number,id);
    }

    @Override
    public void currentUnitNumber(int number,int id) {
        DateTransactionMapper.currentUnitNumber(number,id);
    }

    public List<DateTransaction> askedDate(String dateWanted){
        return DateTransactionMapper.askedDate(dateWanted);
    }
}
