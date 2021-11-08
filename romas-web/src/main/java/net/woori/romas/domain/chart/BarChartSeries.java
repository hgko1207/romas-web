package net.woori.romas.domain.chart;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 막대 차트 데이터 정보
 * 
 * @author hgko
 *
 */
@Data
public class BarChartSeries {

	private String name;
	
	private String type;
	
	private float yAxis;
	
	private List<BarChartData> data;
	
	private List<Float> values;
	
	public BarChartSeries() {
		values = new ArrayList<>();
	}
	
	public void addDataItem(float value) {
		values.add(value);
	}
	
	@Data
	@NoArgsConstructor
	public class BarChartData {

		private float value;

		public BarChartData(float value) {
			this.value = value;
		}
	}
}
