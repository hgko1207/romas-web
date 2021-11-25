package net.woori.romas.domain.param;

import java.util.Date;

import lombok.Data;
import net.woori.romas.domain.Enums.GroupType;

/**
 * 검색조건
 * 
 * @author hgko
 *
 */
@Data
public class FacilitySearchParam {
	
	private String facCode;
	
	/** 시작일시 */
	private Date startDate;
	
	/** 종료일시 */
	private Date endDate;
	
	private GroupType groupType;
	
}
