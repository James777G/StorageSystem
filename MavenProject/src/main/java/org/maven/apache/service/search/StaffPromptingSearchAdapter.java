package org.maven.apache.service.search;

import com.jfoenix.controls.JFXButton;
import org.maven.apache.staff.Staff;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class StaffPromptingSearchAdapter extends AbstractPromptingSearchAdapter<Staff> {

    @Override
    public List<String> doConvert(List<Staff> sourceList) {
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
