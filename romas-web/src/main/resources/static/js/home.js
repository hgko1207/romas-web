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

var tableType = 1;

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
		setTableData(2, '100');
	});
	
	$('#facilityBtn').click(function() {
		$('#search_condition').removeClass('display-none');
		$('.rm-region-table-group').addClass('table-group-top');
		$('#reservoirTable > tbody').empty();
		$('#all_level').addClass('display-none');
		tableType = 3;
	});
	
	$('#searchBtn').click(function() {
		var name = $('#nameText').val();
		setTableData(4, '', name);
	});
	
	$('#mapBtn').click(function() {
		$('#mapHolder').removeClass('display-none');
		$('#vMap').addClass('display-none');
	});
});

$('#reservoirTable tbody').on("click", "tr", function(){
	var tr = $(this);
	var td = tr.children();
	
    if (tableType == 1) {
    	var regionalHead = td.eq(6).text();
    	if (regionalHead != '전국') {
    		$('#areaBtn').removeClass('selected'); 
            $('#branchBtn').addClass('selected'); 
            
        	setTableData(2, regionalHead);
		}
    } else if (tableType == 2) {
    	$('#branchBtn').removeClass('selected'); 
        $('#facilityBtn').addClass('selected');
        $('#search_condition').removeClass('display-none');
		$('.rm-region-table-group').addClass('table-group-top');
        
        var branch = td.eq(0).text();
        var regionalHead = td.eq(6).text();
        setTableData(3, regionalHead, '', branch);
    }
});

/** 두번째 테이블 표출 */
function setTableData(type, regionalHead, facilityName, branch) {
	tableType = type;
	
	$('#reservoirTable > tbody').empty();
	
	let param = new Object();
	param.type = type;
	param.regionalHead = regionalHead;
	param.facilityName = facilityName;
	param.branch = branch;
	
	if (type === 1 || type === 2) {
		$('#all_level').removeClass('display-none');
	} else {
		$('#all_level').addClass('display-none');
	}
	
	$.ajax({
		url: contextPath + "/home/table",
		type: "POST",
		data: JSON.stringify(param),
		contentType: "application/json",
		success: function(response) {
			var html = '';
			
			const allCount = response.map(data => data.allCount).reduce((accumulator, curr) => {
				return accumulator + curr;
			});
			
			if (type === 1) {
				html += '<tr>';
				html += `<th scope="row">전국</th>`;
				html += `<td><span class="">${allCount}</span></td>`;
				html += `<td></td>`;
				html += `<td></td>`;
				html += `<td></td>`;
				html += `<td></td>`;
				html += `<td class="display-none">전국</td>`;
				html += '</tr>';
			}
			
			$.each(response, function(i, data) {
				html += '<tr>';
				html += `<th scope="row">${data.name}</th>`;
				if (type === 1) {
					html += `<td><span class="">${data.allCount}</span></td>`;
					html += `<td><span class="rm-region-status care">${data.attentionCount}</span></td>`;
					html += `<td><span class="rm-region-status caution">${data.cautionCount}</span></td>`;
					html += `<td><span class="rm-region-status boudary">${data.boundaryCount}</span></td>`;
					html += `<td><span class="rm-region-status serious">${data.seriousCount}</span></td>`;
					html += `<td class="display-none">${data.country}</td>`;
				} else if (type === 2) {
					html += `<td><span class="">${data.allCount}</span></td>`;
					html += `<td><span class="rm-region-status care">${data.attentionCount}</span></td>`;
					html += `<td><span class="rm-region-status caution">${data.cautionCount}</span></td>`;
					html += `<td><span class="rm-region-status boudary">${data.boundaryCount}</span></td>`;
					html += `<td><span class="rm-region-status serious">${data.seriousCount}</span></td>`;
					html += `<td class="display-none">${data.regionalHead}</td>`;
				} else if (type === 3 || type === 4) {
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
				}

				html += '</tr>';
			});
			
			$("#reservoirTable").append(html);
		}
	});
}
