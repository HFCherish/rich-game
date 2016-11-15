package com.tw.commands;

/**
 * Created by pzzheng on 11/15/16.
 */
public class ResponseType {
    private int number;
    public static ResponseType Yes = new ResponseType();
    public static ResponseType No = new ResponseType();
    public static ResponseType Number(int number) {
        ResponseType responseType = new ResponseType();
        responseType.number = number;
        return responseType;
    }

    public int getNumber() {
        return number;
    }
}
