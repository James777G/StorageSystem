package org.maven.apache.service.search;

import org.maven.apache.item.Item;
import org.maven.apache.staff.Staff;
import org.maven.apache.utils.CargoCachedUtils;
import org.maven.apache.utils.StaffCachedUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class StaffAdapterConverterAdapter implements SearchAdapterService<Staff>{

    private static final StaffCachedUtils.listType DEFAULT_SOURCE_TYPE = StaffCachedUtils.listType.ALL;

    @Override
    public List<String> doConvert(List<Staff> sourceList) {
        return sourceList.stream().map(Staff::getStaffName).toList();
    }

    @Override
    public List<String> doConvert() {
        List<List<Staff>> lists = StaffCachedUtils.getLists(StaffCachedUtils.listType.ALL);
        List<String> resultList = new ArrayList<>();
        lists.forEach(staffList -> staffList.forEach(staff -> resultList.add(staff.getStaffName())));
        return resultList;
    }
}
