package kr.or.iei.notice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoticeComment {
	private String commentId;
	private String replyTo;
	private String postId;
	private String comments;
	private String commentWriter;
	private String commTime;
	private String commenterName;//(댓글 단 사람 이름 출력용)
}
