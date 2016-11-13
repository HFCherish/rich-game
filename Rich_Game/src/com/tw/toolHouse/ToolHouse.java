package com.tw.toolHouse;

import com.tw.map.Place;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pzzheng on 11/12/16.
 */
public class ToolHouse implements Place {
    public static final int QUIT_INDEX = -1;
    private List<Tool> tools;

    public ToolHouse(Tool... tools) {
        this.tools = Arrays.asList(tools);
    }

    public Tool getToolById(int toolIndex_StartFrom1) {
        return tools.get(toolIndex_StartFrom1 - 1);
    }

    public boolean canAffordWith(int points) {
        return tools.stream().anyMatch(tool -> tool.getPoints() <= points);
    }

}
