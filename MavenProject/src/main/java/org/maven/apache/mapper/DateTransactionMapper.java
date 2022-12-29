package org.maven.apache.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.maven.apache.dateTransaction.DateTransaction;
import org.springframework.stereotype.Component;

@Component
public interface DateTransactionMapper {

	/**
	 * would get all the information in the table
	 * 
	 * @return
	 */
	List<DateTransaction> selectAll();

	/**
	 * the method get all the information of that id represents
	 * 
	 * @param id
	 * @return
	 */
	DateTransaction selectById(int id);

	/**
	 * This method is for if the transation regrist wrong the data could be delete
	 * 
	 * @param id
	 */
	void deleteById(int id);

	/**
	 * if id is not continuous
	 */
	void IdGapInside();

	/**
	 * a new add
	 * 
	 * @param dateTransaction
	 */
	void addTransaction(DateTransaction dateTransaction);

	/**
	 * if the insert Add number is wrong
	 * 
	 * @param dateTransaction
	 */
	void changeAddUnitNumber(DateTransaction dateTransaction);

	/**
	 * if the remove number is wrong
	 * 
	 * @param dateTransaction
	 */

	void changeRemoveUnitNumber(DateTransaction dateTransaction);

	/**
	 * if the insert current number is wrong
	 * 
	 * @param dateTransaction
	 */

	void changeCurrentUnitNumber(DateTransaction dateTransaction);

	/**
	 * Used to get the list of information based on date input
	 * 
	 * @param dateWanted the format of the dateWanted should be exactly like
	 *                   2022-12-24
	 * @return
	 */
	List<DateTransaction> askedDate(String dateWanted);



	List<DateTransaction> pageAskedNOOrder(@Param("pageNumberCal") int pageNumberCal ,@Param("pageSize") int pageSize);

	List<DateTransaction> pageAskedDateAscend(@Param("pageNumberCal") int pageNumberCal ,@Param("pageSize") int pageSize);

	List<DateTransaction> pageAskedDateDescend(@Param("pageNumberCal") int pageNumberCal ,@Param("pageSize") int pageSize);

	List<DateTransaction> pageAskedAddUnitAscend(@Param("pageNumberCal") int pageNumberCal ,@Param("pageSize") int pageSize);

	List<DateTransaction> pageAskedAddUnitDescend(@Param("pageNumberCal") int pageNumberCal ,@Param("pageSize") int pageSize);

	List<DateTransaction> pageAskedRemoveUnitAscend(@Param("pageNumberCal") int pageNumberCal ,@Param("pageSize") int pageSize);

	List<DateTransaction> pageAskedRemoveUnitDescend(@Param("pageNumberCal") int pageNumberCal ,@Param("pageSize") int pageSize);

}