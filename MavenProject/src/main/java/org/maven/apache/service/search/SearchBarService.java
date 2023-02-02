package org.maven.apache.service.search;

import com.jfoenix.controls.JFXButton;

import java.util.List;

public interface SearchBarService extends SearchService {

    void setSearchPrompts(List<String> sourceList, List<JFXButton> buttonList, String inputText, PromptSearchBarServiceHandler.ResultType resultType);

    void setSearchPrompts(List<JFXButton> buttonList, String inputText, PromptSearchBarServiceHandler.ResultType resultType);


}
