package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.BasicConfigurator;
import org.jdatepicker.impl.JDatePickerImpl;

import dao.BacSiDAO;
import dao.NhanVienDAO;
import defaultTableModel.BacSiTableModel;
import defaultTableModel.NhanVienTableModel;
import entity.BacSi;
import entity.NhanVien;

public class GUI_QuanLyBacSy implements ActionListener, MouseListener {
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtTimBacSy, txthoTen, txtChuyenMon, txtDiaChi, txtMa, txtUser, txtPass, txtSoCMND;
	private JButton btnTimBacSy, btnThem, btnXoa, btnUpdate, btnReset, btnXoaTrong;
	private JDatePickerImpl jpkNgaySinh, jpkNgayVL;
	private JRadioButton radNam, radNu;
	private JComboBox cbbChucVu;
	JTable tblBacSy;
	DefaultListModel<String> listModel;
	private List<BacSi> dsBacSi;
	private BacSiDAO xlBacSi = new BacSiDAO();
	private BacSiTableModel bacSiTableModel;
	private int index = -1;

	public GUI_QuanLyBacSy() {

	}

	/**
	 * GUI MAIN GIAO DIỆN.
	 * 
	 * @return
	 * @throws NamingException
	 * @throws JMSException
	 */
	public JPanel designGUI() throws NamingException, JMSException {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		conn.add(store_GUI.createLable(500, 10, 300, 30, "QUẢN LÝ BÁC SỸ "));
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

		JPanel pnBangBacSy = taoPanleBangBacSy();
		JPanel pnChiTietBS = taoPanleTTChiTietBacSy();
		JPanel pnTimKiem = taoPanleTimKiem();
		conn.add(pnBangBacSy);
		conn.add(pnChiTietBS);
		conn.add(pnTimKiem);
		return conn;

	}

	/*
	 * PANEL BẢNG DANH SÁCH NHÂN VIÊN.
	 */
	private JPanel taoPanleBangBacSy() {
		JPanel pnBacSy = store_GUI.createPannel(40, 400, 1200, 255, "Bảng danh sách bác sỹ");
		pnBacSy.setLayout(new BorderLayout());
		tblBacSy = new JTable();
		JPanel pnTableBacSy = store_GUI.createPanelTable(tblBacSy);
		pnBacSy.add(pnTableBacSy);
		return pnBacSy;

	}

	/**
	 * PANLE TÌM KIẾM BÁC SỸ.
	 * 
	 * @return
	 */
	private JPanel taoPanleTimKiem() {
		JPanel pnTimKiem = store_GUI.createPannel(740, 50, 500, 300, "Tìm kiếm");
		pnTimKiem.add(store_GUI.createLable(15, 30, 120, 30, "Tìm Bác sỹ:"));
		txtTimBacSy = store_GUI.createTextField(140, 30, 180, 30, 100);
		btnTimBacSy = store_GUI.createButton(315, 30, 100, 30, "Tìm Kiếm");
		btnTimBacSy.setIcon(store_GUI.taonICon("search.png", 20, 20));
		pnTimKiem.add(txtTimBacSy);
		pnTimKiem.add(btnTimBacSy);
		tblBacSy.addMouseListener(this);
		btnTimBacSy.addActionListener(this);
		return pnTimKiem;
	}

	/**
	 * PANEL THÔNG TIN CHI TIẾT BÁC SỸ.
	 * 
	 * @return
	 */
	private JPanel taoPanleTTChiTietBacSy() {
		JPanel pnChiTietBacSy = store_GUI.createPannel(40, 50, 700, 300, "Thông tin chi tiết Bác sỹ");
		pnChiTietBacSy.add(store_GUI.createLable(20, 30, 80, 30, "Mã :"));
		txtMa = store_GUI.createTextField(115, 30, 200, 30, 100);
		pnChiTietBacSy.add(txtMa);

		// Họ tên
		pnChiTietBacSy.add(store_GUI.createLable(20, 80, 80, 30, "Họ tên:"));
		txthoTen = store_GUI.createTextField(115, 80, 200, 30, 100);
		pnChiTietBacSy.add(txthoTen);

		// số điện thoại
		pnChiTietBacSy.add(store_GUI.createLable(20, 130, 80, 30, "Chuyên môn:"));
		txtChuyenMon = store_GUI.createTextField(115, 130, 200, 30, 100);
		pnChiTietBacSy.add(txtChuyenMon);

		// ngày sinh.
		pnChiTietBacSy.add(store_GUI.createLable(350, 30, 80, 30, "Ngày sinh:"));
		jpkNgaySinh = store_GUI.createJDatePicker(445, 30, 200, 35);
		pnChiTietBacSy.add(jpkNgaySinh);

		// Dia Chị
		pnChiTietBacSy.add(store_GUI.createLable(350, 80, 80, 30, "Địa chỉ :"));
		txtDiaChi = store_GUI.createTextField(445, 80, 200, 30, 100);
		pnChiTietBacSy.add(txtDiaChi);
		// Số CMND
		pnChiTietBacSy.add(store_GUI.createLable(350, 130, 80, 30, "Số CMND:"));
		txtSoCMND = store_GUI.createTextField(445, 130, 200, 30, 100);
		pnChiTietBacSy.add(txtSoCMND);

		pnChiTietBacSy.add(store_GUI.createLable(350, 180, 80, 30, "Tài khoản:"));
		pnChiTietBacSy.add(store_GUI.createLable(350, 230, 80, 30, "Mật khẩu:"));

		txtUser = store_GUI.createTextField(445, 180, 200, 30, 100);
		txtPass = store_GUI.createTextField(445, 230, 200, 30, 100);

		pnChiTietBacSy.add(txtUser);
		pnChiTietBacSy.add(txtPass);

		pnChiTietBacSy.add(store_GUI.createLable(20, 180, 80, 30, "Giới tính	:"));
		radNam = new JRadioButton("Nam");
		radNam.setBounds(115, 180, 70, 30);
		radNu = new JRadioButton("Nữ");
		radNu.setBounds(185, 180, 70, 30);
		pnChiTietBacSy.add(radNam);
		pnChiTietBacSy.add(radNu);
		ButtonGroup group = new ButtonGroup();
		group.add(radNam);
		group.add(radNu);

		pnChiTietBacSy.add(store_GUI.createLable(3, 230, 100, 30, "Ngày vào làm :"));
		jpkNgayVL = store_GUI.createJDatePicker(115, 230, 200, 35);
		pnChiTietBacSy.add(jpkNgayVL);

		return pnChiTietBacSy;
	}

