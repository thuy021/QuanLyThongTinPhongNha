package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;

import dao.BacSiDAO;
import dao.KhachHangDAO;
import defaultTableModel.BacSiTableModel;
import defaultTableModel.KhachHangTableModel;
import entity.BacSi;
import entity.KhachHang;

public class GUI_QuanLyKhachHang implements ActionListener,MouseListener {
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtTimKhachHang, txthoTen, txtsoPhone, txtDiaChi, txtMa, txtSoCMND;
	private JButton btnTimKhachHang, btnThem, btnXoa, btnUpdate, btnReset, btnXoaTrong;
	private JDatePickerImpl jpkNgaySinh;
	private JRadioButton radNam, radNu;
	JTable tblKhachHang;
	DefaultListModel<String> listModel;
	private List<KhachHang> dsKhachhang;
	private KhachHangDAO xlKhachHang = new KhachHangDAO();
	private KhachHangTableModel khachHangTableModel;
	private int index = -1;

	public GUI_QuanLyKhachHang(){

	}

	/**
	 * GUI MAIN GIAO DIỆN.
	 * 
	 * @return
	 */
	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		conn.add(store_GUI.createLable(500, 10, 300, 30, "QUẢN LÝ KHÁCH HÀNG "));
		// button sự kiện.
		btnThem = store_GUI.createButton(340, 360, 100, 30, "Thêm");
		btnXoa = store_GUI.createButton(460, 360, 100, 30, "Xóa");
		btnUpdate = store_GUI.createButton(580, 360, 100, 30, "Sửa ");
		btnXoaTrong = store_GUI.createButton(700, 360, 100, 30, "Xoá trống");
		btnReset = store_GUI.createButton(820, 360, 100, 30, "Reset");

		btnThem.setIcon(store_GUI.taonICon("add.png", 20, 20));
		btnXoa.setIcon(store_GUI.taonICon("delete.png", 20, 20));
		btnUpdate.setIcon(store_GUI.taonICon("update.png", 20, 20));
		btnXoaTrong.setIcon(store_GUI.taonICon("clean.png", 20, 20));
		btnReset.setIcon(store_GUI.taonICon("refresh.png", 20, 20));

		//conn.add(btnThem);
		conn.add(btnXoa);
		conn.add(btnUpdate);
		conn.add(btnXoaTrong);
		conn.add(btnReset);
		btnReset.addActionListener(this);
		btnThem.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaTrong.addActionListener(this);

