var DataTable = {
	ele: "#operationTable",
	table: null,
	option: {
		columns: [
			{ data: "date" },
			{ data: "regionalHead" },
			{ data: "branch" },
			{ data: "facCode" },
			{ data: "facilityName" },
			{ data: "currentWaterLevel" },
			{ data: "attentionWaterLevel" },
			{ data: "cautionWaterLevel" },
			{ data: "boudaryWaterLevel" },
			{ data: "seriusWaterLevel" },
			{
		    	width: "100px",
		    	render: function(data, type, row, meta) {
    				return '<button class="rm-inner-btn">수정</button>';
		    	}
		    }
		]
	},
	init: function() {
		this.table = Datatables.basic(this.ele, this.option);
	}
};

$(document).ready(function() {
	DataTable.init();
	
	$('#searchBtn').click(function() {
		var param = new Object();
		
		Datatables.rowsAdd(DataTable.table, contextPath + "/admin/search", param);
	});
});