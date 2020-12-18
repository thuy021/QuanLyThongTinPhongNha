package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import entity.GoiDichVu;
import entity.Thuoc;

public class ThuocDAO extends AbstractCRUD<Thuoc> {
	protected EntityManager em;
	protected EntityTransaction trans;
	
	
	public ThuocDAO(){
		em=MyEntityManager.getInstance().getEntityManager();
		trans=em.getTransaction();
	}
	public Thuoc timThuocTheoTenThuoc(String tenThuoc) {
		List<Thuoc> list;
		Thuoc b = new Thuoc();

		Query q = em.createQuery("select a from Thuoc a", Thuoc.class);
		list = q.getResultList();
		for (Thuoc a : list) {
			if (a.getTenThuoc().equalsIgnoreCase(tenThuoc))
				b = a;
		}
		return b;
	}
	public List<Thuoc> listThuoc(){
		return em.createNamedQuery("getAllThuoc", Thuoc.class).getResultList();
	}
	public boolean themThuoc(Thuoc t) {
		try {
			trans.begin();
			em.persist(t);
			trans.commit();
			return true;
		}catch(Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	public boolean xoaThuoc(Thuoc t) {
		try {
			trans.begin();
			em.remove(t);
			trans.commit();
			return true;
		}catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	public boolean capNhatThuoc(Thuoc t) {
		try {
			trans.begin();
			em.merge(t);
			trans.commit();
			return true;
		}catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
}
