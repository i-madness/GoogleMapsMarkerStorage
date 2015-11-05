package com.markersdb.pojo;

/**
 * Модель маркера Google Maps, которая будет использована для сущности маркера в БД
 */
public class Marker {

    private Integer id;
    private String name;
    private Double latitude;
    private Double longitude;

    public Marker() { }

    public Marker(Integer id, String name, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double lat) {
        this.latitude = lat;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double lng) {
        this.longitude = lng;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s: %f;%f",
                this.name,
                this.latitude,
                this.longitude
        );
    }
}
