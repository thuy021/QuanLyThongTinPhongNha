package gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;

@SuppressWarnings("serial")
public class DateLabelFormatter extends AbstractFormatter {

	private SimpleDateFormat dateFormatter;
	public DateLabelFormatter(String datePatterm) {
		dateFormatter = new SimpleDateFormat(datePatterm);
	}

	@Override
	public Object stringToValue(String text) throws ParseException {
		return dateFormatter.parseObject(text);
	}

	@Override
	public String valueToString(Object value) throws ParseException {
		if (value != null) {
			Calendar cal = (Calendar) value;
			return dateFormatter.format(cal.getTime());
		}

		return "";
	}

}