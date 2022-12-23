package org.maven.apache.service.DateTransaction;

import org.maven.apache.dateTransaction.DateTransaction;

import java.sql.Timestamp;
import java.util.List;

public interface dateTransactionService {

    /**
     * would get all the information in the table
     * @return
     */
    List<DateTransaction> selectAll();


    /**
     * This method is for if the transation regrist wrong the data could be delete
     * @param id
     */
    void deleteById(int id);

    /**
     * insert the information to the table the input should be the calculation od the addunit and removeunit
     * @param number the calculation od the addunit and removeunit
     */
    void addTransaction(int number);

    /**
     *Used to change the number that if the AddUnit is wrong
     * @param number
     */
    void addUnitNumber(int number);

    /**
     * *Used to change the number that if the RemoveUnit is wrong
     * @param number
     */
    void removeUnitNumber(int number);

    /**
     * *Used to change the currentUnitvalue
     * @param number attention this number should be the calculation of the addUnit and removeUnit
     */
    void currentUnitNumber(int number);

    /**
     * return the information that happen on that day
     * @param date
     * @return
     */
    List<DateTransaction> askedDate(Timestamp date);
}
