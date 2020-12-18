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


public class GUI_TrangChuBacSi extends JFrame implements ActionListener{
	private JMenuItem mniTaiKhoan, mniDangXuat, mniDSBenhNhan;

	private JMenuBar mnuBar;
	private JMenu mnuDSBenhNhan, mnuSettings;
	private JPanel pnGiaoDien;
	private GUI_Store storeGUI = new GUI_Store();
	private GUI_KhamBenh guiKhamBenh;
	/**
	 * Đặt tên cho các giao diện được add vào cardLayout.
	 */
	private static final String KHAMBENH = "KHAMBENH";

	/**
	 * Gui main : GUI giao diện chứa thông tin nhân viên.
	 * 
	 * @param nhanVien
	 * @return 
	 * @throws JMSException 
	 * @throws NamingException 
	 */
	public GUI_TrangChuBacSi() throws NamingException, JMSException {
		setTitle("Khám bệnh");
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

		mnuDSBenhNhan = createJMenu("Khám bệnh");
		mnuDSBenhNhan.add(mniDSBenhNhan = createMenuIterm("Khám bệnh"));
		mnuDSBenhNhan.setIcon(storeGUI.taonICon("khambenh.png", 25, 25));


		mnuBar.add(mnuSettings);
		mnuBar.add(mnuDSBenhNhan);
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
		
		guiKhamBenh = new GUI_KhamBenh();
		panel.add(guiKhamBenh.designGUI(), KHAMBENH);
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
		mniDSBenhNhan.addActionListener(this);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				WebLookAndFeel.install();
				try {
					new GUI_TrangChuBacSi();
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
		if (object.equals(mniDSBenhNhan)) {
			pnGiaoDien.add(guiKhamBenh.designGUI(), KHAMBENH);
			cardLayout.show(pnGiaoDien, KHAMBENH);
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
