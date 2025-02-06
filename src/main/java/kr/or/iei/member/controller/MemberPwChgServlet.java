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

import kr.or.iei.member.model.vo.User;

/**
 * Servlet implementation class MemberPwChgServlet
 */
@WebServlet("/member/pwChg")
public class MemberPwChgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberPwChgServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//1. 인코딩 : 필터
		
		//2. 값 추출
		String userNo = request.getParameter("userNo");
		String userPw = request.getParameter("userPw"); //기존 비밀번호
		String newUserPw = request.getParameter("newUserPw"); //새 비밀번호
		
		//3. 로직 - 비밀번호 변경
		
		//3.1 사용자 입력한 기존 비밀번호와, 등록되어있는 비밀번호가 같은지 비교
		/* 로그인 시, 등록한 세션의 지속시간은 10분으로 설정하였음.
		 * 세션이 만료되었을 때, 아래 코드는 null.getAttribute()로 NullPointerException 발생할 우려가 있다.
		 * 
		 * Member loginMember = (Member)request.getSession(false).getAttribute("loginMember");
		 * //세션에 등록되어 있는 기존 회원 정보
		 */
		HttpSession session = request.getSession();
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		
		if(session !=null) {
			User loginMember = (User)session.getAttribute("loginMember");
			
			if(!loginMember.getUserPw().equals(userPw)) {
				request.setAttribute("title", "실패");
				request.setAttribute("msg", "기존 비밀번호가 일치하지 않습니다.");
				request.setAttribute("icon", "error");
				request.setAttribute("callback", "self.close();"); //이동한 msg.jsp에서, 알림창을 띄워주고난 이후에 실행할 함수 등록
				
				view.forward(request, response);
				return;
			}
			MemberService service = new MemberService();
			int result =service.updateMemberPw(userNo, newUserPw);

			//4.결과 처리
			if(result > 0) {
				request.setAttribute("title", "성공");
				request.setAttribute("msg", "변경 성공 로그인을 다시해주세요");
				request.setAttribute("icon", "success");
				request.setAttribute("callback", "self.close(); window.opener.location.href=\"/member/loginFrm\";");
				//세션 정보 소멸
				session.invalidate();
			}else {
				request.setAttribute("title", "실패");
				request.setAttribute("msg", "비밀번호 변경 중, 오류가 발생하였습니다.");
				request.setAttribute("icon", "error");
				request.setAttribute("callback", "self.close();");
			}
		}
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
