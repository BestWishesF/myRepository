package cn.hotol.app.common.util;

import java.math.BigDecimal;

/**
 * Created by LuBin
 * Date 2016-12-29.
 */
public class LocationDto {

    private BigDecimal longitude;//经度
    private BigDecimal latitude;//纬度

    public LocationDto() {
    }

    public LocationDto(BigDecimal longitude, BigDecimal latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setLat(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public void setLng(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
