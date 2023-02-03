package org.maven.apache.service.search;

import org.maven.apache.staff.Staff;
import org.maven.apache.utils.StaffCachedUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public final class StaffAdapterConverterAdapter implements SearchAdapterService<Staff>{

    private static StaffCachedUtils.listType DEFAULT_SOURCE_TYPE = StaffCachedUtils.listType.ALL;

    public void customizeListType(StaffCachedUtils.listType listType){
        DEFAULT_SOURCE_TYPE = listType;
    }

    @Override
    public List<String> doConvert(List<Staff> sourceList) {
        return sourceList.stream().map(Staff::getStaffName).toList();
    }

    @Override
    public List<String> doConvert() {
        List<List<Staff>> lists = StaffCachedUtils.getLists(DEFAULT_SOURCE_TYPE);
        List<String> resultList = new ArrayList<>();
        lists.forEach(staffList -> staffList.forEach(staff -> resultList.add(staff.getStaffName())));
        return resultList;
    }
}
