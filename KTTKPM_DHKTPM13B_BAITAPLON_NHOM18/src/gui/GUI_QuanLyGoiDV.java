package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
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
import javax.swing.DefaultComboBoxModel;
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

import dao.GoiDichVuDAO;
import dao.NhanVienDAO;
import defaultTableModel.GoiDichVuTableModel;
import defaultTableModel.NhanVienTableModel;
import entity.GoiDichVu;
import entity.NhanVien;

public class GUI_QuanLyGoiDV implements ActionListener,MouseListener{
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtTimGoiDV,txtMa,txtTenGoiDV,txtGiaBan;
	private JButton btnTimGoiDV, btnThem, btnXoa, btnUpdate, btnReset, btnXoaTrong;
	JTable tblGoiDV;
	private List<GoiDichVu>dsGoiDichVu;
	private GoiDichVuDAO xlGoiDichVu = new GoiDichVuDAO();
	private GoiDichVuTableModel goiDichVuTableModel;
	private int index = -1;

	public GUI_QuanLyGoiDV(){
		
	}

	/**
	 * GUI MAIN GIAO DIỆN.
	 * 
	 * @return
	 */
	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		conn.add(store_GUI.createLable(500, 10, 300, 30, "QUẢN LÝ GÓI DỊCH VỤ "));
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

