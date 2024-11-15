package jcj.application.bigs.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class Test {
    public static void main(String[] args) throws IOException {
    	
    	Date now = new Date();
    	
    	SimpleDateFormat fmt1 = new SimpleDateFormat("yyyyMMdd");
		String today = fmt1.format(now);
		
		SimpleDateFormat fmt2 = new SimpleDateFormat("HH");
		String time = fmt2.format(now);
		String checkTime = time + "00";
    	
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=BFRn1%2BEPSEDM4g6G9JxRJsPlcAOoiQwQ1O8xjGTZLM%2FMBw6ns5z%2B8IHzCZF4Yf1IM6cEh2%2FiJakW%2FxuSSmRNwg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*‘yyyy년 MM월 dd일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(checkTime, "UTF-8")); /*HH시30분 발표(30분 단위)*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*예보지점 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점 Y 좌표값*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
        System.out.println(sb.toString());
        
        JSONObject jsonObject = new JSONObject(sb.toString());
        JSONArray items = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");

        String dateStr = ""; // 날짜
        String timeStr = ""; // 시간
        String getT1H = ""; // 기온
        String getRN1 = ""; // 1시간 강수량
        String getSKY = ""; // 하늘상태
        String getREH = ""; // 습도
        String getPTY = ""; // 강수형태
        String getVEC = ""; // 풍향
        String getWSD = ""; // 풍속
        
        
        
        String wind = "";
        
        HashMap<String, Object> map = new HashMap<>();
        
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String category = item.getString("category");
            String obsrValue = item.getString("obsrValue");
            

            switch (category) {
                case "T1H":
                	getT1H = obsrValue;
                    break;
                case "RN1":
                	getRN1 = obsrValue;
                    break;
                case "SKY":
                	getSKY = obsrValue;
                    break;
                case "PTY":
                	getPTY = obsrValue;
                    break;
                case "REH":
                	getREH = obsrValue;
                	break;
                case "VEC":
                	getVEC = obsrValue;
                    break;
                case "WSD":
                	getWSD = obsrValue;
                	break;
            }
        }
        
        
    }
}