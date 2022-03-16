package net.woori.romas.domain.db;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.woori.romas.domain.Domain;
/**
 * 시, 광역시, 군, 구에 대한 위험도를 저장하는 DB
 * 
 * @author jhlee
 *
 */
@Entity
@Table(name = "tb_jisa_Level")
@Data
@NoArgsConstructor
public class JisaLevel implements Domain {

	/** 지역 코드 */
	@Id
	private String indexCode;
	
	/** 지사 이름 */
	private String branch;
	
	/** 관심 개수 */
	private int attentionCount;
	
	/** 주의 개수 */
	private int cautionCount;
	
	/** 경계 개수 */
	private int boundaryCount;
	
	/** 심각 개수 */
	private int seriousCount;

}
