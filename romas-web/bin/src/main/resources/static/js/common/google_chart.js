var GoogleComboChart = function() {
    // Combo chart
    var _googleComboChart = function(id, arrayData) {
        if (typeof google == 'undefined') {
            console.warn('Warning - Google Charts library is not loaded.');
            return;
        }

        // Initialize chart
        google.charts.load('current', {
            callback: function () {

                // Draw chart
                drawCombo();

                // Resize on sidebar width change
                var sidebarToggle = document.querySelector('.sidebar-control');
                sidebarToggle && sidebarToggle.addEventListener('click', drawCombo);

                // Resize on window resize
                var resizeCombo;
                window.addEventListener('resize', function() {
                    clearTimeout(resizeCombo);
                    resizeCombo = setTimeout(function () {
                        drawCombo();
                    }, 200);
                });
            },
            packages: ['corechart']
        });
        
        // Chart settings
        function drawCombo() {
        	
        	// Define charts element
            var combo_chart_element = document.getElementById(id);
            
            // Data
            var data = google.visualization.arrayToDataTable(arrayData);

            // Options
            var options_combo = {
        		isStacked: true,
                fontName: 'Roboto',
                fontSize: 14,
                backgroundColor: 'transparent',
                seriesType: "bars",
                chartArea: {
                    left: '8%',
                    right: '5%',
                    width: '100%',
                    height: '85%',
                },
                tooltip: {
                    textStyle: {
                        fontName: 'Roboto',
                        fontSize: 13
                    }
                },
                vAxis: {
                    textStyle: {
                        color: '#333',
                        bold: true,
                    },
                    baselineColor: '#ccc',
                    gridlines:{
                        color: '#eee',
                        count: 10
                    },
                    minValue: 0
                },
                hAxis: {
                    textStyle: {
                        color: '#333',
                        bold: true,
                    },
                },
                legend: {
                    position: 'top',
                    alignment: 'end',
                    pageIndex: 2,
                    textStyle: {
                        color: '#333',
                        bold: true,
                        fontSize: 13
                    }
                },
                bar: {
                    groupWidth: 80
                },
                series: {
                	0: { targetAxisIndex: 0, type: "bars", color: 'transparent', visibleInLegend: false },
                	1: { targetAxisIndex: 0, type: "line", pointSize: 7, color: '#000' },
                    2: { targetAxisIndex: 0, type: "bars", color: '#FF1B1B' },
                    3: { targetAxisIndex: 0, type: "bars", color: '#FEBE00' },
                    4: { targetAxisIndex: 0, type: "bars", color: '#8EC137' },
                    5: { targetAxisIndex: 0, type: "bars", color: '#009CE9' },
                },
            };
            
            // Draw chart
            var combo = new google.visualization.ComboChart(combo_chart_element);
            combo.draw(data, options_combo);
        }
    };
    
    return {
        init: function(id, data) {
            _googleComboChart(id, data);
        }
    }
}();