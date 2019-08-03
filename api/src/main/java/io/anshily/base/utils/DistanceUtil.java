package io.anshily.base.utils;

public class DistanceUtil {


    /**
     * 计算两点地理坐标之间的距离
     * @param  Decimal $longitude1 起点经度
     * @param  Decimal $latitude1  起点纬度
     * @param  Decimal $longitude2 终点经度
     * @param  Decimal $latitude2  终点纬度
     * @param  Int     $unit       单位 1:米 2:公里
     * @param  Int     $decimal    精度 保留小数位数
     * @return Decimal
     */
//    public  String getDistance(String longitude1, String latitude1, String longitude2, String latitude2, Integer unit, String decimal){
//        Double EARTH_RADIUS = 6370.996; // 地球半径系数
//        Double PI = 3.1415926;
//
//        Double radLat1 = Double.valueOf(latitude1 )* PI / 180.0;
//        Double radLat2 = Double.valueOf(latitude2 )* PI / 180.0;
//
//        Double radLng1 = Double.valueOf(longitude1) * PI / 180.0;
//        Double radLng2 = Double.valueOf(longitude2 )* PI /180.0;
//
//        Double a = radLat1 - radLat2;
//        Double b = radLng1 - radLng2;
//
//        Double distance = 2 * asin(sqrt(pow(sin($a/2),2) + cos($radLat1) * cos($radLat2) * pow(sin($b/2),2)));
//        distance = distance * EARTH_RADIUS * 1000;
//
//        if(unit==2){
//            distance = distance / 1000;
//        }
//
//        return round(distance, decimal);
//
//    }


    private final static double PI = 3.14159265358979323; // 圆周率
    private final static double R = 6371229; // 地球的半径

    public static double getDistance(double longt1, double lat1, double longt2,double lat2) {
        double x, y, distance;
        x = (longt2 - longt1) * PI * R
                * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
        y = (lat2 - lat1) * PI * R / 180;
        distance = Math.hypot(x, y);
        return distance;
    }
}
