package kr.or.iei.search.word.vo;

import java.io.Serializable;

public class Word implements Serializable {
	private int srchRank; //랭크
	private String srchWord; //검색어
	private int srchAmt; //검색량
	
	
	public Word() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Word(int srchRank, String srchWord, int srchAmt) {
		super();
		this.srchRank = srchRank;
		this.srchWord = srchWord;
		this.srchAmt = srchAmt;
	}


	public String getSrchWord() {
		return srchWord;
	}
	public void setSrchWord(String srchWord) {
		this.srchWord = srchWord;
	}
	public int getSrchAmt() {
		return srchAmt;
	}
	public void setSrchAmt(int srchAmt) {
		this.srchAmt = srchAmt;
	}


	public int getSrchRank() {
		return srchRank;
	}


	public void setSrchRank(int srchRank) {
		this.srchRank = srchRank;
	}
	
}
