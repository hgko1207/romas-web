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

/** 평년대비 저수율 정보 조회 */
function getRateInfo() {
	$.ajax({
		url: contextPath + "/home/rate",
		type: "GET",
		success: function(response) {
			$('#gapText').text(response.gap + "% ");
			$('#rateText').text(`(금일:${response.todayValue}%, 평년:${response.commonYearValue}%)`);
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
	getRateInfo();
	showDashboardInfo();
	
	setTableData(1);
	
	$('#areaBtn').click(function() {
		$('#search_condition').addClass('display-none');
		$('.rm-region-table-group').removeClass('table-group-top');
		setTableData(1);
	});
	
	$('#branchBtn').click(function() {
		$('#search_condition').addClass('display-none');
		$('.rm-region-table-group').removeClass('table-group-top');
		setTableData(2, '경기');
	});
	
	$('#facilityBtn').click(function() {
		$('#search_condition').removeClass('display-none');
		$('.rm-region-table-group').addClass('table-group-top');
	});
	
	$('#searchBtn').click(function() {
		var name = $('#nameText').val();
		setTableData(3, '', name);
	});
});

$('#reservoirTable tbody').on("click", "tr", function(){
	var tdArr = new Array();    // 배열 선언

	var tr = $(this);
	var td = tr.children();
	
	// 반복문을 이용해서 배열에 값을 담아 사용할 수 도 있다.
    td.each(function(i){
        tdArr.push(td.eq(i).text());
    });
    
    var regionalHead = td.eq(0).text();
    
    $('#areaBtn').removeClass('selected'); 
    $('#branchBtn').addClass('selected'); 
    
    setTableData(2, regionalHead);
});

/** 두번째 테이블 표출 */
function setTableData(type, regionalHead, facilityName) {
	$('#reservoirTable > tbody').empty();
	
	let param = new Object();
	param.type = type;
	param.regionalHead = regionalHead;
	param.facilityName = facilityName;
	
	$.ajax({
		url: contextPath + "/home/table",
		type: "POST",
		data: JSON.stringify(param),
		contentType: "application/json",
		success: function(response) {
			var html = '';
			
			$.each(response, function(i, data) {
				html += '<tr>';
				html += `<th scope="row">${data.name}</th>`;
				if (data.type == 'Attention') {
					html += `<td><span class="rm-region-status care">${data.waterLevel}</span></td>`;
					html += '<td></td>';
					html += '<td></td>';
					html += '<td></td>';
				} else if (data.type == 'Caution') {
					html += '<td></td>';
					html += `<td><span class="rm-region-status caution">${data.waterLevel}</span></td>`;
					html += '<td></td>';
					html += '<td></td>';
				} else if (data.type == 'Boudary') {
					html += '<td></td>';
					html += '<td></td>';
					html += `<td><span class="rm-region-status boudary">${data.waterLevel}</span></td>`;
					html += '<td></td>';
				} else if (data.type == 'Serious') {
					html += '<td></td>';
					html += '<td></td>';
					html += '<td></td>';
					html += `<td><span class="rm-region-status serious">${data.waterLevel}</span></td>`;
				}
				html += '</tr>';
			});
			
			$("#reservoirTable").append(html);
		}
	});
}
