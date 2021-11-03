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

$(document).ready(function() {
	CurrentDate.init();
	getWeatherInfo();
	getNewsInfo();
	
	let map;
	
	setTimeout(function() {
    	map = GoogleMap.init('googleMap');
	}, 500);
});