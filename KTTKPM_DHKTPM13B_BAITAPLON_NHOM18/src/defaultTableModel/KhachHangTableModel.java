package defaultTableModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.BacSi;
import entity.KhachHang;

public class KhachHangTableModel extends AbstractTableModel {
	String columns[] = { "STT", "Mã Số", "Họ Tên","Giới tính", "Ngày sinh", "Địa Chỉ", "CMND", "SDT" };
	List<KhachHang> listKhachHang = new ArrayList<KhachHang>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public KhachHangTableModel(List<KhachHang> listKhachHang) {
		this.listKhachHang = listKhachHang;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return listKhachHang.size();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		KhachHang khachHang = listKhachHang.get(rowIndex);
		Object result = null;
		switch (columnIndex) {
		case 0:
			result = rowIndex + 1;
			break;
		case 1:
			result = khachHang.getMaKhachHang();
			break;
		case 2:
			result = khachHang.getTenKhachHang();
			break;
		case 3:
			result = khachHang.isGioiTinh();
			break;
		case 4:
			result = khachHang.getNgaySinh();
			break;
		case 5:
			result = khachHang.getDiaChi();
			break;
		case 6:
			result = khachHang.getSoCMND();
			break;
		case 7:
			result=khachHang.getSoDienThoai();
		}
		return result;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		super.setValueAt(aValue, rowIndex, columnIndex);
	}
}
