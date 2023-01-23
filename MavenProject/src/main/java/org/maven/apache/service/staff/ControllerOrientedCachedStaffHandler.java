package org.maven.apache.service.staff;

import jakarta.annotation.Resource;
import lombok.Data;
import org.maven.apache.staff.Staff;
import org.maven.apache.utils.DataUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("staffService")
@Data
public class ControllerOrientedCachedStaffHandler implements CachedStaffService{

    @Resource
    @Qualifier("staffDaoService")
    private StaffDAOService staffDAOService;

    @Resource
    @Qualifier("staffDataManipulationService")
    private StaffDataManipulationService staffDataManipulationService;

    @Override
    public void updateAllCachedStaffData() {
        DataUtils.publicCachedStaffData = staffDAOService.selectAll();
        DataUtils.publicCachedActiveStaffData = staffDataManipulationService
                .sortByActiveStatus(DataUtils.publicCachedStaffData);
        DataUtils.publicCachedInactiveStaffData = staffDataManipulationService
                .sortByInactiveStatus(DataUtils.publicCachedStaffData);
    }

    @Override
    public void addNewStaff(Staff staff) {
        staffDAOService.add(staff);
        updateAllCachedStaffData();
    }

    @Override
    public void deleteStaffById(int id) {
        staffDAOService.deleteById(id);
        updateAllCachedStaffData();
    }

    @Override
    public void updateTransaction(Staff staff) {
        staffDAOService.updateStaff(staff);
        updateAllCachedStaffData();
    }
}
