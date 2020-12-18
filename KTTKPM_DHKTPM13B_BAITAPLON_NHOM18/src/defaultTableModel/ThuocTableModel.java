package defaultTableModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.BacSi;
import entity.Thuoc;

public class ThuocTableModel extends AbstractTableModel{
	String columns[] = { "STT", "Mã Số", "Tên thuốc","Giá bán", "Đơn vị tính", "Ngày sản xuất", "Hạn sử dụng"};
	List<Thuoc> listThuoc = new ArrayList<Thuoc>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public ThuocTableModel(List<Thuoc> listThuoc) {
		this.listThuoc = listThuoc;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return listThuoc.size();
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
		Thuoc thuoc = listThuoc.get(rowIndex);
		Object result = null;
		switch (columnIndex) {
		case 0:
			result = rowIndex + 1;
			break;
		case 1:
			result = thuoc.getMaThuoc();
			break;
		case 2:
			result = thuoc.getTenThuoc();
			break;
		case 3:
			result = thuoc.getDonGia();
			break;
		case 4:
			result = thuoc.getDonVi();
			break;
		}
		return result;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		super.setValueAt(aValue, rowIndex, columnIndex);
	}
}
