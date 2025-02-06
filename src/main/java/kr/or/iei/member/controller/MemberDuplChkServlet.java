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
 * Servlet implementation class MemberDuplChkServlet
 */
@WebServlet("/idDuplChk")
public class MemberDuplChkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDuplChkServlet() {
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
		
		//2.값추출
		String userId = request.getParameter("userId");
		
		//3.비즈니스 로직 - 아이디 중복 체크
		MemberService service = new MemberService();
		int result = 0; 
		result = service.idDuplChk(userId);
		result+=service.selectBannUserId(userId);
		//4.결과처리
		/*
		if(result > 0) {
			//중복된 아이디가 존재하는 경우
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "중복된 아이디가 존재합니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/member/joinFrm");
		}else {
			//중복된 아이디가 없는 경우 == 회원가입 가능!
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "사용할 수 있는 아이디입니다.");
			request.setAttribute("icon", "sucess");
			request.setAttribute("loc", "/member/joinFrm");
		}
		*/
		//jsp에서 ajax(비기통신)을 이용하여
		response.getWriter().print(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
