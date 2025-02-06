package kr.or.iei.notice.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import kr.or.iei.common.vo.MyRenamePolicy;
import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeFile;

/**
 * Servlet implementation class NoticeWriteServlet
 */
@WebServlet("/notice/write")
public class NoticeWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeWriteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // TODO Auto-generated method stub
	      //response.getWriter().append("Served at: ").append(request.getContextPath());
		//1.첨부파일 저장 경로
	      Date date = new Date();
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	      String today = sdf.format(date);//오늘 날짜
	      
	      String rootPath = request.getSession().getServletContext().getRealPath("/");
	      String savePath = rootPath + "resources/upload/" + today + "/"; // 파일 경로
	      
	      int maxSize = 1024*1024*10; //10MB
	      
	      File dir = new File(savePath); //오늘 날짜로 지정한 폴더
	      if(!dir.exists()) { //해당 경로에 폴더가 생성되어 있지 않을 때
	         dir.mkdir();//폴더 생성
	      }
	      MultipartRequest mRequest = new MultipartRequest(request, savePath,maxSize,"UTF-8",new MyRenamePolicy());
	      
	      //2. 값 추출
	      int boardIds=Integer.parseInt(mRequest.getParameter("boardId"));
	      String boardTitle = mRequest.getParameter("boardTitle");
	      String boardContent = mRequest.getParameter("boardContent");
	      String boardWriter =mRequest.getParameter("boardWriter");
	      String boardName = mRequest.getParameter("boardName");
	      
	      //3.첨부파일 복수개 처리(input type=file이 여러개 존재)
	      Enumeration<String> files = mRequest.getFileNames(); //input type이 file인 태그들의, name 속성값
	      
	      ArrayList<NoticeFile> fileList = new ArrayList<NoticeFile>();
	      
	      while(files.hasMoreElements()) {
	         String name = files.nextElement(); //input type이 file인 태그의 name 속성값
	         
	         String fileName = mRequest.getOriginalFileName(name); // 원본 파일명
	         String filePath = mRequest.getFilesystemName(name); //변경된 파일명
	         
	         if(filePath != null) {
	            NoticeFile file = new NoticeFile();
	            file.setFileName(fileName);
	            file.setFilePath(filePath);
	            
	            fileList.add(file);
	         }
	      }
	      
	      NoticeService service = new NoticeService();
	     
	      Notice notice = new Notice();
	     
	      notice.setBoardTitle(boardTitle);
	      notice.setBoardContent(boardContent);
	      notice.setBoardWriter(boardWriter);
	      notice.setBoardId(boardIds);
	      notice.setBoardName(boardName);
	   
	      int result = service.insertNotice(notice, fileList);
	      
	      if(result>0) {
	         request.setAttribute("title", "성공");
	         request.setAttribute("msg",boardName + "이 작성되었습니다.");
	         request.setAttribute("icon", "success");
	         request.setAttribute("loc", "/notice/list?reqPage=1&boardId="+boardIds+"&boardName="+boardName+"&sort=latest");
	      }else {
	         request.setAttribute("title", "실패");
	         request.setAttribute("msg", boardName + "작성 중, 오류가 발생.");
	         request.setAttribute("icon", "error");
	         request.setAttribute("loc", "/notice/list?reqPage=1&boardId="+boardIds+"&boardName="+boardName+"&sort=latest");
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
