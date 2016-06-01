package com.markersdb.dao.implementation;

import com.markersdb.dao.MarkerMapper;
import com.markersdb.pojo.Marker;
import com.markersdb.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация интерфейса для доступа к данным в БД.
 * Предоставляет все необходимые приложению CRUD-операции
 */
@Service
public class MarkerDaoImpl implements MarkerMapper {

    /**
     * Получение списка всех маркеров из БД
     * @return список из маркеров
     */
    public List<Marker> getAllMarkers() {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            MarkerMapper markerMapper = sqlSession.getMapper(MarkerMapper.class);
            return markerMapper.getAllMarkers();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * Получение маркера из БД по его уникальному идентификатору
     * @param id ID маркера в БД
     * @return маркер, соответствующий запросу
     */
    public Marker getMarkerById(int id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            MarkerMapper markerMapper = sqlSession.getMapper(MarkerMapper.class);
            return markerMapper.getMarkerById(id);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * Получение маркера из БД по значению его наименования (является уникальным полем)
     * @param name
     * @return маркер, соответствующий запросу
     */
    public Marker getMarkerByName(String name) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            MarkerMapper markerMapper = sqlSession.getMapper(MarkerMapper.class);
            return markerMapper.getMarkerByName(name);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * Обновление уже существующей записи маркера в БД
     * @param marker полученный объект маркера для записи
     */
    public void updateMarker(Marker marker) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            MarkerMapper markerMapper = sqlSession.getMapper(MarkerMapper.class);
            markerMapper.updateMarker(marker);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * Добавление нового маркера в БД
     * @param marker полученный объект маркера для добавления
     */
    public void insertMarker(Marker marker) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            MarkerMapper markerMapper = sqlSession.getMapper(MarkerMapper.class);
            markerMapper.insertMarker(marker);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * Удаление маркера по его идентификатору
     * @param id ID маркера в БД
     */
    public void deleteMarkerById(int id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            MarkerMapper markerMapper = sqlSession.getMapper(MarkerMapper.class);
            markerMapper.deleteMarkerById(id);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}
