package org.maven.apache.service.staff;

import jakarta.annotation.Resource;
import lombok.Data;
import org.maven.apache.mapper.StaffMapper;
import org.maven.apache.staff.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("staffDAOService")
@Data
public class StaffDAOServiceProvider implements StaffDAOService{

    @Autowired
    @SuppressWarnings("all")
    private StaffMapper staffMapper;

    @Override
    public List<Staff> selectAll() {
        return staffMapper.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Staff selectById(int id) {
        return staffMapper.selectById(id);
    }

    @Override
    @Deprecated
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Staff> selectByStatus(String status) {
        return staffMapper.selectByStatus(status);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateStaff(Staff staff) {
        staffMapper.updateStaff(staff);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(Staff staff) {
        staffMapper.add(staff);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(int id) {
        staffMapper.deleteById(id);
    }
}
