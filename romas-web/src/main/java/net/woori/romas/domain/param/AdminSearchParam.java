package net.woori.romas.domain.param;

import lombok.Data;

/**
 * 관리자 화면 검색조건
 * 
 * @author hgko
 *
 */
@Data
public class AdminSearchParam {

	/** 지역본부 */
	private String regionalHead;

	/** 지사 */
	private String branch;

	/** 지점 */
	private String facilityName;
}
