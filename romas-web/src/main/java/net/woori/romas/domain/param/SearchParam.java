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
	
	private String searchType;
	
	/** 시작일시 */
	private String startDate;
	
	/** 종료일시 */
	private String endDate;
	
	/** 종류(지역: 1, 지사: 2, 시설: 3) */
	private int type;
	
	/** 시설이름 */
	private String facilityName;
}
