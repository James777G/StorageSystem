package org.maven.apache.service.staff;

import org.maven.apache.staff.Staff;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

@Service("staffDataManipulationService")
public class CachedStaffDataManipulationHandler implements StaffDataManipulationService{

    @Override
    public List<Staff> sortByActiveStatus(List<Staff> staffList) {
        if(staffList.isEmpty()){
            return null;
        }
        return staffList.stream()
                .filter(staff -> "ACTIVE".equals(staff.getStatus())).toList();
    }

    @Override
    public List<Staff> sortByInactiveStatus(List<Staff> staffList) {
        if(staffList.isEmpty()){
            return null;
        }
        return staffList.stream()
                .filter(staff -> "INACTIVE".equals(staff.getStatus())).toList();
    }
}
