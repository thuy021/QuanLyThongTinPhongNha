package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.BasicConfigurator;
import org.jdatepicker.impl.JDatePickerImpl;

import com.alee.laf.WebLookAndFeel;

import dao.ChiTietDonThuocDAO;
import dao.DonThuocDAO;
import entity.ChiTietDonThuoc;
import entity.DonThuoc;

public class GUI_TinhTien implements ActionListener, MouseListener {
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtTimKhachHang, txtTenKhachHang, txtTenBacSy, txtTenNhanVien, txtGoiDichVu, txtMa,
			txtGiaGoiDichVu, txtTimThuoc, txtTongtien;
	private JButton btnThanhToan, btnTaoDonThuoc, btnTinhTien;
	private JDatePickerImpl jpkNgayLap;
	private JRadioButton radNam, radNu;
	private JTable tblTTCTThuoc;
	private SpinnerNumberModel spSoLuong;
	DefaultListModel<String> listModel;
	private JList<String> list;
	List<ChiTietDonThuoc> dsChiTietDonThuoc;
	private ChiTietDonThuocDAO xlCTDT = new ChiTietDonThuocDAO();
	private CTDTTableModel ctdtTableModel;
	private DonThuocDAO dtDAO = new DonThuocDAO();

	private int index;

	public GUI_TinhTien() throws NamingException, JMSException {
		BasicConfigurator.configure();
		Properties settings = new Properties();
		settings.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		settings.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
		Context ctx = new InitialContext(settings);
		Object obj = ctx.lookup("ConnectionFactory");
		ConnectionFactory factory = (ConnectionFactory) obj;
		Destination destination = (Destination) ctx.lookup("dynamicQueues/KTTKPM_DHKTPM13B_BAITAPLON_NHOM18_DonThuoc");
		Connection con = factory.createConnection("admin", "admin");
		con.start();
		Session session = con.createSession(/* transaction */false, /* ACK */Session.CLIENT_ACKNOWLEDGE);
		MessageConsumer receiver = session.createConsumer(destination);
		receiver.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message msg) {
				try {
					if (msg instanceof TextMessage) {
						TextMessage tm = (TextMessage) msg;
						String txt = tm.getText();
						int kt = 0;
						for (int i = 0; i < listModel.getSize(); i++) {
							if (txt.equalsIgnoreCase(listModel.getElementAt(i)))
								kt = 1;
						}
						if (kt == 0)
							listModel.addElement(txt);
						msg.acknowledge();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * main gui, chứ các phần thiết kế giao diện.
	 * 
	 * @return
	 */
	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		conn.add(store_GUI.createLable(500, 10, 300, 30, "TÍNH TIỀN ĐƠN THUỐC "));

		conn.add(store_GUI.createLable(800, 370, 80, 30, "Tổng tiền "));
		conn.add(store_GUI.createTextField(900, 370, 200, 30, 100));
		btnThanhToan = store_GUI.createButton(930, 430, 150, 40, " THANH TOÁN ");

		JPanel pnlDonThuocDuocChon = taoPanelTTDonThuocDuocChon();
		JPanel pnDSDonThuoc = taoPanelDTCanTinhTien();
		JPanel pnTaoDonThuoc = taoBangThuoc();
		conn.add(pnTaoDonThuoc);
		conn.add(pnlDonThuocDuocChon);
		conn.add(pnDSDonThuoc);
		conn.add(btnThanhToan);
		addEvents();
		return conn;

	}

	/**
	 * Panle chứa DANH SÁCH ĐƠN THUỐC CẦN TÍNH TIỀN.
	 */

	/**
	 * PANLE hiển thị TÌM KIẾM .
	 * 
	 * @return
	 */
	private JPanel taoPanelDTCanTinhTien() {
		listModel = new DefaultListModel();
		list = new JList<>(listModel);
		btnTinhTien = new JButton("Tính tiền");
		JPanel pnDSDonThuoc = store_GUI.createPannel(20, 50, 200, 600, "Danh sách đơn thuốc cần tính tiền");
		list.setBounds(20, 50, 150, 500);
		btnTinhTien.setBounds(15, 10, 100, 30);
		pnDSDonThuoc.add(list);
		pnDSDonThuoc.add(btnTinhTien);
		return pnDSDonThuoc;
	}

	/**
	 * PANEL đưa thông tin ĐƠN THUỐC lên chi tiết khách hàng trên textField.
	 * 
	 * @return
	 */// 20, 50, 700, 300

	private JPanel taoPanelTTDonThuocDuocChon() {
		JPanel pnDonThuocDuocChon = store_GUI.createPannel(250, 50, 350, 600, "Thông tin đơn thuốc được chọn");
		pnDonThuocDuocChon.add(store_GUI.createLable(20, 30, 80, 30, "Mã đơn thuốc :"));
		txtMa = store_GUI.createTextField(120, 30, 200, 30, 100);
		pnDonThuocDuocChon.add(txtMa);

		// Họ tên
		pnDonThuocDuocChon.add(store_GUI.createLable(20, 80, 100, 30, "Tên khách hàng:"));
		txtTenKhachHang = store_GUI.createTextField(120, 80, 200, 30, 100);
		pnDonThuocDuocChon.add(txtTenKhachHang);

		// số điện thoại
		pnDonThuocDuocChon.add(store_GUI.createLable(20, 130, 80, 30, "Tên bác sĩ:"));
		txtTenBacSy = store_GUI.createTextField(120, 130, 200, 30, 100);
		pnDonThuocDuocChon.add(txtTenBacSy);

		pnDonThuocDuocChon.add(store_GUI.createLable(20, 180, 80, 30, "Nhân viên:"));
		txtTenNhanVien = store_GUI.createTextField(120, 180, 200, 30, 100);
		pnDonThuocDuocChon.add(txtTenNhanVien);
		txtTenNhanVien.setText(GUI_DangNhap.maNV);

		// ngày sinh.
		pnDonThuocDuocChon.add(store_GUI.createLable(20, 230, 80, 30, "Ngày lập:"));
		jpkNgayLap = store_GUI.createJDatePicker(120, 230, 200, 35);
		pnDonThuocDuocChon.add(jpkNgayLap);

		// Dia Chị
		pnDonThuocDuocChon.add(store_GUI.createLable(20, 280, 80, 30, "Gói dịch vụ :"));
		txtGoiDichVu = store_GUI.createTextField(120, 280, 200, 30, 100);
		pnDonThuocDuocChon.add(txtGoiDichVu);
		// Chức vụ.
		pnDonThuocDuocChon.add(store_GUI.createLable(20, 330, 100, 30, "Giá gói dịch vụ:"));
		txtGiaGoiDichVu = store_GUI.createTextField(120, 330, 200, 30, 100);
		pnDonThuocDuocChon.add(txtGiaGoiDichVu);

		return pnDonThuocDuocChon;
	}

	private JPanel taoBangThuoc() {
		JPanel pnTTCTThuoc = store_GUI.createPannel(640, 50, 700, 300, "Danh sách thuốc");
		pnTTCTThuoc.setLayout(new BorderLayout());
		tblTTCTThuoc = new JTable();
		JPanel pnTableThuoc = store_GUI.createPanelTable(tblTTCTThuoc);
		pnTTCTThuoc.add(pnTableThuoc);
		return pnTTCTThuoc;
	}

	/*
	 * Phương thức thực hiện việc XÓA RỖNG TRÊN TEXTFIELD.
	 */
	public void xoaRongTrenTextField() {
		txtTenKhachHang.setText(null);
		txtGoiDichVu.setText(null);
		txtMa.setText(null);
		txtGiaGoiDichVu.setText(null);
		txtTenBacSy.setText(null);
	}

	public void Fill(DonThuoc d) {
		txtMa.setText(String.valueOf(d.getMaDonThuoc()));
		txtTenKhachHang.setText(String.valueOf(d.getKhachHang().getTenKhachHang()));
		txtTenBacSy.setText(String.valueOf(d.getBacSi().getTenBacSi()));
		Date ngayLap = (Date) jpkNgayLap.getModel().getValue();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(ngayLap);
		jpkNgayLap.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		txtGiaGoiDichVu.setText(String.valueOf(d.getGoiDV().getDonGia()));
		txtGoiDichVu.setText(String.valueOf(d.getGoiDV().getTenGoiDV()));
		/////

		String maDT = store_GUI.getValueTextField(txtMa);

		dsChiTietDonThuoc = xlCTDT.timCTDTTheoMaDT(maDT);
		ctdtTableModel = new CTDTTableModel(dsChiTietDonThuoc);
		tblTTCTThuoc.setModel(ctdtTableModel);

	}

	public void thanhTien() {

		double thanhtien = 0;
		double tongTien = 0;
		String giaDV = txtGiaGoiDichVu.getText();
		double tiendv = Double.parseDouble(giaDV);

//		String maDT = store_GUI.getValueTextField(txtMa);
//
//		List<ChiTietDonThuoc> a = new ArrayList<ChiTietDonThuoc>();
//		a = xlCTDT.timCTDTTheoMaDT(maDT);
//		ctdtTableModel = new CTDTTableModel(a);
//		tblTTCTThuoc.setModel(ctdtTableModel);
//		DonThuoc dthuoc = dtDAO.getDonThuoc(maDT);
//
//	
//
//		for (ChiTietDonThuoc gh : a) {
//			tongTien += gh.getThuoc().getDonGia() * gh.getSoLuong();
//		}
	//	DecimalFormat df = new DecimalFormat("#,### VND");
		//thanhtien = tongTien + tiendv;
		txtTongtien.setText(txtGiaGoiDichVu.getText());
		System.out.println(giaDV);
	}



	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				WebLookAndFeel.install();
				try {
					new GUI_TinhTien();
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnTinhTien)) {
			listModel.removeElementAt(list.getSelectedIndex());
		}
		if (o.equals(btnThanhToan)) {
			thanhTien();
			JOptionPane.showMessageDialog(null, "Thanh toán thành công !!");
		}
		if (o.equals(tblTTCTThuoc)) {
			index = tblTTCTThuoc.getSelectedRow();
		}

	}

	private void addEvents() {
		list.addMouseListener(this);
		btnThanhToan.addActionListener(this);
		btnTinhTien.addActionListener(this);
		tblTTCTThuoc.addMouseListener(this);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(list)) {
			String selected = list.getSelectedValue();
			String ma = selected.substring(9);
			DonThuocDAO dao = new DonThuocDAO();
			DonThuoc a = dao.timDonThuocTheoMa(String.valueOf(ma));
			Fill(a);
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
