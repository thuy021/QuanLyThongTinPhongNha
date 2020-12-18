package dao;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
public abstract class AbstractCRUD<T> {
	protected EntityManager em;
	protected EntityTransaction trans;
	
	
	public AbstractCRUD(){
		em=MyEntityManager.getInstance().getEntityManager();
		trans=em.getTransaction();
	}
	public void persist(T t){
		trans.begin();
		em.persist(t);
		trans.commit();
	}
	public void remove(T t){
		trans.begin();
		em.remove(em.merge(t));
	}
	public void merge(T t){
		trans.begin();
		em.merge(t);
		trans.commit();
	}
}
