package net.antra.dao;


import net.antra.entity.MyFile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class FileDaoImp implements FileDao {
	
	@PersistenceContext
	EntityManager em;

	@Override
	public MyFile save(MyFile f) {
		em.persist(f);
		return f;
	}

	@Override
	public MyFile getFile(Integer id) {
		return em.find(MyFile.class, id);
		
	}

	@Override
	public List<MyFile> getAll() {
		Query q = em.createQuery("SELECT file FROM MyFile file");
		return q.getResultList();
	}

	@Override
	public boolean checkExist(String path) {
		Query q = em.createQuery("SELECT file FROM MyFile file where file.path=:path");
		q.setParameter("path",path);
		try{
			q.getSingleResult();
			return true;
		}catch(NoResultException nre){
			return false;
		}
	}

}
