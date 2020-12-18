package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class GUI_Store {
	public JLabel createLable(int x, int y, int width, int height, String text) {
		JLabel lbl = new JLabel("<html><font size='3' color=black>" + text + "</font></html>", SwingConstants.CENTER);
		lbl.setBounds(x, y, width, height);
		lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.red));
		return lbl;
	}

	public JTextField createTextField(int x, int y, int width, int height, int lenght) {
		JTextField txt = new JTextField(lenght);
		txt.setBounds(x, y, width, height);
		return txt;
	}

	public JPasswordField createPasswordField(int x, int y, int width, int height, int lenght) {
		JPasswordField txtPass = new JPasswordField(lenght);
		txtPass.setBounds(x, y, width, height);
		txtPass.setEchoChar('*');
		txtPass.setForeground(Color.BLACK);
		return txtPass;
	}

	public JButton createButton(int x, int y, int width, int height, String text) {
		JButton btn = new JButton("<html><font size='3' color=black >" + text + "</font></html>");
		btn.setBounds(x, y, width, height);
		return btn;
	}

	public JRadioButton createRadioButton(int x, int y, int width, int height, String text) {
		JRadioButton rd = new JRadioButton(text);
		rd.setBounds(x, y, width, height);
		return rd;
	}

	public JPanel createPannel(int x, int y, int width, int height, String title) {
		JPanel pn = new JPanel();
		Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED);

		TitledBorder titleBor = new TitledBorder(border, title);
		pn.setBorder(titleBor);
		pn.setLayout(null);
		pn.setBounds(x, y, width, height);
		return pn;
	}

	public JPanel createPanelPatterm() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		return panel;
	}

	public SpinnerNumberModel createSpinerNumber(int value, int min, int max, int stepNext) {
		SpinnerNumberModel spiner = new SpinnerNumberModel(value, min, max, stepNext);
		return spiner;
	}

	public JPanel createPanelTable(JTable tbl) {
		JPanel pnTable = new JPanel();
		pnTable.setLayout(new BorderLayout());
		JScrollPane sc = new JScrollPane(tbl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sc.setPreferredSize(new Dimension(400, 400));
		pnTable.add(sc, BorderLayout.CENTER);
		return pnTable;
	}

	public JDatePickerImpl createJDatePicker(int x, int y, int width, int height) {
		UtilDateModel model = new UtilDateModel();
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		String datePatterm = "dd/MM/yyyy";
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter(datePatterm));
		datePicker.setBounds(x, y, width, height);
		return datePicker;
	}

	public JDatePickerImpl createJDatePickerHaveDefaultDay(int x, int y, int width, int height) {
		UtilDateModel model = new UtilDateModel();
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		String datePatterm = "dd/MM/yy";
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter(datePatterm));
		datePicker.setBounds(x, y, width, height);
		return datePicker;
	}

	public JPanel showImageRomm(JButton btnNamePhong) {
		String imageSource = "";
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setPreferredSize(new Dimension(100, 100));
		JLabel lbl = new JLabel(new ImageIcon(imageSource));
		panel.add(lbl);
		panel.add(btnNamePhong);
		return panel;
	}

	public String getValueTextFieldHavePatterm(JTextField txt, String patterm, String message) {
		String value = txt.getText();
		JOptionPane.showMessageDialog(null, message);
		return value;
	}

	public String getValueTextField(JTextField txt) {
		String value = txt.getText();
		if (value.trim().equals("")) {
			txt.selectAll();
			txt.requestFocus();
			return null;
		}
		return value;
	}


	public JRadioButton taoRadioButton(int x, int y, int width, int height, String text) {
		JRadioButton rd = new JRadioButton(text);
		rd.setBounds(x, y, width, height);
		return rd;
	}

	public ImageIcon taonICon(String image, int width, int height) {
		ImageIcon imageIcon = new ImageIcon(
				new ImageIcon("images/" + image).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		return imageIcon;
	}

}
