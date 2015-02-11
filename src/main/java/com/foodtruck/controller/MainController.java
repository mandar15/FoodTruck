package com.foodtruck.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodtruck.model.LocatorForm;
import com.foodtruck.model.FoodTruckResponse;
import com.foodtruck.region.RegionProvider;
import com.foodtruck.region.RegionalFoodTruck;
import com.foodtruck.region.RegionalFoodTruckFactory;
import com.foodtruck.rest.RESTProxy;
import com.foodtruck.url.URLParams;

@Controller
@RequestMapping("/home")
public class MainController {

    private final RegionProvider regionProvider;
    private final RegionalFoodTruckFactory regionalFoodTruck;

    public MainController(RegionProvider regionProvider, RegionalFoodTruckFactory regionalFoodTruck) {
        this.regionProvider = regionProvider;
        this.regionalFoodTruck = regionalFoodTruck;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadHome(Model model) {
        setModel(model, "", new LocatorForm());
        return "home";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String findFoodTrucks(Model model, LocatorForm form) {
        String msg = "";
        try {
            /**
             * Get the RegionalFoodTruck instance given the region
             */
            RegionalFoodTruck foodTruck = regionalFoodTruck.getRegionalFoodTruck(form.getRegion());
            
            /**
             * Form the input for creating a URL
             */
            Map<URLParams, String> map = new HashMap<URLParams, String>();
            map.put(URLParams.LATTITUDE, form.getLattitude());
            map.put(URLParams.LONGITUDE, form.getLongitude());

            /**
             * Create the REST URL. 
             * Make a REST call to get the food truck info.
             * Deserialize the JSON string using JACKSON JSON parser. 
             */
            String resourceIdentifier = foodTruck.createURL(map);
            String jsonOutput = RESTProxy.getRESTResource(resourceIdentifier);
            ObjectMapper mapper = new ObjectMapper();
            List<FoodTruckResponse> result = mapper.readValue(jsonOutput, mapper.getTypeFactory()
                    .constructCollectionType(List.class, FoodTruckResponse.class));

            /**
             * Do additional filter operation on the result before returning the result.
             */
            result = foodTruck.filterResponse(result);
            
            if (result.size() == 0) {
                msg = "No results were found for your request.";
                setModel(model, msg, new LocatorForm());
                return "home";
            }

            model.addAttribute("locatorForm", form);
            model.addAttribute("foodTrucks", result.toArray());

            return "foodtrucks";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Sorry, there was a problem while processing your request. Please try again later";
        }

        setModel(model, msg, new LocatorForm());
        return "home";
    }

    private void setModel(Model model, String msg, LocatorForm form) {
        model.addAttribute("welcome", "Welcome to Food Truck Finder!");
        model.addAttribute("msg", msg);
        model.addAttribute("locatorForm", form);
        model.addAttribute("regions", regionProvider.getRegions());
    }
}
