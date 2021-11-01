package net.woori.romas.domain.naver;

import lombok.Data;

/**
 * 네이버 뉴스 정보
 * 
 * @author hgko
 *
 */
@Data
public class NewsInfo {

	/** 제목 */
	private String title;

	/** 언론사 하이퍼텍스트 */
	private String originallink;

	/** 검색 결과 문서의 내용을 요약한 패시지 정보 */
	private String description;

	/** 검색 결과 문서가 네이버에 제공된 시간 */
	private String pubDate;
}
