package kr.or.iei.search.word.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.search.word.dao.SearchWordDao;
import kr.or.iei.search.word.vo.Word;

public class SearchService {
	SearchWordDao dao;
	
	public SearchService() {
		dao = new SearchWordDao();
	}

	public Boolean searchString(String srchInput) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		Boolean result = dao.searchString(conn,srchInput); //dao의 이유로 참||거짓 값으로 판별
		if(result) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}

	public ArrayList<Word> selectAllWord() {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Word> list = dao.selectAllWord(conn);
		JDBCTemplate.close(conn);
		return list;
	}
	
	
	
}
