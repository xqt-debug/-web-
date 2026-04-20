package com.xqt.apprepositories;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AppList {
    private Long id;
    private ArrayList<App> apps;
}
