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
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//1. 인코딩 - 필터
		
		//2. 값추출
		String userNo = request.getParameter("userNo"); //수정 시, where 조건식에 필요한 필터
		String userName = request.getParameter("nickname");
		String userEmail = request.getParameter("userEmail");
		String userPhone = request.getParameter("userPhone");
		//3. 로직-정보수정
		User updUser = new User();
		updUser.setUserNo(userNo);
		updUser.setNickname(userName);
		updUser.setUserEmail(userEmail);
		updUser.setPhone(userPhone);
		MemberService service = new MemberService();
		int result2= service.chkEmail(userEmail);
		int result3=service.chkPhone(userPhone);
		int result=0;
		
		if(result2>0) {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "해당 이메일은 사용이 불가능 합니다");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/member/mypag");
		}else if(result3>0) {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "해당 전화번호는 사용이 불가합니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/member/mypag");
		}else {
			result = service.updateMember(updUser);
		
		//4. 결과처리
		
		if(result>0) {
			/*정상 정보 수정 시, 다시 로그인 하도록 할것임.
			 * 즉, 로그인이 풀린다 == 세션에 등록된 정보를 소멸시킨다.
			 * 로그아웃 서블릿에 존재 == 로그아웃 서블릿으로 sendRedirect == 상단 url에 주소값이 변경
			 * 현재 회원 정보 수정 서블릿에서 세션을 파기
			 
			HttpSession session = request.getSession(false);
			if(session != null) {
				session.invalidate();
			}
			*/
			// 2)정상적으로 수정되었을 때, 다시 마이페이지로 이동
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "회원정보가 수정되었습니다..");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/member/mypage");
			
			// 2-1) 아무런 작업없이, mypage.jsp로 이동하는 경우, 세션에는 정보가 업데이트 되지 않았기 때문에 수정 이전의 데이터가 표기
			/*
			 * 기존 로그인 시, 사용된 메소드 재사용
			 * mypage.jsp에서 id와 pw를 전송해주거나, 현재 서블릿에서 세션에 존재하는 회원 정보 중, id와 pw를 get하거나
			 * Member m = service.memberLogin(updMember.getMemberId(), updMember.getMemberPw());
			 * HttpSession session = request.getSession(false);
			 * session.setAttribute("loginMember",m); 
			service.memberLogin(updMember.getMemberId(), updMember.getMemberPw());
			 */
			
			//세션에서 등록된 회원 정보 중, 수정시 작성한 정보 다시 set!!
			HttpSession session = request.getSession(false);
			User sessionMember = (User) session.getAttribute("loginMember");
			
			sessionMember.setNickname(userName);
			sessionMember.setPhone(userPhone);
			
			sessionMember.setUserEmail(userEmail);
		}else {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "회원정보 수정 중, 오류가 발생");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/member/mypag");
		}
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
