package com.servicepulse.domain;

public class MonitoredService {

    private final String name;
    private final String url;

    public MonitoredService(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
