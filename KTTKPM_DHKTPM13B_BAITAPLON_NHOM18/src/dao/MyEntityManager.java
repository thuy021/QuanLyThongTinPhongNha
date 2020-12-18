package dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class MyEntityManager {
	private static MyEntityManager instance;
	private EntityManager em;
	public MyEntityManager() {
		em = Persistence.createEntityManagerFactory("KTTKPM_DHKTPM13B_BAITAPLON_NHOM18").createEntityManager();
	}
	public synchronized static MyEntityManager getInstance() {
		if(instance == null) return instance = new MyEntityManager();
		return instance;
	}
	public EntityManager getEntityManager() {
		return em;
	}
}
