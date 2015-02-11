package com.foodtruck.model;

import java.util.Map;
/**
 * FoodTruck result object
 * 
 * @author mandarp
 *
 */
public class FoodTruckResponse {
    @Override
    public String toString() {
        return "SFFoodTruckResponse [address=" + address + ", fooditems=" + fooditems + ", block=" + block + ", lot="
                + lot + ", schedule=" + schedule + ", applicant=" + applicant + ", location=" + location + "]";
    }

    private String address;
    private String fooditems;
    private String block;
    private String lot;
    private String schedule;
    private String applicant;
    private String cnn;

    public String getCnn() {
        return cnn;
    }

    public void setCnn(String cnn) {
        this.cnn = cnn;
    }

    private Map<String, String> location;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFooditems() {
        return fooditems;
    }

    public void setFooditems(String fooditems) {
        this.fooditems = fooditems;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Map<String, String> getLocation() {
        return location;
    }

    public void setLocation(Map<String, String> location) {
        this.location = location;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

}
