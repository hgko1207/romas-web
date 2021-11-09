package net.woori.romas.service.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.woori.romas.domain.naver.NaverApiResponse;
import net.woori.romas.domain.naver.NewsInfo;

/**
 * 네이버 API 이용 서비스
 * 
 * @author hgko
 *
 */
@Service
public class NaverApiService {
	
	private final String clientId = "0fUj_3vJLIc9rq6lRhz8"; // 애플리케이션 클라이언트 아이디값"
	private final String clientSecret = "bcz4UyAcji"; // 애플리케이션 클라이언트 시크릿값"
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 네이버 뉴스 정보 조회
	 */
	public List<NewsInfo> getNewsInfo(String keyword) {
		String text = null;
		try {
			text = URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패", e);
		}

		String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text; // json 결과

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		String responseBody = get(apiURL, requestHeaders);
		
		return createNewsInfo(responseBody);
	}
	
	/**
	 * 뉴스 정보 생성
	 * @param json
	 * @return
	 */
	private List<NewsInfo> createNewsInfo(String json) {
		
		List<NewsInfo> newsInfos = new ArrayList<>();
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			NaverApiResponse data = objectMapper.readValue(json, NaverApiResponse.class);
			
			for (NewsInfo newsInfo : data.getItems()) {
				newsInfo.setDate(dateFormat.format(newsInfo.getPubDate()));
				newsInfos.add(newsInfo);
			}
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return newsInfos;
	}
	
	/**
	 * 데이터 조회
	 * @param apiUrl
	 * @param requestHeaders
	 * @return
	 */
	private String get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				return readBody(con.getInputStream());
			} else { // 에러 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}

	/**
	 * 연결 정보 생성
	 * @param apiUrl
	 * @return
	 */
	private HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

	/**
	 * 응답 정보 생성
	 * @param body
	 * @return
	 */
	private String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}
}
