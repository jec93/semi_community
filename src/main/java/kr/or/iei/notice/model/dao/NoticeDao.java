package kr.or.iei.notice.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeComment;
import kr.or.iei.notice.model.vo.NoticeFile;
import kr.or.iei.notice.model.vo.PopularPost;

public class NoticeDao {

	public ArrayList<Notice> selectNoticeList(Connection conn, int boardId, int start, int end) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Notice> list = new ArrayList<Notice>();
		String query = "SELECT * \r\n" + "FROM (\r\n" + "    SELECT \r\n" + "        rownum rnum, \r\n"
				+ "        a.*\r\n" + "    FROM (\r\n" + "        SELECT \r\n" + "            n.post_id,\r\n"
				+ "            n.board_title,\r\n" + "            n.board_content,\r\n"
				+ "            n.read_count,\r\n" + "            n.likes,\r\n" + "            n.board_id,\r\n"
				+ "            n.user_no,\r\n" + "            u.nickname,\r\n" + "            n.notice_yn,\r\n"
				+ "            n.created_date\r\n" + "        FROM \r\n" + "            tbl_notice n\r\n"
				+ "        LEFT JOIN \r\n" + "            tbl_user u ON n.user_no = u.user_no\r\n"
				+ "        WHERE \r\n" + "            n.board_id = ?\r\n" + "        ORDER BY \r\n"
				+ "            n.created_date DESC\r\n" + "    ) a\r\n" + ") \r\n" + "WHERE \r\n"
				+ "    rnum BETWEEN ? AND ? \r\n" + "ORDER BY \r\n" + "    notice_yn DESC";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardId);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				Notice n = new Notice();
				n.setPostId(rset.getString("post_id"));
				n.setBoardId(rset.getInt("board_id"));
				n.setBoardTitle(rset.getString("board_title"));
				n.setBoardContent(rset.getString("board_content"));
				n.setBoardWriter(rset.getString("user_no"));
				n.setCreatedDate(rset.getString("created_date"));
				n.setReadCount(rset.getInt("read_count"));
				n.setLikes(rset.getInt("likes"));
				n.setNoticeYn(rset.getString("notice_yn"));
				n.setNickname(rset.getString("nickname"));
				list.add(n);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public int selectNoticeCount(Connection conn, int boardId) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int totCnt = 0;
		String query = "select count(*) cnt from tbl_notice where board_id=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardId);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				totCnt = rset.getInt("cnt");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return totCnt;
	}

	public String selectPostId(Connection conn) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select seq_notice.nextval as post_id from dual";
		String postId = "";

		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			rset.next();
			postId = rset.getString("post_id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return postId;
	}

	public int insertNotice(Connection conn, Notice notice) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "insert into tbl_notice values(?,?,?,sysdate,default,default,?,?,?)";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, notice.getPostId());
			pstmt.setString(2, notice.getBoardTitle());
			pstmt.setString(3, notice.getBoardContent());
			pstmt.setInt(4, notice.getBoardId());
			pstmt.setString(5, notice.getBoardWriter());
			pstmt.setString(6, notice.getNoticeYn());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;

	}

	// 여기 아직 안했음. 진짜로?
	public int insertNoticeFile(Connection conn, NoticeFile file) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		System.out.println(file);
		String query = "insert into notice_file values(to_char(sysdate, 'yymmdd')||lpad(seq_notice_file.nextval, 5,'0'),?,?,?)";
		int result = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, file.getFileName());
			pstmt.setString(2, file.getFilePath());
			pstmt.setString(3, file.getPostId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public Notice selectOneNotice(Connection conn, String postId) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		Notice n = null;

		String query = "select a.*,c.board_name, b.nickname from tbl_notice a, tbl_board c, tbl_user b where a.post_id = ? and a.board_id = c.board_id and a.user_no = b.user_no";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, postId);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				n = new Notice();
				n.setPostId(rset.getString("post_id"));
				n.setBoardId(rset.getInt("board_id"));
				n.setBoardName(rset.getString("board_name"));
				n.setBoardTitle(rset.getString("board_title"));
				n.setBoardContent(rset.getString("board_content"));
				n.setNickname(rset.getString("nickname"));
				n.setCreatedDate(rset.getString("created_date"));
				n.setLikes(rset.getInt("likes"));
				n.setBoardWriter(rset.getString("user_no"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return n;
	}

	public int updateReadCount(Connection conn, String postId) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "update tbl_notice set read_count = read_count +1 where post_id = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, postId);
			result = pstmt.executeUpdate();
			System.out.println("조회수 1증가");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public ArrayList<NoticeFile> selectNoticeFileList(Connection conn, String postId) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from notice_file where post_id = ?";
		ArrayList<NoticeFile> fileList = new ArrayList<NoticeFile>();

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, postId);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				NoticeFile file = new NoticeFile();
				file.setFileId(rset.getString("file_id"));
				file.setPostId(rset.getString("post_id"));
				file.setFileName(rset.getString("file_name"));
				file.setFilePath(rset.getString("file_path"));
				fileList.add(file);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return fileList;
	}

	public int updateNotice(Connection conn, Notice notice) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "update tbl_notice set board_title = ?, board_content=? where post_id=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, notice.getBoardTitle());
			pstmt.setString(2, notice.getBoardContent());
			pstmt.setString(3, notice.getPostId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public int deleteNoticeFile(Connection conn, String fileNo) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "delete from notice_file where file_no = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fileNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int insertComment(Connection conn, NoticeComment comment) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO tbl_comment VALUES (to_char(sysdate, 'yymmdd') || lpad(seq_comment.nextval, 6, '0'),?,?,sysdate,?,?)";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, comment.getReplyTo());
			pstmt.setString(2, comment.getComments());
			pstmt.setString(3, comment.getCommentWriter());
			pstmt.setString(4, comment.getPostId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public ArrayList<NoticeComment> selectCommentList(Connection conn, String postId) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		String query = "SELECT c.*, u.nickname FROM tbl_comment c JOIN tbl_user u  ON c.user_no = u.user_no WHERE c.post_id = ? ORDER BY c.comm_time DESC";
		ResultSet rset = null;
		ArrayList<NoticeComment> list = new ArrayList<NoticeComment>();

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, postId);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				NoticeComment c = new NoticeComment();
				c.setCommentId(rset.getString("comment_id"));
				c.setReplyTo(rset.getString("reply_to"));
				c.setCommentWriter(rset.getString("user_no"));
				c.setComments(rset.getString("comments"));
				c.setPostId(rset.getString("post_id"));
				c.setCommTime(rset.getString("comm_time"));
				c.setCommenterName(rset.getString("nickname"));
				list.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public int deleteComment(Connection conn, String commentNo) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;

		int result = 0;
		String query = "delete from tbl_comment where comment_id =?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, commentNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public int updateComment(Connection conn, NoticeComment comment) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		String query = "update tbl_comment set comments = ? where comment_id = ?";
		int result = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, comment.getComments());
			pstmt.setString(2, comment.getCommentId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public ArrayList<Notice> selectIndexNoticeList(Connection conn) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		// notice_cd별로 그룹지어 행번호 조회
		String query = "SELECT *\r\n" + "FROM (\r\n" + "    SELECT\r\n"
				+ "        row_number() OVER (PARTITION BY board_id ORDER BY notice_yn DESC, created_date DESC) AS rnum,\r\n"
				+ "        a.post_id,\r\n" + "        a.board_title,\r\n" + "        a.board_content,\r\n"
				+ "        a.read_count,\r\n" + "        a.likes,\r\n" + "        a.board_id,\r\n"
				+ "        a.user_no,\r\n" + "        b.nickname,\r\n" + "        a.notice_yn,\r\n"
				+ "        a.created_date,\r\n" + "        CASE\r\n"
				+ "            WHEN EXTRACT(DAY FROM (SYSTIMESTAMP - a.created_date)) > 0 THEN\r\n"
				+ "                FLOOR(EXTRACT(DAY FROM (SYSTIMESTAMP - a.created_date))) || '일 전'\r\n"
				+ "            WHEN EXTRACT(HOUR FROM (SYSTIMESTAMP - a.created_date)) > 0 THEN\r\n"
				+ "                FLOOR(EXTRACT(HOUR FROM (SYSTIMESTAMP - a.created_date))) || '시간 전'\r\n"
				+ "            WHEN EXTRACT(MINUTE FROM (SYSTIMESTAMP - a.created_date)) > 0 THEN\r\n"
				+ "                FLOOR(EXTRACT(MINUTE FROM (SYSTIMESTAMP - a.created_date))) || '분 전'\r\n"
				+ "            ELSE\r\n"
				+ "                FLOOR(EXTRACT(SECOND FROM (SYSTIMESTAMP - a.created_date))) || '초 전'\r\n"
				+ "        END AS 시간_차이\r\n" + "    FROM\r\n" + "        tbl_notice a\r\n" + "    LEFT JOIN\r\n"
				+ "        tbl_user b ON a.user_no = b.user_no\r\n" + ") \r\n" + "WHERE rnum <= 5\r\n" + "ORDER BY\r\n"
				+ "    notice_yn DESC,\r\n" + "    created_date DESC";
		// 머가리 안돌아가서 created_date도 불러옴 setCreatedDate에는 별칭 "시간_차이" 들어감

		ArrayList<Notice> list = new ArrayList<Notice>();
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				Notice n = new Notice();
				n.setPostId(rset.getString("post_id"));
				n.setBoardId(rset.getInt("board_id"));
				n.setNickname(rset.getString("nickname"));
				n.setBoardTitle(rset.getString("board_title"));
				n.setBoardContent(rset.getString("board_content"));
				n.setBoardWriter(rset.getString("user_no"));
				n.setCreatedDate(rset.getString("시간_차이"));
				n.setLikes(rset.getInt("likes"));
				n.setNoticeYn(rset.getString("notice_yn"));
				list.add(n);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return list;
	}

	public ArrayList<Notice> selectNotice(Connection conn, String searchNotice) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Notice> srchNoticeNm = new ArrayList<Notice>();
		String query = "select board_id,board_name from tbl_board where board_name Like ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + searchNotice + "%");
			rset = pstmt.executeQuery();
			while (rset.next()) {
				Notice n = new Notice();
				n.setBoardId(rset.getInt("board_id"));
				n.setBoardName(rset.getString("board_name"));
				srchNoticeNm.add(n);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return srchNoticeNm;

	}

	public int deleteNotice(Connection conn, Notice deleteNotice) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String postId = deleteNotice.getPostId();
		String query = "delete from tbl_comment where post_id=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, postId);
			result = pstmt.executeUpdate();
			System.out.println("첫번째" + result);

			query = "delete from notice_file where post_id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, postId);
			result += pstmt.executeUpdate();
			System.out.println("두번째" + result);

			query = "delete from tbl_notice where post_id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, postId);
			result += pstmt.executeUpdate();
			System.out.println("세번째" + result);

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return result;
	}

	public Boolean updateAnounce(Connection conn, List<String> postIdList) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		Boolean result = true;
		int re = -1;

		String query = "update tbl_notice set notice_YN = 'Y' where post_id = ?";
		try {
			for (String n : postIdList) {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, n);
				re = pstmt.executeUpdate();
				if (re < 0) {
					result = false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	// 이미 좋아요를 했는지 확인(like_control 테이블은 카톡에 테이블 생성구문 올림)
	public int likeCheck(Connection conn, String userNo, String postId) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from like_control where user_no=? and post_id=?";
		int result = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userNo);
			pstmt.setString(2, postId);

			rset = pstmt.executeQuery();
			if (rset.next()) {
				result = 1;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	// 조회결과가 없음(추천안함). 추천이 가능함.
	public int recommendNotice(Connection conn, String userNo, String postId) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "update tbl_notice set likes=likes+1 where post_id=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, postId);
			result = pstmt.executeUpdate();

			query = "insert into like_control values(?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userNo);
			pstmt.setString(2, postId);
			result += pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public String addNoticeNo(Connection conn) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String noticeNo = null;
		String query = "select to_char(sysdate, 'yymmdd') || lpad(seq_notice.nextval, 5, '0') from dual";
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				noticeNo = rset.getString(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		System.out.println(noticeNo);
		return noticeNo;
	}

	public int addPoint(Connection conn, String userNo, String option) {
		PreparedStatement pstmt = null;
		String query = "update tbl_user set point=point+? where user_no=?";
		int points = 0;
		if (option.equals("write")) {// 작성인가 좋아요인가에 따라 다름.
			points = 2;
		} else {
			points = 1;
		}
		int result = 0;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, points);
			pstmt.setString(2, userNo);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int management(Connection conn, String userNo, int grade2) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		int points = 0;
		int grade = 1;// 수정할 등급 (grade2는 select로 가져온 기존 등급)
		String query1 = "select point from tbl_user where user_no=?";
		String query2 = "update tbl_user set grade=? where user_no=?";
		try {
			pstmt = conn.prepareStatement(query1);
			pstmt.setString(1, userNo);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				points = rset.getInt("point");

				if (points >= 500 && (grade2 == 4 || grade2 == 5 || grade2 == 3 || grade == 2 || grade2 == 1)) {
					grade = 5;
				} else if (400 <= points && points < 500
						&& (grade2 == 1 || grade2 == 2 || grade2 == 3 || grade2 == 4)) {
					grade = 4;
				} else if (300 <= points && points < 400 && (grade2 == 1 || grade2 == 2 || grade2 == 3)) {
					grade = 3;
				} else if (200 <= points && points < 300 && (grade2 == 1 || grade2 == 2)) {
					grade = 2;
				} else {
					grade = grade2;
				}
				pstmt = conn.prepareStatement(query2);
				pstmt.setInt(1, grade);
				pstmt.setString(2, userNo);
				result = pstmt.executeUpdate();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public int sameUserChk(Connection conn, String userNo, String postId) {
				PreparedStatement pstmt=null;
				ResultSet rset=null;
				int result=0;
				String control=null;
				String query="select user_no from tbl_notice where post_id=?";
				
				try {
					pstmt=conn.prepareStatement(query);
					pstmt.setString(1, postId);
					rset=pstmt.executeQuery();
					if(rset.next()) {
						control=rset.getString("user_no");
					}
					if(userNo.equals(control)) {
						result=1;
					}
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				finally {
					JDBCTemplate.close(rset);
					JDBCTemplate.close(pstmt);
				}
				return result;
			}

	public ArrayList<PopularPost> getPopularPosts(Connection conn) {
				// TODO Auto-generated method stub
				PreparedStatement pstmt=null;
				ResultSet rset=null;
				//notice_cd별로 그룹지어 행번호 조회
				String query="select pp.id,pp.post_id,pp.board_id,u.nickname,n.board_title,pp.likes,pp.views,n.board_content,created_at from popular_post pp\r\n"
						+ "join tbl_notice n on pp.post_id=n.post_id join tbl_user u on n.user_no = u.user_no";
				
				ArrayList<PopularPost> PopularPosts =new ArrayList<PopularPost>();
				try {
					pstmt=conn.prepareStatement(query);
					rset=pstmt.executeQuery();
					while(rset.next()) {
						PopularPost pp = new PopularPost();
						pp.setId(rset.getInt("id"));
						pp.setPostId(rset.getString("post_id"));
						pp.setBoardId(rset.getInt("board_id"));
						pp.setLikes(rset.getInt("likes"));
						pp.setViews(rset.getInt("views"));
						pp.setCreatedAt(rset.getString("created_at"));
						pp.setNickname(rset.getString("nickname"));
						pp.setBoardTitle(rset.getString("board_title"));
						pp.setBoardContent(rset.getString("board_content"));
						
						PopularPosts.add(pp);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					JDBCTemplate.close(rset);
					JDBCTemplate.close(pstmt);
				}

				return PopularPosts;
			}

	public ArrayList<PopularPost> selectPopNoticeList(Connection conn, int start, int end) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from(select rownum rnum, a.* from(select pp.id,pp.post_id,pp.board_id,u.nickname,n.board_title,pp.likes,pp.views,n.board_content,created_at from popular_post pp\r\n"
				+ "join tbl_notice n on pp.post_id=n.post_id join tbl_user u on n.user_no = u.user_no)a)a where rnum between ? and ?";
		
		ArrayList<PopularPost> PopularPosts =new ArrayList<PopularPost>();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				PopularPost pp = new PopularPost();
				pp.setId(rset.getInt("id"));
				pp.setPostId(rset.getString("post_id"));
				pp.setBoardId(rset.getInt("board_id"));
				pp.setLikes(rset.getInt("likes"));
				pp.setViews(rset.getInt("views"));
				pp.setCreatedAt(rset.getString("created_at"));
				pp.setNickname(rset.getString("nickname"));
				pp.setBoardTitle(rset.getString("board_title"));
				pp.setBoardContent(rset.getString("board_content"));
				
				PopularPosts.add(pp);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return PopularPosts;
	}

	public int selectPopNoticeCount(Connection conn) {
		// TODO Auto-generated method stub
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				int totCnt = 0;
				String query = "select count(*) cnt from popular_post";

				try {
					pstmt = conn.prepareStatement(query);
					rset = pstmt.executeQuery();
					if (rset.next()) {
						totCnt = rset.getInt("cnt");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					JDBCTemplate.close(rset);
					JDBCTemplate.close(pstmt);
				}
				return totCnt;
	}
}
