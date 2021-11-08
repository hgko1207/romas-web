const CurrentDate = function() {
	const getTime = function() {
		const today = new Date();
	    const hour = today.getHours();
	    const minutes = today.getMinutes();
	    const seconds = today.getSeconds();
		
		const time = `${hour<10 ? `0${hour}`:hour}:${minutes<10 ? `0${minutes}`:minutes}:${seconds<10 ? `0${seconds}`:seconds}`;
		$('#current_time').text(time);
	};
	
	const getDate = function() {
		const today = new Date();
		let year = today.getFullYear(); // 년도
		let month = today.getMonth() + 1;  // 월
		let date = today.getDate();  // 날짜
		let day = today.getDay();  // 요일
		
		var week = ['일', '월', '화', '수', '목', '금', '토'];
		var dayOfWeek = week[day];
		
		$('#current_date').text(`${year}년 ${month}월 ${date}일`);
		$('#current_day').text(`${dayOfWeek}요일`);
	};
	
	const refreshTime = function() {
	    setInterval(getTime, 1000);
	};
	
	return {
        init: function() {
        	getTime();
        	getDate();
        	refreshTime();
        }
    }
}();

function getWeatherInfo() {
	$.ajax({
		url: contextPath + "/api/weather",
		type: "GET",
		success: function(data) {
			$('#wthrWrn_title').text(data.title);
			$('#wthrWrn_date').text(data.date);
       	}
	});
}

function getNewsInfo() {
	$.ajax({
		url: contextPath + "/api/news",
		type: "GET",
		success: function(response) {
			$.each(response, function(i, data) {
				const news = `<article>
					<h2 class="rm-news-title">${data.title}</h2>
					<p class="rm-news-txt">${data.description}</p>
					<span class="rm-news-date">${data.date}</span>
				</article>`;
				
				$('#news_data').append(news);
			});
       	}
	});
}

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
        	const content = hover.get('name');
        	
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

$(document).ready(function() {
	CurrentDate.init();
	getWeatherInfo();
	getNewsInfo();
	
	let map;
	
	setTimeout(function() {
    	map = VWorldMap.init('vMap');
    	VWorldMap.addMarker(map, 127.102, 36.29, 'test');
	}, 500);
	
	
	$.ajax({
		url: contextPath + "/home/chart",
		type: "GET",
		success: function(response) {
			EchartsBarChart.init("rateChart", response.barChartSeries);
		}
	});
});