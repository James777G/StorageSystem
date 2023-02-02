package org.maven.apache.service.search;

import com.jfoenix.controls.JFXButton;
import org.maven.apache.item.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class CargoPromptingSearchAdapter extends AbstractPromptingSearchAdapter<Item> {

    @Override
    public List<String> doConvert(List<Item> sourceList) {
        return null;
    }

    @Override
    public List<String> doConvert() {
        return null;
    }

    @Override
    public void invoke(List<String> sourceList, List<JFXButton> buttonList, String inputText) {

    }
}
