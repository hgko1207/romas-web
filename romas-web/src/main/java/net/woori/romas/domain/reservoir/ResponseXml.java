package net.woori.romas.domain.reservoir;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import net.woori.romas.domain.weather.ResponseJson.Header;
import net.woori.romas.domain.weather.ResponseJson.Items;

/**
 * 기상청 API로 기상정보 조회할 때 쓰이는 도메인
 * 
 * @author hgko
 *
 */
@Data
@XmlRootElement(name = "response")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ResponseXml {

	@XmlElement(name = "header")
	private Header header;
	
//	@XmlElement(name = "body")
//	private XmlBody body;

	@Data
	public static class XmlBody {
		
		/** 한 페이지 결과 수 */
		private int numOfRows;
		
		/** 페이지 번호 */
		private int pageNo;
		
		/** 전체 결과 수 */
		private int totalCount;
		
		private Items items;
	}
}
