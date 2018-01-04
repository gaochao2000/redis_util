package com.x9710.common.redis.domain;

/**
 * 一个地理坐标对象
 *
 * @author 杨高超
 * @since 2017-12-26
 */
public class GeoCoordinate {
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
