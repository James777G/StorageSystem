package org.maven.apache.service.search;

import org.maven.apache.exception.Warning;

import java.util.ArrayList;
import java.util.List;

public abstract class SearchFilteringResultListHandler implements SearchFilteringService{


    /**
     * This method filters out a list of string containing the text input
     * @param sourceList list from cached data after conversion into string form
     * @param textInput text input from user
     * @return a list of string that are similar to the input
     */
    @Warning(Warning.WarningType.DEBUG)
    @Override
    public List<String> doFilter(List<String> sourceList, String textInput) {
        List<String> resultList = new ArrayList<>();
        sourceList.forEach(s -> {
            if(isContaining(s, textInput)){
                resultList.add(s);
            }
        });
        return resultList;
    }



    /**
     * This method justifies whether the text input is similar to the source value.
     * @param sourceValue Single element in source list
     * @param textInput the string input from user
     * @return a boolean indicating if is similar.
     */
    @Warning(Warning.WarningType.DEBUG)
    private boolean isContaining(String sourceValue, String textInput){
        try{
            if(sourceValue.contains(textInput)){
                return true;
            } else if(sourceValue.toLowerCase().contains(textInput.toLowerCase())){
                return true;
            }
        } catch(Exception e){
            return false;
        }
        return false;
    }
}
