package net.woori.romas.service.common;

import org.springframework.stereotype.Service;

import net.woori.romas.domain.chart.BarChartSeries;
import net.woori.romas.domain.chart.ChartInfo;
import net.woori.romas.domain.chart.LineChartSeries;
import net.woori.romas.domain.param.SearchParam;

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
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	public ChartInfo createLineChartInfo(SearchParam param) {
		
		ChartInfo chartInfo = new ChartInfo();
		
		String[] categories = {"1.10", "2.10", "3.10", "4.10", "5.10", "6.10", "7.10", "8.10", "9.10", "10.10", "11.10", "12.10"};
		for (String data : categories) {
			chartInfo.addCategory(data);
		}
		
		LineChartSeries lineChartSeries = new LineChartSeries("저수율");
		lineChartSeries.addDataItem(52);
		lineChartSeries.addDataItem(54);
		lineChartSeries.addDataItem(60);
		lineChartSeries.addDataItem(70);
		lineChartSeries.addDataItem(46);
		lineChartSeries.addDataItem(35);
		lineChartSeries.addDataItem(75);
		lineChartSeries.addDataItem(77);
		lineChartSeries.addDataItem(82);
		lineChartSeries.addDataItem(89);
		lineChartSeries.addDataItem(90);
		
		chartInfo.addLineChartSeries(lineChartSeries);
		
		return chartInfo;
	}
}

