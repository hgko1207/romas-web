package net.woori.romas.domain.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.IdClass;



import lombok.Data;

/**
 * 
 * @author jhlee
 *
 */
@Entity
@Table(name = "tb_reservoir_level")
@IdClass(ReservoirLevel.class) //날짜와 장비표준코드를 primary key로 사용
@Data
public class ReservoirLevel {
	
	/** 측정날짜 */
	@Id
	private Date check_date;
	
	/** 장비표준코드 */
	@Id
	private String facCode;
	
	/** 저수지 이름 */
	private String facName;
	
	/** 저수지 위치 */
	private String country;
	
	/** 저수위 수위(m) */
	private float waterLevel;
	
	/** 저수율(%) */
	private float rate;
	
}
