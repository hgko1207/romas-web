package net.woori.romas.domain;

import lombok.Data;

/**
 * 대쉬보드 정보
 * 
 * @author hgko
 *
 */
@Data
public class DashboardInfo {

	private String name;
	
	private float value;
	
	private float gap;
	
	private boolean up;
	
	public DashboardInfo(String name) {
		this.name = name;
	}

}
