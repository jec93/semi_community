package kr.or.iei.notice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.PopularPost;

/**
 * Servlet implementation class NoticePopularServlet
 */
@WebServlet("/notice/popular")
public class NoticePopularServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticePopularServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 NoticeService service= new NoticeService();
		 List<PopularPost> popularPosts = service.getPopularPosts(); // popular_post 테이블에서 데이터 가져오기

		 Gson gson=new Gson();
		 String jsonStr=gson.toJson(popularPosts);
		 
		 response.setCharacterEncoding("utf-8");
	     response.setContentType("application/json");
	     response.getWriter().print(jsonStr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
