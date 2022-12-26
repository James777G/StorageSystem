package org.maven.apache.mapper;

import org.maven.apache.dateTransaction.DateTransaction;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
@Component
public interface DateTransactionMapper {

    /**
     * would get all the information in the table
     * @return
     */
    List<DateTransaction> selectAll();

    /**
     * the method get all the information of that id represents
     * @param id
     * @return
     */
    DateTransaction selectById(int id);

    /**
     * This method is for if the transation regrist wrong the data could be delete
     * @param id
     */
    void deleteById(int id);

    void  IdGapInside();

    void addTransaction(DateTransaction dateTransaction);


    void changeAddUnitNumber(DateTransaction dateTransaction);



    void changeRemoveUnitNumber(DateTransaction dateTransaction);


    void changeCurrentUnitNumber(DateTransaction dateTransaction);

    /**
     * Used to get the list of information based on date input
     * @param dateWanted the format of the dateWanted should be exactly like 2022-12-24
     * @return
     */
     List<DateTransaction> askedDate(String dateWanted);

}
