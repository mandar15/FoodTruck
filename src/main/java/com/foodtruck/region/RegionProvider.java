package com.foodtruck.region;

import java.util.List;
/**
 * Region Provider will provide all the regions supported by the application.
 * @author mandarp
 */
public class RegionProvider {
    private final List<String> regions;
    
    public RegionProvider(List<String> regions) {
        this.regions = regions;
    }
    
    public List<String> getRegions() {
        return regions;
    }

}
