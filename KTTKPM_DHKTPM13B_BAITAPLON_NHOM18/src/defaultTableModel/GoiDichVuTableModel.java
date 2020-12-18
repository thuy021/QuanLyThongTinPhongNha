package defaultTableModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.GoiDichVu;
import entity.Thuoc;

public class GoiDichVuTableModel extends AbstractTableModel{
	String columns[] = { "STT", "Mã Số", "Tên gói","Đơn giá"};
	List<GoiDichVu> listGoiDichVu = new ArrayList<GoiDichVu>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public GoiDichVuTableModel(List<GoiDichVu> listGoiDichVu) {
		this.listGoiDichVu = listGoiDichVu;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return listGoiDichVu.size();
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
		GoiDichVu goiDichVu = listGoiDichVu.get(rowIndex);
		Object result = null;
		switch (columnIndex) {
		case 0:
			result = rowIndex + 1;
			break;
		case 1:
			result = goiDichVu.getMaGoiDV();
			break;
		case 2:
			result = goiDichVu.getTenGoiDV();
			break;
		case 3:
			result = goiDichVu.getDonGia();
			break;
		}
		return result;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		super.setValueAt(aValue, rowIndex, columnIndex);
	}
}
