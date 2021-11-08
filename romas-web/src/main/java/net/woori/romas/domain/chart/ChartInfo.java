package net.woori.romas.domain.chart;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Chart 정보
 * 
 * @author hgko
 *
 */
@Data
public class ChartInfo {
	
	private List<String> categories;

	private List<BarChartSeries> barChartSeriesList;
	
	private BarChartSeries barChartSeries; 
	
	public ChartInfo() {
		categories = new ArrayList<>();
		barChartSeriesList = new ArrayList<>();
	}
	
	public void addCategory(String value) {
		categories.add(value);
	}
	
	public void addBarChartSeries(BarChartSeries series) {
		barChartSeriesList.add(series);
	}
}
