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
			{
				render: function(data, type, row, meta) {
		    		return round(row.currentWaterLevel);
		    	}
			},
			{
				render: function(data, type, row, meta) {
		    		return round(row.attentionWaterLevel);
		    	}
			},
			{
				render: function(data, type, row, meta) {
		    		return round(row.cautionWaterLevel);
		    	}
			},
			{
				render: function(data, type, row, meta) {
		    		return round(row.boudaryWaterLevel);
		    	}
			},
			{
				render: function(data, type, row, meta) {
		    		return round(row.seriousWaterLevel);
		    	}
			},
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

/**
 * 엑셀 파일 읽기
 * @returns
 */
function importData() {
	var facCode;
	
	var reader = new FileReader();
    reader.onload = function(){
        var fileData = reader.result;
        var wb = XLSX.read(fileData, {type : 'binary'});
        wb.SheetNames.forEach(function(sheetName){
	        var rowObj = XLSX.utils.sheet_to_json(wb.Sheets[sheetName]);
	        //console.log(JSON.stringify(rowObj));
	        var reservoirOperations = [];
	        
	        $.each(rowObj, function (i, data) {
	        	let operation = new Object();
	        	operation.facCode = facCode;
	        	operation.month = data['월'];
	        	operation.eml = data['순'];
	        	operation.attentionWaterLevel = data['관심'];
	        	operation.cautionWaterLevel = data['주의'];
	        	operation.boudaryWaterLevel = data['경계'];
	        	operation.seriousWaterLevel = data['심각'];
	        	
	        	reservoirOperations.push(operation);
			});
	        
	        $.ajax({
				url: contextPath + "/admin/reservoir",
				type: "PUT",
				data: JSON.stringify(reservoirOperations),
				contentType: "application/json",
				success: function(response) {
					swalInit.fire({title: "저수지 정보가 수정 되었습니다.", type: "success"});
		       	},
		        error: function(response) {
		        	swalInit.fire({title: "저수지 정보 수정을 실패하였습니다.", type: "error"});
		        }
			}); 
        })
    };
	
	let input = document.createElement('input');
	input.type = 'file';
	input.onchange = _ => {
		// you can use this method to get file and perform respective operations
        let files = Array.from(input.files);
        if (files[0].name) {
        	var name = files[0].name.trim().replace(/(.xlsx|.jpg|.jpeg|.png)$/,'');
        	var str = name.split('_');
        	facCode = str[str.length - 1];
        	if (facCode) {
        		reader.readAsBinaryString(input.files[0]);
        	} else {
        		swalInit.fire({title: "엑셀 이름에 시설 코드가 없습니다.", type: "error"});
        	}
        } else {
        	swalInit.fire({title: "엑셀 형식이 잘못되었습니다.", type: "error"});
        }
    };
    input.click();
}

$('[name="startDate"]').formatter({
    pattern: '{{9999}}-{{99}}-{{99}}'
});

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
	        	if (response.responseText) {
	        		swalInit.fire({title: response.responseText, type: "warning"});
				} else {
					swalInit.fire({title: "저수지 정보 등록을 실패하였습니다.", type: "error"});
				}
	        }
		});
	});
});