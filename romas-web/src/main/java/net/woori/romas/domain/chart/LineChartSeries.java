package net.woori.romas.domain.chart;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 라인 차트 데이터 정보
 * 
 * @author hgko
 *
 */
@Data
@NoArgsConstructor
public class LineChartSeries {

	private String name;
	
	private String type;

	private List<Float> data;
	
	public LineChartSeries(String name) {
		this.name = name;
		this.type = "line";

		data = new ArrayList<>();
	}

	public void addDataItem(float value) {
		data.add(value);
	}

}
