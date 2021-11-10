package net.woori.romas.domain.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

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
public class ReservoirOperation implements Domain {

	/** 장비표준코드 */
	@Id
	private String facCode;
	
	/** 저장날짜 */
	@Id
	private Date indexDate;
	
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
	private float seriusWaterLevel;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CompositeOperationPK implements Domain {
		
		/** 측정날짜 */
		private Date indexDate;
		
		/** 장비표준코드 */
		private String facCode;
	}
}
