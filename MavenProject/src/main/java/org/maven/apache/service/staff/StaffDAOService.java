package org.maven.apache.service.staff;

import org.apache.ibatis.annotations.Param;
import org.maven.apache.staff.Staff;

import java.util.List;

public interface StaffDAOService {

    /**
     * This method returns all the data stored in the staff table
     *
     * @return a list of Staff
     */
    List<Staff> selectAll();

    /**
     * This method returns the single staff data with the unique id.
     *
     * @param id -> unique value
     * @return staff instance
     */
    Staff selectById(int id);

    /**
     * This method returns all the staff data with certain status
     *
     * @param status -> ENUM('ACTIVE', 'INACTIVE')
     * @return a list of Staff with the respective status
     */
    @Deprecated
    List<Staff> selectByStatus(@Param("status") String status);

    /**
     * This method updates the respective staff with the same ID value
     *
     * @param staff -> the staff instance containing the staff id to be updated
     */
    void updateStaff(Staff staff);

    /**
     * This method adds a new staff into the staff table.
     *
     * @param staff staff info to be added
     */
    void add(Staff staff);

    /**
     * This method deletes a staff from table with the following id
     *
     * @param id -> unique staffID
     */
    void deleteById(int id);

}
