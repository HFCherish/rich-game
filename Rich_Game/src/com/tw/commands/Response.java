package com.tw.commands;

/**
 * Created by pzzheng on 11/15/16.
 */
public class Response {
    private int number;
    public static Response Yes = new Response();
    public static Response No = new Response();
    public static Response Number(int number) {
        Response response = new Response();
        response.number = number;
        return response;
    }

    public int getNumber() {
        return number;
    }
}
