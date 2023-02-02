package org.maven.apache.service.search;

import com.jfoenix.controls.JFXButton;
import jakarta.annotation.Resource;
import org.maven.apache.staff.Staff;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class StaffPromptingSearchAdapter extends AbstractPromptingSearchAdapter<Staff> {

    @Resource
    private SearchAdapterService<Staff> staffAdapterConverterAdapter;

    @Resource
    @Qualifier("searchFilter")
    private SearchFilteringService searchFilter;

    @Override
    public List<String> doConvert(List<Staff> sourceList) {
        return staffAdapterConverterAdapter.doConvert(sourceList);
    }

    @Override
    public List<String> doConvert() {
        return staffAdapterConverterAdapter.doConvert();
    }

    @Override
    public void invoke(List<Staff> sourceList, List<JFXButton> buttonList, String inputText) {
        searchFilter.proceed(doConvert(sourceList), buttonList, inputText);
    }

    @Override
    public void invoke(List<JFXButton> buttonList, String inputText) {
        searchFilter.proceed(doConvert(), buttonList, inputText);
    }
}
