/*******************************************************************************************************
 * 위젯 공통 기능
*******************************************************************************************************/
var CommonWidget = function() {
	
	var _componentJQuery = function() {
    	/** form 데이터들을 JSON 형식으로 변환 */
    	jQuery.fn.serializeObject = function() { 
    		var obj = null; 
    		try { 
    			if(this[0].tagName && this[0].tagName.toUpperCase() == "FORM" ) { 
				var arr = this.serializeArray(); 
				if(arr){ 
					obj = {}; 
					jQuery.each(arr, function() { 
						obj[this.name] = this.value; }); 
					} 
				} 
			} catch(e) { 
				alert(e.message); 
			} finally {} 
			return obj; 
		}
    };
    
    return {
        init: function() {
        	_componentJQuery();
        }
    }
}();

$(document).ready(function() {
	CommonWidget.init();
	
	$(".rm-region-btn-wrap button").click(function() {
		$(".rm-region-btn-wrap button").removeClass("selected");
		$(this).addClass("selected");
	});
	$(".rm-mobile-menu-btn.mobile").click(function() {
		$(".rm-mobile-menu").addClass("open");
	});
	$(".rm-menu-close").click(function() {
		$(".rm-mobile-menu").removeClass("open")
	});
	
	var menuName = this.location.pathname.split("/")[2];
	$("nav a#" + menuName).addClass("selected");
});

var swalInit = swal.mixin({
    buttonsStyling: false,
    confirmButtonClass: 'btn btn-primary',
    cancelButtonClass: 'btn btn-light'
});
