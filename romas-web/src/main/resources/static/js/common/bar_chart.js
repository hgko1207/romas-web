var EchartsBarChart = function() {
	 var _barsChart = function(id, data) {
        if (typeof echarts == 'undefined') {
            console.warn('Warning - echarts.min.js is not loaded.');
            return;
        }
        
        var bars_basic = null;
        
        const colors = ['#08D13F', '#91CC75', '#EE6666'];
        
        var bars_element = document.getElementById(id);
        if (bars_element) {
            bars_basic = echarts.init(bars_element);
            bars_basic.setOption({
            	color: colors,
            	textStyle: {
                    fontFamily: 'Roboto, Arial, Verdana, sans-serif',
                    fontSize: 12
                },
                animationDuration: 750,
                grid: {
                	left: 20,
                    right: 10,
                    top: 35,
                    bottom: 0,
                    containLabel: true
                },
                legend: {
            	    data: ['전국 평균'],
            	    itemHeight: 8,
                    itemGap: 10,
                    itemStyle: {
                    	color: '#08D13F'
                    },
                    textStyle: {
                    	color: '#fff'
                    },
                    right: '1%'
            	},
            	tooltip: {
            		trigger: 'axis',
            	    axisPointer: {
            	    	type: 'cross'
            	    }
            	},
                xAxis: [{
                    type: 'category',
                    axisTick: {
                      alignWithLabel: true
                    },
                    // prettier-ignore
                    data: ['전국', '경기', '강원', '충북', '충남', '전북', '전남', '경북', '경남', '제주'],
                    axisLabel: {
                    	color: '#fff',
                    }
                }],
                yAxis: [
                	{
                		type: 'value',
                		name: '평년대비저수율(%)',
                		min: 0,
                		max: 150,
                		position: 'left',
                		nameLocation: "middle",
                		nameTextStyle: {
                			padding: 16
            		    },
                		axisLine: {
                			show: true,
                			lineStyle: {
                				color: '#959595'
                			}
                		},
                		axisLabel: {
                			formatter: '{value}',
                			color: '#fff'
                		},
                		splitLine: {
                			lineStyle: {
                				color: '#5A5A5A'
                			}
                		}
            	    }
                ],
                series: [
                	{
						name : '전국 평균',
						type : 'bar',
						stack: 'one',
						markLine: {
							symbol: false,
							label: {
								show: false,
							},
							lineStyle: {
								color: '#08D13F',
								type: 'solid',
								width: 1
							},
                            data: [{yAxis: 110}]
                        },
					},
                	{
						name : '저수율(%)',
						type : 'bar',
						stack: 'one',
						itemStyle : {
							color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
					          { offset: 0, color: '#1752FF' },
					          { offset: 0.5, color: '#225BFF' },
					          { offset: 1, color: '#5984FF' }
					        ])
						},
						data: [ 110, 75, 90, 70, 80, 105, 95, 100, 75, 100 ],
						barWidth: '50%',
					},
				]
            });
        }
        
        // Resize function
        var triggerChartResize = function() {
        	bars_element && bars_basic.resize();
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
        
        return bars_basic;
	 }
	
	return {
        init: function(id, data) {
        	return _barsChart(id, data);
        }
    }
}();