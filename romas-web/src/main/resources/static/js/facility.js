function getEventTarget(e) {
    e = e || window.event;
    return e.target || e.srcElement; 
}

$(document).ready(function() {
	const conditionUl = document.getElementById('condition_ul');
	conditionUl.onclick = function(event) {
    	const target = getEventTarget(event);
    	const text = target.innerText;
    	
	    $("#condition_ul li button").removeClass('selected');
        $('#' + target.id).addClass('selected');
	};
	
	const periodUl = document.getElementById('period_ul');
	periodUl.onclick = function(event) {
    	const target = getEventTarget(event);
    	const text = target.innerText;
    	
	    $("#period_ul li button").removeClass('selected');
        $('#' + target.id).addClass('selected');
	};
	
	$('#searchBtn').click(function() {
		
		let param = new Object();
		param.startDate = $('#inptPeriodStart').val();
		param.endDate = $('#inptPeriodEnd').val();
		
		$.ajax({
			url: contextPath + "/facility/search",
			type: "POST",
			data: JSON.stringify(param),
			contentType: "application/json",
			success: function(data) {
				
			}
		}); 
	});
});