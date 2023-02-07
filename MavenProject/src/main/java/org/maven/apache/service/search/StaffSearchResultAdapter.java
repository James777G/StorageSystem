package org.maven.apache.service.search;

import jakarta.annotation.Resource;
import org.maven.apache.staff.Staff;
import org.maven.apache.transaction.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service("staffResultAdapter")
public final class StaffSearchResultAdapter<R> extends AbstractSearchResultAdapter<Staff, R> {

    @Resource
    @Qualifier("staffResultFilter")
    private SearchResultFilteringService<Staff> staffResultFilter;

    @Override
    public List<Staff> doConvert(List<R> sourceList) {
        return sourceList.stream()
                .map(r -> (Staff) r).toList();
    }

    @Override
    public List<R> postConvert(List<Staff> loadedList) {
        return loadedList.stream()
                .map(staff -> (R) staff).toList();
    }

    @Override
    public List<R> invoke(List<R> sourceList, String inputText, SearchResultServiceHandler.ResultType resultType) {
        return postConvert(staffResultFilter.doFilter(doConvert(sourceList), inputText, resultType));
    }
}
