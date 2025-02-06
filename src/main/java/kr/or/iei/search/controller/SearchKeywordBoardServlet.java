package kr.or.iei.search.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.Notice;

/**
 * Servlet implementation class SearchKeywordBoardServlet
 */
@WebServlet("/searchBoard")
public class SearchKeywordBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchKeywordBoardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩 설정
	    request.setCharacterEncoding("UTF-8"); // 요청 인코딩 설정
	    response.setContentType("text/html; charset=UTF-8"); // 응답 인코딩 설정
	    
	    String searchNotice = request.getParameter("search");
	    if (searchNotice == null || searchNotice.trim().isEmpty()) {
	        response.getWriter().write("<li><a >검색 결과가 없습니다.</a></li>");
	        return;
	    }

	    // 검색 로직
	    NoticeService service = new NoticeService();
	    ArrayList<Notice> searchResults = service.srchNotice(searchNotice);
	    StringBuilder htmlBuilder = new StringBuilder();
	    if (searchResults.isEmpty()) {
	        htmlBuilder.append("<li><a '>검색 결과가 없습니다.</a></li>");
	    } else {
	        for (Notice notice : searchResults) {
	        	htmlBuilder.append("<li>")
                .append("<a href='/notice/list?reqPage=1&boardId=")
                .append(notice.getBoardId())  // Custom boardId를 사용
                .append("&boardName=")
                .append(URLEncoder.encode(notice.getBoardName(), "UTF-8")) // URL-safe 인코딩
                .append("'>")
                .append(notice.getBoardName()) // 게시판 이름 출력
                .append("</a></li>");
	        }
	    }

	    response.getWriter().write(htmlBuilder.toString());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
