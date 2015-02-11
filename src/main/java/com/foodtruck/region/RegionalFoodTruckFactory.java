package com.foodtruck.region;

import java.util.List;
/**
 * FoodTruck factory responsible for vending the RegionalFoodTruck depending upon the region
 * 
 * @author mandarp
 *
 */
public class RegionalFoodTruckFactory {
    private final List<RegionalFoodTruck> list;
    
    public RegionalFoodTruckFactory(List<RegionalFoodTruck> list) {
        this.list = list;
    }
    
    public RegionalFoodTruck getRegionalFoodTruck(String region) throws Exception{
        
        for(RegionalFoodTruck foodTruck : list) {
            if(foodTruck.getRegion().equalsIgnoreCase(region)) {
                return foodTruck;
            }
        }
        
        throw new Exception("No URLCreator found for the region: " + region);
    }
}
