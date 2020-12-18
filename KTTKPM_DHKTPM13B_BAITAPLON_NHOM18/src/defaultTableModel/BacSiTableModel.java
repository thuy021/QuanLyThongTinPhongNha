package defaultTableModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.BacSi;
import entity.NhanVien;

public class BacSiTableModel extends AbstractTableModel {
	String columns[] = { "STT", "Mã Số", "Họ Tên","Giới tính", "Ngày sinh", "Địa Chỉ", "Chuyên môn", "CMND" ,"Tài khoản","Mật khẩu"};
	List<BacSi> listBacSi = new ArrayList<BacSi>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public BacSiTableModel(List<BacSi> listBacSi) {
		this.listBacSi = listBacSi;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return listBacSi.size();
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
		BacSi bacSi = listBacSi.get(rowIndex);
		Object result = null;
		switch (columnIndex) {
		case 0:
			result = rowIndex + 1;
			break;
		case 1:
			result = bacSi.getMaBacSi();
			break;
		case 2:
			result = bacSi.getTenBacSi();
			break;
		case 3:
			result = bacSi.isGioiTinh();
			break;
		case 4:
			result = bacSi.getNgaySinh();
			break;
		case 5:
			result = bacSi.getDiaChi();
			break;
		case 6:
			result = bacSi.getChuyenMon();
			break;
		case 7:
			result=bacSi.getSoCMND();
		case 8:
			result=bacSi.getTaiKhoan();
		case 9:
			result=bacSi.getMatKhau();
		}
		return result;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		super.setValueAt(aValue, rowIndex, columnIndex);
	}
}
