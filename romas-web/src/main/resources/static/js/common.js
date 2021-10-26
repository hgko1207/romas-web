$(document).ready(function() {
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