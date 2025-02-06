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
 * Servlet implementation class NoticeUpdateServlet
 */
@WebServlet("/notice/update")
public class NoticeUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//추가 파일 처리
		String today = new SimpleDateFormat("yyyyMMdd").format(new Date()); //오늘 날짜 20241030
		
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String savePath = rootPath + "resources/upload/" + today + "/"; //파일 저장 경로
		File dir = new File(savePath);
		if(!dir.exists()) { //오늘 날짜 폴더가 생성되어 있지 않을 때
			dir.mkdir(); //폴더 생성
		}
		
		int maxSize = 1024*1024*10;
		MultipartRequest mRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyRenamePolicy());
		
		
		//게시글 정보
		String postId =  mRequest.getParameter("postId");
		String boardTitle = mRequest.getParameter("boardTitle");
		String boardContent = mRequest.getParameter("boardContent");
		
		Enumeration<String> files = mRequest.getFileNames(); //type이 file인 태그들
		
		ArrayList<NoticeFile> addFileList = new ArrayList<NoticeFile>();
		
		while(files.hasMoreElements()) {
			String name = files.nextElement();
			
			String fileName = mRequest.getOriginalFileName(name);
			String filePath = mRequest.getFilesystemName(name);
			
			if(filePath != null) {
				NoticeFile file = new NoticeFile();
				file.setPostId(postId);
				file.setFileName(fileName);
				file.setFilePath(filePath);
				
				addFileList.add(file);
			}
		}
		
		//삭제 파일 리스트 (jsp에서 아이콘 클릭 시, form태그 하위에 추가해준 hidden속성을 가진 input 태그들)
		String[] delFileNoList = request.getParameterValues("delFileNo");
		
		Notice notice = new Notice();
		notice.setPostId(postId);
		notice.setBoardTitle(boardTitle);
		notice.setBoardContent(boardContent);
		
		NoticeService service = new NoticeService();
		ArrayList<NoticeFile> delFileList = service.updateNotice(notice, addFileList, delFileNoList);
		
		if(delFileList == null) {
			request.setAttribute("title", "알림");
			request.setAttribute("msg", "수정 중, 오류가 발생하였습니다.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/notice/updateFrm?postId=="+postId);
		}else {
			//서비스에서 모든 insert, update, delete가 정상적으로 이루어진 경우
			for(int i=0; i<delFileList.size(); i++) {
				String uploadDate = delFileList.get(i).getFilePath().substring(0,8); //파일 업로드 날짜 == 삭제해야할 파일의 폴더명
				String delFilePath = rootPath + "resources/upload" + uploadDate + "/" + delFileList.get(i).getFilePath();
				
				File delFile = new File(delFilePath);
				
				//해당 경로에 삭제 파일이 존재하면
				if(delFile.exists()) {
					delFile.delete();
				}
			}
			
			request.setAttribute("title","알림");
			request.setAttribute("msg", "게시글이 수정되었습니다.");
			request.setAttribute("icon", "success");
			request.setAttribute("loc", "/notice/view?postId="+postId);
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
