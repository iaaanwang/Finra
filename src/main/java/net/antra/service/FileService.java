package net.antra.service;


import net.antra.entity.MyFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

	 MyFile saveFile(MultipartFile mFile, MyFile file) throws IllegalStateException, IOException;
	 MyFile getFile(Integer id);
	 boolean checkExist(String path);
	 List<MyFile> getAllFiles();
	
}
