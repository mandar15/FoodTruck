package com.foodtruck.region;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.foodtruck.constants.Constant;
import com.foodtruck.model.FoodTruckResponse;
import com.foodtruck.url.URLParams;
import com.foodtruck.util.Utils;

public class SFFoodTruck extends RegionalFoodTruck {

    /**
     * Limiting the search radius to 500m
     */
    private final int SEARCH_RADIUS = 500;

    public SFFoodTruck(String region, String restURLPrefix) {
        super(region, restURLPrefix);
    }

    /**
     * Creates the SODA REST URL for SF Data set. The constructed URL will search for data within the circular range of
     * radius SEARCH_RADIUS meters and the latitude and longitude from the input as the center.
     * Also, this URL will only search for Food trucks who have an "APPROVED" status.
     */
    @Override
    public String createURL(Map<URLParams, String> args) throws Exception {

        String lattitude = args.get(URLParams.LATTITUDE);
        String longitude = args.get(URLParams.LONGITUDE);
        System.out.println(lattitude + " " + longitude);

        Utils.validateNotNull(URLParams.LATTITUDE.getVal(), lattitude);
        Utils.validateNotNull(URLParams.LONGITUDE.getVal(), longitude);

        StringBuilder url = new StringBuilder(getRestURLPrefix());
        url.append("?");
        url.append(Constant.dataSFSelectURL);
        url.append("&");
        url.append(Constant.dataSFWhereURL);
        url.append("(");
        url.append(URLParams.LOCATION.getVal());
        url.append(",");
        url.append(lattitude);
        url.append(",");
        url.append(longitude);
        url.append(",");
        url.append(SEARCH_RADIUS);
        url.append(")");

        return url.toString();
    }

    /**
     * Function to remove the duplicate entries from the SF Food truck data set based on the "cnn" key
     */
    @Override
    public List<FoodTruckResponse> filterResponse(List<FoodTruckResponse> list) {

        Set<String> keys = new HashSet<String>();
        List<FoodTruckResponse> filteredResult = new ArrayList<FoodTruckResponse>(list.size());
        for (FoodTruckResponse listEntry : list) {
            String key = listEntry.getCnn();
            if (!keys.contains(key)) {
                keys.add(key);
                filteredResult.add(listEntry);
            }
        }

        return filteredResult;
    }

}
