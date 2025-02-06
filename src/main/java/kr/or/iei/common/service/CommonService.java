package kr.or.iei.common.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.common.dao.CommonDao;
import kr.or.iei.common.vo.Board;

public class CommonService {
	private CommonDao dao;
	
	public CommonService() {
		// TODO Auto-generated constructor stub
		dao = new CommonDao();
	}
	
	public ArrayList<Board> selectNoticeTypeList() {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Board> typeList = dao.selectNoticeTypeList(conn);
		JDBCTemplate.close(conn);
		return typeList;
	}

}
