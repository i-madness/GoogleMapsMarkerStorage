package com.markersdb.dao;

import com.markersdb.pojo.Marker;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Интерфейс для доступа к данным в БД
 */
public interface MarkerMapper {

    @Select("SELECT * FROM marker")
    public List<Marker> getAllMarkers();

    @Select("SELECT * FROM marker WHERE id=#{id}")
    public Marker getMarkerById(int id);

    @Select("SELECT * FROM marker WHERE name=#{name}")
    public Marker getMarkerByName(String name);

    @Update("UPDATE marker SET name=#{marker.name}, latitude=#{marker.latitude}, longitude=#{marker.longitude} WHERE id=#{marker.id}")
    public void updateMarker(@Param("marker") Marker marker);

    @Insert("INSERT INTO marker (name,latitude,longitude) VALUES(#{marker.name}, #{marker.latitude}, #{marker.longitude})")
    public void insertMarker(@Param("marker") Marker marker);

    @Delete("DELETE FROM marker WHERE id=#{id}")
    public void deleteMarkerById(@Param("id") int id);

    /*@Delete("DELETE FROM marker IF EXISTS WHERE name=#{name}")
    public void deleteMarkerByName(@Param("name") String name);*/

}
