package com.bharattrustbank.app;

import com.bharattrustbank.exception.AccountInactiveException;
import com.bharattrustbank.exception.AccountNotFoundException;
import com.bharattrustbank.model.Transaction;
import com.bharattrustbank.service.AccountService;
import com.bharattrustbank.service.TransactionService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class MiniStatement extends JFrame implements ActionListener {
    String pin;
    JButton button;
    private TransactionService transactionService;
    private AccountService accountService;
    
    MiniStatement(String pin) {
        this.pin = pin;
        this.transactionService = new TransactionService();
        this.accountService = new AccountService();
        
        getContentPane().setBackground(new Color(255, 255, 255));
        setSize(400, 600);
        setLocation(20, 20);
        setLayout(null);

        JLabel label1 = new JLabel();
        label1.setBounds(20, 140, 400, 200);
        add(label1);

        JLabel label2 = new JLabel("Bharat Trust Bank");
        label2.setFont(new Font("System", Font.BOLD, 15));
        label2.setBounds(120, 20, 200, 20);
        add(label2);

        JLabel label3 = new JLabel();
        label3.setBounds(20, 80, 300, 20);
        add(label3);

        JLabel label4 = new JLabel();
        label4.setBounds(20, 400, 300, 20);
        add(label4);

        try {
            String cardNumber = accountService.getAccountByPin(pin).getCardNumber();
            if (cardNumber != null && cardNumber.length() >= 16) {
                String maskedCard = cardNumber.substring(0, 4) + "XXXXXXXX" + cardNumber.substring(12);
                label3.setText("Card Number:  " + maskedCard);
            }
        } catch (AccountNotFoundException | AccountInactiveException e) {
            label3.setText("Card Number:  Not Available");
        }

        try {
            List<Transaction> transactions = transactionService.getTransactionHistory(pin);
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            
            StringBuilder statement = new StringBuilder("<html>");
            for (Transaction t : transactions) {
                statement.append(sdf.format(t.getDate()))
                        .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                        .append(t.getTypeString())
                        .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                        .append(t.getAmount())
                        .append("<br><br>");
            }
            statement.append("</html>");
            label1.setText(statement.toString());

            double balance = transactionService.getBalance(pin);
            label4.setText("Your Total Balance is Rs " + (int) balance);
        } catch (AccountNotFoundException e) {
            label1.setText("No transactions found");
            label4.setText("Balance: Rs 0");
        }

        button = new JButton("Exit");
        button.setBounds(20, 500, 100, 25);
        button.addActionListener(this);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        add(button);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }
}

