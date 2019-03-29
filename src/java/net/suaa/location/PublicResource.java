package net.suaa.location;

public class PublicResource {

    /**
     * 高德地图请求秘钥
     */
    private static final String KEY = "0f111af513c49c69900bf918a004e4de";
     /**
     * 返回值类型
     */
     private static final String OUTPUT = "JSON";
     /**
     * 根据地名获取高德经纬度Api
     */
     private static final String GET_LNG_LAT_URL = "http://restapi.amap.com/v3/geocode/geo"; /**
     * 根据高德经纬度获取地名Api
     */
     private static final String GET_ADDRESS_URL = "http://restapi.amap.com/v3/geocode/regeo";

    /**
     * 根据ip定位
     */
    private static final String GET_LOCATION_IP_URL = "https://restapi.amap.com/v3/ip?";




}
