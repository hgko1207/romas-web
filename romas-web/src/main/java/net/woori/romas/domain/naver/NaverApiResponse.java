package net.woori.romas.domain.naver;

import java.util.List;

import lombok.Data;

/**
 * 네이버 API 응답 정보
 * 
 * @author hgko
 *
 */
@Data
public class NaverApiResponse {

	private String lastBuildDate;
	
	private long total;
	
	private int start;
	
	private int display;
	
	private List<NewsInfo> items;
}
