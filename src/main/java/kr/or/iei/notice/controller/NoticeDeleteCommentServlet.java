package kr.or.iei.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeDeleteCommentServlet
 */
@WebServlet("/notice/deleteComment")
public class NoticeDeleteCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDeleteCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String postId = request.getParameter("postId");
		String commentId = request.getParameter("commentId");
		
		NoticeService service = new NoticeService();
		int result = service.deleteComment(commentId);
		
		if(result > 0) {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "댓글이 삭제 되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/notice/view?postId="+postId+"&commentChk=chk");
		}else {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "댓글이 삭제중, 오류발생.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/notice/view?postId="+postId+"&commentChk=chk");
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
