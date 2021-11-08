package net.woori.romas.service.common;

import org.springframework.stereotype.Service;

import net.woori.romas.domain.chart.BarChartSeries;
import net.woori.romas.domain.chart.ChartInfo;

/**
 * 차트 관리 서비스
 * 
 * @author hgko
 *
 */
@Service
public class ChartService {

	/**
	 * 저수율 차트 정보 생성
	 * @return
	 */
	public ChartInfo createBarChartInfo() {
		
		ChartInfo chartInfo = new ChartInfo();
		
		BarChartSeries barChartSeries = new BarChartSeries();
		barChartSeries.addDataItem(110);
		barChartSeries.addDataItem(75);
		barChartSeries.addDataItem(90);
		barChartSeries.addDataItem(70);
		barChartSeries.addDataItem(80);
		barChartSeries.addDataItem(105);
		barChartSeries.addDataItem(95);
		barChartSeries.addDataItem(100);
		barChartSeries.addDataItem(75);
		barChartSeries.addDataItem(100);
		barChartSeries.setYAxis(110);
		
		chartInfo.setBarChartSeries(barChartSeries);
		
		return chartInfo;
	}
}

