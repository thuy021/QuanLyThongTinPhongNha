package dao;

import java.util.List;

import javax.persistence.Query;

import entity.KhachHang;
import entity.NhanVien;

public class KhachHangDAO extends AbstractCRUD<KhachHang>{
	
	
	public KhachHang timKHTheoMa(String ma) {
		return em.find(KhachHang.class, ma);
	}
	
//	public KhachHang timKHTheoCMND1(String cm) {
//		return em.createNamedQuery("getKHByCMND", KhachHang.class).getSingleResult();
//	}
	
	public List<KhachHang> getAllKH(){
		return em.createNamedQuery("getAllKH", KhachHang.class).getResultList();
	}
	public KhachHang timKHTheoCMND(String cmnd){
		List<KhachHang> list;
		KhachHang b=new KhachHang();
		
		Query q=em.createQuery("select a from KhachHang a",KhachHang.class);
		list=q.getResultList();
		for(KhachHang a:list){
			if(a.getSoCMND().equalsIgnoreCase(cmnd))
				b=a;
		}
		return b;
	}
	public boolean themKhachHang(KhachHang kh) {
		try {
			trans.begin();
			em.persist(kh);
			trans.commit();
			return true;
		}catch(Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	public boolean xoaKhachHang(KhachHang kh) {
		try {
			trans.begin();
			em.remove(kh);
			trans.commit();
			return true;
		}catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	public boolean capNhatKhachHang(KhachHang kh) {
		try {
			trans.begin();
			em.merge(kh);
			trans.commit();
			return true;
		}catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
}
