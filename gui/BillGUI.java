package gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;

public class BillGUI extends JFrame {
    private final RegistrationManager regManager = RegistrationManager.getInstance();

    private final String[] columns = {"Participant", "Event", "Group Size", "Catering", "Transport", "Status"};
    private final DefaultTableModel regTableModel = new DefaultTableModel(columns, 0);
    private final JTable regTable = new JTable(regTableModel);

    private final JButton calcButton, backButton;
    private final MainMenuGUI parentMenu;

    private final java.util.List<Registration> regList = new java.util.ArrayList<>();

    public BillGUI(MainMenuGUI parentMenu) {
        this.parentMenu = parentMenu;

        setTitle("Bill Calculation");
        setSize(1000, 600);                 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Bill Calculation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // table
        JScrollPane scrollPane = new JScrollPane(regTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registrations"));
        scrollPane.setPreferredSize(new Dimension(800, 400));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        calcButton = new JButton("Calculate Bill");
        backButton = new JButton("Back");
        buttonPanel.add(calcButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        refreshTable();

        calcButton.addActionListener(e -> calcBill());
        backButton.addActionListener(e -> {
            parentMenu.showParticipantMenu();
            parentMenu.setVisible(true);
            dispose();
        });
    }

    private void refreshTable() {
        regTableModel.setRowCount(0);
        regList.clear();

        List<Registration> regs = regManager.getAllRegistrations();
        for (Registration r : regs) {
            String status = r.getEvent().isCancelled() ? "CANCELLED" : "ACTIVE";
            regTableModel.addRow(new Object[]{
                r.getParticipant().getName(),
                r.getEvent().getName(),
                r.getGroupSize(),
                r.hasCatering() ? "Yes" : "No",
                r.hasTransportation() ? "Yes" : "No",
                status
            });
            regList.add(r);
        }
    }

    private void calcBill() {
        int idx = regTable.getSelectedRow();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Please select a registration first!");
            return;
        }

        Registration r = regList.get(idx);

        if (r.getEvent().isCancelled()) {
            JOptionPane.showMessageDialog(this,
                    "This event has been cancelled. Bill is no longer valid.",
                    "Cancelled Event", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DiscountStrategy discount = new GroupDiscount();
        Bill bill = FeeCalculator.calculate(r, discount);

        JPanel billPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        billPanel.add(new JLabel("Base Fee:"));
        billPanel.add(new JLabel(String.format("RM%.2f", bill.getBaseFee())));
        billPanel.add(new JLabel("Extra Services:"));
        billPanel.add(new JLabel(String.format("RM%.2f", bill.getExtraServices())));
        billPanel.add(new JLabel("Total Before Discount:"));
        billPanel.add(new JLabel(String.format("RM%.2f", bill.getBaseFee() + bill.getExtraServices())));
        billPanel.add(new JLabel("Discount:"));
        billPanel.add(new JLabel(String.format("RM%.2f", bill.getDiscountAmount())));
        billPanel.add(new JLabel("Net Payable:"));
        billPanel.add(new JLabel(String.format("RM%.2f", bill.getNetPayable())));

        JOptionPane.showMessageDialog(this, billPanel, "Bill", JOptionPane.INFORMATION_MESSAGE);
    }
}