		JPanel pnBangKhachHang = taoPanleBangKhachHang();
		JPanel pnChiTietKH = taoPanleTTChiTietKhachHang();
		JPanel pnTimKiem = taoPanleTimKiem();
		conn.add(pnBangKhachHang);
		conn.add(pnChiTietKH);
		conn.add(pnTimKiem);
		return conn;

	}

	/*
	 * PANEL BẢNG DANH SÁCH NHÂN VIÊN.
	 */
	private JPanel taoPanleBangKhachHang() {
		JPanel pnKhachHang = store_GUI.createPannel(40, 400, 1200, 255, "Bảng danh sách khách hàng");
		pnKhachHang.setLayout(new BorderLayout());
		tblKhachHang = new JTable();
		JPanel pnTableKhachHang = store_GUI.createPanelTable(tblKhachHang);
		pnKhachHang.add(pnTableKhachHang);
		tblKhachHang.addMouseListener(this);
		return pnKhachHang;

	}

	/**
	 * PANLE TÌM KIẾM THEO MÃ KHÁCH HÀNG. TÌM KIẾM TUYỆT ĐỐI
	 * 
	 * @return
	 */
	private JPanel taoPanleTimKiem() {
		JPanel pnTimKiem = store_GUI.createPannel(740, 50, 500, 300, "Tìm kiếm");
		pnTimKiem.add(store_GUI.createLable(15, 30, 120, 30, "Tìm khách hàng:"));
		txtTimKhachHang = store_GUI.createTextField(140, 30, 180, 30, 100);
		btnTimKhachHang = store_GUI.createButton(315, 30, 100, 30, "Tìm Kiếm");
		btnTimKhachHang.setIcon(store_GUI.taonICon("search.png", 20, 20));
		pnTimKiem.add(txtTimKhachHang);
		pnTimKiem.add(btnTimKhachHang);
		btnTimKhachHang.addActionListener(this);
		return pnTimKiem;
	}

	/**
	 * PANEL THÔNG TIN CHI TIẾT KHÁCH HÀNG.
	 * 
	 * @return
	 */
	private JPanel taoPanleTTChiTietKhachHang() {
		JPanel pnChiTietKhachHang = store_GUI.createPannel(40, 50, 700, 300, "Thông tin chi tiết khách hàng");
		pnChiTietKhachHang.add(store_GUI.createLable(20, 30, 80, 30, "Mã :"));
		txtMa = store_GUI.createTextField(115, 30, 200, 30, 100);
		pnChiTietKhachHang.add(txtMa);

		// Họ tên
		pnChiTietKhachHang.add(store_GUI.createLable(20, 80, 80, 30, "Họ tên:"));
		txthoTen = store_GUI.createTextField(115, 80, 200, 30, 100);
		pnChiTietKhachHang.add(txthoTen);

		// số điện thoại
		pnChiTietKhachHang.add(store_GUI.createLable(20, 130, 80, 30, "số điện thoại:"));
		txtsoPhone = store_GUI.createTextField(115, 130, 200, 30, 100);
		pnChiTietKhachHang.add(txtsoPhone);

		// ngày sinh.
		pnChiTietKhachHang.add(store_GUI.createLable(350, 30, 80, 30, "Ngày sinh:"));
		jpkNgaySinh = store_GUI.createJDatePicker(445, 30, 200, 35);
		pnChiTietKhachHang.add(jpkNgaySinh);

		// Dia Chị
		pnChiTietKhachHang.add(store_GUI.createLable(350, 80, 80, 30, "Địa chỉ :"));
		txtDiaChi = store_GUI.createTextField(445, 80, 200, 30, 100);
		pnChiTietKhachHang.add(txtDiaChi);
		// Số CMND
		pnChiTietKhachHang.add(store_GUI.createLable(350, 130, 80, 30, "Số CMND:"));
		txtSoCMND = store_GUI.createTextField(445, 130, 200, 30, 100);
		pnChiTietKhachHang.add(txtSoCMND);

		pnChiTietKhachHang.add(store_GUI.createLable(20, 180, 80, 30, "Giới tính	:"));
		radNam = new JRadioButton("Nam");
		radNam.setBounds(115, 180, 70, 30);
		radNu = new JRadioButton("Nữ");
		radNu.setBounds(185, 180, 70, 30);
		pnChiTietKhachHang.add(radNam);
		pnChiTietKhachHang.add(radNu);
		ButtonGroup group = new ButtonGroup();
		group.add(radNam);
		group.add(radNu);

		return pnChiTietKhachHang;
	}



	/**
	 * PHƯƠNG THỨC XÓA RỔNG TRÊN TEXT FILED
	 */
	public void xoaRongTrenTextField() {
		txthoTen.setText(null);
		txtDiaChi.setText(null);
		txtMa.setText(null);
		txtsoPhone.setText(null);
	}
	private KhachHang getKhachHangOnTextField() {
		String maKhachHang = "", tenKhachHang = "", soDienThoai = "", soCMND = "", diaChi = "";
		boolean gioiTinh = true;
		maKhachHang = store_GUI.getValueTextField(txtMa);
		if (maKhachHang == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập mã");
		}
		tenKhachHang = store_GUI.getValueTextField(txthoTen);
		if (tenKhachHang == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập họ tên");
		}
		soDienThoai = store_GUI.getValueTextField(txtsoPhone);
		if (soDienThoai == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập số điện thoại");
		}
		soCMND = store_GUI.getValueTextField(txtSoCMND);
		if (soCMND == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập CMND");
		}
		diaChi = store_GUI.getValueTextField(txtDiaChi);
		if (diaChi == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập địa chỉ");
		}
		Date ngaySinh = (Date) jpkNgaySinh.getModel().getValue();
		KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, gioiTinh, ngaySinh, soDienThoai, soCMND, diaChi);
		return khachHang;
	}
	public void taiLaiDuLieuTableKhachHang() {
		dsKhachhang = xlKhachHang.getAllKH();
		khachHangTableModel = new KhachHangTableModel(dsKhachhang);
		tblKhachHang.setModel(khachHangTableModel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if(obj.equals(btnXoa)) {
			if(index==-1) {
				JOptionPane.showMessageDialog(null, "Mời bạn chọn Khách hàng để xóa !");
			}else {
				KhachHang khachHang = dsKhachhang.get(index);
				if(xlKhachHang.xoaKhachHang(khachHang)) {
					JOptionPane.showMessageDialog(null, "Xóa Khách hàng thành công !!");
					taiLaiDuLieuTableKhachHang();
					xoaRongTrenTextField();
					index=-1;
				}
			}
		}
		if (obj.equals(btnUpdate)) {
			if (index == -1) {
				JOptionPane.showMessageDialog(null, "Mời bạn chọn khách hàng trong bảng để cập nhật");
			} else {
				KhachHang khachHang = getKhachHangOnTextField();
				if (xlKhachHang.capNhatKhachHang(khachHang)) {
					JOptionPane.showConfirmDialog(null, "Cập nhật khách hàng thành công !");
					taiLaiDuLieuTableKhachHang();
					xoaRongTrenTextField();
					index = -1;
				}
			}
		}
		if (obj.equals(btnTimKhachHang)) {
			String maKH = store_GUI.getValueTextField(txtTimKhachHang);
			if (maKH != null) {
				KhachHang khachHang = xlKhachHang.timKHTheoMa(maKH);
				List<KhachHang> listKhachHangs = new ArrayList<KhachHang>();
				listKhachHangs.add(khachHang);
				khachHangTableModel = new KhachHangTableModel(listKhachHangs);
				tblKhachHang.setModel(khachHangTableModel);
			} else {
				JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng !");

			}
		}
		if (obj.equals(btnXoaTrong)) {
			xoaRongTrenTextField();
		}
		if (obj.equals(btnReset)) {
			taiLaiDuLieuTableKhachHang();
			index = -1;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		index = tblKhachHang.getSelectedRow();
		if (index != -1) {
			KhachHang khachHang = dsKhachhang.get(index);
			txthoTen.setText(khachHang.getTenKhachHang());
			txtMa.setText(khachHang.getMaKhachHang());
			txtDiaChi.setText(khachHang.getDiaChi());
			txtSoCMND.setText(khachHang.getSoCMND());
			txtsoPhone.setText(khachHang.getSoDienThoai());
			if (khachHang.isGioiTinh()) {
				radNam.setSelected(true);
			} else {
				radNu.setSelected(true);
			}

			Date ngaySinh = (Date) jpkNgaySinh.getModel().getValue();
		}
		
	}

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



}
