package com.example.calendar;

import java.util.ArrayList;

class DataHolder {
    final ArrayList events = new ArrayList<>();

    private DataHolder() {}

    static DataHolder getInstance() {
        if( instance == null ) {
            instance = new DataHolder();
        }
        return instance;
    }

    private static DataHolder instance;
}
