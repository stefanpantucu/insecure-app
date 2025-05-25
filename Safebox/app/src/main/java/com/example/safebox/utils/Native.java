package com.example.safebox.utils;

public class Native {

    public native boolean validateFlag(String jflag);

    static {
        System.loadLibrary("safebox");
    }
}
