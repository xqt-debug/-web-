package com.xqt.apprepositories;

import lombok.Data;

@Data
public class App {
    private final Long id;
    private final String name;
    private final String description;
    private final String url;
}
