package kr.or.iei.notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticePageData;
import kr.or.iei.notice.model.vo.PopNoticePageDate;

/**
 * Servlet implementation class NoticeListServlet
 */
@WebServlet("/notice/list")
public class NoticeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeListServlet() {
        super();
        // TODO Auto-generated constructor stub
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 인코딩 - 필터
		
		//2. 값 추출
		int boardId = Integer.parseInt(request.getParameter("boardId")); //게시글 종류 코드
		String boardName = request.getParameter("boardName"); //게시글 종류 명칭(공지사항, FAQ...)
		
		int reqPage = request.getParameter("reqPage") ==null?1:Integer.parseInt(request.getParameter("reqPage")); //사용자 요청 페이지번호
		
		NoticeService service = new NoticeService();
		//3. 로직 - 전체 게시글 조회
		if(boardId == 0) {
			//3_1인기 게시글인 경우
			PopNoticePageDate popularPd = service.selectPopularNoticeList(reqPage); // 인기 게시글 서비스 호출

		    //4. 결과 처리 
		    request.setAttribute("noticeList", popularPd.getPopularPosts());
		    request.setAttribute("pageNavi", popularPd.getPageNavi());
		    request.setAttribute("boardName", "인기게시판");
		    request.setAttribute("boardId", 0); // 인기 게시판을 나타내는 boardId는 0
		    request.getRequestDispatcher("/WEB-INF/views/notice/PopList.jsp").forward(request, response);
		}else {
			//3_2일반 게시글인 경우
			NoticePageData pd = service.selectNoticeList(boardId,reqPage, boardName);
			
			//4. 결과 처리 
			request.setAttribute("noticeList", pd.getList());
			request.setAttribute("pageNavi", pd.getPageNavi());
			request.setAttribute("boardName", boardName);
			request.setAttribute("boardId", boardId);
			request.getRequestDispatcher("/WEB-INF/views/notice/list.jsp").forward(request, response);
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
