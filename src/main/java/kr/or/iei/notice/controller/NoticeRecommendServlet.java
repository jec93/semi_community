package kr.or.iei.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeRecommendServlet
 */
@WebServlet("/notice/recommend")
public class NoticeRecommendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeRecommendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userNo=request.getParameter("userNo");
		String postId=request.getParameter("postId");
		String boardWriter=request.getParameter("boardWriter");
		NoticeService service= new NoticeService();
		int result=service.likeControl(userNo,postId,boardWriter);
		
		if(result>0) {
			request.setAttribute("title", "성공");
			request.setAttribute("msg", "추천이 완료되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/notice/view?postId="+ postId+"&commentChk=chk");
		}else if(result==-1) {
			request.setAttribute("title", "실패");
			request.setAttribute("msg", "자기 게시글은 추천이 불가합니다.");
			request.setAttribute("icon", "warning");
			request.setAttribute("loc", "/notice/view?postId="+postId);
		}
		else {
			request.setAttribute("title", "실패");
			request.setAttribute("msg", "이미 추천한 게시글 입니다.");
			request.setAttribute("icon", "warning");
			request.setAttribute("loc", "/notice/view?postId="+postId);
		}
		request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
