package defaultTableModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.NhanVien;
@SuppressWarnings("serial")
public class NhanVienTableModel extends AbstractTableModel {
	String columns[] = { "STT", "Mã Số", "Họ Tên", "Số điện thoại", "Địa Chỉ", "Loại tài khoản", "Ngày sinh" };
	List<NhanVien> listNhanVien = new ArrayList<NhanVien>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public NhanVienTableModel(List<NhanVien> listNhanVien) {
		this.listNhanVien = listNhanVien;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return listNhanVien.size();
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
		NhanVien nhanVien = listNhanVien.get(rowIndex);
		Object result = null;
		switch (columnIndex) {
		case 0:
			result = rowIndex + 1;
			break;
		case 1:
			result = nhanVien.getMaNhanVien();
			break;
		case 2:
			result = nhanVien.getTenNhanVien();
			break;
		case 3:
			result = nhanVien.getSoDienThoai();
			break;
		case 4:
			result = nhanVien.getDiaChi();
			break;
		case 5:
			result = nhanVien.getLoaiTaiKhoan();
			break;
		case 6:
			result = nhanVien.getNgaySinh();
			break;
		}
		return result;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		super.setValueAt(aValue, rowIndex, columnIndex);
	}
}