	/**
	 * PHƯƠNG THỨC XÓA RỔNG TRÊN TEXT FILED
	 */
	public void xoaRongTrenTextField() {
		txthoTen.setText(null);
		txtDiaChi.setText(null);
		txtMa.setText(null);
		txtChuyenMon.setText(null);
	}

	private BacSi getBacSiOnTextField() {
		String maBacSi = "", tenBacSi = "", diaChi = "", chuyenMon = "", soCMND = "", taiKhoan = "", matKhau = "";
		boolean gioiTinh = true;
		maBacSi = store_GUI.getValueTextField(txtMa);
		if (maBacSi == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập mã");
		}
		tenBacSi = store_GUI.getValueTextField(txthoTen);
		if (tenBacSi == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập họ tên");
		}
		chuyenMon = store_GUI.getValueTextField(txtChuyenMon);
		if (chuyenMon == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập chuyên môn");
		}
		soCMND = store_GUI.getValueTextField(txtSoCMND);
		if (soCMND == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập CMND");
		}
		taiKhoan = store_GUI.getValueTextField(txtUser);
		if (taiKhoan == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập tài khoản");
		}
		matKhau = store_GUI.getValueTextField(txtPass);
		if (matKhau == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập mật khẩu");
		}
		Date ngaySinh = (Date) jpkNgaySinh.getModel().getValue();
		Date ngayVaoLam = (Date) jpkNgayVL.getModel().getValue();
		BacSi bacSi = new BacSi(maBacSi, tenBacSi, gioiTinh, ngaySinh, chuyenMon, soCMND, diaChi, ngayVaoLam, taiKhoan,
				matKhau);
		return bacSi;
	}

	public void taiLaiDuLieuTableBacSi() {
		dsBacSi = xlBacSi.getAllBS();
		bacSiTableModel = new BacSiTableModel(dsBacSi);
		tblBacSy.setModel(bacSiTableModel);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		index = tblBacSy.getSelectedRow();
		if (index != -1) {
			BacSi bacSi = dsBacSi.get(index);
			txthoTen.setText(bacSi.getTenBacSi());
			txtMa.setText(bacSi.getMaBacSi());
			txtDiaChi.setText(bacSi.getDiaChi());
			txtChuyenMon.setText(bacSi.getChuyenMon());
			txtSoCMND.setText(bacSi.getSoCMND());
			if (bacSi.isGioiTinh()) {
				radNam.setSelected(true);
			} else {
				radNu.setSelected(true);
			}
			txtUser.setText(bacSi.getTaiKhoan());
			txtPass.setText(bacSi.getMatKhau());
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj.equals(btnThem)) {
			BacSi bacSi = getBacSiOnTextField();
			taiLaiDuLieuTableBacSi();
			if (xlBacSi.themBacSi(bacSi)) {
				JOptionPane.showMessageDialog(null, "Thêm bác sĩ thành công");

			}
		}
		if(obj.equals(btnXoa)) {
			if(index==-1) {
				JOptionPane.showMessageDialog(null, "Mời bạn chọn bác sĩ để xóa !");
			}else {
				BacSi bacSi = dsBacSi.get(index);
				if(xlBacSi.xoaBacSi(bacSi)) {
					JOptionPane.showMessageDialog(null, "Xóa Bác sĩ thành công !!");
					taiLaiDuLieuTableBacSi();
					xoaRongTrenTextField();
					index=-1;
				}
			}
		}
		if (obj.equals(btnUpdate)) {
			if (index == -1) {
				JOptionPane.showMessageDialog(null, "Mời bạn chọn bác sĩ trong bảng để cập nhật");
			} else {
				BacSi bacSi = getBacSiOnTextField();
				if (xlBacSi.capNhatBacSi(bacSi)) {
					JOptionPane.showConfirmDialog(null, "Cập nhật bác sĩ thành công !");
					taiLaiDuLieuTableBacSi();
					xoaRongTrenTextField();
					index = -1;
				}
			}
		}
		if (obj.equals(btnTimBacSy)) {
			String maBS = store_GUI.getValueTextField(txtTimBacSy);
			if (maBS != null) {
				BacSi bacSi = xlBacSi.timBSTheoMa(maBS);
				List<BacSi> listBacSis = new ArrayList<BacSi>();
				listBacSis.add(bacSi);
				bacSiTableModel = new BacSiTableModel(listBacSis);
				tblBacSy.setModel(bacSiTableModel);
			} else {
				JOptionPane.showMessageDialog(null, "Không tìm thấy bác sĩ !");

			}
		}
		if (obj.equals(btnXoaTrong)) {
			xoaRongTrenTextField();
		}
		if (obj.equals(btnReset)) {
			taiLaiDuLieuTableBacSi();
			index = -1;
		}
	}

}
