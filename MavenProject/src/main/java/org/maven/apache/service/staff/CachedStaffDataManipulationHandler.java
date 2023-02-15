package org.maven.apache.service.staff;

import org.maven.apache.staff.Staff;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("staffDataManipulationService")
public class CachedStaffDataManipulationHandler implements StaffDataManipulationService {

    @Override
    @Deprecated
    @SuppressWarnings("all")
    public List<Staff> sortByActiveStatus(List<Staff> staffList) {
        if (staffList.isEmpty()) {
            return null;
        }
        return staffList.stream()
                .filter(staff -> "ACTIVE".equals(staff.getStatus())).toList();
    }

    @Override
    @Deprecated
    @SuppressWarnings("all")
    public List<Staff> sortByInactiveStatus(List<Staff> staffList) {
        if (staffList.isEmpty()) {
            return null;
        }
        return staffList.stream()
                .filter(staff -> "INACTIVE".equals(staff.getStatus())).toList();
    }

    @Override
    public List<List<Staff>> getPagedCacheList(List<Staff> cachedList, int pageSize) {
        List<List<Staff>> pagedCachedList = new ArrayList<>();
        int pageNumber = cachedList.size() / pageSize;
        for (int i = 0; i < pageNumber; i++) {
            List<Staff> staffList = new ArrayList<>(4);
            for (int j = 0; j < pageSize; j++) {
                staffList.add(cachedList.get((i * pageSize) + j));
            }
            pagedCachedList.add(i, staffList);
        }
        if (cachedList.size() % pageSize != 0) {
            List<Staff> staffList = new ArrayList<>();
            for (int i = 0; i < cachedList.size() % pageSize; i++) {
                staffList.add(i, cachedList.get(cachedList.size() - cachedList.size() % pageSize + i));
            }
            pagedCachedList.add(staffList);
        }
        return pagedCachedList;
    }

    @Override
    public List<List<Staff>> getPagedCacheActiveList(List<Staff> staffList, int pageSize) {
        return getPagedCacheList(sortByActiveStatus(staffList), 7);
    }

    @Override
    public List<List<Staff>> getPagedCacheInactiveList(List<Staff> staffList, int pageSize) {
        return getPagedCacheList(sortByInactiveStatus(staffList), 7);
    }


}
