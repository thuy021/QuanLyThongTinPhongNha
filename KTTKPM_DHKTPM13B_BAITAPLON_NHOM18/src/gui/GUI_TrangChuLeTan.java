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


public class GUI_TrangChuLeTan extends JFrame implements ActionListener{
	private JMenuItem mniTaiKhoan, mniDangXuat, mniTinhTien, mniNhanKH;

	private JMenuBar mnuBar;
	private JMenu mnuNhanKH, mnuSettings, mnuTinhTien;
	private JPanel pnGiaoDien;
	private GUI_Store storeGUI = new GUI_Store();
	private GUI_NhanKhachHang guiNhanKhachHang;
	private GUI_TinhTien guiTinhTien;
	/**
	 * Đặt tên cho các giao diện được add vào cardLayout.
	 */
	private static final String NHANKHACHHANG = "NHANKHACHHANG";
	private static final String TINHTIEN = "TINHTIEN";

	/**
	 * Gui main : GUI giao diện chứa thông tin nhân viên.
	 * 
	 * @param nhanVien
	 * @return 
	 * @throws JMSException 
	 * @throws NamingException 
	 */
	public GUI_TrangChuLeTan() throws NamingException, JMSException {
		setTitle("Nhận bệnh");
		addControls();
		showWinDows();
		addEvents();
	}


	private void addControls() throws NamingException, JMSException {
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

		mnuNhanKH = createJMenu("Nhận khách hàng");
		mnuNhanKH.add(mniNhanKH = createMenuIterm("Nhận khách hàng"));
		mnuNhanKH.setIcon(storeGUI.taonICon("nhanbenh1.jpg", 25, 25));

		mnuTinhTien = createJMenu("Tính tiền");
		mnuTinhTien.add(mniTinhTien = createMenuIterm("Tính tiền đơn thuốc"));
		mnuTinhTien.setIcon(storeGUI.taonICon("order.png", 25, 25));

		mnuBar.add(mnuSettings);
		mnuBar.add(mnuNhanKH);
		mnuBar.add(mnuTinhTien);
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

	private JPanel createPanelGiaoDien() throws NamingException, JMSException {
		JPanel panel = new JPanel();
		panel.setLayout(new CardLayout());
		guiNhanKhachHang = new GUI_NhanKhachHang();
		guiTinhTien = new GUI_TinhTien();
		panel.add(guiNhanKhachHang.designGUI(), NHANKHACHHANG);
		panel.add(guiTinhTien.designGUI(), TINHTIEN);
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
		mniNhanKH.addActionListener(this);
		mniTinhTien.addActionListener(this);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				WebLookAndFeel.install();
				try {
					new GUI_TrangChuLeTan();
				} catch (NamingException | JMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
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
		if (object.equals(mniNhanKH)) {
			pnGiaoDien.add(guiNhanKhachHang.designGUI(), NHANKHACHHANG);
			cardLayout.show(pnGiaoDien, NHANKHACHHANG);
		}
		if (object.equals(mniTinhTien)) {
			pnGiaoDien.add(guiTinhTien.designGUI(), TINHTIEN);
			cardLayout.show(pnGiaoDien, TINHTIEN);
		}
		if(object.equals(mniDangXuat)) {
			new GUI_DangNhap();
			this.dispose();
		}
//
//		if (object.equals(mniTaiKhoan)) {
//			JDialog dialog = new GUI_ThongTinTaiKhoan(this, nhanVien);
//			dialog.setLocation(50, 50);
//			dialog.isFontSet();
//			dialog.setVisible(true);
//			dialog.setAlwaysOnTop(true);
//
//		}
//		if (object.equals(mniDangXuat)) {
//			this.dispose();
//			new GUI_DangNhap();
//		}

	}

}
