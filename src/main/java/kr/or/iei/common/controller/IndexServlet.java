package kr.or.iei.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.aside.model.vo.Product;
import kr.or.iei.common.vo.DataFetcher;
import kr.or.iei.news.model.vo.NewsItem;
import kr.or.iei.search.word.service.SearchService;
import kr.or.iei.search.word.vo.Word;
import kr.or.iei.weather.model.vo.Weather;

/**
 * Servlet implementation class IndexServlet
 */

public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session = request.getSession();
    	// 현재 시간을 밀리초로 가져오기
    	long currentTime = System.currentTimeMillis();

    	// 세션에서 저장된 마지막 갱신 시간을 가져오기
    	Long lastUpdateTime = (Long) session.getAttribute("lastUpdateTime");

    	// 세션에 데이터가 없거나 10분이 지난 경우 데이터 갱신
    	if (lastUpdateTime == null || (currentTime - lastUpdateTime) > 10 * 60 * 1000) {
    	    // 검색어 랭킹 데이터 가져오기
    	    SearchService service = new SearchService();
    	    ArrayList<Word> list = service.selectAllWord();
    	    
    	    // 애플리케이션에 저장 (wordList는 여러 사용자가 공유할 수 있도록)
    	    getServletContext().setAttribute("wordList", list);

    	    // 네이버 쇼핑/뉴스 데이터 가져오기
    	    List<Product> productItems = (List<Product>) session.getAttribute("productItems");
    	    List<NewsItem> newsItems = (List<NewsItem>) session.getAttribute("newsItems");

    	    if (productItems == null || newsItems == null) {
    	        Map<String, Object> result = DataFetcher.fetchNaverData();
    	        productItems = (List<Product>) result.get("productItems");
    	        newsItems = (List<NewsItem>) result.get("newsItems");

    	        session.setAttribute("productList", productItems);
    	        session.setAttribute("newsList", newsItems);
    	    }

    	    /*
    	    // 날씨 데이터 가져오기
    	    Weather weather = (Weather) session.getAttribute("weather");
    	    if (weather == null) {
    	        weather = DataFetcher.fetchWeatherData();
    	        if (weather != null) {
    	            session.setAttribute("weather", weather); // 날씨 데이터 세션에 저장
    	        }
    	    }
*/
    	    // 갱신된 시간을 세션에 저장 (10분 동안 유효)
    	    session.setAttribute("lastUpdateTime", currentTime);

    	    // 쇼핑 및 뉴스 데이터 JSP 전달 (필요하면)
    	    request.setAttribute("productList", productItems);
    	    request.setAttribute("newsList", newsItems);
    	} else {
    	    // 10분 이내에 데이터가 갱신된 경우, 세션에서 기존 데이터를 그대로 사용

    	    // 쇼핑 및 뉴스 데이터 JSP 전달 (필요하면)
    	    List<Product> productItems = (List<Product>) session.getAttribute("productList");
    	    List<NewsItem> newsItems = (List<NewsItem>) session.getAttribute("newsList");
    	    request.setAttribute("productList", productItems);
    	    request.setAttribute("newsList", newsItems);
    	}

    	// 인덱스 페이지로 포워딩
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
    	dispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