		JPanel pnBangGoiDV = taoPanelBangGoiDV();
		JPanel pnChiTietGoiDV = taoPanelTTChiTietGoiDV();
		JPanel pnTimKiem = taoPanleTimKiem();
		conn.add(pnBangGoiDV);
		conn.add(pnChiTietGoiDV);
		conn.add(pnTimKiem);
		return conn;

	}

	/*
	 * PANEL BẢNG DANH SÁCH THUỐC
	 */
	private JPanel taoPanelBangGoiDV() {
		JPanel pnGoiDV = store_GUI.createPannel(40, 400, 1200, 255, "Bảng danh sách gói dịch vụ");
		pnGoiDV.setLayout(new BorderLayout());
		tblGoiDV = new JTable();
		JPanel pnTableThuoc = store_GUI.createPanelTable(tblGoiDV);
		pnGoiDV.add(pnTableThuoc);
		tblGoiDV.addMouseListener(this);
		return pnGoiDV;

	}

	/**
	 * PANLE TÌM KIẾM THUỐC
	 * 
	 * @return
	 */
	private JPanel taoPanleTimKiem() {
		JPanel pnTimKiem = store_GUI.createPannel(740, 50, 500, 300, "Tìm kiếm");
		pnTimKiem.add(store_GUI.createLable(15, 30, 120, 30, "Tìm gói:"));
		txtTimGoiDV = store_GUI.createTextField(140, 30, 180, 30, 100);
		btnTimGoiDV = store_GUI.createButton(315, 30, 100, 30, "Tìm Kiếm");
		btnTimGoiDV.setIcon(store_GUI.taonICon("search.png", 20, 20));
		pnTimKiem.add(txtTimGoiDV);
		pnTimKiem.add(btnTimGoiDV);
		btnTimGoiDV.addActionListener(this);
		return pnTimKiem;
	}

	/**
	 * PANEL THÔNG TIN CHI TIẾT THUỐC.
	 * 
	 * @return
	 */
	private JPanel taoPanelTTChiTietGoiDV() {
		JPanel pnChiTietGoiDV = store_GUI.createPannel(40, 50, 700, 300, "Thông tin chi tiết gói dịch vụ:");
		// mã gói

		pnChiTietGoiDV.add(store_GUI.createLable(10, 30, 100, 30, "Mã gói :"));
		txtMa = store_GUI.createTextField(115, 30, 200, 30, 100);
		pnChiTietGoiDV.add(txtMa);
		
		// tên gói
		pnChiTietGoiDV.add(store_GUI.createLable(10, 80, 100, 30, "Tên gói:"));
		txtTenGoiDV = store_GUI.createTextField(115, 80, 200, 30, 100);
		pnChiTietGoiDV.add(txtTenGoiDV);


		// giá bán
		pnChiTietGoiDV.add(store_GUI.createLable(350, 30, 85, 30, "Đơn giá:"));
		txtGiaBan = store_GUI.createTextField(445, 30, 200, 30, 100);
		pnChiTietGoiDV.add(txtGiaBan);

		return pnChiTietGoiDV;
	}



	/**
	 * PHƯƠNG THỨC XÓA RỔNG TRÊN TEXT FILED
	 */
	public void xoaRongTrenTextField() {
		txtTenGoiDV.setText(null);
		txtGiaBan.setText(null);
		txtMa.setText(null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		index = tblGoiDV.getSelectedRow();
		if (index != -1) {
			GoiDichVu goiDichVu = dsGoiDichVu.get(index);
			txtTenGoiDV.setText(goiDichVu.getTenGoiDV());
			txtMa.setText(goiDichVu.getMaGoiDV());
			txtGiaBan.setText(df.format(goiDichVu.getDonGia()));
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
			GoiDichVu goiDichVu = getGoiDichVuOnTextField();
			taiLaiDuLieuTableGoiDichVu();
			if(xlGoiDichVu.themGoiDV(goiDichVu)) {
				JOptionPane.showMessageDialog(null, "Thêm thành công");

			}
		}
		if(obj.equals(btnXoa)) {
			if(index==-1) {
				JOptionPane.showMessageDialog(null, "Mời bạn chọn gói dịch vụ để xóa !");
			}else {
				GoiDichVu goiDichVu = dsGoiDichVu.get(index);
				if(xlGoiDichVu.xoaGoiDV(goiDichVu)) {
					JOptionPane.showMessageDialog(null, "Xóa gói dịch vụ thành công !!");
					taiLaiDuLieuTableGoiDichVu();
					xoaRongTrenTextField();
					index=-1;
				}
			}
		}
		if(obj.equals(btnUpdate)) {
			if(index==-1) {
				JOptionPane.showMessageDialog(null, "Mời bạn chọn gói dịch vụ trong bảng để cập nhật");
			}else {
				GoiDichVu goiDichVu = getGoiDichVuOnTextField();
				if(xlGoiDichVu.capNhatGoiDV(goiDichVu)) {
					JOptionPane.showConfirmDialog(null, "Cập nhật gói dịch vụ thành công !");
					taiLaiDuLieuTableGoiDichVu();
					xoaRongTrenTextField();
					index=-1;
				}
			}
		}
		if(obj.equals(btnTimGoiDV)) {
			String maDV = store_GUI.getValueTextField(txtTimGoiDV);
			if(maDV!=null) {
				GoiDichVu goiDichVu = xlGoiDichVu.timGoiDichVuTheoMa(maDV);
				List<GoiDichVu>listGoiDichVus = new ArrayList<GoiDichVu>();
				listGoiDichVus.add(goiDichVu);
				goiDichVuTableModel = new GoiDichVuTableModel(listGoiDichVus);
				tblGoiDV.setModel(goiDichVuTableModel);
			}else {
				JOptionPane.showMessageDialog(null, "Không tìm thấy gói dịch vụ !");

			}
		}
		if(obj.equals(btnXoaTrong)) {
			xoaRongTrenTextField();
		}
		if(obj.equals(btnReset)) {
			taiLaiDuLieuTableGoiDichVu();
			index=-1;
		}
	}
	private GoiDichVu getGoiDichVuOnTextField() {
		String maGoiDV="",tenGoiDV="";
		String donGia="";
		double gia=0;
		maGoiDV = store_GUI.getValueTextField(txtMa);
		if(maGoiDV==null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập mã");
		}
		tenGoiDV=store_GUI.getValueTextField(txtTenGoiDV);
		if(tenGoiDV==null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập tên gói dịch vụ");
		}
		donGia=store_GUI.getValueTextField(txtGiaBan);
		if(donGia==null) {
				JOptionPane.showMessageDialog(null, "Chưa nhập đơn giá");
		}else {
			gia=Double.parseDouble(donGia);
		}
		GoiDichVu goiDichVu = new GoiDichVu(maGoiDV, tenGoiDV, gia);
		return goiDichVu;
	}
	public void taiLaiDuLieuTableGoiDichVu() {
		dsGoiDichVu = xlGoiDichVu.getAllGoiDichVu();
		goiDichVuTableModel = new GoiDichVuTableModel(dsGoiDichVu);
		tblGoiDV.setModel(goiDichVuTableModel);
	}

}
