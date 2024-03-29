package org.maven.apache.service.search;

import com.jfoenix.controls.JFXButton;

import java.util.List;

public sealed abstract class AbstractPromptingSearchAdapter<R> implements SearchAdapterService<R> permits CargoPromptingSearchAdapter, StaffPromptingSearchAdapter {

    public abstract List<String> doConvert(List<R> sourceList);

    public abstract void invoke(List<R> sourceList, List<JFXButton> buttonList, String inputText);

    public abstract void invoke(List<JFXButton> buttonList, String inputText);
}
