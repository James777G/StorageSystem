package org.maven.apache.service.staff;

import jakarta.annotation.Resource;
import lombok.Data;
import org.maven.apache.staff.Staff;
import org.maven.apache.utils.StaffCachedUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service("staffService")
@Data
@Transactional
public class ControllerOrientedCachedStaffHandler implements CachedStaffService {

    @Resource
    @Qualifier("staffDaoService")
    private StaffDAOService staffDAOService;

    @Resource
    @Qualifier("staffDataManipulationService")
    private StaffDataManipulationService staffDataManipulationService;

    @Resource
    private GeneralStaffStrategies staffStrategies;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void updateAllCachedStaffData() {
        List<Staff> staffList = staffDAOService.selectAll();
        StaffCachedUtils.putLists(StaffCachedUtils.listType.ALL, staffDataManipulationService
                .getPagedCacheList(staffList, 7));
        StaffCachedUtils.putLists(StaffCachedUtils.listType.ACTIVE, staffDataManipulationService
                .getPagedCacheActiveList(staffList, 7));
        StaffCachedUtils.putLists(StaffCachedUtils.listType.INACTIVE, staffDataManipulationService
                .getPagedCacheInactiveList(staffList, 7));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewStaff(Staff staff) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        staffDAOService.add(staff);
        updateAllCachedStaffData();
        staffStrategies.doStrategies();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteStaffById(int id) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        staffDAOService.deleteById(id);
        updateAllCachedStaffData();
        staffStrategies.doStrategies();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateStaff(Staff staff) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        staffDAOService.updateStaff(staff);
        updateAllCachedStaffData();
        staffStrategies.doStrategies();
    }
}
