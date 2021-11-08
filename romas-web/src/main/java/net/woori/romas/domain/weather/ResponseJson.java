package net.woori.romas.domain.weather;

import java.util.List;

import lombok.Data;

/**
 * 기상청 날씨 API 정보
 * 
 * @author hgko
 *
 */
@Data
public class ResponseJson {

	private Header header;
	
	private Body body;
	
	@Data
	public static class Header {
		
		private int resultCode;
		
		private String resultMsg;
	}
	
	@Data
	public static class Body {
		
		/** 한 페이지 결과 수 */
		private int numOfRows;
		
		/** 페이지 번호 */
		private int pageNo;
		
		/** 전체 결과 수 */
		private int totalCount;
		
		private String dataType;
		
		private Items items;
	}
	
	@Data
	public static class Items {
		
		private List<WthrWrnData> item;
	}
}
