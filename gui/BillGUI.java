package gui;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import model.*;

public class BillGUI extends JFrame {
    private final RegistrationManager regManager = RegistrationManager.getInstance();
    private final DefaultListModel<String> regListModel = new DefaultListModel<>();
    private final JList<String> regJList = new JList<>(regListModel);
    private final JButton calcButton, backButton;

    private final MainMenuGUI parentMenu;

    public BillGUI(MainMenuGUI parentMenu) {
        this.parentMenu = parentMenu;

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
            parentMenu.showParticipantMenu();
            parentMenu.setVisible(true);
            dispose();
        });
    }

    private void refreshList() {
        regListModel.clear();
        ArrayList<Registration> regs = regManager.getAllRegistrations();
        for (Registration r : regs) {
            String status = r.getEvent().isCancelled() ? " [CANCELLED]" : "";
            String display = r.getParticipant().getName()
                + " (Group size: " + r.getGroupSize() + ") registered for "
                + r.getEvent().getName()
                + status;
            regListModel.addElement(display);
        }
    }

    private void calcBill() {
        int idx = regJList.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Please select a registration first!");
            return;
        }

        // 重新获得 Registration 对象
        Registration r = regManager.getAllRegistrations().get(idx);

        if (r.getEvent().isCancelled()) {
            JOptionPane.showMessageDialog(this,
                "This event has been cancelled. Bill is no longer valid.",
                "Cancelled Event", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DiscountStrategy discount = new GroupDiscount();
        Bill bill = FeeCalculator.calculate(r, discount);

        JOptionPane.showMessageDialog(this, bill.toString());
    }
}
