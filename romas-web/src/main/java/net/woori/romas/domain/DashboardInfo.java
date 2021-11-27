package net.woori.romas.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 대쉬보드 정보
 * 
 * @author hgko
 *
 */
@Data
@NoArgsConstructor
public class DashboardInfo {

	private String name;
	
	private float value;
	
	private int gap;
	
	private UpDown upDown;
	
	/** 전국 금일 저수율 평균 */
	private float todayValue;
	
	/** 평년 저수율 평균 */
	private float commonYearValue;
	
	public DashboardInfo(String name) {
		this.name = name;
	}

	public enum UpDown {
		UP, DOWN, EQUAL
	}
}
