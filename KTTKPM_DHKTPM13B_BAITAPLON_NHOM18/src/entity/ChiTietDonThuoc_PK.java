package entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class ChiTietDonThuoc_PK implements Serializable {
	private String thuoc;
	private String donThuoc;

	public String getThuoc() {
		return thuoc;
	}

	public void setThuoc(String thuoc) {
		this.thuoc = thuoc;
	}

	public String getDonThuoc() {
		return donThuoc;
	}

	public void setDonThuoc(String donThuoc) {
		this.donThuoc = donThuoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((donThuoc == null) ? 0 : donThuoc.hashCode());
		result = prime * result + ((thuoc == null) ? 0 : thuoc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietDonThuoc_PK other = (ChiTietDonThuoc_PK) obj;
		if (donThuoc == null) {
			if (other.donThuoc != null)
				return false;
		} else if (!donThuoc.equals(other.donThuoc))
			return false;
		if (thuoc == null) {
			if (other.thuoc != null)
				return false;
		} else if (!thuoc.equals(other.thuoc))
			return false;
		return true;
	}

}
