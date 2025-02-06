package kr.or.iei.member.controller;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.member.model.service.MemberService;

import kr.or.iei.member.model.vo.User;

/**
 * Servlet implementation class MemberAdminPageServlet
 */
@WebServlet("/member/adminPage")
public class MemberAdminPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberAdminPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =  request.getSession(false);
		if(session != null) {
			User loginMember = (User) session.getAttribute("loginMember");
			
			//현재 로그인된 회원 레벨이 1이 아닐 때 (관리자가 아닐 때)
			if(100 != loginMember.getUserGrade()) {
				request.setAttribute("title", "알림");
				request.setAttribute("msg", "해당 메뉴에 대한 접속 권한이 없습니다.");
				request.setAttribute("icon", "error");
				request.setAttribute("loc", "/member/mypage");
				
				request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
				
				return;
			}
		}
		//1. 인코딩 - 필터
		
		//2. 값 추출
		
		
		//3. 로직 - 전체 회원 조회
		MemberService service = new MemberService();
		ArrayList<User> list = service.selectAllUser();
		
		
		//4. 결과 처리
		request.setAttribute("memberList", list);
		request.getRequestDispatcher("/WEB-INF/views/member/adminPage.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
