package kr.or.iei.search.word.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.search.word.vo.Word;

public class SearchWordDao {

	public Boolean searchString(Connection conn, String srchInput) {
		// TODO Auto-generated method stub
		// 검색 문장의 앞뒤 공백 제거
		srchInput = srchInput.trim();
		//공백 기준으로 나누어 배열 생성
		String[] wordsArray = srchInput.split(" ");
		//배열 리스트 생성
		List<String> wordList = Arrays.asList(wordsArray);
		
		//arrayList 변환
		ArrayList<String> arrayList = new ArrayList<>(wordList);
		
		//결과(숫자) 여러개가 출력될 예정이라 반복문 중 하나라도 re<0 판별이 나오면 result를 false로 하여 롤백 예정 
		PreparedStatement pstmt = null;
		Boolean result = true;
		int re = 0;
		
		//없으면 컬럼추가 + 양1 || 있으면 양+1
		String query = "MERGE INTO tbl_search sr USING (SELECT ? AS srch_Name FROM dual) new_word ON (sr.srch_Name = new_word.srch_Name) WHEN MATCHED THEN UPDATE SET sr.srch_amount = sr.srch_amount + 1, sr.srch_date = SYSDATE WHEN NOT MATCHED THEN INSERT (srch_Name, srch_amount, srch_date) VALUES (new_word.srch_Name, 1, SYSDATE)";
		
		try {
			for (String ch : arrayList) {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, ch);
				re = pstmt.executeUpdate();

				if (re < 0) {
					result = false;
				}
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCTemplate.close(pstmt);
			}
		
		return result;
	}

	public ArrayList<Word> selectAllWord(Connection conn) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//랭크 10위까지로 하면 중복 10위가 나올시 10개 이상으로 나올수 있기에 rownum으로 대체(완벽한 랭킹은 아니지만 문제없이 10위까지 뽑음)
		String query = "select rownum, srch_Name, srch_amount from (select * from tbl_search sr WHERE srch_date >= TRUNC(SYSDATE) - 2 order by sr.srch_amount desc) where rownum<=10";
		
		ArrayList<Word> list = new ArrayList<Word>();
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Word w = new Word();
				w.setSrchRank(rset.getInt("rownum"));
				w.setSrchWord(rset.getString("srch_Name"));
				w.setSrchAmt(rset.getInt("srch_amount"));
				
				list.add(w);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

}
