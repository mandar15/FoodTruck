package com.foodtruck.region;

import java.util.List;
import java.util.Map;

import com.foodtruck.model.FoodTruckResponse;
import com.foodtruck.url.URLParams;

public abstract class RegionalFoodTruck {
    private final String region;
    
    private final String restURLPrefix;
    
    public RegionalFoodTruck(String region, String restURLPrefix) {
        this.region = region;
        this.restURLPrefix = restURLPrefix;
    }
    
    public String getRegion() {
        return region;
    }
    
    public String getRestURLPrefix() {
        return restURLPrefix;
    }
    
    public abstract String createURL(Map<URLParams, String> args) throws Exception;

    public List<FoodTruckResponse> filterResponse(List<FoodTruckResponse> list) {
        return list;
    }
}
