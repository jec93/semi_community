package kr.or.iei.search.word.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Blog {
	private String blogTitle;
	private String blogLink;
	private String blogDescription;
}
