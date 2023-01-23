package org.maven.apache.service.staff;


import org.maven.apache.staff.Staff;

public interface CachedStaffService {

    /**
     * This method updates all the cached staff data currently stored
     * <p>
     *     1. This method is highly time-consuming, thus should be delegated to loading animation.
     *     2. This method should be called whenever there is a change to database, thus should be
     *     included in add, delete and update methods.
     * </p>
     */
    void updateAllCachedStaffData();


    /**
     * This method adds a new staff to the database.
     * <p>
     *     1. This method will call updateAllCachedStaffData to retrieve the latest data after
     *     the insertion of data is completed.
     *     2. This method is highly time-consuming, thus should be delegated to loading animation.
     *     3. This method might throw exceptions thus should be surrounded by try/catch for further
     *     operations (Some fields are left blank accidentally).
     * </p>
     * @param staff encapsulated staff to be added
     */
    void addNewStaff(Staff staff);


    /**
     * This method deletes an existing staff from the database.
     * <p>
     *     1. This method will call updateAllCachedStaffData to retrieve the latest data after
     *     the insertion of data is completed.
     *     2. This method is highly time-consuming, thus should be delegated to loading animation.
     * </p>
     * @param id Staff ID which is unique
     */
    void deleteStaffById(int id);

    /**
     * This method updates an existing staff in the database.
     * <p>
     *     1. This method will call updateAllCachedStaffData to retrieve the latest data after
     *     the insertion of data is completed.
     *     2. This method is highly time-consuming, thus should be delegated to loading animation.
     *     3. This method might throw exceptions thus should be surrounded by try/catch for further
     *     operations. (Some fields are left blank accidentally)
     * </p>
     * @param staff encapsulated staff object to be updated with desired attributes
     */
    void updateTransaction(Staff staff);
}
