package org.maven.apache.service.search;

import com.jfoenix.controls.JFXButton;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class StaffPromptingSearchAdapter<Staff> extends AbstractPromptingSearchAdapter<Staff> {

    @Override
    public List<String> doConvert(List<Staff> sourceList) {
        return null;
    }

    @Override
    public void invoke(List<String> sourceList, List<JFXButton> buttonList, String inputText) {

    }
}
