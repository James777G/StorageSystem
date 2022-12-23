package org.maven.apache.service.DateTransaction;

import org.maven.apache.dateTransaction.DateTransaction;
import org.maven.apache.mapper.DateTransactionMapper;
import org.maven.apache.mapper.ItemMapper;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class dateTransactionServiceProvider implements dateTransactionService{

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
    public void addTransaction(int number) {
        DateTransactionMapper.addTransaction(number);
    }

    @Override
    public void addUnitNumber(int number) {
    DateTransactionMapper.addUnitNumber(number);
    }

    @Override
    public void removeUnitNumber(int number) {
DateTransactionMapper.removeUnitNumber(number);
    }

    @Override
    public void currentUnitNumber(int number) {
        DateTransactionMapper.currentUnitNumber(number);
    }

    @Override
    public List<DateTransaction> askedDate(Timestamp date) {
        List<DateTransaction> allTable = selectAll();
        List<DateTransaction> askedDate= new LinkedList<>();
        for(DateTransaction eachDate:allTable){
            if(eachDate.getRecordTime().getDate()==date.getDate()){
                DateTransactionMapper.selectById(eachDate.getItemID());
                askedDate.add(eachDate);
            }
        }

        return askedDate ;
    }
}
