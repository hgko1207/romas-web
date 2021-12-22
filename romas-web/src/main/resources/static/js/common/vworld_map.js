var VWorldMap = {
	map: null,
	init: function(id) {
		var mapLayer; //맵 레이어 선언 : 지도 그림(타일) 설정
		var mapView; //맵 뷰 선언 : 보여지는 지도 부분 설정
		
		mapLayer = new ol.layer.Tile({ //타일 생성
			title : 'Vworld Map', //이름
			visible : true, //보여짐 여부
			type : 'base', //지도 종류(일반) ---(야간(midnight), 위성(satellite) 등)
			source : new ol.source.XYZ({ //vworld api 사용
				url : 'http://api.vworld.kr/req/wmts/1.0.0/45A1571F-5735-359F-9136-BCD6CF4CC339/Satellite/{z}/{y}/{x}.jpeg'
			})
		});
		
		mapView = new ol.View({ //뷰 생성
			projection : 'EPSG:3857', //좌표계 설정 (EPSG:3857은 구글에서 사용하는 좌표계) 
			center : new ol.geom.Point([ 127, 36.5 ]) //처음 중앙에 보여질 경도, 위도 
					.transform('EPSG:4326', 'EPSG:3857') //GPS 좌표계 -> 구글 좌표계
					.getCoordinates(), //포인트의 좌표를 리턴함
			zoom : 10 //초기지도 zoom의 정도값
		});
		
		this.map = new ol.Map({ //맵 생성	
			target : 'vMap', //html 요소 id 값
			layers : [mapLayer], //레이어
			view : mapView //뷰
		});
		
		return this.map;
	},
	addMarker: function(map, lon, lat, id, name, level) { //경도 위도 이름값(마커들을 구분하기위해)
		
		// 마커 feature 설정
		var feature = new ol.Feature({
	        geometry: new ol.geom.Point(ol.proj.fromLonLat([lon, lat])), //경도 위도에 포인트 설정
	        name: name,
	        id: id
//	        text: new ol.style.Text({
//	            text: "Test text",
//	            scale: 1.2,
//	            fill: new ol.style.Fill({
//	              color: "#fff"
//	            }),
//	            stroke: new ol.style.Stroke({
//	              color: "0",
//	              width: 3
//	            })
//	        });
	    });
		
		let markerSrc;
		if (level == 0) 
			markerSrc = 'images/status_region_care.png';
		else if (level == 1) 
			markerSrc = 'images/status_region_caution.png';
		else if (level == 2) 
			markerSrc = 'images/status_region_alert.png';
		else if (level == 3) 
			markerSrc = 'images/status_region_serious.png';
		
		// 마커 스타일 설정
	    var markerStyle = new ol.style.Style({
	        image: new ol.style.Icon({ //마커 이미지
	        	opacity: 1, //투명도 1=100% 
	        	scale: 0.7, //크기 1=100%
	            src: markerSrc
	        }),
	        zindex: 10
	    });
	    
	    // 마커 레이어에 들어갈 소스 생성
	    var markerSource = new ol.source.Vector({
	        features: [feature] //feature의 집합
	    });
	    
	    // 마커 레이어 생성
	    var markerLayer = new ol.layer.Vector({
	        source: markerSource, //마커 feacture들
	        style: markerStyle //마커 스타일
	    });
	    
	    // 지도에 마커가 그려진 레이어 추가
	    map.addLayer(markerLayer);
	},
	centerMap: function(lon, lat) {
	    this.map.getView().setCenter(ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857'));
	    this.map.getView().setZoom(10);
	},
	getMap: function() {
		return this.map;
	}
};

function showPopup(map) {
	var hover = null; //마우스 이벤트에 사용될 변수
	var container = document.getElementById('popup'); //팝업 컨테이너
	var popupContent = document.getElementById('popup-content'); //팝업 내용
	
	//맵 오버레이 선언 : 지도 위에 팝업 옵션을 사용할 때
	var mapOverlay = new ol.Overlay(({ element: container })); //Overlay 생성, 요소는 컨테이너
	
	map.on('pointermove', function(evt) { //마우스 올렸을 때
		var coordinate = evt.coordinate; //마우스가 올려진 좌표값
		
		//마커가 있는 곳에 마우스가 올려지면 커서의 스타일을 pointer로 설정
        map.getTargetElement().style.cursor = map.hasFeatureAtPixel(evt.pixel) ? 'pointer': '';
        
        //마우스를 다른 곳으로 옮길 때를 위해 스위치역할
        if (hover != null) {
        	hover = null;
        }
        
        //마우스가 올려진 곳의 마커를 가져와 hover에 저장
        map.forEachFeatureAtPixel(evt.pixel, function(f) {
            hover = f;
            return true;
        });
        
        //마커가 있을 경우
        if (hover){
        	const content = `<div class='map-content'>${hover.get('name')}</div>`;
        	
        	//popup-content 부분에 content를 넣어줌
        	popupContent.innerHTML = content;

        	map.addOverlay(mapOverlay);
        	
            //오버레이의 좌표를 정해줌
            mapOverlay.setPosition(coordinate);
        } else {
        	popupContent.innerHTML = '';
    	}
	});
}

function initWorldMap(lon, lat) {
	setTimeout(function() {
		if (VWorldMap.getMap() == null) {
			var map = VWorldMap.init('vMap');
			if (map) {
				$.ajax({
					url: contextPath + "/home/reservoir",
					type: "GET",
					success: function(response) {
						$.each(response, function(i, data) {
							var name = `<div>지역: ${data.branch}</div><div>시설: ${data.facilityName}</div>`;
							VWorldMap.addMarker(map, data.longitude, data.latitude, data.facCode, name, data.level);
						});
					}
				});
				
				showPopup(map);
				
				let clicker = null;
				map.on('click', function(evt) {
					map.forEachFeatureAtPixel(evt.pixel, function(f) {
						clicker = f;
						return true;
					});
					
					if (clicker != null) {
						var id = clicker.get('id');
						location.href = contextPath + "/facility/" + id;
						clicker = null;
					}
				});
			}
		} else {
			VWorldMap.centerMap(lon, lat);
		}
	}, 500);
}

