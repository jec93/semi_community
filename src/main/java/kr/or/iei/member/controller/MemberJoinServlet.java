package kr.or.iei.member.controller;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.member.model.service.MemberService;

import kr.or.iei.member.model.vo.User;

/**
 * Servlet implementation class MemberJoinServlet
 */
@WebServlet("/member/join")
public class MemberJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberJoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//1. 인코딩 -> EncodingFilter.java에서 수행
		
		//2. 값 추출
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String userName = request.getParameter("nickname");
		String userPhone = request.getParameter("userPhone");
		String userEmail = request.getParameter("userEmail");
		
		
	
		
		
		//3.비즈니스 로직 - 회원가입
		User u = new User();
		u.setUserId(userId);
		u.setUserPw(userPw);
		u.setNickname(userName);
		u.setPhone(userPhone);
		u.setUserEmail(userEmail);
		
		
		MemberService service = new MemberService();
		int result2=service.chkEmail(userEmail);
		int result3=service.chkPhone(userPhone);
		if(result2>0) {
			request.setAttribute("title", "실패");
			request.setAttribute("msg", "해당 이메일로는 가입이 불가능 합니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/member/joinFrm");
		}else if(result3>0) {
			request.setAttribute("title", "실패");
			request.setAttribute("msg", "해당 전화번호로는 가입이 불가능 합니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/member/joinFrm");
		}else {
		int result = service.insertMember(u);
		
		//4.결과 처리 - 로그인 화면으로 이동
		if(result>0) {
			request.setAttribute("title", "성공");
			request.setAttribute("msg", "회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/member/loginFrm");
		}else {
			request.setAttribute("title", "실패");
			request.setAttribute("msg", "회원가입에 실패하였습니다. 메인 페이지로 이동합니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/");
		}
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
