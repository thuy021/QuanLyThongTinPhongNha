package defaultTableModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.DonThuoc;
import entity.GoiDichVu;

public class DonThuocTableModel extends AbstractTableModel{
	String columns[] = { "STT", "Mã Số", "Ngày lập","Tổng tiền","Bác sĩ","Gói dịch vụ","Khách hàng","Nhân viên"};
	List<DonThuoc> listDonThuoc = new ArrayList<DonThuoc>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public DonThuocTableModel(List<DonThuoc> listDonThuoc) {
		this.listDonThuoc = listDonThuoc;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return listDonThuoc.size();
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
		DonThuoc donThuoc = listDonThuoc.get(rowIndex);
		Object result = null;
		switch (columnIndex) {
		case 0:
			result = rowIndex + 1;
			break;
		case 1:
			result = donThuoc.getMaDonThuoc();
			break;
		case 2:
			result = donThuoc.getNgayLap();
			break;
		case 3:
			result = donThuoc.getTongTien();
			break;
		case 4:
			result = donThuoc.getBacSi();
			break;
		case 5:
			result = donThuoc.getGoiDV();
			break;
		case 6:
			result = donThuoc.getKhachHang();
			break;
		case 7:
			result = donThuoc.getNhanVien();
			break;
		}
		return result;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		super.setValueAt(aValue, rowIndex, columnIndex);
	}

}
