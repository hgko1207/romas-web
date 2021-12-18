package net.woori.romas.domain;

import lombok.Data;

/**
 * 저수지 정보
 *  
 * @author hgko
 *
 */
@Data
public class ReservoirInfo {

	/** 장비표준코드 */
	private String facCode;
	
	/** 시설이름 */
	private String facilityName;
	
	/** 주소 */
	private String address;
	
	/** 위도 */
	private float latitude;
	
	/** 경도 */
	private float longitude;
	
	/** 수원공급원 */
	private String waterClass;
	
	/** 종별 */
	private int classification;
	
	/** 제체길이 */
	private int length;
	
	/** 제체높이 */
	private int height;
		
	/** 총저수량 */
	private int totalStroageCapacity;
	
	/** 유효저수량 */
	private int enableStorageCapacity;
	
	/** 유역면적 */
	private String watershedArea;
	
	/** 수혜면적 */
	private String beneArea;
	
	/** 만수면적 */
	private int pullArea;
	
	/** 관리자주소 */
	private String mgmtAdress;
	
	/** 착공일 */
	private String startDate;
	
	/** 준공일 */
	private String completionDate;

}
