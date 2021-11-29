var DataTable = {
	ele: "#operationTable",
	table: null,
	option: {
		columns: [
			{ data: "month" },
			{ data: "eml" },
			{ data: "regionalHead" },
			{ data: "branch" },
			{ data: "facilityName" },
			{ data: "currentWaterLevel" },
			{ data: "attentionWaterLevel" },
			{ data: "cautionWaterLevel" },
			{ data: "boudaryWaterLevel" },
			{ data: "seriousWaterLevel" },
			{
		    	width: "100px",
		    	render: function(data, type, row, meta) {
		    		return '<button class="rm-inner-btn" onclick="importData()">수정</button>';
		    	}
		    }
		]
	},
	init: function() {
		this.table = Datatables.basic(this.ele, this.option);
	}
};

function importData() {
	let input = document.createElement('input');
	input.type = 'file';
	input.onchange = _ => {
		// you can use this method to get file and perform respective operations
        let files = Array.from(input.files);
        console.log(files);
    };
    input.click();
}

$(document).ready(function() {
	DataTable.init();
	
	var date = new Date();
	var day = date.getDate();
	$('#seltMonth').val(date.getMonth() + 1);
	
	var eml;
	if (day >= 1 && day <= 10) {
		eml = "1초순";
	} else if (day >= 11 && day <= 20) {
		eml = "2중순";
	} else if (day >= 21 && day <= 31) {
		eml = "3하순";
	}
	$('#seltEml').val(eml);
	
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
					$('#seltfacility').append($('<option>', {
					    value: "전체",
					    text: "- 전 체 -"
					}));
					
					$.each(response, function (i, item) {
						$('#seltfacility').append($('<option>', {
						    value: item.facilityName,
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
		var param = new Object();
		param.month = $('#seltMonth').val();
		param.eml = $('#seltEml').val();
		param.regionalHead = $('#seltHq').val();
		param.branch = $('#seltBranch').val();
		param.facilityName = $('#seltfacility').val();
		
		Datatables.rowsAdd(DataTable.table, contextPath + "/admin/search", param);
	});
	
	$("#registForm").submit(function(e) {
		e.preventDefault();
		var form = $(this);
		var url = form.attr('action');
		
		document.getElementById('setUp').style.display='none';
		
	    $.ajax({
	       	url: url,
			type: "POST",
	       	data: form.serializeObject(), // serializes the form's elements.
	       	success: function(response) {
	       		swalInit.fire({
	   				title: "저수지 정보가 등록 되었습니다.", 
	   				type: "success"
	   			}).then(function(e) {
	   				
	   			});
	       	},
	        error: function(response) {
	        	swalInit.fire({title: "저수지 정보 등록을 실패하였습니다.", type: "error"})
	        }
		});
	});
});