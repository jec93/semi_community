package kr.or.iei.notice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class NoticeFile {
	private String fileId;
	private String postId;
	private String fileName;
	private String filePath;
	
}
