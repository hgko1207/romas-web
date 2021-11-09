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
import net.woori.romas.domain.db.ReservoirLevel.CompositePK;

/**
 * 저수지 수위 정보
 * 
 * @author jhlee
 *
 */
@Entity
@Table(name = "tb_reservoir_level")
@IdClass(CompositePK.class) //날짜와 장비표준코드를 primary key로 사용
@Data
public class ReservoirLevel implements Domain {
	
	/** 측정날짜 */
	@Id
	private Date checkDate;
	
	/** 장비표준코드 */
	@Id
	@Column(length = 255)
	private String facCode;
	
	/** 저수지 이름 */
	private String facName;
	
	/** 저수지 위치 */
	private String country;
	
	/** 저수위 수위(m) */
	private float waterLevel;
	
	/** 저수율(%) */
	private float rate;
	
	@Column(length = 10)
	private String createDate;
	
	/** 지역본부 */
	@Column(nullable = false, length = 20)
	private String regionalHead;

	/** 지사 */
	@Column(nullable = false, length = 20)
	private String branch;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CompositePK implements Domain {
		
		/** 측정날짜 */
		private Date checkDate;
		
		/** 장비표준코드 */
		private String facCode;
	}
	
}
