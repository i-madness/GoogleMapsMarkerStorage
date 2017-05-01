<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="/static/js/bootstrap.js"></script>
    <!-- Основная часть клиентского приложения: -->
    <script src="/static/js/service.js"></script>
	<link href="/static/css/main.css" rel="stylesheet">
	<title>Google Maps Markers</title>
</head>
<body>

	<div class="container col-md-offset-2 col-md-8" id="main-container">

		<h3><span class="glyphicon glyphicon-map-marker"></span> Маркеры Google Maps</h3>

		<div class="col-md-4">
			<button id="add-btn" class="btn btn-default" data-toggle="modal" data-target="#add-marker-modal"><span class="glyphicon glyphicon-plus"></span> Добавить</button>
			<button class="btn btn-default" onclick="editMarker()"><span class="glyphicon glyphicon-edit"></span> Изменить</button>
			<button class="btn btn-default" onclick="deleteMarker()"><span class=" glyphicon glyphicon-trash"></span> Удалить</button>
            <div id="table-container">
                <table class="table table-hover table-bordered">
                    <thead>
                        <td>Маркер</td>
                        <td>Координаты</td>
                    </thead>
                    <c:forEach var="marker" items="${allMarkers}">
                        <tr class="marker-entry" data-id="${marker.id}">
                            <td><c:out value="${marker.name}"/></td>
                            <td><c:out value="${marker.latitude}"/> ; <c:out value="${marker.longitude}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
		</div>
		<div class="col-md-8" id="map"></div>
	</div>


	<!-- Модальное окно маркера -->
	<div class="modal fade" id="add-marker-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="modal-title">Добавление маркера</h4>
				</div>
				<div class="modal-body">
					<form action="/add" method="post" id="add-marker-form">
                        <div class="input-group">
                            <span class="input-group-addon">Введите координаты или название локации</span>
                            <input class="form-control" pattern="{3,}" id="gmaps-search" data-toggle="tooltip" data-placement="top" title="Введите широту и долготу или название места. Также маркер можно поставить кликом мыши по карте">
                            <a class="btn input-group-addon" onclick="submitAddress()">Поиск</a>
                        </div>
						<div class="input-group">
							<span class="input-group-addon">Название маркера</span>
							<input class="form-control" pattern="{3,}" id="marker-name" data-toggle="tooltip" data-placement="top" title="Введите уникальное название маркера">
						</div>
					</form>
					<div id="modal-map" style="height: 500px; width: 100%"></div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default" type="button" data-dismiss="modal">Закрыть</button>
					<input form="add-marker-form" type="submit" class="btn btn-primary" value="Сохранить">
				</div>
			</div>
		</div>
	</div>

    <script>

        // Глобальные переменные
        var map; 		   // основная карта
        var addModalMap;   // карта для модального окна добавления маркера
        var currentMarker; // текущий отображаемый маркер
        var newMarker;	   // текущий маркер для добавления
        var geocoder;	   // преобразователь адреса
        var formState = 'add'; // состояние формы, add / edit
        var lastReceivedID = ${allMarkers.get(allMarkers.size()-1).id} // ID последнего добавленного маркера

        // Инициализация карт на странице
        function initMaps() {
            var initialLatLng = {lat: ${allMarkers.get(0).latitude}, lng: ${allMarkers.get(0).longitude}};
            $('.marker-entry').first().addClass('selected-entry');
            map = new google.maps.Map(document.getElementById('map'), {
                zoom: 4,
                center: initialLatLng
            });
            addModalMap = new google.maps.Map(document.getElementById('modal-map'), {
                zoom: 4,
                center: initialLatLng
            });
            addModalMap.addListener('click', function(e) {
                if (newMarker!==undefined)
                    newMarker.setMap(null)
                newMarker = new google.maps.Marker({
                    position: e.latLng,
                    map: addModalMap
                });
                addModalMap.setCenter(newMarker.getPosition());
            });
            currentMarker = new google.maps.Marker({
                position: initialLatLng,
                map: map,
                title: '${allMarkers.get(0).name}'
            });
            geocoder = new google.maps.Geocoder;
        }

        // Обработка введённого адреса и получение маркера
        var submitAddress = function() {
            var address = $('#gmaps-search').val();
            geocoder.geocode( { 'address': address }, function(results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    addModalMap.setCenter(results[0].geometry.location);
                    if (newMarker!==undefined)
                        newMarker.setMap(null);
                    newMarker = new google.maps.Marker({ // маркер
                        position: results[0].geometry.location,
                        map: addModalMap
                    });
                }
            });
        };

        var editMarker = function(){
            formState = 'edit';
            $('#add-marker-form').attr('action','/update');
            $('#add-marker-form').attr('method','put');
            $('#modal-title').html('Изменение маркера');
            $('#marker-name').val($('.selected-entry').children('td').first().html());
            $('#add-marker-modal').modal();
        }

        // Удаление маркера
        var deleteMarker = function(){
            if ($('.selected-entry')!==undefined && confirm("Вы действительно хотите удалить маркер?")) {
                $.get('delete/'+$('.selected-entry').data('id').toString());
                $('.selected-entry').remove();
                currentMarker.setMap(null);
            }
        }

        // Отправка данных из модального окна
        $('#add-marker-form').submit(function(e) {
            if (newMarker.getMap()==map || $('#marker-name').val()=="")
                return false;
            e.preventDefault();
            $.ajax({
                type: $('#add-marker-form').attr('method'),
                url:  $('#add-marker-form').attr('action'),
                contentType: 'application/json',
                data: JSON.stringify({
                    id: (formState == 'add') ? null : $('.selected-entry').data('id'),
                    name: $('#marker-name').val(),
                    latitude: newMarker.getPosition().lat().toFixed(6),
                    longitude: newMarker.getPosition().lng().toFixed(6)
                }),
                success: function(){
                    currentMarker.setMap(null);
                    newMarker.setMap(map);
                    map.setCenter(newMarker.getPosition());
                    if (formState == 'add') {
                        lastReceivedID++;
                        $('.selected-entry').removeClass('selected-entry');
                        $('.table').append('<tr data-id="' + lastReceivedID + '" class="marker-entry selected-entry"><td>' + $('#marker-name').val() + '</td><td>' +
                                newMarker.getPosition().lat().toFixed(6) + ' ; ' + newMarker.getPosition().lng().toFixed(6) + '</td></tr>');
                    }
                    else {
                        $('.selected-entry').children().first().html($('#marker-name').val());
                        $('.selected-entry').children().eq(1).html(newMarker.getPosition().lat().toFixed(6)+" ; "+newMarker.getPosition().lng().toFixed(6));
                    }
                    $('#add-marker-modal').modal('hide');
                }
            });
        });

        // Отправка данных из окна по клавише Enter
        $('#add-marker-form').keypress(function(evt) {
            if(evt.which == 13)
                submitAddress();
        });

        // Подготовка карты при запуске модального окна
        $('#add-marker-modal').on("shown.bs.modal", function () {
            google.maps.event.trigger(addModalMap, 'resize');
            addModalMap.setCenter(currentMarker.position);
            newMarker = new google.maps.Marker({
                position: currentMarker.position,
                title: currentMarker.title,
                map: addModalMap
            })
        });

        // Возвращение состояния формы в "добавление нового элемента" по закрытию модального окна
        $('#add-marker-modal').on("hidden.bs.modal", function () {
            if (formState == 'edit') {
                formState = 'add';
                newMarker.setMap(null);
                $('#add-marker-form').attr('action','/add');
                $('#add-marker-form').attr('method','post');
                $('#modal-title').html('Добавление маркера');
                $('#marker-name').val("");
                $('#gmaps-search').val("");
            }
        });

        // Выделение маркера для размещения на карту и дальнейших действий (edit/delete)
        $('body').on('click','.marker-entry',function(){
            $('.selected-entry').removeClass('selected-entry');
            $(this).addClass('selected-entry');
            var entryID = $(this).data('id');
            currentMarker.setMap(null);
            if (newMarker!==undefined) newMarker.setMap(null); // в случае, если мы добавляли новый маркер на основную карту
            var storedMarker = $.get('/get/'+entryID,function(Marker){
                currentMarker = new google.maps.Marker({
                    position: {lat: Marker.latitude, lng: Marker.longitude},
                    title: Marker.name,
                    map: map,
                });
                map.setCenter(currentMarker.position)
            })
        });

        // Активация всплывающих подсказок для элементов input
        $('#gmaps-search').tooltip()
        $('#marker-name').tooltip()

    </script>
	<script async defer	src="https://maps.googleapis.com/maps/api/js?v=3.20&signed_in=true&callback=initMaps"></script>

</body>

</html>