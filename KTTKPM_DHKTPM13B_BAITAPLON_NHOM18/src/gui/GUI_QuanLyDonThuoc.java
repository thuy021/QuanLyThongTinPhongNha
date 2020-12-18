package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import dao.ChiTietDonThuocDAO;
import dao.DonThuocDAO;
import dao.GoiDichVuDAO;
import dao.ThuocDAO;
import defaultTableModel.DonThuocTableModel;
import defaultTableModel.GoiDichVuTableModel;
import defaultTableModel.ThuocTableModel;
import entity.ChiTietDonThuoc;
import entity.DonThuoc;
import entity.GoiDichVu;
import entity.Thuoc;

public class GUI_QuanLyDonThuoc implements ActionListener, MouseListener {
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtHoTen, txtCMND, txtSoDienThoai, txtBacSy, txtTongTien;
	private JButton btnReset, btnXemChiTiet;
	private JTable tblDonThuoc, tblDSThuoc;
	int indexHoaDon = -1, indexGioHang = -1;
	JTable tblNhanVien;
	private List<DonThuoc> dsDonThuoc;
	private DonThuocDAO xlDonThuoc = new DonThuocDAO();
	private DonThuocTableModel donThuocTableModel;
	private int index = -1;
	private List<ChiTietDonThuoc> dsChiTietDonThuoc;
	private ChiTietDonThuoc ctdt = new ChiTietDonThuoc();
	private ChiTietDonThuocDAO xlCTDonThuoc = new ChiTietDonThuocDAO();
	private CTDTTableModel chiTietDonThuocTableModel;

	public GUI_QuanLyDonThuoc() {

	}

	/**
	 * Thiết kế GUI ĐƠN THUỐC
	 * 
	 * @return
	 */
	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		conn.add(store_GUI.createLable(500, 10, 300, 30, "QUẢN LÝ ĐƠN THUỐC "));
		// button sự kiện.
		conn.add(store_GUI.createLable(855, 60, 80, 30, "Bác sỹ:"));
		txtBacSy = store_GUI.createTextField(985, 60, 200, 30, 10);
		conn.add(store_GUI.createLable(550, 360, 110, 30, "Tổng tiền:"));
		txtTongTien = store_GUI.createTextField(670, 360, 200, 30, 100);
		txtTongTien.setEditable(false);
		btnReset = store_GUI.createButton(300, 360, 100, 30, "Reset");
		conn.add(btnReset);
		conn.add(txtTongTien);
		conn.add(txtBacSy);
		btnReset.addActionListener(this);
		txtBacSy.setEditable(false);
		JPanel pnDSDonThuoc = taoPanelDSDonThuoc();
		JPanel pnDSThuoc = taoPanelDSThuoc();
		JPanel pnKhachHang = taoPanleThongTinKH();
		conn.add(pnDSDonThuoc);
		conn.add(pnDSThuoc);
		conn.add(pnKhachHang);
		return conn;
	}

	/**
	 * Thiết kế bàng danh sách các ĐƠN THUỐC.
	 * 
	 * @return
	 */
	private JPanel taoPanelDSDonThuoc() {
		JPanel pnDSDonThuoc = store_GUI.createPannel(40, 400, 1200, 255, "Bảng danh sách đơn thuốc");
		pnDSDonThuoc.setLayout(new BorderLayout());

		tblDonThuoc = new JTable();
		JPanel pnTable = store_GUI.createPanelTable(tblDonThuoc);
		pnDSDonThuoc.add(pnTable);
		tblDonThuoc.addMouseListener(this);
		return pnDSDonThuoc;
	}

	/*
	 * Thiết kế panle chứa thông tin của khách hàng.
	 *
	 */
	private JPanel taoPanleThongTinKH() {
		JPanel pnKhachHang = store_GUI.createPannel(840, 150, 400, 200, "Thông tin khách hàng");

		pnKhachHang.add(store_GUI.createLable(15, 30, 80, 30, "Họ Tên:"));
		txtHoTen = store_GUI.createTextField(140, 30, 200, 30, 150);
		pnKhachHang.add(txtHoTen);

		// cmnd
		pnKhachHang.add(store_GUI.createLable(15, 80, 80, 30, "Số CMND:"));
		txtCMND = store_GUI.createTextField(140, 80, 200, 30, 10);
		pnKhachHang.add(txtCMND);

		// so DienThoai.
		pnKhachHang.add(store_GUI.createLable(15, 130, 80, 30, "Số điện thoại:"));
		txtSoDienThoai = store_GUI.createTextField(140, 130, 200, 30, 11);
		pnKhachHang.add(txtSoDienThoai);
		txtHoTen.setEditable(false);
		txtCMND.setEditable(false);
		txtSoDienThoai.setEditable(false);
		return pnKhachHang;
	}

	/**
	 * Thiết kế bảng chứa danh sách THUỐC theo từng ĐƠN THUỐC đã được thanh toán.
	 * 
	 * @return
	 */
	private JPanel taoPanelDSThuoc() {
		JPanel pnDSThuoc = store_GUI.createPannel(40, 50, 800, 300, "Danh sách thuốc");
		pnDSThuoc.setLayout(new BorderLayout());
		tblDSThuoc= new JTable();
		JPanel pnTable = store_GUI.createPanelTable(tblDSThuoc);
		pnDSThuoc.add(pnTable);
		return pnDSThuoc;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		index = tblDonThuoc.getSelectedRow();
		if (index != -1) {
			DonThuoc donThuoc = dsDonThuoc.get(index);
			txtBacSy.setText(donThuoc.getBacSi().getTenBacSi());
			txtHoTen.setText(donThuoc.getKhachHang().getTenKhachHang());
			txtCMND.setText(donThuoc.getKhachHang().getSoCMND());
			txtSoDienThoai.setText(donThuoc.getKhachHang().getSoDienThoai());
			txtTongTien.setText(df.format(donThuoc.getTongTien()));
			taiLaiDuLieuCTDTTableDonThuoc();
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
		if (obj.equals(btnReset)) {
			taiLaiDuLieuTableDonThuoc();
		}
	}

	public void taiLaiDuLieuTableDonThuoc() {
		dsDonThuoc = xlDonThuoc.getAllDonThuoc();
		donThuocTableModel = new DonThuocTableModel(dsDonThuoc);
		tblDonThuoc.setModel(donThuocTableModel);
	}

	public void taiLaiDuLieuCTDTTableDonThuoc() {
		dsChiTietDonThuoc = xlCTDonThuoc.getAllChiTietDonThuoc();
		chiTietDonThuocTableModel = new CTDTTableModel(dsChiTietDonThuoc);
		tblDSThuoc.setModel(chiTietDonThuocTableModel);
	}
}
