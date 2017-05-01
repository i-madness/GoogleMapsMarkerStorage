package io.github.imadness.markersdb.controllers;

import io.github.imadness.markersdb.dao.MarkerMapper;
import io.github.imadness.markersdb.pojo.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * REST-контроллер приложения. Предоставляет API для всех CRUD-операций, связанных с маркерами Google Maps
 */
@Controller
public class MarkerController {

    @Autowired
    private MarkerMapper markerMapper;

    /**
     * Добавляет на главную страницу все маркеры из БД. Если маркеров в таблице нет, то будет добавлен т.н.
     * образец маркера, который может быть отредактирован или удалён пользоватлем.
     *
     * @return фактический адрес главной страницы
     */
    @RequestMapping("/")
    public String showIndex(ModelMap model) {
        List<Marker> allMarkers = markerMapper.getAllMarkers();
        if (allMarkers.isEmpty()) {
            Marker sampleMarker = new Marker(0, "Это образец маркера", 0d, 0d);
            allMarkers.add(sampleMarker);
            markerMapper.insertMarker(sampleMarker);
        }
        model.addAttribute("allMarkers", allMarkers);
        return "index";
    }

    /**
     * Ответ сервера в виде JSON-объекта, возвращает объект маркера по его ID
     *
     * @param id ID маркера в БД
     * @return полученный объект Marker
     * @see Marker
     */
    @RequestMapping("/get/{id}")
    @ResponseBody
    public Marker getMarkerById(@PathVariable int id) {
        return markerMapper.getMarkerById(id);
    }

    /**
     * Ответ сервера в виде JSON-объекта, возвращает объект маркера по его имени.
     *
     * @param name наименование маркера
     * @return полученный объект Marker
     * @see Marker
     */
    @RequestMapping("/getByName/{name}")
    @ResponseBody
    public Marker getMarkerByName(@PathVariable String name) {
        try {
            name = new String(name.getBytes("ISO8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        return markerMapper.getMarkerByName(name);
    }

    /**
     * Удаляет маркер из БД по его ID и перенаправляет пользователя на главную страницу
     *
     * @param id ID маркера в БД
     */
    @RequestMapping("/delete/{id}")
    public ResponseEntity<String> deleteMarker(@PathVariable int id) {
        markerMapper.deleteMarkerById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Получает маркер от клиента в формате JSON и сохраняет его в БД
     *
     * @param marker ID маркера в БД
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> addMarker(@RequestBody Marker marker) {
        markerMapper.insertMarker(marker);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Получает маркер от клиента в формате JSON и обновляет уже существующий экземпляр сущности маркера в БД
     *
     * @param marker ID маркера в БД
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<String> updateMarker(@RequestBody Marker marker) {
        markerMapper.updateMarker(marker);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}