package org.maven.apache.service.search;

import org.maven.apache.staff.Staff;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("staffSearchServiceHandlerAdapter")
public class StaffSearchServiceHandlerAdapter<R> implements AbstractSearchServiceAdapter<Staff, R> {

    @Override
    public List<Staff> doConvert(List<R> sourceList) {
        List<Staff> staffList = new ArrayList<>();
        sourceList.forEach(r -> staffList.add((Staff) r));
        return staffList;
    }

}
