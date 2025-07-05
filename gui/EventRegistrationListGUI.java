package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Event;
import model.Registration;
import model.RegistrationManager;

public class EventRegistrationListGUI extends JFrame {
    private final Event event;
    private final EventManagementGUI parent;
    private final DefaultTableModel regTableModel;
    private final JTable regTable;

    public EventRegistrationListGUI(EventManagementGUI parent, Event event) {
        this.parent = parent;
        this.event = event;

        setTitle("Registrations for: " + event.getName());
        setSize(1000, 600);                 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titleLabel = new JLabel("Registrations for: " + event.getName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // table
        String[] columns = {"Participant Name", "ID", "Group Size", "Catering", "Transport"};
        regTableModel = new DefaultTableModel(columns, 0);
        regTable = new JTable(regTableModel);
        JScrollPane scrollPane = new JScrollPane(regTable);
        add(scrollPane, BorderLayout.CENTER);

        // back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            parent.setVisible(true);
            dispose();
        });
        add(backButton, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        regTableModel.setRowCount(0);
        java.util.List<Registration> regs = RegistrationManager.getInstance().getRegistrationsByEvent(event);
        for(Registration r : regs){
            regTableModel.addRow(new Object[]{
                r.getParticipant().getName(),
                r.getParticipant().getId(),
                r.getGroupSize(),
                r.hasCatering() ? "Yes" : "No",
                r.hasTransportation() ? "Yes" : "No"
            });
        }
    }
}

