package kr.or.iei.notice.model.vo;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PopNoticePageDate {
	private ArrayList<PopularPost> PopularPosts;
	private String pageNavi;
}
