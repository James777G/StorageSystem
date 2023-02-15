package org.maven.apache.service.DateTransaction;

import org.maven.apache.dateTransaction.DateTransaction;

import java.util.List;

public interface DateTransactionService {

    /**
     * would get all the information in the table
     *
     * @return
     */
    List<DateTransaction> selectAll();

    /**
     * This method is for if the transaction register wrong the data could be delete
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

    /**
     * a new transaction insert to the table
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
     * return a string that shows the current system time
     * the format is "yyyy-MM-dd :HH:mm:ss"
     *
     * @return
     */
    String getCurrentTime();

    /**
     * Used to get the list of information based on date input
     *
     * @param dateWanted the format of the dateWanted should be exactly like
     *                   2022-12-24
     * @return
     */
    List<DateTransaction> askedDate(String dateWanted);

    /**
     * this method should be use after using delete by id
     */
    void IdGapInside();

    /**
     * just separate the page with no order
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<DateTransaction> pageAskedNOOrder(int pageNumber, int pageSize);

    /**
     * based on date to separate the page by ascending order
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<DateTransaction> pageAskedDateAscend(int pageNumber, int pageSize);

    /**
     * based on date to separate the page by descending order
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<DateTransaction> pageAskedDateDescend(int pageNumber, int pageSize);

    /**
     * based on addUnit to separate the page by ascending order
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<DateTransaction> pageAskedAddUnitAscend(int pageNumber, int pageSize);

    /**
     * based on addUnit to separate the page by descending order
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<DateTransaction> pageAskedAddUnitDescend(int pageNumber, int pageSize);

    /**
     * based on RemoveUnit to separate the page by ascendingx order
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<DateTransaction> pageAskedRemoveUnitAscend(int pageNumber, int pageSize);

    /**
     * based on RemoveUnit to separate the page by descending order
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<DateTransaction> pageAskedRemoveUnitDescend(int pageNumber, int pageSize);

    /**
     * based on CurrentUnit to separate the page by ascending order
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<DateTransaction> pageAskedCurrentUnitAscend(int pageNumber, int pageSize);

    /**
     * based on CurrentUnit to separate the page by descending  order
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<DateTransaction> pageAskedCurrentUnitDescend(int pageNumber, int pageSize);

    /**
     * return a list merely contain AddUnit based on Time recently
     *
     * @return
     */
    List<DateTransaction> pageAskedDateAddUnitDescend();

    /**
     * return a list merely contain RemoveUnit based on Time recently
     *
     * @return
     */
    List<DateTransaction> pageAskedDateRemoveUnitDescend();

}