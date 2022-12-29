package org.maven.apache.service.DateTransaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.maven.apache.dateTransaction.DateTransaction;
import org.maven.apache.mapper.DateTransactionMapper;

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
    public DateTransaction selectById(int id) {
        return DateTransactionMapper.selectById(id);
    }

    @Override
    public void addTransaction(DateTransaction dateTransaction) {
        DateTransactionMapper.addTransaction(dateTransaction);
    }

    @Override
    public void changeAddUnitNumber(DateTransaction dateTransaction) {
        DateTransactionMapper.changeAddUnitNumber(dateTransaction);
    }

    @Override
    public void changeRemoveUnitNumber(DateTransaction dateTransaction) {
        DateTransactionMapper.changeRemoveUnitNumber(dateTransaction);
    }

    @Override
    public void changeCurrentUnitNumber(DateTransaction dateTransaction) {
        DateTransactionMapper.changeCurrentUnitNumber(dateTransaction);
    }

    @Override
    public String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
        String timeNow = dateFormat.format(date);
        return timeNow;
    }

    public List<DateTransaction> askedDate(String dateWanted) {
        return DateTransactionMapper.askedDate(dateWanted + "%");
    }

    @Override
    public void IdGapInside() {
        DateTransactionMapper.IdGapInside();
    }


    @Override
    public List<DateTransaction> pageAskedNOOrder( int pageNumber,int pageSize) {


        int pageCal = pageNumberCal(pageNumber,pageSize);
        return DateTransactionMapper.pageAskedNOOrder(pageCal, pageSize);
    }

    @Override
    public List<DateTransaction> pageAskedDateAscend(int pageNumber, int pageSize) {
        int pageCal = pageNumberCal(pageNumber,pageSize);
        return DateTransactionMapper.pageAskedDateAscend(pageCal,pageSize);
    }

    @Override
    public List<DateTransaction> pageAskedDateDescend(int pageNumber, int pageSize) {
        int pageCal = pageNumberCal(pageNumber,pageSize);
        return DateTransactionMapper.pageAskedDateDescend(pageCal,pageSize);
    }

    @Override
    public List<DateTransaction> pageAskedAddUnitAscend(int pageNumber, int pageSize) {
        int pageCal = pageNumberCal(pageNumber,pageSize);
        return DateTransactionMapper.pageAskedAddUnitAscend(pageCal,pageSize);
    }

    @Override
    public List<DateTransaction> pageAskedAddUnitDescend(int pageNumber, int pageSize) {
        int pageCal = pageNumberCal(pageNumber,pageSize);

        return DateTransactionMapper.pageAskedAddUnitDescend(pageCal,pageSize);
    }

    @Override
    public List<DateTransaction> pageAskedRemoveUnitAscend(int pageNumber, int pageSize) {
        int pageCal = pageNumberCal(pageNumber,pageSize);
        return DateTransactionMapper.pageAskedRemoveUnitAscend(pageCal,pageSize);
    }

    @Override
    public List<DateTransaction> pageAskedRemoveUnitDescend(int pageNumber, int pageSize) {
        int pageCal = pageNumberCal(pageNumber,pageSize);
        return  DateTransactionMapper.pageAskedRemoveUnitDescend(pageCal,pageSize);
    }

    private int pageNumberCal(int pageNumber, int pageSize ){  if(pageSize<0||pageNumber<0){
        System.out.println("input should be greater than 0");
    }
        int pageCal = (pageNumber - 1) * pageSize;
        return pageCal;
    }

}