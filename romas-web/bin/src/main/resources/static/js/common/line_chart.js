var EchartsLineChart = function() {
	 var _lineChart = function(id, data) {
        if (typeof echarts == 'undefined') {
            console.warn('Warning - echarts.min.js is not loaded.');
            return;
        }
        
        var line_basic = null;
        
        const colors = ['#000'];
        
        var line_element = document.getElementById(id);
        if (line_element) {
        	line_basic = echarts.init(line_element);
        	line_basic.setOption({
            	color: colors,
            	textStyle: {
                    fontFamily: 'Roboto, Arial, Verdana, sans-serif',
                    fontSize: 12
                },
                animationDuration: 750,
                grid: {
                	left: 0,
                    right: 0,
                    top: 30,
                    bottom: 0,
                    containLabel: true
                },
                legend: {
            	    data: ['저수율'],
            	    itemHeight: 8,
                    itemGap: 10,
                    itemStyle: {
                    	color: '#08D13F',
                    },
                    right: '1%',
                    textStyle: {
                        fontSize: 14
                    }
            	},
            	tooltip: {
            		trigger: 'axis',
            	},
                xAxis: [{
                    type: 'category',
                    axisTick: {
                      alignWithLabel: true
                    },
                    axisLabel: {
            			margin: 20,
            			fontSize: 16,
            			fontWeight: "bold"
        		    },
                    data: data.categories,
                }],
                yAxis: {
                	type: 'value',
                	min: 0,
            		max: 100,
            		interval: 10,
            		axisLabel: {
            			margin: 20,
            			fontSize: 16,
            			fontWeight: "bold"
        		    }
                },
                series: data.lineChartSeries,
            });
        }
        
        // Resize function
        var triggerChartResize = function() {
        	line_element && line_basic.resize();
        };

        // On sidebar width change
        var sidebarToggle = document.querySelector('.sidebar-control');
        sidebarToggle && sidebarToggle.addEventListener('click', triggerChartResize);

        // On window resize
        var resizeCharts;
        window.addEventListener('resize', function() {
            clearTimeout(resizeCharts);
            resizeCharts = setTimeout(function () {
                triggerChartResize();
            }, 200);
        });
        
        return line_basic;
	 }
	
	return {
        init: function(id, data) {
        	return _lineChart(id, data);
        }
    }
}();