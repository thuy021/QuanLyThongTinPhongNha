package gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.ChiTietDonThuoc;
import entity.Thuoc;

@SuppressWarnings("serial")
public class CTDTTableModel extends AbstractTableModel {
	String columns[] = { "STT","Mã đơn thuốc","Mã Thuốc", "Tên thuốc", "Đơn giá", "Đơn vị", "Số lượng", "Tổng tiền"};
	List<ChiTietDonThuoc> listCTDThuoc = new ArrayList<ChiTietDonThuoc>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public CTDTTableModel(List<ChiTietDonThuoc> listCTDThuoc) {
		this.listCTDThuoc = listCTDThuoc;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return listCTDThuoc.size();
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
		ChiTietDonThuoc ctdt = listCTDThuoc.get(rowIndex);
		Object result = null;
		switch (columnIndex) {
		case 0:
			result = rowIndex + 1;
			break;
		case 1:
			result = ctdt.getDonThuoc().getMaDonThuoc();
			break;
		case 2:
			result = ctdt.getThuoc().getMaThuoc();
			break;
		case 3:
			result = ctdt.getThuoc().getTenThuoc();
			break;
		case 4:
			result = ctdt.getThuoc().getDonGia();
			break;
		case 5:
			result = ctdt.getThuoc().getDonVi();
			break;
		case 6:
			result = ctdt.getSoLuong();
			break;
		case 7:
			result = ctdt.getThuoc().getDonGia() * ctdt.getSoLuong();
			break;
		}
		return result;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		super.setValueAt(aValue, rowIndex, columnIndex);
	}

}
