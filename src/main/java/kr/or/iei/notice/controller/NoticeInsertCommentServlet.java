package kr.or.iei.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.NoticeComment;

/**
 * Servlet implementation class NoticeInsertCommentServlet
 */
@WebServlet("/notice/insertComment")
public class NoticeInsertCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//1.인코딩
		
		//2.값 추출
		String replyTo=request.getParameter("replyTo");
		String commentRef = request.getParameter("postId");
		String commentWriter = request.getParameter("commentWriter");
		String commentContent = request.getParameter("commentContent");
		
		//3.로직
		NoticeComment comment = new NoticeComment();
		comment.setPostId(commentRef);
	    comment.setCommentWriter(commentWriter);
		comment.setComments(commentContent);
		comment.setReplyTo(replyTo);
		NoticeService service = new NoticeService();
		int result = service.insertComment(comment);
		
		//4.결과 처리
		if(result>0) {
			request.setAttribute("title", "성공");
			request.setAttribute("msg", "댓글이 작성되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/notice/view?postId="+ commentRef+"&commentChk=chk");
		}else {
			request.setAttribute("title", "실패");
			request.setAttribute("msg", "댓글 작성 중,오류가 발생하였습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/notice/view?noticeNo="+commentRef);
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
