package org.maven.apache.service.search;

import org.maven.apache.staff.Staff;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("staffResultFilter")
public class StaffResultFilteringHandler implements SearchResultFilteringService<Staff>{

    @Override
    public List<Staff> doFilter(List<Staff> sourceList, String inputText, SearchResultServiceHandler.ResultType resultType) {
        List<Staff> resultList = new ArrayList<>();
        sourceList.forEach(staff -> {
            if(isContaining(staff.getStaffName(), inputText)){
                resultList.add(staff);
            }
        });
        return resultList;
    }
}
