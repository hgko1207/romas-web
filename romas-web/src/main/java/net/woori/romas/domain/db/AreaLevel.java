package net.woori.romas.domain.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.woori.romas.domain.Domain;
import net.woori.romas.domain.db.AreaLevel.CompositeAreaLevelPK;
/**
 * 시, 광역시, 군, 구에 대한 위험도를 저장하는 DB
 * 
 * @author jhlee
 *
 */
@Entity
@Table(name = "tb_area_Level")
@IdClass(CompositeAreaLevelPK.class) 
@Data
@NoArgsConstructor
public class AreaLevel implements Domain {

	/** 지역 코드 */
	@Id
	private int province;
	
	@Id
	private int country;
		
	/** 도=1,광역시=1,군=2,구=2*/
	@Column(nullable = false, length = 20)
	private int type;

	/** 관심 단계 
	 * 0 : 관심 파랑
	 * 1 : 주의 노랑
	 * 2 : 경계 주황
	 * 3 : 심각 빨강
	 * */
	@Column(nullable = false, length = 20)
	private int level;
	
	/** 경도 */
	@Column(nullable = true)
	private float longitude;
	
	/** 위도 */
	@Column(nullable = true)
	private float latitude;
	
	/** label */
	@Column(nullable = false)
	private String label;
	
	/** 관심 개수 */
	private int attentionCount;
	
	/** 주의 개수 */
	private int cautionCount;
	
	/** 경계 개수 */
	private int boundaryCount;
	
	/** 심각 개수 */
	private int seriousCount;
	
	/** 내림차순 관련 컬럼 **/
	private String indexCode;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CompositeAreaLevelPK implements Domain {
				
		/** 시,도 */
		private int province;
		
		/** 군,구*/
		private int country;
		
	}
}
