package kr.or.iei.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.member.model.service.MemberService;

/**
 * Servlet implementation class MemberChgLevelServlet
 */
@WebServlet("/member/chgLevel")
public class MemberChgLevelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberChgLevelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 인코딩 - 필터
		
		//2. 값 추출
		String userNo = request.getParameter("userNo");
		String userGrade = request.getParameter("userGrade");
		
		//3. 로직
		MemberService service = new MemberService();
		
		int result = service.updChgLevel(userNo, userGrade);
		
		if(result>0) {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "등급 변경이 완료되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/member/adminPage");
		}else {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "등급 변경중 오류가 발생했습니다..");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/member/adminPage");
		}
		//4. 결과처리
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
