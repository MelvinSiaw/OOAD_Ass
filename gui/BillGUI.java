package gui;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import model.*;

public class BillGUI extends JFrame {
    private final RegistrationManager regManager = RegistrationManager.getInstance();
    private final DefaultListModel<Registration> regListModel = new DefaultListModel<>();
    private final JList<Registration> regJList = new JList<>(regListModel);
    private final JButton calcButton, backButton;

    public BillGUI() {
        setTitle("View Bills");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JScrollPane scroll = new JScrollPane(regJList);
        scroll.setPreferredSize(new Dimension(550, 200));

        calcButton = new JButton("Calculate Bill");
        backButton = new JButton("Back");

        add(scroll);
        add(calcButton);
        add(backButton);

        refreshList();

        calcButton.addActionListener(e -> calcBill());
        backButton.addActionListener(e -> {
            new MainMenuGUI("Participant").setVisible(true); // role-based back
            dispose();
        });
    }

    private void refreshList() {
        regListModel.clear();
        ArrayList<Registration> regs = regManager.getAllRegistrations();
        for (Registration r : regs) {
            regListModel.addElement(r);
        }
    }

    private void calcBill() {
        Registration r = regJList.getSelectedValue();
        if (r == null) {
            JOptionPane.showMessageDialog(this, "Please select a registration first!");
            return;
        }

        DiscountStrategy discount = new GroupDiscount();
        Bill bill = FeeCalculator.calculate(r, discount);

        JOptionPane.showMessageDialog(this, bill.toString());
    }
}
