package org.maven.apache.service.DateTransaction;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.maven.apache.dateTransaction.DateTransaction;

public interface DateTransactionService {

	/**
	 * would get all the information in the table
	 * 
	 * @return
	 */
	List<DateTransaction> selectAll();

	/**
	 * This method is for if the transation regrist wrong the data could be delete
	 * 
	 * @param id
	 */
	void deleteById(int id);

	/**
	 * the method get all the information of that id represents
	 * 
	 * @param id
	 * @return
	 */
	DateTransaction selectById(int id);

	void addTransaction(DateTransaction dateTransaction);

	void changeAddUnitNumber(DateTransaction dateTransaction);

	void changeRemoveUnitNumber(DateTransaction dateTransaction);

	void changeCurrentUnitNumber(DateTransaction dateTransaction);

	String getCurrentTime();

	/**
	 * Used to get the list of information based on date input
	 * 
	 * @param dateWanted the format of the dateWanted should be exactly like
	 *                   2022-12-24
	 * @return
	 */
	public List<DateTransaction> askedDate(String dateWanted);

	/**
	 * this method should be use after using delete by id
	 */
	void IdGapInside();

	List<DateTransaction> pageAskedNOOrder(  int pageNumber, int pageSize);

	List<DateTransaction> pageAskedDateAscend( int pageNumber , int pageSize);

	List<DateTransaction> pageAskedDateDescend( int pageNumber , int pageSize);

	List<DateTransaction> pageAskedAddUnitAscend(int pageNumber , int pageSize);

	List<DateTransaction> pageAskedAddUnitDescend(int pageNumber , int pageSize);

	List<DateTransaction> pageAskedRemoveUnitAscend(int pageNumber , int pageSize);
	List<DateTransaction> pageAskedRemoveUnitDescend(int pageNumber , int pageSize);

}