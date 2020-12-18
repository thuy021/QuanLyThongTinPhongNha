package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;

import dao.NhanVienDAO;
import dao.ThuocDAO;
import defaultTableModel.GoiDichVuTableModel;
import defaultTableModel.NhanVienTableModel;
import entity.GoiDichVu;
import entity.NhanVien;
import entity.Thuoc;

public class GUI_QuanLyThuoc implements ActionListener, MouseListener {
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtTimThuoc,txtMa,txtTenThuoc,txtGiaBan,txtDonViTinh;
	private JButton btnTimThuoc, btnThem, btnXoa, btnUpdate, btnReset, btnXoaTrong;
	private JDatePickerImpl jpkNgaySanXuat,jpkHSD;
	private JRadioButton radNam, radNu;
	private JComboBox cbbChucVu;
	JTable tblThuoc;
	private List<Thuoc>dsThuoc;
	private ThuocDAO xlThuoc = new ThuocDAO();
	private ThuocTableModel thuocTableModel;
	private int index = -1;
	public GUI_QuanLyThuoc(){

	}

	/**
	 * GUI MAIN GIAO DIỆN.
	 * 
	 * @return
	 */
	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		conn.add(store_GUI.createLable(500, 10, 300, 30, "QUẢN LÝ THUỐC "));
		// button sự kiện.

		btnThem = store_GUI.createButton(340, 360, 100, 30, " Thêm ");
		btnXoa = store_GUI.createButton(460, 360, 100, 30, "Xóa");
		btnUpdate = store_GUI.createButton(580, 360, 100, 30, "Sửa ");
		btnXoaTrong = store_GUI.createButton(700, 360, 100, 30, "Xoá trống");
		btnReset = store_GUI.createButton(820, 360, 100, 30, "Reset");

		btnThem.setIcon(store_GUI.taonICon("add.png", 20, 20));
		btnXoa.setIcon(store_GUI.taonICon("delete.png", 20, 20));
		btnUpdate.setIcon(store_GUI.taonICon("update.png", 20, 20));
		btnXoaTrong.setIcon(store_GUI.taonICon("clean.png", 20, 20));
		btnReset.setIcon(store_GUI.taonICon("refresh.png", 20, 20));

		conn.add(btnThem);
		conn.add(btnXoa);
		conn.add(btnUpdate);
		conn.add(btnXoaTrong);
		conn.add(btnReset);
		
		btnReset.addActionListener(this);
		btnThem.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaTrong.addActionListener(this);
		
