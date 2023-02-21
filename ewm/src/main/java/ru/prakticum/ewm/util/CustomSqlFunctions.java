package ru.prakticum.ewm.util;

import java.util.Objects;
import java.lang.Math;

public class CustomSqlFunctions {
    public static double getDistance(double lat1, double lon1, double lat2, double lon2)  {
        double dist = 0;
        double rad_lat1;
        double rad_lat2;
        double theta;
        double rad_theta;
        if (Objects.equals(lat1, lat2) && Objects.equals(lon1, lon2)) {
            return dist;
        }
        rad_lat1 = Math.PI * lat1 / 180;
        rad_lat2 = Math.PI * lat2 / 180;
        theta = lon1 - lon2;
        rad_theta = Math.PI * theta / 180;
        dist = Math.sin(rad_lat1) * Math.sin(rad_lat2) + Math.cos(rad_lat1) * Math.cos(rad_lat2) * Math.cos(rad_theta);

        if (dist > 1) {
            dist = 1;
        }

        dist = Math.acos(dist);
        dist = dist * 180 / Math.PI;
        dist = dist * 60 * 1.8524;

        return dist;
    }
}
