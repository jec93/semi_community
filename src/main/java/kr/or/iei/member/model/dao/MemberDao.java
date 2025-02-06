package kr.or.iei.member.model.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;

import kr.or.iei.member.model.vo.User;

public class MemberDao {

	public int insertMember(Connection conn, User member) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "insert into tbl_user(user_no, user_id, user_pw,user_email, nickname, phone)  values (to_char(sysdate, 'yymmdd') || lpad(seq_user.nextval, 5, '0'),?,?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getUserPw());
			pstmt.setString(3, member.getUserEmail());
			pstmt.setString(4, member.getNickname());
			pstmt.setString(5, member.getPhone());
			
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int idDuplChk(Connection conn, String userId) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select count(*) as cnt from tbl_user where user_id = ?";
		int cnt = 0;
		

		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				//select 결과를 int로 꺼내온다
				cnt = rset.getInt("cnt");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return cnt;
	}

	public User userLogin(Connection conn, String loginId, String loginPw) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		User u = null;
		String query = "select * from tbl_user where user_id =? and user_pw=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, loginId);
			pstmt.setString(2, loginPw);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				u = new User();
				u.setUserNo(rset.getString("user_no"));
				u.setUserId(rset.getString("user_id"));
				u.setUserPw(rset.getString("user_pw"));
				u.setNickname(rset.getString("nickname"));
				u.setUserEmail(rset.getString("user_email"));
				u.setPhone(rset.getString("phone"));
				u.setUserDate(rset.getString("user_date"));
				u.setUserGrade(rset.getInt("grade"));
				u.setUserPoint(rset.getInt("point"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return u;
	}

	public int updateMember(Connection conn, User updUser) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "update tbl_user set nickname=?, user_email=?,phone=? where user_no=?";
		
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, updUser.getNickname());
			pstmt.setString(2, updUser.getUserEmail());
			pstmt.setString(3, updUser.getPhone());
			pstmt.setString(4, updUser.getUserNo());
			
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int deleteMember(Connection conn, String userNo) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "delete from tbl_user where user_no = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int updateMemberPw(Connection conn, String userNo, String newUserPw) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "update tbl_user set user_pw =? where user_no =?";
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, newUserPw);
			pstmt.setString(2, userNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public ArrayList<User> selectAllUser(Connection conn) {
		PreparedStatement pstmt = null;
		ArrayList<User> list = new ArrayList<User>();
		ResultSet rset = null;
		String query = "select * from tbl_user where grade != '100' order by user_no";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				User u = new User();
				u.setUserNo(rset.getString("user_no"));
				u.setUserId(rset.getString("user_id"));
				u.setNickname(rset.getString("nickname"));
				u.setPhone(rset.getString("phone"));
				u.setUserEmail(rset.getString("user_email"));
				u.setUserGrade(rset.getInt("grade"));
				u.setUserDate(rset.getString("user_date"));
				u.setUserPoint(rset.getInt("point"));
				
				list.add(u);
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

	public int updChgLevel(Connection conn, String userNo, String userGrade) {
		PreparedStatement pstmt = null;
		int result = 0;
		int point=0;
		String query = "update tbl_user set grade = ?, point=? where user_no = ?";
		if(userGrade.equals("1")) {
			point=100;
		}else if(userGrade.equals("2")) {
			point=200;
		}else if(userGrade.equals("3")) {
			point=300;
		}else if(userGrade.equals("4")) {
			point=400;
		}else {
			point=500; //한 회원이 갑자기 대량의 포인트를 얻기는 거의 불가능하니 관리자 권한으로 등급상승, 강등시 포인트 조정(추후 여유 될시 기능 따로 구현예정)
		}
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userGrade);
			pstmt.setInt(2, point);
			pstmt.setString(3, userNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			JDBCTemplate.close(pstmt);
			
		}
		
		return result;
	}

	public String sechInfoId(Connection conn, String srchEmail) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query="select user_id from tbl_user where user_email=?";
		String userId=null;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1,srchEmail);
			rset=pstmt.executeQuery();
			if(rset.next()) {
				userId=rset.getString("user_id");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return userId;
	}

	public int addBann(Connection conn, String userId, String userEmail, String phone) {
		PreparedStatement pstmt=null;
		String query="insert into tbl_bann values(?,?,?)";
		int result=0;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setString(2, userEmail);
			pstmt.setString(3, phone);
			result=pstmt.executeUpdate();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return result;
	}

	public int selectBannUser(String userId, Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query="select * from tbl_bann where user_id=?";
		int result=0;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				result=1;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public int chkEmail(String userEmail, Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query= "select * from tbl_bann where user_email=?";
		int result=0;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userEmail);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				result=1;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int chkPhone(String userPhone, Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query= "select * from tbl_bann where phone=?";
		int result=0;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userPhone);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				result=1;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int selectUserGrade(Connection conn, String userNo) {
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		int result=0;
		String query="select grade from tbl_user where user_no=?";
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userNo);
			rset=pstmt.executeQuery();
			if(rset.next()) {
				result=rset.getInt("grade");
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	

	
}
