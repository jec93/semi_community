package kr.or.iei.member.model.vo;

import java.util.ArrayList;

import kr.or.iei.notice.model.vo.NoticeComment;
import kr.or.iei.notice.model.vo.NoticeFile;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class User {
	private String userNo;
	private String userId;
	private String userPw;
	private String nickname;
	private String userEmail;
	private String phone;
	private String userDate;
	private int userGrade;
	private int userPoint;
	
	private String boardName;
	private ArrayList<NoticeFile> fileList;
	private ArrayList<NoticeComment> commentList;
}
