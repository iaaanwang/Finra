package net.antra.service;


import net.antra.entity.MyFile;
import net.antra.dao.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImp implements FileService {
	
	@Autowired
	FileDao fileDAO;

	@Override
	@Transactional
	public MyFile saveFile(MultipartFile mFile, MyFile file) throws IllegalStateException, IOException {
		System.out.println(file.getPath());
		File des=new File(file.getPath());
		mFile.transferTo(des);
		return fileDAO.save(file);
	}
	@Override
	@Transactional
	public MyFile getFile(Integer id) {
		return fileDAO.getFile(id);
	}

	@Override
	@Transactional
	public boolean checkExist(String path) {
		return fileDAO.checkExist(path);
	}

	@Override
	public List<MyFile> getAllFiles() {
		return fileDAO.getAll();
	}
}
