package net.woori.romas.domain.weather;

import lombok.Data;

/**
 * 기상 특보 정보
 * 
 * @author hgko
 *
 */
@Data
public class WthrWrnData {

	/** 지점 코드 */
	private String stnId;
	
	/** 제목 */
	private String title;
	
	/** 발표번호(월별) */
	private String tmSeq;

	/** 발표시각(년월일시분) */
	private String tmFc;
}
