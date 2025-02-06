package kr.or.iei.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.member.model.service.MemberService;

/**
 * Servlet implementation class MemberBannAllUserServlet
 */
@WebServlet("/member/bannUsers")
public class MemberBannAllUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberBannAllUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userNoArr = request.getParameter("userNoArr");
		String userIdArr = request.getParameter("userIdArr");
		String userEmailArr = request.getParameter("userEmailArr");
		String phoneArr = request.getParameter("phoneArr");
		MemberService service = new MemberService();
		int result = service.deleteAllUser(userNoArr,userIdArr,userEmailArr,phoneArr);
		
		if(result==-1) {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "밴 유저 테이블 삽입중 문제가 생겼습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/member/adminPage");
		}else if( result==0) {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "강퇴중 문제가 발생했습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/member/adminPage");
		}else {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "강퇴에 성공했습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/member/adminPage");
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
