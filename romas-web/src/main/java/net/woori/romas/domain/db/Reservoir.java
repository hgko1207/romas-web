package net.woori.romas.domain.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.woori.romas.domain.Domain;
import net.woori.romas.domain.ReservoirInfo;

/**
 * 저수지 제원 정보
 * 
 * @author jhlee
 *
 */
@Entity
@Table(name = "tb_reservoir")
@Data
@NoArgsConstructor
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
	
	/** 지역코드 도, 특별시*/
	private Integer areaSpark;
	
	/** 지역코드 시, 군*/
	private Integer areaSiGun;
	
	/** 지역코드 동, 면, 읍*/
	private Integer areaDong;
	
	/** 관심 단계 
	 * 0 : 관심 파랑
	 * 1 : 주의 노랑
	 * 2 : 경계 주황
	 * 3 : 심각 빨강
	 * */
	private int level;
	
	private double indexCode;

	public Reservoir(ReservoirInfo info) {
		this.facCode = info.getFacCode();
	}

}
