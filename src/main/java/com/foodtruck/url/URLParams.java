package com.foodtruck.url;

public enum URLParams {

    LATTITUDE("latitude"), LONGITUDE("longitude"), LOCATION("location"), WHERE("$where="), WITHIN_CIRCLE(
            "within_circle");

    private String val;

    URLParams(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}