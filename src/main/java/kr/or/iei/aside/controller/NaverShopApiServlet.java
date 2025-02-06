package kr.or.iei.aside.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import kr.or.iei.aside.model.vo.Product;

/**
 * Servlet implementation class NaverShopApiServlet
 */
@WebServlet("/naverShop")
public class NaverShopApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NaverShopApiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		String clientId = "o3gVuFJjxpwDv5Y0Xmbj"; //애플리케이션 클라이언트 아이디
        String clientSecret = "D4hHT4K4TO"; //애플리케이션 클라이언트 시크릿

        String text = null;
        try {
            text = URLEncoder.encode("그래픽카드", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }


        String apiURL = "https://openapi.naver.com/v1/search/shop?query=" + text;    // JSON 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

      //아래는 기존 네이버 제공 코드에서 json 파싱 추가 코드, 객체리스트 출력
        List<Product> productItems = parseNewsItems(responseBody);
        
        request.setAttribute("list", productItems);
        request.getRequestDispatcher("/WEB-INF/views/AllShopList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }
	
	private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }
    //json 파싱 코드
    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
    
  //api, String -> Json
    private List<Product> parseNewsItems(String jsonResponse) {
        List<Product> Items = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray itemsArray = jsonObject.getJSONArray("items");

        int limit = Math.min(itemsArray.length(), 3); // 최대 3개까지만 처리

        for (int i = 0; i < limit; i++) { // i < limit 조건으로 변경
            JSONObject itemObject = itemsArray.getJSONObject(i);

            String shopTitle = itemObject.getString("title");
            String shopLink = itemObject.getString("link");
            String shopImg = itemObject.getString("image");
            int shopLowPrice = itemObject.getInt("lprice");
            String shopName = itemObject.getString("mallName");
            String shopCategory1 = itemObject.getString("category1");
            

            Product productItem = new Product(shopTitle, shopLink, shopImg,shopLowPrice,shopName,shopCategory1);
            Items.add(productItem);
        }

        return Items;
    }
}
