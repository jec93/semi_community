package kr.or.iei.news.model;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.common.dao.CommonSearchDao;
import kr.or.iei.search.word.vo.Word;

public class CommonSearchService {

	private CommonSearchDao dao;
	
	public CommonSearchService() {
		dao = new CommonSearchDao();
	}

	public ArrayList<Word> searchWordList() {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Word> wordList = dao.searchWordList(conn);
		JDBCTemplate.close(conn);
		return wordList;
	}
	
	
}
