package com.foodtruck.rest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class RESTProxy {
    public static String getRESTResource(String resourceIdentifier) throws Exception {
        URL url = new URL(resourceIdentifier);
        URLConnection connection = url.openConnection();
        if (connection instanceof HttpURLConnection) {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            try {
                if (httpConnection.getResponseCode() != 200) {
                    throw new Exception("Failed REST call for resourceIdentifier: " + resourceIdentifier);
                }

                StringBuilder result = new StringBuilder();
                String nextLine;
                while ((nextLine = br.readLine()) != null) {
                    result.append(nextLine);
                }
                return result.toString();
            } finally {
                br.close();
                httpConnection.disconnect();
            }
        } else {
            throw new Exception("REST call returned a non HTTP Connection");
        }
    }
}
