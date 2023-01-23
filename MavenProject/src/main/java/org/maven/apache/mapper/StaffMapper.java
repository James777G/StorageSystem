package org.maven.apache.mapper;

import org.apache.ibatis.annotations.Param;
import org.maven.apache.item.Item;
import org.maven.apache.staff.Staff;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StaffMapper {

    List<Staff> selectAll();
    Staff selectById(int id);

    List<Staff> selectByStatus(@Param("status") String status);

    void updateStaff(Staff staff);

    void add(Staff staff);

    void deleteById(int id);
}
