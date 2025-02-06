package kr.or.iei.notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeComment;
import kr.or.iei.notice.model.vo.NoticeFile;

/**
 * Servlet implementation class NoticeDeleteServlet
 */
@WebServlet("/notice/delete")
public class NoticeDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String postId=request.getParameter("postId");
		NoticeService service=new NoticeService();
		Notice deleteNotice=service.selectDelNotice(postId);
		int boardId= deleteNotice.getBoardId();
			String boardName=deleteNotice.getBoardName();
					
			//파일삭제 하는거 어떻게 하는지 까먹음
			
			
			
			
			
					int control=0;
					
					if(deleteNotice.getFileList().size()<1) {
						control=control;
					}else {
						control+=deleteNotice.getFileList().size();
					}
					if(deleteNotice.getCommentList().size()<1) {
						control=control;
					}else {
						control+=deleteNotice.getCommentList().size();
					}
					
					int result=service.deleteNotice(deleteNotice,control);
			
			if(result > control) {
				request.setAttribute("title", "알림");
				request.setAttribute("msg", "게시글이 삭제 되었습니다.");
				request.setAttribute("icon", "success");
				request.setAttribute("loc", "/notice/list?boardId="+boardId+"&boardName="+boardName);
			}else {
				request.setAttribute("title", "알림");
				request.setAttribute("msg", "게시글 삭제중, 오류발생.");
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
