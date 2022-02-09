moment.locale('ko');

function getEventTarget(e) {
    e = e || window.event;
    return e.target || e.srcElement; 
}

function searchChart(param) {
	$.ajax({
		url: contextPath + "/facility/chart/search",
		type: "POST",
		data: JSON.stringify(param),
		contentType: "application/json",
		success: function(response) {
			if (response.length > 0) {
				var arrayData = [];
				var legends = ['date', '', '저수율', '심각', '경계', '주의', '관심'];
				arrayData.push(legends);
				
				$.each(response, function(i, data) {
					var results = [data.resultDate, data.emptyLevel, data.rate, data.seriousWaterLevel, 
						data.boudaryWaterLevel, data.cautionWaterLevel, data.attentionWaterLevel];
					arrayData.push(results);
				});
				
				GoogleComboChart.init('google-combo', arrayData);
			}
		}
	}); 
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
	
	let param = new Object();
	param.facCode = $("#facCodeText").text();
	param.startDate = startDate;
	param.endDate = endDate;
	
	searchChart(param);
	
	$("#seltHq").change(function() {
		$("#seltBranch").empty();
		
		$.ajax({
			url: contextPath + "/admin/branch/list",
			type: "GET",
			data: {"regionalHead": $(this).val()},
			success: function(response) {
				if (response.length > 0) {
					$.each(response, function (i, item) {
						$('#seltBranch').append($('<option>', {
						    value: item,
						    text: item
						}));
					});
				} else {
					$('#seltBranch').append($('<option>', {
					    value: 0,
					    text: "데이터 없음"
					}));
				}
	       	}
		}); 
	});
	
	$("#seltBranch").change(function() {
		$("#seltfacility").empty();
		
		$.ajax({
			url: contextPath + "/admin/facility/list",
			type: "GET",
			data: {"branch": $(this).val()},
			success: function(response) {
				if (response.length > 0) {
					$.each(response, function (i, item) {
						$('#seltfacility').append($('<option>', {
						    value: item.facCode,
						    text: item.facilityName
						}));
					});
				} else {
					$('#seltfacility').append($('<option>', {
					    value: 0,
					    text: "데이터 없음"
					}));
				}
	       	}
		}); 
	});
	
	$('#searchBtn').click(function() {
		let param = new Object();
		param.facCode = $("#seltfacility").val();
		param.startDate = startDate;
		param.endDate = endDate;
		
		$.ajax({
			url: contextPath + "/facility/search",
			type: "POST",
			data: JSON.stringify(param),
			contentType: "application/json",
			success: function(response) {
				const reservoir = response.reservoir;
				const reservoirMgmt = response.reservoirMgmt;
				
				if (reservoir != null) {
					$("#facCodeText").text(reservoir.facCode);
					$("#facilityNameText").text(reservoir.facilityName);
					$("#waterClassText").text(reservoir.waterClass);
					$("#totalStroageCapacityText").text(reservoir.totalStroageCapacity + " ㎡ ");
					$("#enableStorageCapacityText").text(reservoir.enableStorageCapacity + " ㎡ ");
					$("#watershedAreaText").text(reservoir.watershedArea + " (만㎥)");
					$("#beneAreaText").text(reservoir.beneArea + " (만㎥)");
					$("#adminText").text(reservoir.facilityName);
				}
				
				if (reservoirMgmt != null) {
					$("#addressText").text(reservoirMgmt.address);
					$("#classificationText").text(reservoirMgmt.classification);
					$("#lengthText").text(reservoirMgmt.length + " m");
					$("#heightText").text(reservoirMgmt.height + " m");
					$("#pullAreaText").text(reservoirMgmt.pullArea + " (만㎥)");
					$("#mgmtAdressText").text(reservoirMgmt.mgmtAdress);
					$("#startDateText").text(moment(reservoirMgmt.startDate).format('YYYY-MM-DD'));
					$("#completionDateText").text(moment(reservoirMgmt.completionDate).format('YYYY-MM-DD'));
				}
				
				$("#rateText").text(response.rate + "%");
			}
		});
		
		searchChart(param);
	});
});