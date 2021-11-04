package net.woori.romas.domain.param;

import lombok.Data;

/**
 * 검색조건
 * 
 * @author hgko
 *
 */
@Data
public class SearchParam {

	private String name;
	
	/** 시작일시 */
	private String startDate;
	
	/** 종료일시 */
	private String endDate;
}