		JPanel pnBangThuoc = taoPanleBangThuoc();
		JPanel pnChiTietNV = taoPanleTTChiTietThuoc();
		JPanel pnTimKiem = taoPanleTimKiem();
		conn.add(pnBangThuoc);
		conn.add(pnChiTietNV);
		conn.add(pnTimKiem);
		return conn;

	}

	/*
	 * PANEL BẢNG DANH SÁCH THUỐC
	 */
	private JPanel taoPanleBangThuoc() {
		JPanel pnThuoc = store_GUI.createPannel(40, 400, 1200, 255, "Bảng danh sách thuốc");
		pnThuoc.setLayout(new BorderLayout());
		tblThuoc = new JTable();
		JPanel pnTableThuoc = store_GUI.createPanelTable(tblThuoc);
		pnThuoc.add(pnTableThuoc);
		tblThuoc.addMouseListener(this);
		return pnThuoc;

	}

	/**
	 * PANLE TÌM KIẾM THUỐC
	 * 
	 * @return
	 */
	private JPanel taoPanleTimKiem() {
		JPanel pnTimKiem = store_GUI.createPannel(740, 50, 500, 300, "Tìm kiếm");
		pnTimKiem.add(store_GUI.createLable(15, 30, 120, 30, "Tìm thuốc:"));
		txtTimThuoc = store_GUI.createTextField(140, 30, 180, 30, 100);
		btnTimThuoc = store_GUI.createButton(315, 30, 100, 30, "Tìm Kiếm");
		btnTimThuoc.setIcon(store_GUI.taonICon("search.png", 20, 20));
		pnTimKiem.add(txtTimThuoc);
		pnTimKiem.add(btnTimThuoc);
		btnTimThuoc.addActionListener(this);
		return pnTimKiem;
	}

	/**
	 * PANEL THÔNG TIN CHI TIẾT THUỐC.
	 * 
	 * @return
	 */
	private JPanel taoPanleTTChiTietThuoc() {
		JPanel pnChiTietSanPham = store_GUI.createPannel(40, 50, 700, 300, "Thông tin chi tiết thuốc:");
		// mã Thuốc

		pnChiTietSanPham.add(store_GUI.createLable(10, 30, 100, 30, "Mã thuốc :"));
		txtMa = store_GUI.createTextField(115, 30, 200, 30, 100);
		pnChiTietSanPham.add(txtMa);
		
		// tên Thuốc
		pnChiTietSanPham.add(store_GUI.createLable(10, 80, 100, 30, "Tên thuốc:"));
		txtTenThuoc = store_GUI.createTextField(115, 80, 200, 30, 100);
		pnChiTietSanPham.add(txtTenThuoc);


		// giá bán
		pnChiTietSanPham.add(store_GUI.createLable(350, 30, 85, 30, "Giá bán:"));
		txtGiaBan = store_GUI.createTextField(445, 30, 200, 30, 100);
		pnChiTietSanPham.add(txtGiaBan);

		// đơn vị tính
		pnChiTietSanPham.add(store_GUI.createLable(350, 80, 85, 30, "Đơn vị tính:"));
		txtDonViTinh = store_GUI.createTextField(455, 80, 200, 30, 100);
		pnChiTietSanPham.add(txtDonViTinh);

		// hạn sử dụng
		pnChiTietSanPham.add(store_GUI.createLable(10, 130, 85, 30, "Ngày Sản Xuất:"));
		jpkNgaySanXuat = store_GUI.createJDatePicker(115, 130, 200, 35);
		pnChiTietSanPham.add(jpkNgaySanXuat);

		pnChiTietSanPham.add(store_GUI.createLable(350, 130, 85, 30, "Hạn sử dụng:"));
		jpkHSD = store_GUI.createJDatePicker(445, 130, 200, 35);
		pnChiTietSanPham.add(jpkHSD);

		return pnChiTietSanPham;
	}



	/**
	 * PHƯƠNG THỨC XÓA RỔNG TRÊN TEXT FILED
	 */
	public void xoaRongTrenTextField() {
		txtTenThuoc.setText(null);
		txtGiaBan.setText(null);
		txtMa.setText(null);
		txtDonViTinh.setText(null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		index = tblThuoc.getSelectedRow();
		if (index != -1) {
			Thuoc thuoc = dsThuoc.get(index);
			txtTenThuoc.setText(thuoc.getTenThuoc());
			txtMa.setText(thuoc.getMaThuoc());
			txtGiaBan.setText(df.format(thuoc.getDonGia()));
			txtDonViTinh.setText(thuoc.getDonVi());
		}
	}
	DecimalFormat df = new DecimalFormat("#, ### VND");
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if(obj.equals(btnThem)) {
			Thuoc thuoc = getThuocOnTextField();
			taiLaiDuLieuTableThuoc();
			if(xlThuoc.themThuoc(thuoc)) {
				JOptionPane.showMessageDialog(null, "Thêm thành công");

			}
		}
		if(obj.equals(btnXoa)) {
			if(index==-1) {
				JOptionPane.showMessageDialog(null, "Mời bạn chọn thuốc để xóa !");
			}else {
				Thuoc thuoc = dsThuoc.get(index);
				if(xlThuoc.xoaThuoc(thuoc)) {
					JOptionPane.showMessageDialog(null, "Xóa thuốc thành công !!");
					taiLaiDuLieuTableThuoc();
					xoaRongTrenTextField();
					index=-1;
				}
			}
		}
		if(obj.equals(btnUpdate)) {
			if(index==-1) {
				JOptionPane.showMessageDialog(null, "Mời bạn chọn thuốc trong bảng để cập nhật");
			}else {
				Thuoc thuoc = getThuocOnTextField();
				if(xlThuoc.capNhatThuoc(thuoc)) {
					JOptionPane.showConfirmDialog(null, "Cập nhật thuốc thành công !");
					taiLaiDuLieuTableThuoc();
					xoaRongTrenTextField();
					index=-1;
				}
			}
		}
		if(obj.equals(btnTimThuoc)) {
			String tenThuoc = store_GUI.getValueTextField(txtTimThuoc);
			if(tenThuoc!=null) {
				Thuoc thuoc = xlThuoc.timThuocTheoTenThuoc(tenThuoc);
				List<Thuoc>listThuocs = new ArrayList<Thuoc>();
				listThuocs.add(thuoc);
				thuocTableModel = new ThuocTableModel(listThuocs);
				tblThuoc.setModel(thuocTableModel);
			}else {
				JOptionPane.showMessageDialog(null, "Không tìm thấy thuốc !");

			}
		}
		if(obj.equals(btnXoaTrong)) {
			xoaRongTrenTextField();
		}
		if(obj.equals(btnReset)) {
			taiLaiDuLieuTableThuoc();
			index=-1;
		}
	}
	private Thuoc getThuocOnTextField() {
		String maThuoc="",tenThuoc="",donVi="";
		String donGia="";
		double gia=0;
		maThuoc = store_GUI.getValueTextField(txtMa);
		if(maThuoc==null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập mã");
		}
		tenThuoc=store_GUI.getValueTextField(txtTenThuoc);
		if(tenThuoc==null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập tên thuốc dịch vụ");
		}
		donVi=store_GUI.getValueTextField(txtDonViTinh);
		if(donVi==null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập đơn vị dịch vụ");
		}
		donGia=store_GUI.getValueTextField(txtGiaBan);
		if(donGia==null) {
				JOptionPane.showMessageDialog(null, "Chưa nhập đơn giá");
		}else {
			gia=Double.parseDouble(donGia);
		}
		Thuoc thuoc = new Thuoc(maThuoc, tenThuoc, donVi, gia);
		return thuoc;
	}
	public void taiLaiDuLieuTableThuoc() {
		dsThuoc = xlThuoc.listThuoc();
		thuocTableModel = new ThuocTableModel(dsThuoc);
		tblThuoc.setModel(thuocTableModel);
	}

}
