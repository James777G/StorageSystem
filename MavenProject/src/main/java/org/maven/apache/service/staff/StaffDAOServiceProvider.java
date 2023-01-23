package org.maven.apache.service.staff;

import jakarta.annotation.Resource;
import lombok.Data;
import org.maven.apache.mapper.StaffMapper;
import org.maven.apache.staff.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public Staff selectById(int id) {
        return staffMapper.selectById(id);
    }

    @Override
    @Deprecated
    public List<Staff> selectByStatus(String status) {
        return staffMapper.selectByStatus(status);
    }

    @Override
    public void updateStaff(Staff staff) {
        staffMapper.updateStaff(staff);
    }

    @Override
    public void add(Staff staff) {
        staffMapper.add(staff);
    }

    @Override
    public void deleteById(int id) {
        staffMapper.deleteById(id);
    }
}
