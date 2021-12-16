package net.woori.romas.domain.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.woori.romas.domain.Domain;
import net.woori.romas.domain.ReservoirInfo;

/**
 * 저수지 공사 정보
 * 
 * @author jhlee
 *
 */
@Entity
@Table(name = "tb_reservoir_mgmt")
@Data
@NoArgsConstructor
public class ReservoirMgmt implements Domain {

	/** 장비표준코드 */
	@Id
	private String facCode;
		
	/** 소재지 */
	private String address;

	/** 종별 */
	private int classification;

	/** 제체길이 */
	private int length;

	/** 제체높이 */
	private int height;
	
	/** 만수면적 */
	private int pullArea;
	
	/** 관리자 주소 */
	private String mgmtAdress;
	
	/** 착공일 */
	private Date startDate;
	
	/** 준공일 */
	private Date completionDate;
		
	public ReservoirMgmt(ReservoirInfo info) {
		this.facCode = info.getFacCode();
		this.address = info.getAddress();
		this.classification = info.getClassification();
		this.length = info.getLength();
		this.height = info.getHeight();
		this.pullArea = info.getPullArea();
		this.mgmtAdress = info.getMgmtAdress();
		this.startDate = info.getStartDate();
		this.completionDate = info.getCompletionDate();
	}

}
