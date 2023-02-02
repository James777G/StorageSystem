package org.maven.apache.service.search;

import com.jfoenix.controls.JFXButton;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("searchFilter")
public class SearchFilteringResultUpdatingHandler extends SearchFilteringResultListHandler{

    private void update(List<JFXButton> buttonList, List<String> resultList){
        super.setButtonAndVisibility(resultList, buttonList);
    }

    /**
     * This is the final method that is responsible for the main functionalities
     * @param sourceList source cached data
     * @param buttonList list of button from UI
     * @param inputText text input from user
     */
    public void proceed(List<String> sourceList, List<JFXButton> buttonList, String inputText){
        List<String> resultList = super.doFilter(sourceList, inputText);
        update(buttonList, resultList);
    }
}
