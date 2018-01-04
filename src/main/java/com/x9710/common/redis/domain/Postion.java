package com.x9710.common.redis.domain;

/**
 * 一个位置对象
 *
 * @author 杨高超
 * @since 2017-12-26
 */
public class Postion {
    /**
     * 位置的标识
     */
    private String id;
    /**
     * 位置的类型
     */
    private String type;
    /**
     * 位置的坐标点
     */
    private GeoCoordinate coordinate;

    /**
     * 距离
     */
    private Double distinct;

    public Postion(String id, String type, Double longitude, Double latitude) {
        this.id = id;
        this.type = type;
        coordinate = new GeoCoordinate();
        coordinate.setLongitude(longitude);
        coordinate.setLatitude(latitude);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeoCoordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(GeoCoordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Double getDistinct() {
        return distinct;
    }

    public void setDistinct(Double distinct) {
        this.distinct = distinct;
    }
}
