moment.locale('ko');

function getEventTarget(e) {
    e = e || window.event;
    return e.target || e.srcElement; 
}

$(document).ready(function() {
	const dateFormat = 'YYYY-MM-DD';
	let startDate = moment().subtract(7, 'days').format(dateFormat);
	let endDate = moment().format(dateFormat);
	
	$('#inptPeriod').daterangepicker({ 
		autoApply: true,
        startDate: startDate,
        endDate: endDate,
        locale: {
            format: dateFormat
        },
        applyClass: 'btn-primary',
        cancelClass: 'btn-light'
    });
	
	$('#inptPeriod').on('apply.daterangepicker', function(ev, picker) {
		startDate = picker.startDate.format(dateFormat);
		endDate = picker.endDate.format(dateFormat);
		$(this).val(picker.startDate.format(dateFormat) + ' - ' + picker.endDate.format(dateFormat));
	});
	
	let searchType = '일별';
	
	const conditionUl = document.getElementById('condition_ul');
	conditionUl.onclick = function(event) {
    	const target = getEventTarget(event);
    	searchType = target.innerText;
    	
	    $("#condition_ul li button").removeClass('selected');
        $('#' + target.id).addClass('selected');
	};
	
	const periodUl = document.getElementById('period_ul');
	periodUl.onclick = function(event) {
    	const target = getEventTarget(event);
    	const text = target.innerText;
    	
//    	var endDate = $('#inptPeriodEnd').val();
//    	const fromDay = moment(endDate, dateFormat);
    	
    	let changeDate;
    	
	    if (text == '6개월') {
	    	changeDate = moment().subtract(6, 'month');
	    } else if (text == '1년') {
	    	changeDate = moment().subtract(1, 'year');
	    } else if (text == '2년') {
	    	changeDate = moment().subtract(2, 'year');
	    }
	    
	    $('#inptPeriod').data('daterangepicker').setStartDate(changeDate.format(dateFormat));
	    $('#inptPeriod').data('daterangepicker').setEndDate(moment().format(dateFormat));
	};
	
	$('#searchBtn').click(function() {
		let param = new Object();
		param.searchType = searchType;
		param.startDate = startDate;
		param.endDate = endDate;
		
		console.log(param);
		
		$.ajax({
			url: contextPath + "/facility/search",
			type: "POST",
			data: JSON.stringify(param),
			contentType: "application/json",
			success: function(response) {
				var arrayData = [];
				var legends = ['date', '', '저수율', '심각', '경계', '주의', '관심'];
				arrayData.push(legends);
				
				$.each(response, function(i, data) {
					var results = [data.resultDate, data.emptyLevel, data.waterLevel, data.seriousWaterLevel, 
						data.boudaryWaterLevel, data.cautionWaterLevel, data.attentionWaterLevel];
					arrayData.push(results);
				});
				
				GoogleComboChart.init('google-combo', arrayData);
			}
		}); 
	});
});