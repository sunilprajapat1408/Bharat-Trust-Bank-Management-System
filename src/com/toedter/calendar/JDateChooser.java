package com.toedter.calendar;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Lightweight fallback replacement for the external JCalendar dependency.
 *
 * This keeps the existing Swing UI flow intact (a date input component with getDate()/setDate()).
 * If you later add the real JCalendar jar to the classpath, remove this class.
 */
public class JDateChooser extends JPanel {
    private final JTextField textField = new JTextField();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public JDateChooser() {
        setLayout(new BorderLayout());
        add(textField, BorderLayout.CENTER);
        // Keep it visually similar to a standard input field
        textField.setColumns(10);
    }

    public Date getDate() {
        String v = textField.getText();
        if (v == null || v.trim().isEmpty()) {
            return null;
        }
        try {
            return sdf.parse(v.trim());
        } catch (ParseException e) {
            return null;
        }
    }

    public void setDate(Date date) {
        if (date == null) {
            textField.setText("");
        } else {
            textField.setText(sdf.format(date));
        }
    }

    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg);
        if (textField != null) {
            textField.setForeground(fg);
        }
    }
}


