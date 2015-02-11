package com.foodtruck.constants;

public class Constant {
    public static final String dataSFSelectURL = "$select=location,address,block,lot,schedule,fooditems,applicant,cnn";

    public static final String dataSFWhereURL = "status=APPROVED&$where=within_circle";
}
