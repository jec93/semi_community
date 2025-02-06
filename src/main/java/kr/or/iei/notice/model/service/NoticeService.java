package kr.or.iei.notice.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.member.model.dao.MemberDao;
import kr.or.iei.notice.model.dao.NoticeDao;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeComment;
import kr.or.iei.notice.model.vo.NoticeFile;
import kr.or.iei.notice.model.vo.NoticePageData;
import kr.or.iei.notice.model.vo.PopNoticePageDate;
import kr.or.iei.notice.model.vo.PopularPost;

public class NoticeService {
	private NoticeDao dao;

	public NoticeService() {
		// TODO Auto-generated constructor stub
		dao = new NoticeDao();
	}

	public NoticePageData selectNoticeList(int boardId, int reqPage, String boardName) {
		Connection conn = JDBCTemplate.getConnection();

		// 한 페이지에 보여질 게시글의 갯수
		int viewNoticeCnt = 10;

		/*
		 * 1번 페이지 일때,start = 10, end=10 2번 페이지 일때,start = 11, end=20 3번 페이지 일때,start =
		 * 21, end=30
		 */
		int end = reqPage * viewNoticeCnt;
		int start = end - viewNoticeCnt + 1;

		ArrayList<Notice> list = dao.selectNoticeList(conn, boardId, start, end);

		// 전체 게시물의 갯수
		int totCnt = dao.selectNoticeCount(conn, boardId);
		// 전체 페이지의 갯수
		int totPage = 0;

		/*
		 * 한페이지 보여질 갯수 == 10
		 * 
		 * 전체 게시글 갯수 : 전체 페이지 갯수
		 * 
		 * 5 : 1 13 : 2 20 : 2 21 : 3
		 */

		if (totCnt % viewNoticeCnt > 0) {
			totPage = totCnt / viewNoticeCnt + 1;
		} else {
			totPage = totCnt / viewNoticeCnt;
		}

		// 페이지 하단에, 보여질 페이지 네비게이션 사이즈 1,2,3,4,5
		int pageNaviSize = 5; // 1,2,3,4,5 or 6,7,8,9,10 ......

		// 페이지 네비게이션 시작 번호
		/*
		 * 
		 * 요청 페이지가 1페이지고, 페이지 네비게이션 사이즈가 5일때 == 1,2,3,4,5 요청 페이지가 4페이지고, 페이지 네비게이션 사이즈가
		 * 5일때 == 1,2,3,4,5 요청 페이지가 5페이지고, 페이지 네비게이션 사이즈가 5일때 == 1,2,3,4,5 요청 페이지가
		 * 6페이지고, 페이지 네비게이션 사이즈가 5일때 == 6,7,8,9,10 요청 페이지가 9페이지고, 페이지 네비게이션 사이즈가 5일때 ==
		 * 6,7,8,9,10
		 */

		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1; // 페이지 시작번호 연산 식

		// 페이지 네비게이션 사이즈만큼 반복하여, 태그 생성
		String pageNavi = "<ul class='pagination circle-style'>";

		// 이전 버튼 생성
		if (pageNo != 1) {
			// 6 7 8 9 10 or 11 12 13 14 15 or 16 17 18 19 20.....
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/notice/list?reqPage=" + (pageNo - 1) + "&boardId=" + boardId
					+ "&boardName=" + boardName + "'>";
			pageNavi += "<span class = 'material-icons'>chevron_left</span>";
			pageNavi += "</li>";
		}

		for (int i = 0; i < pageNaviSize; i++) {
			pageNavi += "<li>";

			// 선택한 페이지와, 선택하지 않은 페이지를 시각적으로 다르게 표현

			if (reqPage == pageNo) {
				pageNavi += "<a class='page-item active-page' href='/notice/list?reqPage=" + pageNo + "&boardId="
						+ boardId + "&boardName=" + boardName + "'>";
			} else {
				pageNavi += "<a class='page-item' href='/notice/list?reqPage=" + pageNo + "&boardId=" + boardId
						+ "&boardName=" + boardName + "'>";
			}

			pageNavi += pageNo + "</a></li>";
			pageNo++;

			if (pageNo > totPage) {
				break;
			}
		}

		// 시작번호 <= 전체 페이지 갯수
		if (pageNo <= totPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/notice/list?reqPage=" + pageNo + "&boardId=" + boardId
					+ "&boardName=" + boardName + "'>";
			pageNavi += "<span class = 'material-icons'>chevron_right</span>";
			pageNavi += "</li>";
		}

		pageNavi += "</ul>";

		NoticePageData pd = new NoticePageData();
		pd.setList(list);
		pd.setPageNavi(pageNavi);

		JDBCTemplate.close(conn);
		return pd;
	}

	public int insertNotice(Notice notice, ArrayList<NoticeFile> fileList) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		notice.setNoticeYn("N");
		// 1.게시글 번호 조회(파일 등록시에도, 게시글 번호가 필요)
		String userNo = notice.getBoardWriter();

		MemberDao mDao = new MemberDao();
		int grade = 0;
		grade = mDao.selectUserGrade(conn, userNo);// 작성시 포인트와 등급조정위한 것.
		String noticeId = dao.addNoticeNo(conn);

