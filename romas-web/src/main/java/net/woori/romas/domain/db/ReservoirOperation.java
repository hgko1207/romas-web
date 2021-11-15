package net.woori.romas.domain.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.woori.romas.domain.Domain;
import net.woori.romas.domain.db.ReservoirOperation.CompositeOperationPK;

/**
 * 저수지 1년 정보
 * 
 * @author jhlee
 *
 */
@Entity
@Table(name = "tb_reservoir_operation")
@IdClass(CompositeOperationPK.class) //날짜와 장비표준코드를 primary key로 사용
@Data
@NoArgsConstructor
public class ReservoirOperation implements Domain {

	/** 장비표준코드 */
	@Id
	private String facCode;
	
	/** 저장날짜 월*/
	@Id
	private int month;
	
	/** 저장날짜 순*/
	@Id
	private String eml;
	
	/** 지역본부 */
	@Column(nullable = false, length = 20)
	private String regionalHead;

	/** 지사 */
	@Column(nullable = false, length = 20)
	private String branch;

	/** 지점 */
	@Column(nullable = false, length = 20)
	private String facilityName;
	
	/** 금일 수위 */
	private float currentWaterLevel;
	
	/** 관심 수위 */
	private float attentionWaterLevel;
	
	/** 주의 수위 */
	private float cautionWaterLevel;
	
	/** 경계 수위 */
	private float boudaryWaterLevel;
	
	/** 심각 수위 */
	private float seriousWaterLevel;
	
	@Transient
	private String resultDate;
	
	/** 저수위 수위(m) */
	@Transient
	private float waterLevel;
	
	@Transient
	private float emptyLevel;
	
	public ReservoirOperation(String resultDate, float emptyLevel, float seriousWaterLevel, float boudaryWaterLevel, float cautionWaterLevel,
			float attentionWaterLevel, float waterLevel) {
		this.resultDate = resultDate;
		this.seriousWaterLevel = seriousWaterLevel;
		this.boudaryWaterLevel = boudaryWaterLevel;
		this.cautionWaterLevel = cautionWaterLevel;
		this.attentionWaterLevel = attentionWaterLevel;
		this.waterLevel = waterLevel;
		this.emptyLevel = emptyLevel;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CompositeOperationPK implements Domain {
				
		/** 장비표준코드 */
		private String facCode;
		
		/** 저장날짜 월*/
		private int month;
		
		/** 저장날짜 순*/
		private String eml;
	}
	
	public enum OperationType {
		Attention, Caution, Boudary, Serious;
	}
}
