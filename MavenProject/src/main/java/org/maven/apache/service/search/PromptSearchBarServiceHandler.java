package org.maven.apache.service.search;

import com.jfoenix.controls.JFXButton;
import jakarta.annotation.Resource;
import org.maven.apache.item.Item;
import org.maven.apache.staff.Staff;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("searchBarService")
public class PromptSearchBarServiceHandler<R> implements SearchBarService<R>{

    public enum ResultType {
        CARGO, STAFF
    }

    @Resource
    private AbstractPromptingSearchAdapter<Item> cargoPromptingSearchAdapter;

    @Resource
    private AbstractPromptingSearchAdapter<Staff> staffPromptingSearchAdapter;

    @Resource
    private AbstractSearchServiceAdapter<Item, R> itemSearchServiceHandlerAdapter;

    @Resource
    private AbstractSearchServiceAdapter<Staff, R> staffSearchServiceHandlerAdapter;


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
    public void setSearchPrompts(List<R> sourceList, List<JFXButton> buttonList, String inputText, ResultType resultType) {
        if(resultType == ResultType.CARGO){
            cargoPromptingSearchAdapter.invoke(itemSearchServiceHandlerAdapter.doConvert(sourceList), buttonList, inputText);
        } else if (resultType == ResultType.STAFF) {
            staffPromptingSearchAdapter.invoke(staffSearchServiceHandlerAdapter.doConvert(sourceList), buttonList, inputText);
        }
    }

    /**
     * This method is responsible for setting the results to the given buttonList based on the inputText
     * provided in the controllers.
     * This method does not ask user for a source list input, because type ALL is chosen as default.
     * <p>
     *     This method supports TWO strategies of search approach:
     *     1. search by the name of cargo
     *     2. search by the name of staff
     *     The selection of these two approaches is done through selecting the correct enum
     * </p>
     * @param buttonList The list of JFXButton to update the results upon.
     * @param inputText The String value input that the user type through the system
     * @param resultType the enum type CARGO, STAFF
     */
    @Override
    public void setSearchPrompts(List<JFXButton> buttonList, String inputText, ResultType resultType) {
        if(resultType == ResultType.CARGO){
            cargoPromptingSearchAdapter.invoke(buttonList, inputText);
        } else if (resultType == ResultType.STAFF) {
            staffPromptingSearchAdapter.invoke(buttonList, inputText);
        }
    }



}
