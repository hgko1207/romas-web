/** 현재시간 표출 */
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

/** 기상특보 정보 조회 */
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

/** 뉴스정보 조회 */
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

/** 우측 대쉬보드 정보 조회 */
function getDashboardInfo(name) {
	$.ajax({
		url: contextPath + "/home/dashboard",
		type: "GET",
		data: {'name': name},
		success: function(response) {
			let status = '';
			let reservoir = 'reservoir-equal';
			
			if (response.upDown == 'UP') {
				status = 'status-up';
				reservoir = 'reservoir-up';
			} else if (response.upDown == 'DOWN') {
				status = 'status-down';
				reservoir = 'reservoir-down';
			}
			
			if (name == '전국') {
				$('#all_reservoir_status').removeClass('status-up status-down');
				$('#all_reservoir_rate').removeClass('reservoir-up reservoir-down reservoir-equal');
				$('#all_reservoir_gap').removeClass('reservoir-up reservoir-down reservoir-equal');
				
				$('#all_reservoir_status').addClass(status);
				$('#all_reservoir_rate').addClass(reservoir);
				$('#all_reservoir_gap').addClass(reservoir);
				$('#all_reservoir_rate').html(response.value);
				$('#all_reservoir_gap').html(response.gap + "%");
			} else {
				$('#reservoir_status').removeClass('status-up status-down');
				$('#reservoir_rate').removeClass('reservoir-up reservoir-down reservoir-equal');
				$('#reservoir_gap').removeClass('reservoir-up reservoir-down reservoir-equal');
				
				$('#area_name').html(name);
				$('#reservoir_status').addClass(status);
				$('#reservoir_rate').addClass(reservoir);
				$('#reservoir_gap').addClass(reservoir);
				$('#reservoir_rate').html(response.value);
				$('#reservoir_gap').html(response.gap + "%");
			}
		}
	});
}

/** 차트정보 조회 */
function getChartInfo() {
	$.ajax({
		url: contextPath + "/home/chart",
		type: "GET",
		success: function(response) {
			EchartsBarChart.init("rateChart", response.barChartSeries);
		}
	});
}

/** 대쉬보드 정보 표출 */
function showDashboardInfo() {
	var index = 1;
	
	getDashboardInfo('전국');
	getDashboardInfo('경기');
	
	var areas = ['경기', '강원', '충북', '충남', '전북', '전남', '경북', '경남', '제주'];
	setInterval(() => {
		if (index == 9) {
			index = 0;
		}
		
		getDashboardInfo(areas[index]);
		
		index++;
	}, 1000 * 10);
}

$(document).ready(function() {
	CurrentDate.init();
	getWeatherInfo();
	getNewsInfo();
	getChartInfo();
	showDashboardInfo();
	
	let map;
	
	setTimeout(function() {
    	map = VWorldMap.init('vMap');
    	if (map) {
    		$.ajax({
    			url: contextPath + "/home/reservoir",
    			type: "GET",
    			success: function(response) {
    				$.each(response, function(i, data) {
    					VWorldMap.addMarker(map, data.longitude, data.latitude, data.facCode, data.level);
    				});
    			}
    		});
    	}
	}, 500);
	
	$('#areaBtn').click(function() {
		$('#search_condition').addClass('display-none');
		$('.rm-region-table-group').removeClass('table-group-top');
	});
	
	$('#branchBtn').click(function() {
		$('#search_condition').addClass('display-none');
		$('.rm-region-table-group').removeClass('table-group-top');
	});
	
	$('#facilityBtn').click(function() {
		$('#search_condition').removeClass('display-none');
		$('.rm-region-table-group').addClass('table-group-top');
	});
	
	/*$('#reservoirTable > tbody').empty();
	var html = '';
	html += '<tr>';
	html += '<th scope="row">경기</th>';
	html += '<td><span class="rm-region-status care">673</span></td>';
	html += '<td></td>';
	html += '<td></td>';
	html += '<td></td>';
	html += '</tr>';
	$("#reservoirTable").append(html);*/
});

/** 지역, 지사, 시설 탭*/
/*var targetLink = document.querySelectorAll('.rm-region-btn-wrap a');
var tabContent = document.querySelectorAll('.rm-region-table-group > table');

for (var i = 0; i < targetLink.length; i++) {
	targetLink[i].addEventListener('click', function(e){
		e.preventDefault();
		var orgTarget = e.target.getAttribute('href');
		
		var tabTarget = orgTarget.replace('#', '');
		
		for(var x=0; x < tabContent.length; x++) {
			tabContent[x].style.display = 'none';
		}
		
		document.getElementById(tabTarget).style.display = '';
		
		for (var y =0; y < targetLink.length; y++){
			targetLink[y].classList.remove('selected');
			e.target.classList.add('selected');
		}
	})
}
for(var x=0; x < tabContent.length; x++) {
	tabContent[x].style.display = 'none';
}
document.getElementById('tabs-1').style.display = '';*/

