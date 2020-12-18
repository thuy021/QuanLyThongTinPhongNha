package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.alee.laf.WebLookAndFeel;

import dao.BacSiDAO;
import dao.NhanVienDAO;
import entity.BacSi;
import entity.NhanVien;

@SuppressWarnings("serial")
public class GUI_DangNhap extends JFrame implements ActionListener {
	private JTextField txtUser;
	private JPasswordField txtPass;
	private JButton btnDangNhap;
	private JButton btnDoiMatKhau;
	private GUI_Store storeGUI = new GUI_Store();
	public static ResultSet rs = null;
	public static PreparedStatement pst = null;
	private NhanVienDAO xlNhanVien = new NhanVienDAO();
	private BacSiDAO xlBacSi = new BacSiDAO();
	public static String maBS;
	public static String maNV;
	public GUI_DangNhap() {
		this.setTitle("Đăng nhập");
		designGUI();
		events();
		showWinDows();
	}

	/*
	 * NƠI ĐĂNG KÍ SỰ KIỆN CHO BUTTON
	 */
	private void events() {
		btnDangNhap.addActionListener(this);
	}

	/**
	 * Thiết kế ra GUI đăng nhập.
	 */
	private void designGUI() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("images/login5.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		JLabel lblBackGround = new JLabel(new ImageIcon(image));
		setContentPane(lblBackGround);

		Container conn = getContentPane();
		conn.setLayout(null);
		JLabel lblTieuDe = new JLabel("QUẢN LÝ THÔNG TIN PHÒNG NHA", SwingConstants.CENTER);
		lblTieuDe.setBounds(200, 5, 500, 40);
		lblTieuDe.setFont(new Font("arial", Font.ITALIC, 30));
		lblTieuDe.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.YELLOW));
		lblTieuDe.setForeground(Color.red);
		conn.add(lblTieuDe);

		JLabel lblIcon = new JLabel(storeGUI.taonICon("doc.png", 160, 140));
		lblIcon.setBounds(360, 50, 160, 140);
		conn.add(lblIcon);

		JLabel lblUser = storeGUI.createLable(250, 200, 150, 30, "TÊN ĐĂNG NHẬP:");
		lblUser.setIcon(storeGUI.taonICon("user.png", 20, 20));
		lblUser.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.yellow));
		txtUser = storeGUI.createTextField(410, 200, 200, 30, 30);
		conn.add(lblUser);
		conn.add(txtUser);

		JLabel lblPass = storeGUI.createLable(250, 250, 150, 30, "MẬT KHẨU :");
		lblPass.setIcon(storeGUI.taonICon("lock.png", 20, 20));
		lblPass.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.yellow));
		txtPass = storeGUI.createPasswordField(410, 250, 200, 30, 30);
		conn.add(lblPass);
		conn.add(txtPass);
		txtUser.setText("nvlt1");
		txtPass.setText("123");
		btnDangNhap = storeGUI.createButton(450, 300, 100, 30, "Đăng Nhập");
		//btnDoiMatKhau = storeGUI.createButton(450, 300, 120, 30, "Đổi mật khẩu");
		conn.add(btnDangNhap);
		//conn.add(btnDoiMatKhau);
	}

	private void showWinDows() {
		this.setSize(900, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	/**
	 * Hàm main thực thị ứng dụng.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				WebLookAndFeel.install();
				new GUI_DangNhap();
				try {
					UIManager.setLookAndFeel(new WebLookAndFeel());
					UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
					UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
				} catch (Exception e) {
				}
			}
		});

	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent s) {
		Object object = s.getSource();
		String user = "";
		String pass = "";
		pass = txtPass.getText();
		user = txtUser.getText();

		if (object.equals(btnDangNhap)) {

			if (user.isEmpty() || pass.isEmpty()) {
				JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập thông tin tài khoản và mật khẩu", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (user != null && pass != null) {
				NhanVien nhanVien = xlNhanVien.getNhanVienByTaiKhoan(user);	
				BacSi bacSi = xlBacSi.getBacSiByTaiKhoan(user);
				if (nhanVien != null && nhanVien.getLoaiTaiKhoan().equalsIgnoreCase("NVLT")) {
					maNV = nhanVien.getMaNhanVien();
					try {
						new GUI_TrangChuLeTan();
					} catch (NamingException | JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.dispose();
				}
				if (nhanVien != null && nhanVien.getLoaiTaiKhoan().equalsIgnoreCase("NVQT")) {
					maNV = nhanVien.getMaNhanVien();
					new GUI_TrangChuQuanLy();
					this.dispose();
				}
				
				if (bacSi != null) {
					maBS = bacSi.getMaBacSi();
					try {
						new GUI_TrangChuBacSi();
					} catch (NamingException | JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
					this.dispose();
				}
				if(nhanVien==null && bacSi==null) {
					JOptionPane.showMessageDialog(rootPane, "Sai thông tin tài khoản hoặc mật khẩu", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}

			} else{
				JOptionPane.showMessageDialog(rootPane, "Nhân viên không tồn tại", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

	}

	/*
	 * getter cho username và pass.
	 */
	public String getTxtUser() {
		return txtUser.getText();
	}

	@SuppressWarnings("deprecation")
	public String getTxtPass() {
		return txtPass.getText();
	}

}
