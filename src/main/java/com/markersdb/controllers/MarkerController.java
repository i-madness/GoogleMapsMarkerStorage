package com.markersdb.controllers;

import com.markersdb.dao.MarkerMapper;
import com.markersdb.pojo.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * REST-контроллер приложения
 */
@Controller
@EnableWebMvc
public class MarkerController {

    @Autowired
	private MarkerMapper markerMapper;

    /**
     * Добавляет на главную страницу все маркеры из БД. Если маркеров в таблице нет, то будет добавлен т.н.
     * образец маркера, который может быть отредактирован или удалён пользоватлем.
     * @return фактический адрес главной страницы
     */
	@RequestMapping("/")
	public String showIndex(ModelMap model) {
        List<Marker> allMarkers = markerMapper.getAllMarkers();
        if (allMarkers.size()==0) {
            Marker sampleMarker = new Marker(0, "Это образец маркера", 0d, 0d);
            allMarkers.add(sampleMarker);
            markerMapper.insertMarker(sampleMarker);
        }
		model.addAttribute("allMarkers",allMarkers);
		return "index";
	}

	/**
	 * Ответ сервера в виде JSON-объекта, возвращает объект маркера по его ID
	 * @param id ID маркера в БД
	 * @return полученный объект com.markersdb.pojo.Marker
	 * @see com.markersdb.pojo.Marker
	 */
 	@RequestMapping("/get/{id}")
	@ResponseBody
	public Marker getMarkerById(@PathVariable int id) {
		Marker m = markerMapper.getMarkerById(id);
		return m;
	}

    /**
     * Ответ сервера в виде JSON-объекта, возвращает объект маркера по его имени.
     * @param name наименование маркера
     * @return полученный объект com.markersdb.pojo.Marker
     * @see com.markersdb.pojo.Marker
     */
	@RequestMapping("/getByName/{name}")
	@ResponseBody
	public Marker getMarkerByName(@PathVariable String name) {
		try {
			name = new String(name.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) { return null; }
		Marker m = markerMapper.getMarkerByName(name);
		return m;
	}

    /**
     * Удаляет маркер из БД по его ID и перенаправляет пользователя на главную страницу
     * @param id ID маркера в БД
     */
	@RequestMapping("/delete/{id}")
	public String deleteMarker(@PathVariable int id) {
		markerMapper.deleteMarkerById(id);
		return "redirect:/";
	}

    /**
     * Получает маркер от клиента в формате JSON и сохраняет его в БД
     * @param marker ID маркера в БД
     */
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json")
	public String addMarker(@RequestBody Marker marker) {
		markerMapper.insertMarker(marker);
		return "redirect:/";
	}

    /**
     * Получает маркер от клиента в формате JSON и обновляет уже существующий экземпляр сущности маркера в БД
     * @param marker ID маркера в БД
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String updateMarker(@RequestBody Marker marker){
        markerMapper.updateMarker(marker);
        return "redirect:/";
    }

}