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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;

import dao.NhanVienDAO;
import defaultTableModel.NhanVienTableModel;
import entity.NhanVien;

public class GUI_QuanLyNhanVien implements ActionListener,MouseListener{
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtTimNhanVien, txthoTen, txtsoPhone, txtDiaChi, txtMa, txtUser, txtPass, txtSoCMND,txtLoaiTaiKhoan;
	private JButton btnTimNhanVien, btnThem, btnXoa, btnUpdate, btnReset, btnXoaTrong;
	private JDatePickerImpl jpkNgaySinh, jpkNgayVL;
	private JRadioButton radNam, radNu;
	private JComboBox cbbChucVu;
	JTable tblNhanVien;
	private List<NhanVien>dsNhanVien;
	private NhanVienDAO xlNhanVien = new NhanVienDAO();
	private NhanVienTableModel nhanVienTableModel;
	private int index = -1;
	public GUI_QuanLyNhanVien() {

	}

	/**
	 * GUI MAIN GIAO DIỆN.
	 * 
	 * @return
	 */
	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		conn.add(store_GUI.createLable(500, 10, 300, 30, "QUẢN LÝ NHÂN VIÊN "));
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
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnReset.addActionListener(this);

		JPanel pnBangNhanVien = taoPanleBangNhanVien();
		JPanel pnChiTietNV = taoPanleTTChiTietNhanVien();
		JPanel pnTimKiem = taoPanleTimKiem();
		conn.add(pnBangNhanVien);
		conn.add(pnChiTietNV);
		conn.add(pnTimKiem);
		return conn;

	}

	/*
	 * PANEL BẢNG DANH SÁCH NHÂN VIÊN.
	 */
	private JPanel taoPanleBangNhanVien() {
		JPanel pnNhanVien = store_GUI.createPannel(40, 400, 1200, 255, "Bảng danh sách nhân viên");
		pnNhanVien.setLayout(new BorderLayout());
		dsNhanVien = xlNhanVien.getAllNV();
		nhanVienTableModel = new NhanVienTableModel(dsNhanVien);
		tblNhanVien = new JTable(nhanVienTableModel);
		JPanel pnTableNhanVien = store_GUI.createPanelTable(tblNhanVien);
		pnNhanVien.add(pnTableNhanVien);
		return pnNhanVien;

	}

	/**
	 * PANLE TÌM KIẾM THEO NHÂN VIÊN
	 * 
	 * @return
	 */
	private JPanel taoPanleTimKiem() {
		JPanel pnTimKiem = store_GUI.createPannel(740, 50, 500, 300, "Tìm kiếm");
		pnTimKiem.add(store_GUI.createLable(15, 30, 120, 30, "Tìm Nhân viên:"));
		txtTimNhanVien = store_GUI.createTextField(140, 30, 180, 30, 100);
		btnTimNhanVien = store_GUI.createButton(315, 30, 100, 30, "Tìm Kiếm");
		btnTimNhanVien.setIcon(store_GUI.taonICon("search.png", 20, 20));
		pnTimKiem.add(txtTimNhanVien);
		pnTimKiem.add(btnTimNhanVien);
		tblNhanVien.addMouseListener(this);
		btnTimNhanVien.addActionListener(this);
		return pnTimKiem;
	}

	/**
	 * PANEL THÔNG TIN CHI TIẾT NHÂN VIÊN.
	 * 
	 * @return
	 */
	private JPanel taoPanleTTChiTietNhanVien() {
		JPanel pnChiTietNhanVien = store_GUI.createPannel(40, 50, 700, 300, "Thông tin chi tiết Nhân Viên");
		pnChiTietNhanVien.add(store_GUI.createLable(20, 30, 80, 30, "Mã :"));
		txtMa = store_GUI.createTextField(115, 30, 200, 30, 100);
		pnChiTietNhanVien.add(txtMa);

		// Họ tên
		pnChiTietNhanVien.add(store_GUI.createLable(20, 80, 80, 30, "Họ tên:"));
		txthoTen = store_GUI.createTextField(115, 80, 200, 30, 100);
		pnChiTietNhanVien.add(txthoTen);

		// số điện thoại
		pnChiTietNhanVien.add(store_GUI.createLable(20, 130, 80, 30, "số điện thoại:"));
		txtsoPhone = store_GUI.createTextField(115, 130, 200, 30, 100);
		pnChiTietNhanVien.add(txtsoPhone);

		// ngày sinh.
		pnChiTietNhanVien.add(store_GUI.createLable(350, 30, 80, 30, "Ngày sinh:"));
		jpkNgaySinh = store_GUI.createJDatePicker(445, 30, 200, 35);
		pnChiTietNhanVien.add(jpkNgaySinh);

		// Dia Chị
		pnChiTietNhanVien.add(store_GUI.createLable(350, 80, 80, 30, "Địa chỉ :"));
		txtDiaChi = store_GUI.createTextField(445, 80, 200, 30, 100);
		pnChiTietNhanVien.add(txtDiaChi);
		// Số CMND
		pnChiTietNhanVien.add(store_GUI.createLable(350, 130, 80, 30, "Số CMND:"));
		txtSoCMND = store_GUI.createTextField(445, 130, 200, 30, 100);
		pnChiTietNhanVien.add(txtSoCMND);

		pnChiTietNhanVien.add(store_GUI.createLable(350, 180, 80, 30, "Tài khoản:"));
		pnChiTietNhanVien.add(store_GUI.createLable(350, 230, 80, 30, "Mật khẩu:"));

		txtUser = store_GUI.createTextField(445, 180, 200, 30, 100);
		txtPass = store_GUI.createTextField(445, 230, 200, 30, 100);
		// Loai TK
		pnChiTietNhanVien.add(store_GUI.createLable(350, 260, 80, 30, "Loại tài khoản :"));
		txtLoaiTaiKhoan = store_GUI.createTextField(445, 260, 200, 30, 100);
		pnChiTietNhanVien.add(txtLoaiTaiKhoan);

		pnChiTietNhanVien.add(txtUser);
		pnChiTietNhanVien.add(txtPass);

		pnChiTietNhanVien.add(store_GUI.createLable(20, 180, 80, 30, "Giới tính	:"));
		radNam = new JRadioButton("Nam");
		radNam.setBounds(115, 180, 70, 30);
		radNu = new JRadioButton("Nữ");
		radNu.setBounds(185, 180, 70, 30);
		pnChiTietNhanVien.add(radNam);
		pnChiTietNhanVien.add(radNu);
		ButtonGroup group = new ButtonGroup();
		group.add(radNam);
		group.add(radNu);

		pnChiTietNhanVien.add(store_GUI.createLable(3, 230, 100, 30, "Ngày vào làm :"));
		jpkNgayVL = store_GUI.createJDatePicker(115, 230, 200, 35);
		pnChiTietNhanVien.add(jpkNgayVL);

		return pnChiTietNhanVien;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if(obj.equals(btnThem)) {
			NhanVien nhanVien = getNhanVienOnTextField();
			taiLaiDuLieuTableNhanVien();
			if(xlNhanVien.themNhanVien(nhanVien)) {
				JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công");

			}
		}
		if(obj.equals(btnXoa)) {
			if(index==-1) {
				JOptionPane.showMessageDialog(null, "Mời bạn chọn Nhân Viên để xóa !");
			}else {
				NhanVien nhanVien = dsNhanVien.get(index);
				if(xlNhanVien.xoaNhanVien(nhanVien)) {
					JOptionPane.showMessageDialog(null, "Xóa Nhân viên thành công !!");
					taiLaiDuLieuTableNhanVien();
					ClearText();
					index=-1;
				}
			}
		}
		if(obj.equals(btnUpdate)) {
			if(index==-1) {
				JOptionPane.showMessageDialog(null, "Mời bạn chọn nhân viên trong bảng để cập nhật");
			}else {
				NhanVien nhanVien = getNhanVienOnTextField();
				if(xlNhanVien.capNhatNhanVien(nhanVien)) {
					JOptionPane.showConfirmDialog(null, "Cập nhật Nhân viên thành công !");
					taiLaiDuLieuTableNhanVien();
					ClearText();
					index=-1;
				}
			}
		}
		if(obj.equals(btnTimNhanVien)) {
			String maNV = store_GUI.getValueTextField(txtTimNhanVien);
			if(maNV!=null) {
				NhanVien nhanVien = xlNhanVien.timNVTheoMa(maNV);
				List<NhanVien>listNhanViens = new ArrayList<NhanVien>();
				listNhanViens.add(nhanVien);
				nhanVienTableModel = new NhanVienTableModel(listNhanViens);
				tblNhanVien.setModel(nhanVienTableModel);
			}else {
				JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên !");

			}
		}
		if(obj.equals(btnXoaTrong)) {
			ClearText();
		}
		if(obj.equals(btnReset)) {
			taiLaiDuLieuTableNhanVien();
			index=-1;
		}
	}
	private NhanVien getNhanVienOnTextField() {
		String ma="",hoTen="",diaChi="",sdt="",cmnd="",taiKhoan="",matKhau="",loaitk="";
		boolean gioiTinh=true;
		ma = store_GUI.getValueTextField(txtMa);
		if(ma==null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập mã");
		}
		hoTen=store_GUI.getValueTextField(txthoTen);
		if(hoTen==null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập họ tên");
		}
		diaChi=store_GUI.getValueTextField(txtDiaChi);
		if(diaChi==null) {
				JOptionPane.showMessageDialog(null, "Chưa nhập địa chỉ");
		}
		sdt=store_GUI.getValueTextField(txtsoPhone);
		if(sdt==null) {
				JOptionPane.showMessageDialog(null, "Chưa nhập số điện thoại");
		}
		cmnd=store_GUI.getValueTextField(txtSoCMND);
		if(cmnd==null) {
				JOptionPane.showMessageDialog(null, "Chưa nhập CMND");
		}
		taiKhoan=store_GUI.getValueTextField(txtUser);
		if(taiKhoan==null) {
				JOptionPane.showMessageDialog(null, "Chưa nhập tài khoản");
		}
		matKhau=store_GUI.getValueTextField(txtPass);
		if(matKhau==null) {
				JOptionPane.showMessageDialog(null, "Chưa nhập mật khẩu");
		}
		loaitk=store_GUI.getValueTextField(txtLoaiTaiKhoan);
		if(loaitk==null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập loại tài khoản");
		}
		Date ngaySinh = (Date)jpkNgaySinh.getModel().getValue();
		Date ngayVaoLam = (Date)jpkNgayVL.getModel().getValue();
		NhanVien nhanVien = new NhanVien(ma,hoTen,gioiTinh,ngaySinh,sdt,cmnd,diaChi,ngayVaoLam,taiKhoan,loaitk,matKhau);
		return nhanVien;
	}
	public void taiLaiDuLieuTableNhanVien() {
		dsNhanVien = xlNhanVien.getAllNV();
		nhanVienTableModel = new NhanVienTableModel(dsNhanVien);
		tblNhanVien.setModel(nhanVienTableModel);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		index = tblNhanVien.getSelectedRow();
		if (index != -1) {
			NhanVien nhanVien = dsNhanVien.get(index);
			txthoTen.setText(nhanVien.getTenNhanVien());
			txtMa.setText(nhanVien.getMaNhanVien());
			txtDiaChi.setText(nhanVien.getDiaChi());
			txtsoPhone.setText(nhanVien.getSoDienThoai());
			if (nhanVien.isGioiTinh()) {
				radNam.setSelected(true);
			} else {
				radNu.setSelected(true);
			}
			txtUser.setText(nhanVien.getTaiKhoan());
			txtPass.setText(nhanVien.getMatKhau());
			txtLoaiTaiKhoan.setText(nhanVien.getLoaiTaiKhoan());
			txtSoCMND.setText(nhanVien.getSoCMND());
			Date ngaySinh = (Date) jpkNgaySinh.getModel().getValue();
		}
	}
	public void ClearText() {
		txtDiaChi.setText("");
		txthoTen.setText("");
		txtLoaiTaiKhoan.setText("");
		txtMa.setText("");
		txtPass.setText("");
		txtSoCMND.setText("");
		txtsoPhone.setText("");
		txtTimNhanVien.setText("");
		txtUser.setText("");
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
