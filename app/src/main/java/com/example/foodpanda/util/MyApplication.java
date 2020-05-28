package com.example.foodpanda.util;

import androidx.fragment.app.Fragment;

public class MyApplication {
    private static MyApplication myApplication;
    public static Fragment fragment;

    public static MyApplication getInstance()
    {
        return myApplication;
    }
}
