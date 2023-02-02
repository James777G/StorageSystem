package org.maven.apache.service.search;

import com.jfoenix.controls.JFXButton;

import java.util.List;

public interface SearchService {


    /**
     * This method is responsible for setting the contents in the prompt search bar, as
     * well as the visibility of them.
     * @param resultList List of filtered result after search
     * @param buttonList list of button to display the results at
     */
    default void setButtonAndVisibility(List<String> resultList, List<JFXButton> buttonList){
        for(int i = 0; i < resultList.size(); i++){
            buttonList.get(i).setText(resultList.get(i));
        }
        for(int j = resultList.size(); j < buttonList.size(); j++){
            buttonList.get(j).setVisible(false);
        }
    }

}
