package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import entity.DonThuoc;
import entity.NhanVien;

public class NhanVienDAO extends AbstractCRUD<NhanVien> {

	
	public NhanVien timNVTheoMa(String ma) {
		return em.find(NhanVien.class, ma);
	}
	public NhanVien timNVtheoTK(String tk) {
		return em.find(NhanVien.class, tk);
	}
	
	/*
	 * public NhanVien timNVTheoMaNV(String ma) { return
	 * em.createNamedQuery("getNVByMa", NhanVien.class).getSingleResult(); }
	 */
	
	public List<NhanVien> getAllNV(){
		return em.createNamedQuery("getAllNV", NhanVien.class).getResultList();
	}
	public NhanVien getNhanVienByTaiKhoan(String taiKhoan) {
		try {
			return em.createNamedQuery("getNVByUser",NhanVien.class).setParameter("taiKhoan", taiKhoan).getSingleResult();
		}catch (NoResultException e) {
			//e.printStackTrace();
		}
		return null;
	}
	public boolean themNhanVien(NhanVien nv) {
		try {
			trans.begin();
			em.persist(nv);
			trans.commit();
			return true;
		}catch(Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	public boolean xoaNhanVien(NhanVien nv) {
		try {
			trans.begin();
			em.remove(nv);
			trans.commit();
			return true;
		}catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	public boolean capNhatNhanVien(NhanVien nv) {
		try {
			trans.begin();
			em.merge(nv);
			trans.commit();
			return true;
		}catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
}
