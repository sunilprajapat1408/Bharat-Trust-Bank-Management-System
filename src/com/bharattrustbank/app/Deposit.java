package com.bharattrustbank.app;

import com.bharattrustbank.exception.AccountInactiveException;
import com.bharattrustbank.exception.AccountNotFoundException;
import com.bharattrustbank.service.TransactionService;
import com.bharattrustbank.util.InputValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Deposit extends JFrame implements ActionListener {
    String pin;
    TextField textField;
    JButton b1, b2;
    private TransactionService transactionService;
    
    Deposit(String pin) {
        super("BHARAT TRUST BANK");
        this.pin = pin;
        this.transactionService = new TransactionService();

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        JLabel label1 = new JLabel("ENETR AMOUNT YOU WANT TO DEPOSIT");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(460, 180, 400, 35);
        l3.add(label1);

        textField = new TextField();
        textField.setBackground(new Color(65, 125, 128));
        textField.setForeground(Color.WHITE);
        textField.setBounds(460, 230, 320, 25);
        textField.setFont(new Font("Raleway", Font.BOLD, 22));
        l3.add(textField);

        b1 = new JButton("DEPOSIT");
        b1.setBounds(700, 362, 150, 35);
        b1.setBackground(new Color(65, 125, 128));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(700, 406, 150, 35);
        b2.setBackground(new Color(65, 125, 128));
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        l3.add(b2);

        setLayout(null);
        setSize(1550, 1080);
        setLocation(0, 0);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == b1) {
                String amountStr = textField.getText();
                
                if (InputValidator.isEmpty(amountStr)) {
                    JOptionPane.showMessageDialog(null, "Please enter the Amount you want to Deposit");
                    return;
                }
                
                if (!InputValidator.isPositiveNumber(amountStr)) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid positive amount");
                    return;
                }
                
                try {
                    double amount = Double.parseDouble(amountStr);
                    transactionService.deposit(pin, amount);
                    JOptionPane.showMessageDialog(null, "Rs. " + amount + " Deposited Successfully");
                    setVisible(false);
                    new Main(pin);
                } catch (AccountNotFoundException | AccountInactiveException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else if (e.getSource() == b2) {
                setVisible(false);
                new Main(pin);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid amount format");
        }
    }
}

