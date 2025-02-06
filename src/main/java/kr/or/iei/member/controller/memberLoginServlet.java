package kr.or.iei.member.controller;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.member.model.service.MemberService;

import kr.or.iei.member.model.vo.User;

/**
 * Servlet implementation class memberLoginServlet
 */
@WebServlet("/member/login")
public class memberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public memberLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//1.인코딩 -> 필터
		//2. 값 추출
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");
		//3. 로직 -> 로그인
		MemberService service = new MemberService();
		User loginMember = service.userLogin(loginId, loginPw);
		//4. 결과 처리

		
		
		if(loginMember !=null) {
			//정상 로그인!!
			
			//추후 밴 구현시 기능 구현 예정
			/*
			if(loginMember.getUserGrade() == 0) {
				request.setAttribute("title", "알림");
				request.setAttribute("msg", "로그인 권한이 없습니다. 관리자에게 문의하세요");
				request.setAttribute("icon", "error");
				request.setAttribute("loc", "/member/loginFrm");
				
				request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
				return;
			}*/
			/*
			 * getSession() : 매개변수에 true를 전달한 것과 동일
			getSession(true): 기존에 세션이 종료하면, 해당 세션을 반환. 존재하지 않으면 새로운 세션을 만들어서 반환
			getSession(false) : 기존에 세션이 존재하면, 해당 세션을 반환. 존재하지 않으면 null을 반환
			*/
			HttpSession session = request.getSession();
			session.setAttribute("loginMember", loginMember); //새션에 로그인 회원 정보 등록
			session.setMaxInactiveInterval(600); //단위 == 초 ==600초 == 10분
			
			/*
			 * 쿠키 : 클라이언트에 저장되는 공간을 의미한다.
			 * - 세션에 비해 상대적으로 보안이 취약하다.
			 * -
			 * */
			
			Cookie cookie = new Cookie("saveId",loginId);
			
			if(request.getParameter("saveId") !=null) {
				//아이디 저장 체크박스를 체크한 경우
				Cookie saveIdCookie = new Cookie("saveId", request.getParameter("loginId"));
			    saveIdCookie.setMaxAge(60 * 60 * 24 * 7); // 7일 동안 쿠키 저장
			    response.addCookie(saveIdCookie);
				//단위==초==60*60*24*30==30일
			}else {
				//아이디 저장 체크박스를 체크하지 않은 경우
				Cookie saveIdCookie = new Cookie("saveId", null); // 쿠키 삭제
			    saveIdCookie.setMaxAge(0);
			    response.addCookie(saveIdCookie);
				 //유효시간을 0초로 변경하여, 결론적으로 쿠키를 해제한다.
			}
			
			//쿠키를 적용시킬 경로
			cookie.setPath("/member/loginFrm");
			
			//쿠키는 클라이언트에 저장되는 정보이므로, 응답 객체를 통해 전달해줌
			response.sendRedirect("/");
		}else {
			//null 일때 == 입력한 아이디, 비밀번호와 일치하는 회원이 없을 때 == 로그인 실패
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "아이디 또는 비밀번호를 확인하세요");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/member/loginFrm"); //다시 로그인 페이지 이동
			
			RequestDispatcher view= request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
			view.forward(request, response);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
