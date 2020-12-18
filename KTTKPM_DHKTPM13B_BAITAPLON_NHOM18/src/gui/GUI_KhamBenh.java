package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import javax.jms.MessageProducer;
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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.BasicConfigurator;
import org.jdatepicker.impl.JDatePickerImpl;

import com.alee.laf.WebLookAndFeel;

import dao.BacSiDAO;
import dao.ChiTietDonThuocDAO;
import dao.DonThuocDAO;
import dao.GoiDichVuDAO;
import dao.KhachHangDAO;
import dao.ThuocDAO;
import entity.BacSi;
import entity.ChiTietDonThuoc;
import entity.DonThuoc;
import entity.GoiDichVu;
import entity.KhachHang;
import entity.Thuoc;

public class GUI_KhamBenh implements ActionListener, MouseListener {
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtTimKhachHang, txthoTen, txtMaDonThuoc, txtsoPhone, txtDiaChi, txtMa, txtSoCMND, txtTimThuoc,
			txtTenBacSy;
	private JButton btnTimThuoc, btnReset, btnChonThuoc, btnXoaThuocDaChon, btnTaoDonThuoc, btnGoiKham, btnGuiDonThuoc;
	private JDatePickerImpl jpkNgaySinh, jpkNgayLap;
	private JRadioButton radNam, radNu;
	private JTable tblTTCTThuoc, tblDSThuocDaChon;
	private SpinnerNumberModel spSoLuong;
	DefaultListModel<String> listModel;
	private JList<String> list;
	private ThuocTableModel thuocTableModel;
	private ThuocDAO thuocDAO = new ThuocDAO();
	private DonThuocDAO donThuocDAO = new DonThuocDAO();
	private ChiTietDonThuocDAO ctdtDAO = new ChiTietDonThuocDAO();
	private Thuoc thuoc = null;
	private DonThuoc donThuoc = null;
	private int indexTblThuoc = -1;
	private int indexTblCTDT = -1;
	private int indexTaoDon;
	private List<Thuoc> dsThuocDaChon = null;
	private List<Thuoc> dsThuoc;
	private List<ChiTietDonThuoc> dsCTDTs = new ArrayList<ChiTietDonThuoc>();
	private CTDTTableModel ctdtTableModel;
	private double tongTien = 0;
	private JComboBox cmbGoiDichVu;
	private GUI_DangNhap dangnhap;
	private List<GoiDichVu> listDV;
	private GoiDichVuDAO dvDAO = new GoiDichVuDAO();
	private BacSiDAO bacsiDAO = new BacSiDAO();
	private KhachHangDAO khDAO = new KhachHangDAO();
	DefaultComboBoxModel<GoiDichVu> dvCBBModel;

	public GUI_KhamBenh() throws NamingException, JMSException {

		BasicConfigurator.configure();
		Properties settings = new Properties();
		settings.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		settings.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
		Context ctx = new InitialContext(settings);
		Object obj = ctx.lookup("ConnectionFactory");
		ConnectionFactory factory = (ConnectionFactory) obj;
		Destination destination = (Destination) ctx.lookup("dynamicQueues/KTTKPM_DHKTPM13B_BAITAPLON_NHOM18");
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
		conn.add(store_GUI.createLable(500, 10, 300, 30, "BÁC SỸ KHÁM BỆNH "));

		JPanel pnChiTietNV = taoPanleTTChiTietKhachHang();
		JPanel pnTimKiem = taoPanleTimKiem();
		JPanel pnTaoDonThuoc = taoPanleTaoDonThuoc();
		JPanel panleTTThem = taoPanleTTThem();
		conn.add(panleTTThem);
		conn.add(pnTaoDonThuoc);
		conn.add(pnChiTietNV);
		conn.add(pnTimKiem);
		addEvents();
		return conn;

	}

	/**
	 * Panle chứa DANH SÁCH KHÁCH HÀNG.
	 */

