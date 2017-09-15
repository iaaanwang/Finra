package net.antra.dao;


import net.antra.entity.MyFile;

import java.util.List;

public interface FileDao {
	
	 MyFile save(MyFile f);
	 MyFile getFile(Integer id);
	 boolean checkExist(String path);
	 List<MyFile> getAll();
}
