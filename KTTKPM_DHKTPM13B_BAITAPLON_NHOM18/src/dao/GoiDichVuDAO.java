package dao;

import java.util.List;

import javax.persistence.NoResultException;

import entity.GoiDichVu;
import entity.NhanVien;

public class GoiDichVuDAO extends AbstractCRUD<GoiDichVu>{
	public GoiDichVu timGoiDichVuTheoMa(String ma) {
		return em.find(GoiDichVu.class, ma);
	}
	
	public List<GoiDichVu> listThuoc(){
		return em.createNamedQuery("getAllGoiDV", GoiDichVu.class).getResultList();
	}
	public List<GoiDichVu> getAllGoiDichVu(){
		return em.createNamedQuery("getAllGoiDV", GoiDichVu.class).getResultList();
	}
	public boolean themGoiDV(GoiDichVu dv) {
		try {
			trans.begin();
			em.persist(dv);
			trans.commit();
			return true;
		}catch(Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	public boolean xoaGoiDV(GoiDichVu dv) {
		try {
			trans.begin();
			em.remove(dv);
			trans.commit();
			return true;
		}catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	public boolean capNhatGoiDV(GoiDichVu dv) {
		try {
			trans.begin();
			em.merge(dv);
			trans.commit();
			return true;
		}catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
}
