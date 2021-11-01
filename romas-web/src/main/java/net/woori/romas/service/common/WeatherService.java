package net.woori.romas.service.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.woori.romas.domain.weather.ApiData;
import net.woori.romas.domain.weather.RestApiResponse.Items;
import net.woori.romas.domain.weather.WeatherInfo;
import net.woori.romas.domain.weather.WthrWrnData;

/**
 * 날씨 정보 불러오기
 * 
 * @author hgko
 *
 */
@Service
public class WeatherService {
	
	private final String BASE_URL = "http://apis.data.go.kr/1360000/WthrWrnInfoService/getWthrWrnList";
	private final String serviceKey = "";
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 기상특보목록조회
	 */
	public WeatherInfo getWthrWrnInfo() {
		try {
			URL url = new URL(createUrl(BASE_URL));
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        
	        System.out.println("Response code: " + conn.getResponseCode());
	        
			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			
			rd.close();
			conn.disconnect();
			
			return createWeatherInfo(sb.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return null;
	}
	
	/**
	 * URL 생성
	 * @param url
	 * @return
	 */
	private String createUrl(String url) {
		StringBuilder urlBuilder = new StringBuilder(url);
		
		String today = dateFormat.format(new Date());
		
		try {
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
	        urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
	        urlBuilder.append("&" + URLEncoder.encode("stnId", "UTF-8") + "=" + URLEncoder.encode("184", "UTF-8")); /*지점코드 *하단 지점코드 자료 참조*/
	        urlBuilder.append("&" + URLEncoder.encode("fromTmFc", "UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*시간(년월일)(데이터 생성주기 : 시간단위로 생성)*/
	        urlBuilder.append("&" + URLEncoder.encode("toTmFc", "UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*시간(년월일) (데이터 생성주기 : 시간단위로 생성)*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return urlBuilder.toString();
	}
	
	/**
	 * 날씨 정보 생성
	 * @param json
	 * @return
	 */
	private WeatherInfo createWeatherInfo(String json) {
		
		System.out.println(json);
		
		WeatherInfo weatherInfo = new WeatherInfo();
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ApiData data = objectMapper.readValue(json, ApiData.class);
			
			Items items = data.getResponse().getBody().getItems();
			for (WthrWrnData item : items.getItem()) {
				
			}
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return weatherInfo;
	}
}
