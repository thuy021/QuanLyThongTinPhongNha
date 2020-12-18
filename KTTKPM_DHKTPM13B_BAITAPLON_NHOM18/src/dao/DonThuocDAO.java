package dao;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import entity.BacSi;
import entity.DonThuoc;
import entity.GoiDichVu;
import entity.KhachHang;

public class DonThuocDAO extends AbstractCRUD<DonThuoc> {

	private EntityManager em;
	private EntityTransaction trans;

	public DonThuocDAO() {
		em=MyEntityManager.getInstance().getEntityManager();
		trans=em.getTransaction();
	}
	public List<DonThuoc> getAllDonThuoc(){
		return em.createNamedQuery("getAllDonThuoc", DonThuoc.class).getResultList();
	}
	public DonThuoc timKHKham(String date, BacSi a, KhachHang b) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		List<DonThuoc> list;
		DonThuoc c = new DonThuoc();
		Query q = em.createQuery("select c from DonThuoc c", DonThuoc.class);
		list = q.getResultList();
		for (DonThuoc d : list) {
			if ((d.getBacSi() == a) && (d.getKhachHang() == b) && (date.equalsIgnoreCase(df.format(d.getNgayLap()))))
				;
			c = d;
		}
		return c;
	}

	public DonThuoc getDonThuoc(String ma) {
		return em.find(DonThuoc.class, ma);
	}

	public boolean themDonThuoc(DonThuoc dt) {
		try {
			trans.begin();
			em.persist(dt);
			trans.commit();
			return true;
		}catch(Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;

	}
	public DonThuoc timDonThuocTheoMa(String ma) {
		return em.find(DonThuoc.class, ma);
	}
	

}
