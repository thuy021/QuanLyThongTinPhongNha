package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.BasicConfigurator;
import org.jdatepicker.impl.JDatePickerImpl;
import com.alee.laf.WebLookAndFeel;

import dao.KhachHangDAO;
import entity.KhachHang;

public class GUI_NhanKhachHang implements ActionListener{
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtTimKhachHang, txthoTen, txtsoPhone, txtDiaChi, txtMa, txtSoCMND;
	private JButton btnTimKhachHang,  btnLuuThongTin;
	private JDatePickerImpl jpkNgaySinh;
	private JRadioButton radNam, radNu;

	


	public GUI_NhanKhachHang() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * main gui, chứa các phần thiết kế giao diện.
	 * 
	 * @return
	 */
	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		conn.add(store_GUI.createLable(500, 10, 300, 30, "NHẬN KHÁCH HÀNG "));

		JButton pnBangKhachHang = taoButtonLuu();
		JPanel pnChiTietNV = taoPanleTTChiTietKhachHang();
		JPanel pnTimKiem = taoPanleTimKiem();
		conn.add(pnBangKhachHang);
		conn.add(pnChiTietNV);
		conn.add(pnTimKiem);
		addEvents();
		return conn;
		

	}

	/**
	 * Panle chứa DANH SÁCH KHÁCH HÀNG.
	 */

	private JButton taoButtonLuu() {
		btnLuuThongTin = store_GUI.createButton(600, 510, 100, 50, "Đặt khám");
		return btnLuuThongTin;
	}

	/**
	 * PANLE hiển thị TÌM KIẾM .
	 * 
	 * @return
	 */
	private JPanel taoPanleTimKiem() {
		JPanel pnTimKiem = store_GUI.createPannel(400, 50, 500, 100, "Tìm kiếm");
		pnTimKiem.add(store_GUI.createLable(15, 30, 120, 30, "Nhập số CMND:"));
		txtTimKhachHang = store_GUI.createTextField(140, 30, 180, 30, 100);
		btnTimKhachHang = store_GUI.createButton(315, 30, 100, 30, "Tìm Kiếm");
		btnTimKhachHang.setIcon(store_GUI.taonICon("search.png", 20, 20));
		pnTimKiem.add(txtTimKhachHang);
		pnTimKiem.add(btnTimKhachHang);
		return pnTimKiem;
	}

	/**
	 * PANEL đưa thông tin khách hàng lên chi tiết khách hàng trên textField.
	 * 
	 * @return
	 */

	private JPanel taoPanleTTChiTietKhachHang() {
		JPanel pnChiTietKhachHang = store_GUI.createPannel(300, 200, 700, 300, "Thông tin chi tiết Khách Hàng");
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

	public void xoaRongTrenTextField() {
		txthoTen.setText(null);
		txtDiaChi.setText(null);
		txtMa.setText(null);
		txtSoCMND.setText(null);
		txtsoPhone.setText(null);
	}
	
	public void Fill(KhachHang a){
		txtMa.setText(String.valueOf(a.getMaKhachHang()));
		txthoTen.setText(String.valueOf(a.getTenKhachHang()));
		txtsoPhone.setText(String.valueOf(a.getSoDienThoai()));
		txtDiaChi.setText(String.valueOf(a.getDiaChi()));
		Date ngaySinh = (Date) jpkNgaySinh.getModel().getValue();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(ngaySinh);
		jpkNgaySinh.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		if (a.isGioiTinh()) {
			radNam.setSelected(true);
		} else {
			radNu.setSelected(true);
		}
		txtSoCMND.setText(String.valueOf(a.getSoCMND()));
	}
	
	private void addEvents() {
		btnLuuThongTin.addActionListener(this);
		btnTimKhachHang.addActionListener(this);
		
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				WebLookAndFeel.install();
				new GUI_NhanKhachHang();
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o=e.getSource();
		if(o.equals(btnTimKhachHang)){
			//call finding method in BenhNhanDao CLASS
			KhachHangDAO dao=new KhachHangDAO();
			KhachHang a=dao.timKHTheoCMND(String.valueOf(txtTimKhachHang.getText()));
			Fill(a);

		}
		if(o.equals(btnLuuThongTin)) {
			try {
				luuDuLieu();
			}catch (NamingException e1) {
				e1.printStackTrace();
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	//Tìm khách hàng theo CMND
	public void timKhachHang() {
		
	}
	
	
	public void luuDuLieu() throws NamingException, JMSException{
		BasicConfigurator.configure();

		Properties settings=new Properties();
		
		settings.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		settings.setProperty(Context.PROVIDER_URL,"tcp://localhost:61616");
		Context ctx=new InitialContext(settings);
		ConnectionFactory factory=(ConnectionFactory) ctx.lookup("ConnectionFactory");
		Destination destination=(Destination)ctx.lookup("dynamicQueues/KTTKPM_DHKTPM13B_BAITAPLON_NHOM18");
		Connection con=factory.createConnection("admin","admin"); 
		con.start(); 
		Session session=con.createSession(
				/*transaction*/ false, /*ACK*/ Session.AUTO_ACKNOWLEDGE);
		try{

			
			KhachHangDAO dao = new KhachHangDAO();
			String ma = String.valueOf(txtMa.getText());
			String ten = String.valueOf(txthoTen.getText());
			String sdt = String.valueOf(txtsoPhone.getText());
			String diachi = String.valueOf(txtDiaChi.getText());
			String cmnd = String.valueOf(txtSoCMND.getText());
			Date nSinh = (Date) jpkNgaySinh.getModel().getValue();
			boolean gioiTinh = true;
			if (radNam.isSelected()) {
				gioiTinh = true;
			}
			if (radNu.isSelected()) {
				gioiTinh = false;
			}
			KhachHang a = new KhachHang(ma, ten, gioiTinh, nSinh, sdt, cmnd, diachi);
			dao.persist(a);

			SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
			String ngay=df.format(System.currentTimeMillis());

			MessageProducer producer = session.createProducer(destination); 
			Message msg=session.createTextMessage(ngay+"_"+String.valueOf(txtMa.getText())); 
			producer.send(msg);

		}catch(Exception e){
			SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
			String ngay=df.format(System.currentTimeMillis());
			MessageProducer producer = session.createProducer(destination); 
			Message msg=session.createTextMessage(ngay+"_"+String.valueOf(txtMa.getText())); 
			producer.send(msg); 
		}
	}
	

}
