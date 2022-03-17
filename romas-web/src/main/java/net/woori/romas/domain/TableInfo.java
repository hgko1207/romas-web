package net.woori.romas.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.woori.romas.domain.db.ReservoirOperation.OperationType;

@Data
@NoArgsConstructor
public class TableInfo {

	private String name;
	
	private String regionalHead;
	
	private OperationType type;
	
	private float waterLevel;
	
	/** 전체 개수 */
	private int allCount;
	
	/** 관심 개수 */
	private int attentionCount;
	
	/** 주의 개수 */
	private int cautionCount;
	
	/** 경계 개수 */
	private int boundaryCount;
	
	/** 심각 개수 */
	private int seriousCount;
	
	private int country;
	
	public TableInfo(String name, int country, int attentionCount, int cautionCount, int boundaryCount, int seriousCount) {
		this.name = name;
		this.country = country;
		this.allCount = attentionCount + cautionCount + boundaryCount + seriousCount;
		this.attentionCount = attentionCount;
		this.cautionCount = cautionCount;
		this.boundaryCount = boundaryCount;
		this.seriousCount = seriousCount;
	}
	
	public TableInfo(String name, String regionalHead, int attentionCount, int cautionCount, int boundaryCount, int seriousCount) {
		this.name = name;
		this.regionalHead = regionalHead;
		this.allCount = attentionCount + cautionCount + boundaryCount + seriousCount;
		this.attentionCount = attentionCount;
		this.cautionCount = cautionCount;
		this.boundaryCount = boundaryCount;
		this.seriousCount = seriousCount;
	}

	public TableInfo(String facilityName, OperationType type, float waterLevel) {
		this.name = facilityName;
		this.type = type;
		this.waterLevel = waterLevel;
	}
}
