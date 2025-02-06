package kr.or.iei.notice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeAnnounceUpdateServlet
 */
@WebServlet("/NoticeAnnounceUpdate")
public class NoticeAnnounceUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeAnnounceUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String boardId = request.getParameter("boardId"); //게시글 종류 코드
        String boardName = request.getParameter("boardName"); //게시글 종류 명칭(공지사항, FAQ...)

        // 서비스 객체 호출하여 공지 리스트 조회
        NoticeService service = new NoticeService();

        // 클라이언트로부터 받은 selectedPostIds 값들
        String[] postIds = request.getParameterValues("selectedPostIds");
        if (postIds != null) {
            // 각 postId에 대해 처리
            List<String> postIdList = new ArrayList<>();
            for (String postId : postIds) {
                try {
                    postIdList.add(postId); // 리스트에 추가
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // 예외 처리 (필요시 로깅 추가)
                }
            }

            // 공지사항 업데이트 로직 호출
            boolean result = service.updateAnounce(postIdList);
            if (result) {
                request.setAttribute("boardName", boardName);
                request.setAttribute("boardId", boardId);
                
            	request.getRequestDispatcher("/notice/list").forward(request, response);
            } else {
                // 실패 시 처리 (예: 에러 메시지 처리)
                response.getWriter().write("공지사항 업데이트 실패");
            }
        } else {
            System.out.println("선택된 ID가 없습니다.");
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
