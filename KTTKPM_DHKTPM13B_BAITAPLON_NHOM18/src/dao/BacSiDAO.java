package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import entity.BacSi;
import entity.NhanVien;

public class BacSiDAO extends AbstractCRUD<BacSi> {
	
	public BacSi timBSTheoMa(String ma) {
		return em.find(BacSi.class, ma);
	}
	
	public BacSi timBSTheoCMND(String cm) {
		return em.createNamedQuery("getBSByCMND", BacSi.class).getSingleResult();
	}
	
	public List<BacSi> getAllBS(){
		return em.createNamedQuery("getAllBS", BacSi.class).getResultList();
	}
	public BacSi getBacSiByTaiKhoan(String taiKhoan) {
		try {
			return em.createNamedQuery("getBacSiByTaiKhoan",BacSi.class).setParameter("taiKhoan", taiKhoan).getSingleResult();
		}catch (NoResultException e) {
			// TODO: handle exception
			//e.printStackTrace();
		}
		return null;
	}
	public BacSi timBSTheoTK(String tk) {
		return em.find(BacSi.class, tk);
	}
	public boolean themBacSi(BacSi bs) {
		try {
			trans.begin();
			em.persist(bs);
			trans.commit();
			return true;
		}catch(Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	public boolean xoaBacSi(BacSi bs) {
		try {
			trans.begin();
			em.remove(bs);
			trans.commit();
			return true;
		}catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	public boolean capNhatBacSi(BacSi bs) {
		try {
			trans.begin();
			em.merge(bs);
			trans.commit();
			return true;
		}catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
}
