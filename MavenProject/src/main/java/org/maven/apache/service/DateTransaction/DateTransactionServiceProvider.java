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
}