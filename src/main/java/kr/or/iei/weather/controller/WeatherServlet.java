package kr.or.iei.weather.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.weather.model.vo.Weather;


/**
 * Servlet implementation class WeatherServlet
 */
@WebServlet("/weather")
public class WeatherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeatherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// API URL을 만듭니다.
		URL url = new URL("https://apihub.kma.go.kr/api/typ01/url/fct_afs_dl.php?reg=11B10101&disp=0&help=1&authKey=-oaVHnMzRGmGlR5zM5RpHA");
		// HttpURLConnection 객체를 만들어 API를 호출합니다.
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		// 요청 방식을 GET으로 설정합니다.
		con.setRequestMethod("GET");
		// 요청 헤더를 설정합니다. 여기서는 Content-Type을 application/json으로 설정합니다.
		con.setRequestProperty("Content-Type", "application/json");

		// API의 응답을 읽기 위한 BufferedReader를 생성합니다. CP949 인코딩을 사용합니다.
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream(), "CP949"));
		String inputLine;
		StringBuffer respon = new StringBuffer();

		// 응답을 한 줄씩 읽어들이면서 StringBuffer에 추가합니다.
		while ((inputLine = in.readLine()) != null) {
			respon.append(inputLine);
		}
		// BufferedReader를 닫습니다.
		in.close();
		System.out.println(respon);
		
		String responStr = respon.toString();


        // #START7777#과 구분선 제거
		responStr = responStr.replace("#START7777#--------------------------------------------------------------------------------------------------#  단기예보 육상 조회 [입력인수형태][예] ?reg=&tmfc1=2013121018&tmfc2=2013121106&disp=0&help=1#--------------------------------------------------------------------------------------------------#  1. REG_ID   : 예보구역코드#  2. TM_FC    : 발표시각(년월일시분,KST)#  3. TM_EF    : 발효시각(년월일시분,KST)#  4. MOD      : 구간 (A01(24시간),A02(12시간))#  5. NE       : 발효번호#  6. STN      : 발표관서#  7. C        : 발표코드#  8. MAN_ID   : 예보관ID#  9. MAN_FC   : 예보관명# 10. W1       : 풍향1(16방위)# 11. T        : 풍향경향(1:-, 2:후)# 12. W2       : 풍향2(16방위)# 13. TA       : 기온# 14. ST       : 강수확률(%)# 15. SKY      : 하늘상태코드 (DB01(맑음),DB02(구름조금),DB03(구름많음),DB04(흐림))# 16. PREP     : 강수유무코드 (0(없음),1(비),2(비/눈),3(눈),4(눈/비(~'19.6.4.),소나기('19.6.4~)))# 17. WF       : 예보#--------------------------------------------------------------------------------------------------# REG_ID TM_FC        TM_EF        MOD NE STN C MAN_ID       MAN_FC     W1 T W2  TA  ST SKY  PREP WF","");  
        System.out.println(responStr);
		// 주어진 데이터 문자열 (여러 항목이 이어져 있는 데이터)

		// StringBuffer를 String으로 변환
        String responseStr = respon.toString();

        // 파싱된 Weather 객체를 저장할 리스트
        List<Weather> weatherList = new ArrayList<>();

        // 정규 표현식 패턴: 예보 데이터 추출 (REG_ID, TM_ST, TM_ED, REG_SP, REG_NAME)
        Pattern pattern = Pattern.compile(
                "(\\w{8,9})\\s+" +        // REG_ID
                "(\\d{12})\\s+" +         // TM_FC
                "(\\d{12})\\s+" +         // TM_EF
                "(\\w{3})\\s+" +          // MOD
                "(\\d)\\s+" +             // NE
                "(\\d{3})\\s+" +          // STN
                "(\\d)\\s+" +             // C
                "(\\w+)\\s+" +            // MAN_ID
                "([가-힣]+)\\s+" +        // MAN_FC
                "(\\w+)\\s+" +            // W1
                "(\\d)\\s+" +             // T
                "(\\w{2})\\s+" +          // W2
                "(\\d{2})\\s+" +          // TA
                "(\\d{2})\\s+" +          // ST
                "(\\w{4})\\s+" +          // SKY
                "(\\d)\\s+" +             // PREP
                "\"([^\"]*)\""            // WF (날씨 상태 텍스트)
            );

     // 데이터 파싱
        Matcher matcher = pattern.matcher(responseStr);

        while (matcher.find()) {
            String regId = matcher.group(1);
            String tmFc = matcher.group(2);
            String tmEf = matcher.group(3);
            String mod = matcher.group(4);
            String ne = matcher.group(5);
            String stn = matcher.group(6);
            String c = matcher.group(7);
            String manId = matcher.group(8);
            String manFc = matcher.group(9);
            String w1 = matcher.group(10);
            String t = matcher.group(11);
            String w2 = matcher.group(12);
            String ta = matcher.group(13);
            String st = matcher.group(14);
            String sky = matcher.group(15);
            String prepStr = "";
            String prep = matcher.group(16);
            switch(prep) {
            case "1":
            	prepStr="&비";
            	break;
            case "2":
            	prepStr="&비/눈";
            	break;
            case "3":
            	prepStr="&눈";
            	break;
            case "4":
            	prepStr="&눈/비";
            	break;
            }
            String wf = matcher.group(17)+prepStr;

            // Weather 객체 생성 및 리스트에 추가
            Weather weather = new Weather(regId, tmFc, tmEf, mod, ne, stn, c, manId, manFc, w1, t, w2, ta, st, sky, prep, wf);
            weatherList.add(weather);
            //System.out.println(weather);  // 각 Weather 객체 출력
        }
        HttpSession session = request.getSession();
        session.setAttribute("weather",weatherList.get(1));
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
