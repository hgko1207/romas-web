package net.woori.romas.domain.chart;

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
@NoArgsConstructor
public class BarChartSeries {

	private String name;
	
	private String type;
	
	private List<BarChartData> data;
	
	@Data
	@NoArgsConstructor
	public class BarChartData {
		
		private float value;
	}
}
