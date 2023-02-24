package ru.prakticum.ewm.util;

import java.util.Objects;

public class CustomSqlFunctions {
    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double dist = 0;
        double radLat1;
        double radLat2;
        double theta;
        double radTheta;
        if (Objects.equals(lat1, lat2) && Objects.equals(lon1, lon2)) {
            return dist;
        }
        radLat1 = Math.PI * lat1 / 180;
        radLat2 = Math.PI * lat2 / 180;
        theta = lon1 - lon2;
        radTheta = Math.PI * theta / 180;
        dist = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radTheta);

        if (dist > 1) {
            dist = 1;
        }

        dist = Math.acos(dist);
        dist = dist * 180 / Math.PI;
        dist = dist * 60 * 1.8524;

        return dist;
    }
}
