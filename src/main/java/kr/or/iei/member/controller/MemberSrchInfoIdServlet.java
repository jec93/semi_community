package kr.or.iei.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.member.model.service.MemberService;

/**
 * Servlet implementation class MemberSrchInfoIdServlet
 */
@WebServlet("/member/srchInfoId")
public class MemberSrchInfoIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberSrchInfoIdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String srchEmail=request.getParameter("userEmail");
		MemberService service=new MemberService();
		String memberId=service.srchInfoId(srchEmail);
		if(memberId!=null) {
			int idLen=memberId.length();
			String first = memberId.substring (0,3);
			String last=memberId.substring(idLen-3);
			String marker ="*".repeat(idLen-6);//아이디 길이에서 6글자 제외한 갯수만큼
			
			memberId=first+marker+last;
			
		}else {
			memberId="";
		}
		response.getWriter().print(memberId);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
