package kr.or.iei.notice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PopularPost {
	private int id;
    private String postId;
    private int boardId;
    private int likes;
    private int views;
    private String createdAt;
    private String nickname;
    private String boardTitle;
    private String boardContent;
}
