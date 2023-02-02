package org.maven.apache.service.search;

import com.jfoenix.controls.JFXButton;
import jakarta.annotation.Resource;
import org.maven.apache.item.Item;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class CargoPromptingSearchAdapter extends AbstractPromptingSearchAdapter<Item> {

    @Resource
    private SearchAdapterService<Item> itemAdapterConverterAdapter;

    @Resource
    @Qualifier("searchFilter")
    private SearchFilteringService searchFilter;

    @Override
    public List<String> doConvert(List<Item> sourceList) {
        return itemAdapterConverterAdapter.doConvert(sourceList);
    }

    @Override
    public List<String> doConvert() {
        return itemAdapterConverterAdapter.doConvert();
    }

    @Override
    public void invoke(List<Item> sourceList, List<JFXButton> buttonList, String inputText) {
        searchFilter.proceed(doConvert(sourceList), buttonList, inputText);
    }

    @Override
    public void invoke(List<JFXButton> buttonList, String inputText) {
        searchFilter.proceed(doConvert(), buttonList, inputText);
    }
}
