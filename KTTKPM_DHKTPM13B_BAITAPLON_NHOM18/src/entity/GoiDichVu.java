package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: GoiDichVu
 *
 */
@Entity
@Table(name = "GoiDichVu")
@NamedQueries({
	@NamedQuery(name = "getGoiDichVuByMa", query ="select k from GoiDichVu k where k.maGoiDV = :maGoiDV"),
	@NamedQuery(name = "getAllGoiDV", query ="select k from GoiDichVu k")
	
})
public class GoiDichVu implements Serializable {

	@Id
	private String maGoiDV;
	private String tenGoiDV;
	private double donGia;
	
	public GoiDichVu(String maGoiDV, String tenGoiDV, double donGia) {
		super();
		this.maGoiDV = maGoiDV;
		this.tenGoiDV = tenGoiDV;
		this.donGia = donGia;
	}

	public String getMaGoiDV() {
		return maGoiDV;
	}

	public void setMaGoiDV(String maGoiDV) {
		this.maGoiDV = maGoiDV;
	}

	public String getTenGoiDV() {
		return tenGoiDV;
	}

	public void setTenGoiDV(String tenGoiDV) {
		this.tenGoiDV = tenGoiDV;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	@Override
	public String toString() {
		return "GoiDichVu [maGoiDV=" + maGoiDV + ", tenGoiDV=" + tenGoiDV + ", donGia=" + donGia + "]";
	}

	private static final long serialVersionUID = 1L;

	public GoiDichVu() {
		super();
	}
   
}
