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
}
