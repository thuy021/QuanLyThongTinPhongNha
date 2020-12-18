package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.alee.laf.WebLookAndFeel;


public class GUI_TrangChuQuanLy extends JFrame implements ActionListener{
	private JMenuItem mniTaiKhoan, mniDangXuat, mniQLKhachHang, mniQLNhanVien, mniQLBacSy, mniQLDonThuoc, mniQLThuoc, mniQLGoiDV ;

	private JMenuBar mnuBar;
	private JMenu mnuQLNhanVien, mnuSettings, mnuQLKhachHang, mnuQLBacSy, mnuQLDonThuoc, mnuQLThuoc, mnuQLGoiDV;
	private JPanel pnGiaoDien;
	private GUI_Store storeGUI = new GUI_Store();
	private GUI_QuanLyNhanVien gui_QuanLyNhanVien = new GUI_QuanLyNhanVien();
	private GUI_QuanLyBacSy gui_QuanLyBacSy = new GUI_QuanLyBacSy();
	private GUI_QuanLyKhachHang gui_QuanLyKhachHang = new GUI_QuanLyKhachHang();
	private GUI_QuanLyThuoc gui_QuanLyThuoc = new GUI_QuanLyThuoc();
	private GUI_QuanLyDonThuoc gui_QuanLyDonThuoc = new GUI_QuanLyDonThuoc();
	private GUI_QuanLyGoiDV gui_QuanLyGoiDV  = new GUI_QuanLyGoiDV();
	/**
	 * Đặt tên cho các giao diện được add vào cardLayout.
	 */
	private static final String QUANLYKHACHHANG = "QUANLYKHACHHANG";
	private static final String QUANLYBACSY = "QUANLYBACSY";
	private static final String QUANLYNHANVIEN = "QUANLYNHANVIEN";
	private static final String QUANLYDONTHUOC = "QUANLYDONTHUOC";
	private static final String QUANLYTHUOC = "QUANLYTHUOC";
	private static final String QUANLYGOIDV = "QUANLYGOIDV";

	/**
	 * Gui main : GUI giao diện chứa thông tin nhân viên.
	 * 
	 * @param nhanVien
	 * @return 
	 */
	public GUI_TrangChuQuanLy() {
		setTitle("Quản lý");
		addControls();
		showWinDows();
		addEvents();
	}


	private void addControls() {
		Container con = getContentPane();
		mnuBar = createMenuBar();
		setJMenuBar(mnuBar);
		con.setLayout(new BorderLayout());
		pnGiaoDien = createPanelGiaoDien();
		con.add(pnGiaoDien, BorderLayout.CENTER);
	}

	/**
	 * TẠO JMENUBAR CHO ỨNG DỤNG.
	 * 
	 * @return
	 */
	private JMenuBar createMenuBar() {
		JMenuBar mnuBar = new JMenuBar();
		mnuSettings = createJMenu("Cài đặt");
		mnuSettings.setIcon(storeGUI.taonICon("setting.png", 25, 25));
		mnuSettings.add(mniTaiKhoan = createMenuIterm("Xem Thông tin tài khoản"));
		mniTaiKhoan.setIcon(storeGUI.taonICon("NhanVien.png", 20, 20));
		mnuSettings.add(mniDangXuat = createMenuIterm("Đăng xuất"));
		mniDangXuat.setIcon(storeGUI.taonICon("logout.png", 20, 20));

		mnuQLNhanVien = createJMenu("Quản lý nhân viên");
		mnuQLNhanVien.add(mniQLNhanVien = createMenuIterm("Quản lý nhân viên"));
		mnuQLNhanVien.setIcon(storeGUI.taonICon("em.png", 25, 25));

		mnuQLKhachHang = createJMenu("Quản lý khách hàng");
		mnuQLKhachHang.add(mniQLKhachHang = createMenuIterm("Quản lý khách hàng"));
		mnuQLKhachHang.setIcon(storeGUI.taonICon("cus.png", 25, 25));

		mnuQLBacSy = createJMenu("Quản lý bác sỹ");
		mnuQLBacSy.add(mniQLBacSy = createMenuIterm("Quản lý bác sỹ"));
		mnuQLBacSy.setIcon(storeGUI.taonICon("doc1.jpg", 25, 25));
		
		mnuQLDonThuoc = createJMenu("Quản lý đơn thuốc");
		mnuQLDonThuoc.add(mniQLDonThuoc = createMenuIterm("Quản lý đơn thuốc"));
		mnuQLDonThuoc.setIcon(storeGUI.taonICon("order.png", 25, 25));
		
		mnuQLThuoc = createJMenu("Quản lý thuốc");
		mnuQLThuoc.add(mniQLThuoc = createMenuIterm("Quản lý thuốc"));
		mnuQLThuoc.setIcon(storeGUI.taonICon("thuoc.jpg", 25, 25));
		
		mnuQLGoiDV = createJMenu("Quản lý gói dịch vụ");
		mnuQLGoiDV.add(mniQLGoiDV = createMenuIterm("Quản lý gói dịch vụ"));
		mnuQLGoiDV.setIcon(storeGUI.taonICon("dichvu.jpg", 25, 25));
		
		mnuBar.add(mnuSettings);
		mnuBar.add(mnuQLNhanVien);
		mnuBar.add(mnuQLKhachHang);
		mnuBar.add(mnuQLBacSy);
		mnuBar.add(mnuQLDonThuoc);
		mnuBar.add(mnuQLThuoc);
		mnuBar.add(mnuQLGoiDV);
		return mnuBar;
	}

