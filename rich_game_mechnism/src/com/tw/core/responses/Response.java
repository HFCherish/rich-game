package com.tw.core.responses;

import com.tw.core.tools.AssistentPower;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Response {
    public static Response Yes = new Response();
    public static Response No = new Response();
    public static Response Quit = new Response();

    private AssistentPower item;
    public Response() {
    }

    private Response(AssistentPower item) {
        this.item = item;
    }

    public AssistentPower getItem() {
        return item;
    }

    public static Response GetAssistencePower(AssistentPower item) {
        return new Response(item);
    }
}
