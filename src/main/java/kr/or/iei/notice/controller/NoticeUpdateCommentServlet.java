package kr.or.iei.notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.NoticeComment;

/**
 * Servlet implementation class NoticeUpdateCommentServlet
 */
@WebServlet("/notice/updateComment")
public class NoticeUpdateCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String commentId = request.getParameter("commentId");
		String postId =  request.getParameter("postId");
		String comments = request.getParameter("commentContent");
		
		NoticeComment comment = new NoticeComment();
		comment.setCommentId(commentId);
		comment.setPostId(postId);
		comment.setComments(comments);
		
		NoticeService service= new NoticeService();
		int result = service.updateComment(comment);
		
		if(result>0) {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "댓글 수정이 완료되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/notice/view?postId="+postId+"&commentChk=chk");

		}else {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "댓글 수정중 오류가 발생");
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
