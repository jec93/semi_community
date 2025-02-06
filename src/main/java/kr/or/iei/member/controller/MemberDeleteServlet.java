package kr.or.iei.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.member.model.service.MemberService;

/**
 * Servlet implementation class MemberDeleteServlet
 */
@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	
		//1.인코딩 - 필터
		
		//2.값 추출
		String userNo = request.getParameter("userNo"); //삭제 시 필요한 값
		
		//3.로직 - 회원 탈퇴
		MemberService service = new MemberService();
		int result = service.deleteMember(userNo);
		
		//4.결과 처리
		if(result >0) {
			HttpSession session = request.getSession(false);
			if(session != null) {
				session.invalidate();
			}
			
			//정상 탈퇴
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "회원 탈퇴가 완료되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/");
		}else {
			//탈퇴 실패
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "회원 탈퇴 중, 오류가 발생하였습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/member/mypage");
		}
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
