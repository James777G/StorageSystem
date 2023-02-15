package org.maven.apache.service.DateTransaction;

import org.maven.apache.dateTransaction.DateTransaction;
import org.maven.apache.mapper.DateTransactionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DateTransactionServiceProvider implements DateTransactionService {


    private DateTransactionMapper DateTransactionMapper;

    @SuppressWarnings("all")
    public DateTransactionMapper getItemMapper() {
        return DateTransactionMapper;
    }

    public void setDateTransactionMapper(DateTransactionMapper DateTransactionMapper) {
        this.DateTransactionMapper = DateTransactionMapper;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<DateTransaction> selectAll() {
        return DateTransactionMapper.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(int id) {
        DateTransactionMapper.deleteById(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public DateTransaction selectById(int id) {
        return DateTransactionMapper.selectById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addTransaction(DateTransaction dateTransaction) {
        DateTransactionMapper.addTransaction(dateTransaction);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void changeAddUnitNumber(DateTransaction dateTransaction) {
        DateTransactionMapper.changeAddUnitNumber(dateTransaction);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void changeRemoveUnitNumber(DateTransaction dateTransaction) {
        DateTransactionMapper.changeRemoveUnitNumber(dateTransaction);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void changeCurrentUnitNumber(DateTransaction dateTransaction) {
        DateTransactionMapper.changeCurrentUnitNumber(dateTransaction);
    }

    @Override
    public String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
        return dateFormat.format(date);
    }

    @SuppressWarnings("all")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<DateTransaction> askedDate(String dateWanted) {
        return DateTransactionMapper.askedDate(dateWanted + "%");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void IdGapInside() {
        DateTransactionMapper.IdGapInside();
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<DateTransaction> pageAskedNOOrder(int pageNumber, int pageSize) {

        int pageCal = pageNumberCal(pageNumber, pageSize);
        return DateTransactionMapper.pageAskedNOOrder(pageCal, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<DateTransaction> pageAskedDateAscend(int pageNumber, int pageSize) {
        int pageCal = pageNumberCal(pageNumber, pageSize);
        return DateTransactionMapper.pageAskedDateAscend(pageCal, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<DateTransaction> pageAskedDateDescend(int pageNumber, int pageSize) {
        int pageCal = pageNumberCal(pageNumber, pageSize);
        return DateTransactionMapper.pageAskedDateDescend(pageCal, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<DateTransaction> pageAskedAddUnitAscend(int pageNumber, int pageSize) {
        int pageCal = pageNumberCal(pageNumber, pageSize);
        return DateTransactionMapper.pageAskedAddUnitAscend(pageCal, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<DateTransaction> pageAskedAddUnitDescend(int pageNumber, int pageSize) {
        int pageCal = pageNumberCal(pageNumber, pageSize);

        return DateTransactionMapper.pageAskedAddUnitDescend(pageCal, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<DateTransaction> pageAskedRemoveUnitAscend(int pageNumber, int pageSize) {
        int pageCal = pageNumberCal(pageNumber, pageSize);
        return DateTransactionMapper.pageAskedRemoveUnitAscend(pageCal, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<DateTransaction> pageAskedRemoveUnitDescend(int pageNumber, int pageSize) {
        int pageCal = pageNumberCal(pageNumber, pageSize);
        return DateTransactionMapper.pageAskedRemoveUnitDescend(pageCal, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<DateTransaction> pageAskedCurrentUnitAscend(int pageNumber, int pageSize) {
        int pageCal = pageNumberCal(pageNumber, pageSize);
        return DateTransactionMapper.pageAskedCurrentUnitAscend(pageCal, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<DateTransaction> pageAskedCurrentUnitDescend(int pageNumber, int pageSize) {
        int pageCal = pageNumberCal(pageNumber, pageSize);
        return DateTransactionMapper.pageAskedCurrentUnitDescend(pageCal, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<DateTransaction> pageAskedDateAddUnitDescend() {
        return DateTransactionMapper.pageAskedDateAddUnitDescend();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<DateTransaction> pageAskedDateRemoveUnitDescend() {
        return DateTransactionMapper.pageAskedDateRemoveUnitDescend();
    }

    private int pageNumberCal(int pageNumber, int pageSize) {
        if (pageSize < 0 || pageNumber < 0) {
            System.out.println("input should be greater than 0");
        }
        return (pageNumber - 1) * pageSize;
    }

}