	private JMenuItem createMenuIterm(String text) {
		JMenuItem mnuIterm = new JMenuItem(text);
		return mnuIterm;
	}

	private JMenu createJMenu(String text) {
		JMenu mnu = new JMenu(text);
		return mnu;
	}

	private JPanel createPanelGiaoDien() {
		JPanel panel = new JPanel();
		panel.setLayout(new CardLayout());
		panel.add(gui_QuanLyNhanVien.designGUI(), QUANLYNHANVIEN);
		return panel;
	}

	private void showWinDows() {
		this.setSize(1360, 720);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void addEvents() {
		mniDangXuat.addActionListener(this);
		mniTaiKhoan.addActionListener(this);
		mniQLNhanVien.addActionListener(this);
		mniQLKhachHang.addActionListener(this);
		mniQLBacSy.addActionListener(this);
		mniQLThuoc.addActionListener(this);
		mniQLDonThuoc.addActionListener(this);
		mniQLGoiDV.addActionListener(this);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				WebLookAndFeel.install();
				new GUI_TrangChuQuanLy();
				
				try {
					UIManager.setLookAndFeel(new WebLookAndFeel());
					UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
					UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
				} catch (Exception e) {
				}
			}
		});

	}


	@Override
	public void actionPerformed(ActionEvent s) {
		// TODO Auto-generated method stub
		Object object = s.getSource();
		CardLayout cardLayout = (CardLayout) pnGiaoDien.getLayout();
		if (object.equals(mniQLNhanVien)) {
			pnGiaoDien.add(gui_QuanLyNhanVien.designGUI(), QUANLYNHANVIEN);
			cardLayout.show(pnGiaoDien, QUANLYNHANVIEN);
		}
//
		if (object.equals(mniQLBacSy)) {
			try {
				pnGiaoDien.add(gui_QuanLyBacSy.designGUI(), QUANLYBACSY);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cardLayout.show(pnGiaoDien, QUANLYBACSY);
		}
		if (object.equals(mniQLKhachHang)) {
			pnGiaoDien.add(gui_QuanLyKhachHang.designGUI(), QUANLYKHACHHANG);
			cardLayout.show(pnGiaoDien, QUANLYKHACHHANG);
		}
		if (object.equals(mniQLThuoc)) {
			pnGiaoDien.add(gui_QuanLyThuoc.designGUI(), QUANLYTHUOC);
			cardLayout.show(pnGiaoDien, QUANLYTHUOC);
		}
		if (object.equals(mniQLDonThuoc)) {
			pnGiaoDien.add(gui_QuanLyDonThuoc.designGUI(), QUANLYDONTHUOC);
			cardLayout.show(pnGiaoDien, QUANLYDONTHUOC);
		}
		if (object.equals(mniQLGoiDV)) {
			pnGiaoDien.add(gui_QuanLyGoiDV.designGUI(), QUANLYGOIDV);
			cardLayout.show(pnGiaoDien, QUANLYGOIDV);
		}
		if(object.equals(mniDangXuat)) {
			new GUI_DangNhap();
			this.dispose();
		}
//		if (object.equals(mniDangXuat)) {
//			this.dispose();
//			new GUI_DangNhap();
//		}

	}

}
