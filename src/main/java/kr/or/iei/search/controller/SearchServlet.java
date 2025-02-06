package kr.or.iei.search.controller;

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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import kr.or.iei.news.model.vo.NewsItem;
import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.search.word.service.SearchService;
import kr.or.iei.search.word.vo.Blog;
import kr.or.iei.search.word.vo.Img;
import kr.or.iei.search.word.vo.Word;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//2.값추출
		String srchInput = request.getParameter("search");
		
		// 검색어가 없거나 값이 비어 있는 경우 처리
		if (srchInput == null || srchInput.trim().isEmpty()) {
		    // 이전 페이지 URL 가져오기
		    String referer = request.getHeader("referer");
		    if (referer != null && !referer.trim().isEmpty()) {
		        // referer가 유효하면 이전 페이지로 리다이렉트
		        response.sendRedirect(referer);
		    } else {
		        // referer가 없거나 비어 있으면 기본 페이지로 이동
		        request.getRequestDispatcher("/index").forward(request, response);
		    }
		}else {
			
			request.setAttribute("search", srchInput);
			
			//3.비즈니스 로직 - 검색
			//_1 통합검색용 api 호출
			//네이버 api 시작부분
			String clientId = "o3gVuFJjxpwDv5Y0Xmbj"; //애플리케이션 클라이언트 아이디
			String clientSecret = "D4hHT4K4TO"; //애플리케이션 클라이언트 시크릿
			
			String productText = null; //
			String NewsText = null;
			String imgText = null;
			try {
				productText = URLEncoder.encode(srchInput, "UTF-8");
				NewsText = URLEncoder.encode(srchInput, "UTF-8");
				imgText = URLEncoder.encode(srchInput, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("검색어 인코딩 실패",e);
			}
			
			
			String bloApiURL = "https://openapi.naver.com/v1/search/blog?query=" + productText;    // 숍 JSON 결과
			String NewsApiURL = "https://openapi.naver.com/v1/search/news?query=" + NewsText;
			String imgApiURL = "https://openapi.naver.com/v1/search/image?query=" + imgText;
			
			Map<String, String> requestHeaders = new HashMap<>();
			requestHeaders.put("X-Naver-Client-Id", clientId);
			requestHeaders.put("X-Naver-Client-Secret", clientSecret);
			String blResponseBody = get(bloApiURL,requestHeaders);
			String nwResponseBody = get(NewsApiURL,requestHeaders);
			String imResponseBody = get(imgApiURL,requestHeaders);
			
			//아래는 기존 네이버 제공 코드에서 json 파싱 추가 코드, 객체리스트 출력, 숫자는 가져오는 갯수
			List<Blog> blogItems = parseBlogItems(blResponseBody);
			List<NewsItem> newsItems = parseNewsItems(nwResponseBody);
			List<Img> imgItems = parseImgItems(imResponseBody);
			
			request.setAttribute("blogList", blogItems);
			request.setAttribute("newsList", newsItems);
			request.setAttribute("imgList", imgItems);
			//네이버 api 끝부분
			
			
			NoticeService service =  new NoticeService();
			ArrayList<Notice> srchNoticeNm = service.srchNotice(srchInput);
			//게시판 검색 끝
			
			request.setAttribute("srchNoticeNm", srchNoticeNm);
			
			
			
			
			//_2 명사구별 -> 검색어 저장
			ArrayList<Word> words = new ArrayList<Word>();
			//문장의 명사들을 뽑는 노가다 작업(?) 외부 라이브러리 실패, 부조사들은 추후 추가하거나 라이브러리 연결
			String[] wordsToRemove = {"이 ", "가 ", "을 ", "를 ", "의 ", "에 ", "에서 ", "로 ", "으로 ", "더러 ", "까지 ", "부터 ", "와 ", "과 ","랑 ", "이랑 ", "도 ", "만 ","뿐 ", "마저 ", "까지 ", "조차 ","나마 ", "마다 ","하고 ","게 ","입니다.","께서 ","께 ","조차도 ", "이나마 ", "이만큼 ", "쯤 ", "마저도 ","보다 ", "로서 ", "하러 ", "한테서 ", "한테 ", "대해 ", "데에 ", "탓에 ", "위해 ","이다.", "군요.","네요.","더라 "};
			for(String ch: wordsToRemove) {
				srchInput = srchInput.replaceAll(ch, " "); //부조사 삭제 작업
			}
			
			SearchService searchService = new SearchService();
			Boolean result = searchService.searchString(srchInput);
			
			//4.결과 처리
			RequestDispatcher view = null;
			if(result) {
				ArrayList<Word> wordList = searchService.selectAllWord();
				request.setAttribute("wordList",wordList ); //wordList로 list 객체 등록
				request.getRequestDispatcher("/WEB-INF/views/searchResult.jsp").forward(request, response);
			}else {
				request.getRequestDispatcher("/WEB_INF/views/search/InsertFail.jsp").forward(request, response);
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	//네이버 api 기능 시작부분
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
	            	String errorResponse = readBody(con.getErrorStream());
	                System.out.println("Error Response: " + errorResponse); // 오류 메시지 출력
	                return errorResponse;
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
	    
	    //네이버 블로그 파싱
	    private List<Blog> parseBlogItems(String jsonResponse) {
	        List<Blog> items = new ArrayList<>();
	        JSONObject jsonObject = new JSONObject(jsonResponse);

	        // "items" 키가 없거나 빈 배열일 경우 처리
	        if (!jsonObject.has("items") || jsonObject.getJSONArray("items").length() == 0) {
	            return items;
	        }

	        JSONArray itemsArray = jsonObject.getJSONArray("items");
	        int limit = Math.min(itemsArray.length(), 5);

	        for (int i = 0; i < limit; i++) {
	            JSONObject itemObject = itemsArray.getJSONObject(i);
	            String blogTitle = itemObject.getString("title");
	            String blogLink = itemObject.getString("link");
	            String blogDescription = itemObject.getString("description");

	            Blog blogItem = new Blog(blogTitle, blogLink, blogDescription);
	            items.add(blogItem);
	        }

	        return items;
	    }
	    
	    //네이버 뉴스 파싱
	    private List<NewsItem> parseNewsItems(String jsonResponse) {
	        List<NewsItem> items = new ArrayList<>();

	        JSONObject jsonObject = new JSONObject(jsonResponse);
	        // "items" 키가 없거나 빈 배열일 경우 처리
	        if (!jsonObject.has("items") || jsonObject.getJSONArray("items").length() == 0) {
	            return items;
	        }
	        JSONArray itemsArray = jsonObject.getJSONArray("items");

	        int limit = Math.min(itemsArray.length(), 5); // 5개 만큼 출력

	        for (int i = 0; i < limit; i++) { // i < limit 조건으로 변경
	            JSONObject itemObject = itemsArray.getJSONObject(i);

	            String title = itemObject.getString("title");
	            String link = itemObject.getString("link");
	            String description = itemObject.getString("description");
	            
	            NewsItem newsItem = new NewsItem(title, link, description);
	            items.add(newsItem);
	        }

	        return items;
	    }
	    
	  //네이버 이미지 파싱
	    private List<Img> parseImgItems(String jsonResponse) {
	        List<Img> items = new ArrayList<>();

	        JSONObject jsonObject = new JSONObject(jsonResponse);
	        // "items" 키가 없거나 빈 배열일 경우 처리
	        if (!jsonObject.has("items") || jsonObject.getJSONArray("items").length() == 0) {
	            return items;
	        }
	        JSONArray itemsArray = jsonObject.getJSONArray("items");

	        int limit = Math.min(itemsArray.length(), 10); // 최대 10개

	        for (int i = 0; i < limit; i++) {
	            JSONObject itemObject = itemsArray.getJSONObject(i);

	            String imgTitle = itemObject.getString("title");
	            String imgLink = itemObject.getString("link");
	            String imgThumbnail = itemObject.getString("thumbnail");

	            // 유효한 URL 검사 후, 사용할 URL 설정
	            String validImgSrc = isValidUrl(imgThumbnail) ? imgThumbnail : imgLink;

	            Img imgItem = new Img(imgTitle, imgLink, validImgSrc);
	            items.add(imgItem);
	        }
	        return items;
	    }
	    
	    //이미지 유효 확인(섬네일 주소vs링크 주소)
	    private boolean isValidUrl(String url) {
	        try {
	            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	            connection.setRequestMethod("HEAD");
	            return (connection.getResponseCode() == HttpURLConnection.HTTP_OK);
	        } catch (Exception e) {
	            return false;
	        }
	    }
	  //네이버 api 기능 끝부분
}
