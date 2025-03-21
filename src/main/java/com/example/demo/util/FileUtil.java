package com.example.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUtil {

	// 파일을 저장할 기본 경로
		@Value("${filepath}")
		String filepath;
		
		public String fileUpload(MultipartFile multipartFile) {
			Path copyOfLocation = null;
			
			if(multipartFile == null || multipartFile.isEmpty()) { //파일스트림이 없으면 메소드를 종료
				return null;
			}
			try {
				//파일 전체 경로
				copyOfLocation = Paths
						.get(filepath + File.separator + StringUtils.cleanPath(multipartFile.getOriginalFilename())); 
				
				//파일을 폴더에 저장
				Files.copy(multipartFile.getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//파일이름을 반환
			return multipartFile.getOriginalFilename();
		}
	}
