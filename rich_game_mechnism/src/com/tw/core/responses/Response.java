package com.tw.core.responses;

import com.tw.core.tools.Tool;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Response {
    public static Response Yes = new Response();
    public static Response No = new Response();

    private Tool tool;
    public Response() {
    }

    private Response(Tool tool) {
        this.tool = tool;
    }

    public Tool getTool() {
        return tool;
    }

    public static Response GetTool(Tool tool) {
        return new Response(tool);
    }
}
