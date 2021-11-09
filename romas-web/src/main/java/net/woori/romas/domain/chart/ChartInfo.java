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

	private BarChartSeries barChartSeries; 
	
	private List<LineChartSeries> lineChartSeries;
	
	public ChartInfo() {
		categories = new ArrayList<>();
		lineChartSeries = new ArrayList<>();
	}
	
	public void addCategory(String value) {
		categories.add(value);
	}
	
	public void addLineChartSeries(LineChartSeries series) {
		lineChartSeries.add(series);
	}
}
