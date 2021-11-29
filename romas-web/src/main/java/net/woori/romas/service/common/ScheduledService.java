package net.woori.romas.service.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.woori.romas.domain.db.Reservoir;
import net.woori.romas.domain.db.ReservoirLevel;
import net.woori.romas.service.ReservoirLevelService;
import net.woori.romas.service.ReservoirService;

/**
 * 스케줄 관리 서비스
 * 
 * @author hgko
 *
 */
@Service
public class ScheduledService {
	
//	private final String BASE_URL = "http://210.90.40.182/openapi/svc/reservoirlevel/";
	private final String BASE_URL = "http://rawris.uirri.kr/svc_temp/reservoirlevel/";
	private final String serviceKey = "bTm7%2FgmDLl%2Brg1Kzp1NgwASontpVfDI9JIPD%2FN%2FsuUHosT7w4nOd9IUafIfHX2OOCoDHgQub%2BGSmtDisbWnjQQ%3D%3D";
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	@Autowired
	private ReservoirService reservoirService;
	
	@Autowired
	private ReservoirLevelService reservoirLevelService;

	/**
	 * 저수지 수위 정보 삽입
	 */
	@Scheduled(cron = "0 0 16 * * *")
//	@PostConstruct
	public void insertReservoirWaterLevel() {
		
		reservoirService.getList().stream().forEach(data -> {
			getReservoirWaterLevel(data).forEach(level -> {
				reservoirLevelService.regist(level);
			});
		});
	}
	
	/**
	 * 저수지 수위 조회
	 * 오후 4시에 실행
	 */
	public List<ReservoirLevel> getReservoirWaterLevel(Reservoir reservoir) {
		
		try {
			URL url = new URL(createUrl(BASE_URL, reservoir.getFacCode()));
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
			
			return createReservoirInfo(sb.toString(), reservoir);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return new ArrayList<>();
	}
	
	/**
	 * 저수지 수위 정보 생성
	 * @param result
	 */
	private List<ReservoirLevel> createReservoirInfo(String result, Reservoir reservoir) {
		
		List<ReservoirLevel> reservoirLevels = new ArrayList<>();
		
		System.err.println(result);
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(result)));

            NodeList nodes = document.getElementsByTagName("item");
            
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				NodeList node = element.getElementsByTagName("fac_code");
				Element data = (Element) node.item(0);
				String facCode = getCharacterDataFromElement(data);
				
				node = element.getElementsByTagName("fac_name");
				data = (Element) node.item(0);
				String facName = getCharacterDataFromElement(data);
				
				node = element.getElementsByTagName("county");
				data = (Element) node.item(0);
				String county = getCharacterDataFromElement(data);
				
				node = element.getElementsByTagName("check_date");
				data = (Element) node.item(0);
				String checkDate = getCharacterDataFromElement(data);
				
				node = element.getElementsByTagName("water_level");
				data = (Element) node.item(0);
				String waterLevel = getCharacterDataFromElement(data);

				node = element.getElementsByTagName("rate");
				data = (Element) node.item(0);
				String rate = getCharacterDataFromElement(data);
				
//				System.out.println("fac_code: " + facCode);
//				System.out.println("fac_name: " + facName);
//				System.out.println("county: " + county);
//				System.out.println("check_date: " + checkDate);
//				System.out.println("water_level: " + waterLevel);
//				System.out.println("rate: " + rate);
				
				ReservoirLevel reservoirLevel = new ReservoirLevel();
				reservoirLevel.setCheckDate(dateFormat.parse(checkDate));
				reservoirLevel.setFacCode(facCode);
				reservoirLevel.setFacName(facName);
				reservoirLevel.setCountry(county);
				reservoirLevel.setWaterLevel(Float.parseFloat(waterLevel));
				reservoirLevel.setRate(Float.parseFloat(rate));
				reservoirLevel.setCreateDate(checkDate);
				reservoirLevel.setRegionalHead(reservoir.getRegionalHead());
				reservoirLevel.setBranch(reservoir.getBranch());
				
				reservoirLevels.add(reservoirLevel);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return reservoirLevels;
	}
	
	/**
	 * URL 생성
	 * @param url
	 * @return
	 */
	private String createUrl(String url, String facCode) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		
		String yesterday = dateFormat.format(calendar.getTime());
		String today = dateFormat.format(new Date());
		
		StringBuilder urlBuilder = new StringBuilder(url);
		
		try {
			urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("fac_code", "UTF-8") + "=" + URLEncoder.encode(facCode, "UTF-8")); /*저수지코드*/
	        urlBuilder.append("&" + URLEncoder.encode("date_s", "UTF-8") + "=" + URLEncoder.encode(yesterday, "UTF-8")); /*조회시작날짜(yyyymmdd)*/
	        urlBuilder.append("&" + URLEncoder.encode("date_e", "UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*조회끝날짜(yyyymmdd)*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return urlBuilder.toString();
	}
	
	private String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "?";
	}
}
