package kr.or.iei.notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeIndexServlet
 */
@WebServlet("/notice/index")
public class NoticeIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		NoticeService service= new NoticeService();
		ArrayList<Notice>list=service.selectIndexNoticeList();
		
		//{noticeCd:"", noticeCdNm: "", noticeTitle:""}
		/*
		ArrayList<Notice>list=new ArrayList<Notice>(); 
		JSONArray jsonArr=new JSONArray();
		for(Notice n:list) {
			JSONObject json=new JsonObject();
			json.put("noticeCd",n.getNoticeCd());
			json.put("noticeTitle",n.getNoticeTitle());
			json.put("noticeWriter",n.getNoticeWriter());
			json.put("noticeDate",n.getNoticeDate());
			json.add(json);
			
		}
		response.getWriter().print(json.toJSONString());*/
		//방법2
		Gson gson=new Gson();
		String jsonStr=gson.toJson(list);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");//응답 데이터 형식 지정
		response.getWriter().print(jsonStr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