	/**
	 * PANLE hiển thị TÌM KIẾM .
	 * 
	 * @return
	 */
	private JPanel taoPanleTimKiem() {
		listModel = new DefaultListModel();
		list = new JList<>(listModel);
		btnGoiKham = new JButton("Gọi khám");
		JPanel pnTimKiem = store_GUI.createPannel(20, 50, 200, 600, "Danh sách bệnh nhân chờ khám");
		list.setBounds(20, 50, 150, 500);
		btnGoiKham.setBounds(15, 10, 100, 30);
		pnTimKiem.add(list);
		pnTimKiem.add(btnGoiKham);

		return pnTimKiem;
	}

	/**
	 * PANEL đưa thông tin khách hàng lên chi tiết khách hàng trên textField.
	 * 
	 * @return
	 */// 20, 50, 700, 300

	private JPanel taoPanleTTChiTietKhachHang() {
		JPanel pnChiTietKhachHang = store_GUI.createPannel(250, 50, 350, 400, "Thông tin bệnh nhân được chọn");
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
		pnChiTietKhachHang.add(store_GUI.createLable(20, 230, 80, 30, "Ngày sinh:"));
		jpkNgaySinh = store_GUI.createJDatePicker(115, 230, 200, 35);
		pnChiTietKhachHang.add(jpkNgaySinh);

		// Dia Chị
		pnChiTietKhachHang.add(store_GUI.createLable(20, 280, 80, 30, "Địa chỉ :"));
		txtDiaChi = store_GUI.createTextField(115, 280, 200, 30, 100);
		pnChiTietKhachHang.add(txtDiaChi);
		// So CMND
		pnChiTietKhachHang.add(store_GUI.createLable(20, 330, 80, 30, "số CMND:"));
		txtSoCMND = store_GUI.createTextField(115, 330, 200, 30, 100);
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

	private JPanel taoPanleTTThem() {
		JPanel pnTTThem = store_GUI.createPannel(250, 460, 350, 190, "Thông tin thêm");
		pnTTThem.add(store_GUI.createLable(20, 30, 80, 30, "Tên bác sỹ"));
		txtTenBacSy = store_GUI.createTextField(115, 30, 200, 30, 100);
		pnTTThem.add(txtTenBacSy);
		txtTenBacSy.setText(GUI_DangNhap.maBS);
		pnTTThem.add(store_GUI.createLable(20, 80, 80, 30, "Ngày lập:"));
		jpkNgayLap = store_GUI.createJDatePicker(115, 80, 200, 35);
		pnTTThem.add(jpkNgayLap);
		pnTTThem.add(store_GUI.createLable(20, 130, 80, 30, "Gói dịch vụ:"));
		listDV = dvDAO.listThuoc();
		dvCBBModel = new DefaultComboBoxModel<GoiDichVu>();
		for (GoiDichVu dv : listDV) {
			dvCBBModel.addElement(dv);
		}
		cmbGoiDichVu = new JComboBox<GoiDichVu>(dvCBBModel);
		cmbGoiDichVu.setBounds(115, 130, 200, 30);
		pnTTThem.add(cmbGoiDichVu);
		return pnTTThem;

	}

	private JPanel taoPanleTaoDonThuoc() {
		JPanel pnTaoDonThuoc = store_GUI.createPannel(640, 50, 700, 600, "Tạo đơn thuốc");

		JPanel pnTimThuoc = store_GUI.createPannel(20, 30, 600, 200, "Tìm thuốc");

		JPanel pnTimKiem = store_GUI.createPannel(10, 25, 580, 55, "Tìm kiếm");
		txtTimThuoc = store_GUI.createTextField(100, 20, 180, 30, 100);
		btnTimThuoc = store_GUI.createButton(300, 20, 130, 30, "Tìm thuốc");
		btnTimThuoc.setIcon(store_GUI.taonICon("search.png", 20, 20));
		btnReset = store_GUI.createButton(450, 20, 100, 30, "Reset");
		btnReset.setIcon(store_GUI.taonICon("refresh.png", 20, 20));
		pnTimKiem.add(btnReset);
		pnTimKiem.add(txtTimThuoc);
		pnTimKiem.add(btnTimThuoc);
		pnTimKiem.add(store_GUI.createLable(10, 20, 80, 25, "Tên thuốc:"));
		pnTaoDonThuoc.add(pnTimKiem);

		JPanel pnTTCTThuoc = store_GUI.createPannel(10, 90, 650, 130, "Thông tin chi tiết thuốc");
		pnTTCTThuoc.setLayout(new BorderLayout());
		tblTTCTThuoc = new JTable();
		JPanel pnTableSP = store_GUI.createPanelTable(tblTTCTThuoc);
		pnTTCTThuoc.add(pnTableSP);
		pnTaoDonThuoc.add(pnTTCTThuoc);

		pnTaoDonThuoc.add(store_GUI.createLable(150, 250, 100, 25, "Nhập Số Lượng"));
		spSoLuong = store_GUI.createSpinerNumber(1, 1, 10, 1);
		JSpinner spinner = new JSpinner(spSoLuong);
		spinner.setBounds(250, 250, 100, 30);
		pnTaoDonThuoc.add(spinner);

		btnChonThuoc = store_GUI.createButton(10, 250, 130, 30, "Chọn thuốc ");
		pnTaoDonThuoc.add(btnChonThuoc);
		btnXoaThuocDaChon = store_GUI.createButton(400, 250, 130, 30, "Xóa thuốc");
		pnTaoDonThuoc.add(btnXoaThuocDaChon);

		JPanel pnDSThuoc = store_GUI.createPannel(10, 300, 650, 220, "Danh sách thuốc đã chọn");
		pnDSThuoc.setLayout(new BorderLayout());
		tblDSThuocDaChon = new JTable();
		JPanel pnTableThuocDaChon = store_GUI.createPanelTable(tblDSThuocDaChon);
		pnDSThuoc.add(pnTableThuocDaChon);
		pnTaoDonThuoc.add(pnDSThuoc);

		btnTaoDonThuoc = store_GUI.createButton(20, 530, 130, 50, "Tạo đơn thuốc ");
		pnTaoDonThuoc.add(btnTaoDonThuoc);
		txtMaDonThuoc = store_GUI.createTextField(230, 530, 180, 50, 100);
		pnTaoDonThuoc.add(txtMaDonThuoc);
		btnGuiDonThuoc = store_GUI.createButton(480, 530, 130, 50, "Gửi đơn thuốc ");
		pnTaoDonThuoc.add(btnGuiDonThuoc);

		return pnTaoDonThuoc;
	}

	/*
	 * Phương thức thực hiện việc XÓA RỖNG TRÊN TEXTFIELD.
	 */
	public void xoaRongTrenTextField() {
		txthoTen.setText(null);
		txtDiaChi.setText(null);
		txtMa.setText(null);
		txtSoCMND.setText(null);
		txtsoPhone.setText(null);
	}

	private void addEvents() {
		btnTimThuoc.addActionListener(this);
		btnGoiKham.addActionListener(this);
		list.addMouseListener(this);
		btnTaoDonThuoc.addActionListener(this);
		btnGuiDonThuoc.addActionListener(this);
		btnChonThuoc.addActionListener(this);
		btnReset.addActionListener(this);
		tblTTCTThuoc.addMouseListener(this);
		tblDSThuocDaChon.addMouseListener(this);
		btnXoaThuocDaChon.addActionListener(this);
	}

	public void Fill(KhachHang a) {
		txtMa.setText(String.valueOf(a.getMaKhachHang()));
		txthoTen.setText(String.valueOf(a.getTenKhachHang()));
		txtsoPhone.setText(String.valueOf(a.getSoDienThoai()));
		txtDiaChi.setText(String.valueOf(a.getDiaChi()));
		Date ngaySinh = (Date) jpkNgaySinh.getModel().getValue();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(ngaySinh);
		jpkNgaySinh.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		if (a.isGioiTinh()) {
			radNam.setSelected(true);
		} else {
			radNu.setSelected(true);
		}
		txtSoCMND.setText(String.valueOf(a.getSoCMND()));
	}

	private void layThongTinTrongDonThuoc() {
		tongTien = 0;
		for (ChiTietDonThuoc ctdt : dsCTDTs) {
			tongTien += ctdt.getThuoc().getDonGia() * ctdt.getSoLuong();
		}
		DecimalFormat df = new DecimalFormat("#,### VND");
	}

	public void taiLaiDataThuoc() {
		dsThuoc = thuocDAO.listThuoc();
		thuocTableModel = new ThuocTableModel(dsThuoc);
		tblTTCTThuoc.setModel(thuocTableModel);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(list)) {
			String selected = list.getSelectedValue();
			String ma = selected.substring(9);
			KhachHangDAO dao = new KhachHangDAO();
			KhachHang a = dao.timKHTheoMa(String.valueOf(ma));
			Fill(a);
		}

		if (o.equals(tblTTCTThuoc)) {
			indexTblThuoc = tblTTCTThuoc.getSelectedRow();
		}
		if (o.equals(tblDSThuocDaChon)) {
			indexTblCTDT = tblDSThuocDaChon.getSelectedRow();
		}
		if(o.equals(tblDSThuocDaChon)) {
			indexTaoDon = tblDSThuocDaChon.getSelectedRow();
		}
	}
	
	
	
