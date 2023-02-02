package org.maven.apache.service.search;

import com.jfoenix.controls.JFXButton;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromptSearchBarServiceHandler implements SearchBarService{

    enum ResultType {
        CARGO, STAFF
    }

    @Resource
    private AbstractPromptingSearchAdapter cargoPromptingBarServiceHandler;

    @Resource
    private AbstractPromptingSearchAdapter staffPromptingSearchAdapter;


    /**
     * This method is responsible for setting the results to the given buttonList based on the inputText
     * provided in the controllers.
     * <p>
     *     This method supports TWO strategies of search approach:
     *     1. search by the name of cargo
     *     2. search by the name of staff
     *     The selection of these two approaches is done through selecting the correct enum
     * </p>
     * @param sourceList The cached list that the users want to search within.
     * @param buttonList The list of JFXButton to update the results upon.
     * @param inputText The String value input that the user type through the system
     * @param resultType the enum type CARGO, STAFF
     */
    @Override
    public void setSearchPrompts(List<String> sourceList, List<JFXButton> buttonList, String inputText, ResultType resultType) {

    }


}
