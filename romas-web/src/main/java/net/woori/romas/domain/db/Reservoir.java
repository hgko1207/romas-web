package net.woori.romas.domain.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import net.woori.romas.domain.Domain;

/**
 * 
 * @author jhlee
 *
 */
@Entity
@Table(name = "tb_reservoir")
@Data
public class Reservoir implements Domain {

	/** 장비표준코드 */
	@Id
	private String facCode;
	
	/** 지역본부 */
	@Column(nullable = false, length = 20)
	private String regionalHead;

	/** 지사 */
	@Column(nullable = false, length = 20)
	private String branch;

	/** 시설이름 */
	@Column(nullable = false, length = 20)
	private String facilityName;

	/** 장비서브코드 */
	@Column(nullable = false, length = 20)
	private String subCode;
	
	/** 경도 */
	@Column(nullable = false)
	private float longitude;
	
	/** 위도 */
	@Column(nullable = false)
	private float latitude;
	
	/** 사수위 */
	private int dsl;
	
	/** 만수위 */
	private int nwl;
	
	/** 홍수위 */
	private int fwl;
	
	/** 유효저수량 */
	private int enableStorageCapacity;
	
	/** 총저수량 */
	private int totalStroageCapacity;
	
	/** 수원공구분 */
	private String waterClass;
	
	/** 취수 시설 */
	private String waterIntakeFacility;
	
	/** 수혜 면적 */
	private String beneArea;
	
	/** 유역 면적 */
	private String watershedArea;
	
	/** 현재 수위 */
	private int currentWaterLevel;
	
	/** 관심단계 적용 값*/
	private int attentionLevel;
	
	/** 주의단계 적용 값*/
	private int cautionLevel;
	
	/** 경계단계 적용 값*/
	private int boundaryLevel;
	
	/** 심각단계 적용 값*/
	private int seriousLevel;

}