		notice.setPostId(noticeId);
		// 2.게시글 등록

		int result = dao.insertNotice(conn, notice);
		String option = "write";

		if (result > 0) {
			boolean fileChk = true;
			for (NoticeFile file : fileList) {
				file.setPostId(notice.getPostId());

				result = dao.insertNoticeFile(conn, file);

				if (result < 1) {
					JDBCTemplate.rollback(conn);
					fileChk = false;
					break;
				}
			}
			if (fileChk && grade != 0) {
				result = dao.addPoint(conn, userNo, option);
				if (result > 0) {
					result = dao.management(conn, userNo, grade);
					if (result > 0) {
						JDBCTemplate.commit(conn);
					} else {
						JDBCTemplate.rollback(conn);
					}
				} else {
					JDBCTemplate.rollback(conn);
				}
			} else {
				JDBCTemplate.rollback(conn);
			}
		}
		JDBCTemplate.close(conn);
		return result;
	}

	public Notice selectOneNotice(String postId, String commentChk) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();

		Notice n = dao.selectOneNotice(conn, postId);

		if (n != null) {
			int result = 0;

			// commentChk ==null인 것은 댓글을 작성하고 상세보기 이동하는 경우를 제외 모든 요청
			if (commentChk == null) {
				result = dao.updateReadCount(conn, postId);
			}

			// commentChk != null인것은 댓글을 작성하고 상세보기 이동하는 경우에도, 파일 정보를 select 할 수 있도록
			if (result > 0 || commentChk != null) {
				JDBCTemplate.commit(conn);

				// 파일 리스트, 댓글 리스트 모두 1개의 게시글에 종속적인 데이터이므로,별도의 클래스를 생성하지 않고,
				ArrayList<NoticeFile> fileList = dao.selectNoticeFileList(conn, postId);
				n.setFileList(fileList);

				ArrayList<NoticeComment> commentList = dao.selectCommentList(conn, postId);
				n.setCommentList(commentList);

			} else {
				JDBCTemplate.rollback(conn);
			}
		}
		JDBCTemplate.close(conn);
		return n;
	}

	public Notice getOneNotice(String postId) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		Notice n = dao.selectOneNotice(conn, postId);
		if (n != null) {
			ArrayList<NoticeFile> fileList = dao.selectNoticeFileList(conn, postId);
			n.setFileList(fileList);
		}
		JDBCTemplate.close(conn);
		return n;
	}

	public ArrayList<NoticeFile> updateNotice(Notice notice, ArrayList<NoticeFile> addFileList,
			String[] delFileNoList) {
		// TODO Auto-generated method stub

		Connection conn = JDBCTemplate.getConnection();

		int result = dao.updateNotice(conn, notice);

		ArrayList<NoticeFile> preFileList = new ArrayList<NoticeFile>();

		if (result > 0) {
			// DB 삭제 이전에, 기존 파일 리스트 조회
			preFileList = dao.selectNoticeFileList(conn, notice.getPostId());

			if (delFileNoList != null) {
				// 사용자가 삭제 요청한 파일을 DB에서 삭제
				String delFileNoStr = "";
				for (String s : delFileNoList) {
					delFileNoStr += s + "|";
				}

				/*
				 * 기존 파일 갯수가 5개 일때 4 - 3- 2- 1 - 0
				 */

				for (int i = preFileList.size() - 1; i >= 0; i--) {

					// 기존 파일리스트 중, 삭제 대상 파일인 경우
					if (delFileNoStr.indexOf(preFileList.get(i).getFileId()) > -1) {
						// DB 삭제
						result += dao.deleteNoticeFile(conn, preFileList.get(i).getFilePath());
					} else {
						// 삭제 대상이 아닌것 {서블릿으로 preFileList를 리턴할 때, 삭제 대상파일만 리턴할 것임)
						preFileList.remove(i);
					}
				}
			}

			// 추가 파일 DB등록
			for (NoticeFile addFile : addFileList) {
				result += dao.insertNoticeFile(conn, addFile);
			}
		}
		// 총 변경된 행의 수
		int updTotCnt = delFileNoList == null ? addFileList.size() + 1 : delFileNoList.length + addFileList.size() + 1;

		if (updTotCnt == result) {
			JDBCTemplate.commit(conn);
			JDBCTemplate.close(conn);
			return preFileList;
		} else {
			JDBCTemplate.rollback(conn);
			JDBCTemplate.close(conn);
			return null;
		}

	}

	public int insertComment(NoticeComment comment) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.insertComment(conn, comment);

		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}

	public int deleteComment(String commentNo) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.deleteComment(conn, commentNo);
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}

	public int updateComment(NoticeComment comment) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.updateComment(conn, comment);
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}

	public ArrayList<Notice> selectIndexNoticeList() {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Notice> list = dao.selectIndexNoticeList(conn);
		JDBCTemplate.close(conn);

		return list;
	}

	public ArrayList<Notice> srchNotice(String searchNotice) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Notice> srchNoticeNm = dao.selectNotice(conn, searchNotice);
		JDBCTemplate.close(conn);
		return srchNoticeNm;
	}

	public String selectPostId() {
		Connection conn = JDBCTemplate.getConnection();
		String postId = dao.selectPostId(conn);
		return postId;
	}

	public Notice selectDelNotice(String postId) {
		Connection conn = JDBCTemplate.getConnection();
		Notice n = new Notice();

		n = dao.selectOneNotice(conn, postId);

		ArrayList<NoticeFile> fileList = dao.selectNoticeFileList(conn, postId);
		n.setFileList(fileList);

		ArrayList<NoticeComment> commentList = dao.selectCommentList(conn, postId);
		n.setCommentList(commentList);

		return n;
	}

	public int deleteNotice(Notice deleteNotice, int control) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.deleteNotice(conn, deleteNotice);

		if (result > control) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);

		return result;
	}

	public Boolean updateAnounce(List<String> postIdList) {
		// TODO Auto-generated method stub
		for (String s : postIdList) {
			System.out.println(s + "여긴 서비스");
		}
		Connection conn = JDBCTemplate.getConnection();
		Boolean result = dao.updateAnounce(conn, postIdList);
		if (result) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		return result;
	}

	public int likeControl(String userNo, String postId, String boardWriter) {
		MemberDao mDao = new MemberDao();
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.likeCheck(conn, userNo, postId);// 좋아요를 누른 상태인가?
		int result2 = 0;
		String option = "like";
		int grade = 0;
		int result3 = 0;
		result3 = dao.sameUserChk(conn, userNo, postId);// 작성자와 똑같은가?
		grade = mDao.selectUserGrade(conn, boardWriter);
		if (result3 > 0) {
			result2 = -1;
		} else if (result > 0 || grade == 0) {
			return result2;
		} else {
			result2 = dao.recommendNotice(conn, userNo, postId);
			if (result2 > 0) {
				result2 = dao.addPoint(conn, boardWriter, option);
				if (result2 > 0) {
					result2 = dao.management(conn, boardWriter, grade);
					if (result2 > 0) {
						JDBCTemplate.commit(conn);
					} else {
						JDBCTemplate.rollback(conn);
					}
				} else {
					JDBCTemplate.rollback(conn);
				}
			} else {
				JDBCTemplate.rollback(conn);

			}
		}
		JDBCTemplate.close(conn);
		return result2;
	}

	public List<PopularPost> getPopularPosts() {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<PopularPost> PopularPosts = dao.getPopularPosts(conn);
		JDBCTemplate.close(conn);
		return PopularPosts;
	}

	public PopNoticePageDate selectPopularNoticeList(int reqPage) {
		Connection conn = JDBCTemplate.getConnection();

		// 한 페이지에 보여질 게시글의 갯수
		int viewNoticeCnt = 10;

		int end = reqPage * viewNoticeCnt;
		int start = end - viewNoticeCnt + 1;

		ArrayList<PopularPost> PopularPosts = dao.selectPopNoticeList(conn,start, end);

		// 전체 게시물의 갯수
		int totCnt = dao.selectPopNoticeCount(conn);
		// 전체 페이지의 갯수
		int totPage = 0;

		if (totCnt % viewNoticeCnt > 0) {
			totPage = totCnt / viewNoticeCnt + 1;
		} else {
			totPage = totCnt / viewNoticeCnt;
		}

		// 페이지 하단에, 보여질 페이지 네비게이션 사이즈 1,2,3,4,5
		int pageNaviSize = 5;

		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1; // 페이지 시작번호 연산 식

		String pageNavi = "<ul class='pagination circle-style'>";

		// 이전 버튼 생성
		if (pageNo != 1) {
			// 6 7 8 9 10 or 11 12 13 14 15 or 16 17 18 19 20.....
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/notice/list?reqPage=" + (pageNo - 1)+"'>";
			pageNavi += "<span class = 'material-icons'>chevron_left</span>";
			pageNavi += "</li>";
		}

		for (int i = 0; i < pageNaviSize; i++) {
			pageNavi += "<li>";

			// 선택한 페이지와, 선택하지 않은 페이지를 시각적으로 다르게 표현

			if (reqPage == pageNo) {
				pageNavi += "<a class='page-item active-page' href='/notice/list?reqPage=" + pageNo + "'>";
			} else {
				pageNavi += "<a class='page-item' href='/notice/list?reqPage=" + pageNo+"'>";
			}

			pageNavi += pageNo + "</a></li>";
			pageNo++;

			if (pageNo > totPage) {
				break;
			}
		}

		// 시작번호 <= 전체 페이지 갯수
		if (pageNo <= totPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/notice/list?reqPage=" + pageNo + "&boardId=" + "'>";
			pageNavi += "<span class = 'material-icons'>chevron_right</span>";
			pageNavi += "</li>";
		}

		pageNavi += "</ul>";

		PopNoticePageDate pd = new PopNoticePageDate();

		pd.setPopularPosts(PopularPosts);
		pd.setPageNavi(pageNavi);

		JDBCTemplate.close(conn);
		return pd;
	}

}