	private void thanhToan() {
		double thanhtien = 0;
		double tongTien = 0;

		DecimalFormat df = new DecimalFormat("#,### VND");
	}
	
	private void tinhThanhTienThuoc(List<ChiTietDonThuoc> dsCTHD) {
		double tongTien = 0;
		for (ChiTietDonThuoc gh : dsCTHD) {
			tongTien += gh.getThuoc().getDonGia() * gh.getSoLuong();
		}
		DecimalFormat df = new DecimalFormat("#,### VND");
	//	txtTongTien.setText(df.format(tongTien));
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				WebLookAndFeel.install();
				try {
					new GUI_KhamBenh();
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
		if (o.equals(btnGoiKham)) {
			listModel.removeElementAt(list.getSelectedIndex());
		}

		if (o.equals(btnTimThuoc)) {
			String tenThuoc = store_GUI.getValueTextField(txtTimThuoc);
			if (tenThuoc != null) {
				Thuoc thuoc = thuocDAO.timThuocTheoTenThuoc(tenThuoc);
				List<Thuoc> listThuocs = new ArrayList<Thuoc>();
				listThuocs.add(thuoc);
				thuocTableModel = new ThuocTableModel(listThuocs);
				tblTTCTThuoc.setModel(thuocTableModel);
			}
		}

		if (o.equals(btnTaoDonThuoc)) {
			String maDT = store_GUI.getValueTextField(txtMaDonThuoc);
			if (maDT != null) {
				donThuoc = new DonThuoc(maDT);
				if (donThuocDAO.getDonThuoc(donThuoc.getMaDonThuoc()) == null) {
					if (donThuocDAO.themDonThuoc(donThuoc)) {
						JOptionPane.showMessageDialog(null, "Tạo đơn thuốc thành công !\n Mời bạn chọn thuốc!");
						btnTaoDonThuoc.setEnabled(false);
						txtMaDonThuoc.setEditable(false);
						dsThuocDaChon = new ArrayList<Thuoc>();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Đơn thuốc đã tồn tại !!");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Bạn Chưa Nhập Mã Đơn thuốc");
			}
		}

		if (o.equals(btnChonThuoc)) {
			int soLuong = (int) spSoLuong.getValue();
			if (donThuoc == null) {
				JOptionPane.showMessageDialog(null, "Bạn phải tạo đơn thuốc mới !!");
			} else if (indexTblThuoc == -1) {
				JOptionPane.showMessageDialog(null, "Chọn thuốc trong danh sách thuốc");
			} else {
				thuoc = dsThuoc.get(indexTblThuoc);
				if (!dsThuocDaChon.contains(thuoc)) {
					dsThuocDaChon.add(thuoc);
					ChiTietDonThuoc ghsp = new ChiTietDonThuoc();
					ghsp.setDonThuoc(donThuoc);
					ghsp.setThuoc(thuoc);
					ghsp.setSoLuong(soLuong);
					dsCTDTs.add(ghsp);
					ctdtTableModel = new CTDTTableModel(dsCTDTs);
					tblDSThuocDaChon.setModel(ctdtTableModel);
				} else {
					JOptionPane.showMessageDialog(null,
							"Thuốc đã tồn tại trong đơn thuốc\n Nếu muốn thay đổi số lượng hãy thực hiện chức năng update số lượng !!");
				}

			}
		}
		if (o.equals(btnXoaThuocDaChon)) {
			if (indexTblCTDT == -1) {
				JOptionPane.showMessageDialog(null, "Mời bạn chọn thuốc để xóa. !!");
			} else {
				Thuoc sp = dsCTDTs.get(indexTblCTDT).getThuoc();
				dsThuocDaChon.remove(sp);
				dsCTDTs.remove(indexTblCTDT);
				ctdtTableModel = new CTDTTableModel(dsCTDTs);
				tblDSThuocDaChon.setModel(ctdtTableModel);
				indexTblCTDT = -1;
			}
		}
		if (o.equals(btnReset)) {
			taiLaiDataThuoc();
			indexTblThuoc = -1;
		}
		if (o.equals(btnGuiDonThuoc)) {
			try {
				guiDonThuoc();
			} catch (NamingException | JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	
	
	private DonThuoc getDonThuocOnTextField() {
				
		List<ChiTietDonThuoc> listCT = new ArrayList<ChiTietDonThuoc>();

		
		ChiTietDonThuoc dsctdt = dsCTDTs.get(indexTaoDon);
		ctdtDAO.persist(dsctdt);
		listCT.add(dsctdt);
		String maDT = store_GUI.getValueTextField(txtMaDonThuoc);
		String maBS = store_GUI.getValueTextField(txtTenBacSy);
		String maKH = store_GUI.getValueTextField(txtMa);
		Date ngayLap = (Date) jpkNgayLap.getModel().getValue();
		GoiDichVu dv = (GoiDichVu) cmbGoiDichVu.getSelectedItem();
		BacSi bacsi = bacsiDAO.timBSTheoMa(maBS);
		KhachHang kh = khDAO.timKHTheoMa(maKH);
		DonThuoc donthuoc = new DonThuoc(maDT, kh, bacsi, dv, listCT, ngayLap);
		return donthuoc;
	}

	public void guiDonThuoc() throws NamingException, JMSException {

//		try {
//			List<ChiTietDonThuoc> listCT = new ArrayList<ChiTietDonThuoc>();
//			ChiTietDonThuoc dsctdt = dsCTDTs.get(indexTblCTDT);
//			ctdtDAO.persist(dsctdt);
//			listCT.add(dsctdt);
//			String maDT = store_GUI.getValueTextField(txtMaDonThuoc);
//			String maBS = store_GUI.getValueTextField(txtTenBacSy);
//			String maKH = store_GUI.getValueTextField(txtMa);
//			Date ngayLap = (Date) jpkNgayLap.getModel().getValue();
//			GoiDichVu dv = (GoiDichVu) cmbGoiDichVu.getSelectedItem();
//			BacSi bacsi = bacsiDAO.timBSTheoMa(maBS);
//			KhachHang kh = khDAO.timKHTheoMa(maKH);
//			DonThuoc donthuoc = new DonThuoc(maDT, kh, bacsi, dv, listCT, ngayLap);
//			donThuocDAO.merge(donthuoc);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		DonThuoc donthuoc = getDonThuocOnTextField();
		donThuocDAO.merge(donthuoc);

		BasicConfigurator.configure();

		Properties settings = new Properties();

		settings.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		settings.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
		Context ctx = new InitialContext(settings);
		ConnectionFactory factory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
		Destination destination = (Destination) ctx.lookup("dynamicQueues/KTTKPM_DHKTPM13B_BAITAPLON_NHOM18_DonThuoc");
		Connection con = factory.createConnection("admin", "admin");
		con.start();
		Session session = con.createSession(/* transaction */ false, /* ACK */ Session.AUTO_ACKNOWLEDGE);
		try {

			DonThuocDAO dao = new DonThuocDAO();
			String maDon = String.valueOf(txtMaDonThuoc.getText());
			DonThuoc a = new DonThuoc(maDon);
			dao.persist(a);

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String ngay = df.format(System.currentTimeMillis());

			MessageProducer producer = session.createProducer(destination);
			Message msg = session.createTextMessage(ngay + "_" + String.valueOf(txtMaDonThuoc.getText()));
			producer.send(msg);

		} catch (Exception e) {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String ngay = df.format(System.currentTimeMillis());
			MessageProducer producer = session.createProducer(destination);
			Message msg = session.createTextMessage(ngay + "_" + String.valueOf(txtMaDonThuoc.getText()));
			producer.send(msg);
		}
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
