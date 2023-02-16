package org.maven.apache.service.search;

import com.jfoenix.controls.JFXButton;

import java.util.List;

public interface SearchFilteringService extends SearchService {

    List<String> doFilter(List<String> sourceList, String textInput);

    void proceed(List<String> sourceList, List<JFXButton> buttonList, String inputText);
}
