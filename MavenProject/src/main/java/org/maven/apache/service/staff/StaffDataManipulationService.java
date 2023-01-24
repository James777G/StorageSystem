package org.maven.apache.service.staff;

import org.maven.apache.staff.Staff;

import java.util.List;

public interface StaffDataManipulationService {


    /**
     * This method returns all the staff with a status 'ACTIVE'
     * @param staffList a list of staff to be sorted within
     * @return a list of staff with status 'ACTIVE'
     */
    List<Staff> sortByActiveStatus(List<Staff> staffList);


    /**
     * This method returns all the staff with a status 'INACTIVE'
     * @param staffList a list of staff to be sorted within
     * @return a list of staff with status 'INACTIVE'
     */
    List<Staff> sortByInactiveStatus(List<Staff> staffList);


    /**
     * This method separated all the data into list of list with each list containing a maximum
     * of seven elements.
     * @param staffList the presorted list
     * @return a sorted list with each index representing a page.
     */
    List<List<Staff>> getPagedCacheList(List<Staff> staffList, int pageSize);


    /**
     * This method separated all the active data into list of list, and each list contains
     * a maximum of seven elements.
     * @param staffList the presorted list
     * @param pageSize maximum number of data to be presented in one page
     * @return a sorted list with each index representing a page.
     */
    List<List<Staff>> getPagedCacheActiveList(List<Staff> staffList, int pageSize);


    /**
     * This method separated all the inactive data into list of list, and each list contains
     * a maximum of seven elements.
     * @param staffList the presorted list
     * @param pageSize maximum number of data to be presented in one page
     * @return a sorted list with each index representing a page.
     */
    List<List<Staff>> getPagedCacheInactiveList(List<Staff> staffList, int pageSize);

}
