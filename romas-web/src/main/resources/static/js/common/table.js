/**
 * 테이블 관련 유틸
 */
$.extend( $.fn.dataTable.defaults, {
	autoWidth: false,
    dom: '<"datatable-header"fl><"datatable-scroll-wrap"t><"datatable-footer"ip>',
    language: {
		emptyTable: '데이터가 없습니다.',
		infoEmpty: "",
		info: " _TOTAL_ 개의 데이터가 있습니다.",
	    search: '<span>검색 :</span> _INPUT_',
	    searchPlaceholder: '내용 입력...',
	    lengthMenu: '<span>Show:</span> _MENU_',
	    paginate: { 'first': 'First', 'last': 'Last', 
	    	'next': $('html').attr('dir') == 'rtl' ? '&larr;' : '&rarr;', 'previous': $('html').attr('dir') == 'rtl' ? '&rarr;' : '&larr;' }
	},
	searching: false,
	lengthChange: false,
	ordering: false,
	pageLength: 15
});

var Datatables = {
	basic: function(id, tableOption, info) {
		var table = $(id).DataTable({
			language: {
				info: info ? info : " _TOTAL_ 개의 데이터가 있습니다." 
			},
			columns: tableOption.columns,
			order: [[0, 'asc']]
		});
		
		return table;
	},
	download: function(id, tableOption, info, visible, exportColumns) {
		var table = $(id).DataTable({
			dom: '<"datatable-header"fl><"datatable-scroll-wrap"t><"datatable-footer"iBp>',
			language: {
				info: info ? info : " _TOTAL_ 개의 데이터가 있습니다."
			},
			columns: tableOption ? tableOption.columns : null,
			columnDefs: [
				{ orderable: true, className: 'reorder', targets: 0 },
		    	{ orderable: false, targets: '_all' },
				{ visible: false, targets: visible }
			],
			buttons: {
		        buttons: [{
	                extend: 'excelHtml5',
	                className: 'btn bg-primary-400 ml-3',
                    text: '<i class="icon-folder-download mr-2"></i> 다운로드',
                    fieldSeparator: '\t',
		            exportOptions: {
		                columns: exportColumns
		            }
	            }]
		    },
			order: [[0, 'asc']]
		});
		
		return table;
	},
	rowsAdd: function(table, url, param) {
		table.clear().draw();
		
		$.ajax({
			url: url,
			type: "POST",
			data: JSON.stringify(param),
			contentType: "application/json",
			success: function(data) {
				table.rows.add(data).draw();
		   	}
		});
	},
	refresh: function(table, data) {
		table.clear().draw();
		table.rows.add(data).draw();
	}
}
