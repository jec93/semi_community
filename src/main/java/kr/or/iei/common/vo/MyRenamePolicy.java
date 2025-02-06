package kr.or.iei.common.vo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyRenamePolicy implements FileRenamePolicy{

	@Override
	public File rename(File originFile) {
		
		//현재시간(밀리세컨드 단위)
		long currentTime = System.currentTimeMillis();
		
		int ranNum = new Random().nextInt(10000) + 1; //1~10000 랜덤
		
		
		String str ="_" + String.format("%05d", ranNum);  //랜덤 숫자를 모두 5자리로 생성
				//20241029122734658_60 ==>20241029122734658_00060 
		
		String name = originFile.getName();
		String ext = null;
		
		int dot = name.lastIndexOf("."); //파일명.확장자 중, .의 위치
		
		if(dot!=-1) {
			//파일명에 .이 있을 때
			ext = name.substring(dot); //확장자
		}else {
			//파일명에 .이 없을 때
			ext = "";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS"); //20241029122734658
		
		String fileName= sdf.format(new Date(currentTime)) + str + ext; //20241029122734658 + _0060 + .txt
		
		File newFile = new File(originFile.getParent(), fileName);
		
		return newFile;
	}
}
