package kr.or.iei.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.User;

/**
 * Servlet implementation class MemberBannServlet
 */
@WebServlet("/member/bann")
public class MemberBannServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberBannServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bannNo=request.getParameter("userNo");
		String userId=request.getParameter("userId");
		String userEmail=request.getParameter("userEmail");
		String phone=request.getParameter("phone");
		MemberService service=new MemberService();
		
		int result=0;
		result+=service.addBann(bannNo,userId,userEmail,phone);
		if(result>0) {
			result+=service.bannUser(bannNo);
		}
		if(result>1) {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "강퇴에 성공했습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/member/adminPage");
			
		}else if(result==1) {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "강퇴중 문제가 발생했습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/member/adminPage");
			
		}else {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "밴 유저 테이블 삽입중 문제가 생겼습니다.");
			request.setAttribute("icon", "error");
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
