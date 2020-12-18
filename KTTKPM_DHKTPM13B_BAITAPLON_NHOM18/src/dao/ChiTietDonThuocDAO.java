package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import entity.ChiTietDonThuoc;
import entity.DonThuoc;
import entity.Thuoc;

public class ChiTietDonThuocDAO extends AbstractCRUD<ChiTietDonThuoc>{
	public List<ChiTietDonThuoc> getAllChiTietDonThuoc(){
		return em.createNamedQuery("getAllCTDonThuoc", ChiTietDonThuoc.class).getResultList();
	}
	public ChiTietDonThuoc timCTThuocTheoMa(String ma) {
		List<ChiTietDonThuoc> list;
		ChiTietDonThuoc b = new ChiTietDonThuoc();

		Query q = em.createQuery("select a from ChiTietDonThuoc a", Thuoc.class);
		list = q.getResultList();
		for (ChiTietDonThuoc a : list) {
			if (a.getDonThuoc().getMaDonThuoc().equalsIgnoreCase(ma))
				b = a;
		}
		return b;
	}
	public List<Thuoc> listThuoc(){
		return em.createNamedQuery("getAllThuoc", Thuoc.class).getResultList();
	}
	public List<ChiTietDonThuoc> timCTDTTheoMaDT(String ma) {
		List<ChiTietDonThuoc> list1 = new ArrayList<ChiTietDonThuoc>();
		List<ChiTietDonThuoc> list = new ArrayList<ChiTietDonThuoc>();

		Query q = em.createQuery("select a from ChiTietDonThuoc a", ChiTietDonThuoc.class);
		list = q.getResultList();
		for (ChiTietDonThuoc a : list) {
			if (a.getDonThuoc().getMaDonThuoc().equalsIgnoreCase(ma))
				list1.add(a);
		}

		return list1;
	}
	
}
