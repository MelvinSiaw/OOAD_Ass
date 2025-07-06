package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Event;
import model.EventManager;

public class EventManagementGUI extends JFrame {
    private final EventManager eventManager = EventManager.getInstance();

    private final String[] columns = {"Name", "Date", "Venue", "Type", "Capacity", "Fee", "Status"};
    private final DefaultTableModel eventTableModel = new DefaultTableModel(columns, 0);
    private final JTable eventTable = new JTable(eventTableModel);

    private final JTextField nameField, dateField, venueField, capacityField, feeField;
    private final JComboBox<String> typeComboBox;

    private final MainMenuGUI parentMenu;

    public EventManagementGUI(MainMenuGUI parentMenu) {
        this.parentMenu = parentMenu;

        setTitle("Campus Event Management System - Event Management");
        setSize(1000, 600);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15,15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JLabel titleLabel = new JLabel("Event Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Event Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField(15);
        dateField = new JTextField(15);
        venueField = new JTextField(15);
        capacityField = new JTextField(15);
        feeField = new JTextField(15);
        typeComboBox = new JComboBox<>(new String[]{"Seminar","Workshop","Cultural Event","Sports Event"});

        gbc.gridx=0; gbc.gridy=0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx=1;
        formPanel.add(nameField, gbc);

        gbc.gridx=0; gbc.gridy++;
        formPanel.add(new JLabel("Date:"), gbc);
        gbc.gridx=1;
        formPanel.add(dateField, gbc);

        gbc.gridx=0; gbc.gridy++;
        formPanel.add(new JLabel("Venue:"), gbc);
        gbc.gridx=1;
        formPanel.add(venueField, gbc);

        gbc.gridx=0; gbc.gridy++;
        formPanel.add(new JLabel("Type:"), gbc);
        gbc.gridx=1;
        formPanel.add(typeComboBox, gbc);

        gbc.gridx=0; gbc.gridy++;
        formPanel.add(new JLabel("Capacity:"), gbc);
        gbc.gridx=1;
        formPanel.add(capacityField, gbc);

        gbc.gridx=0; gbc.gridy++;
        formPanel.add(new JLabel("Fee (RM):"), gbc);
        gbc.gridx=1;
        formPanel.add(feeField, gbc);

        mainPanel.add(formPanel, BorderLayout.WEST);

        // event table
        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Event List"));
        scrollPane.setPreferredSize(new Dimension(600,400));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1,5,10,10));
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");
        JButton viewRegsButton = new JButton("View Regs");
        JButton backButton = new JButton("Back");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(viewRegsButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        refreshEventTable();

        // listeners
        addButton.addActionListener(e -> addEvent());
        updateButton.addActionListener(e -> updateEvent());
        cancelButton.addActionListener(e -> cancelEvent());
        viewRegsButton.addActionListener(e -> viewRegistrations());
        backButton.addActionListener(e -> {
            parentMenu.showOrganizerMenu();
            parentMenu.setVisible(true);
            dispose();
        });

        eventTable.getSelectionModel().addListSelectionListener(e -> {
            int idx = eventTable.getSelectedRow();
            if (idx >= 0) {
                Event ev = eventManager.getAllEvents().get(idx);
                nameField.setText(ev.getName());
                dateField.setText(ev.getDate());
                venueField.setText(ev.getVenue());
                typeComboBox.setSelectedItem(ev.getType());
                capacityField.setText(String.valueOf(ev.getCapacity()));
                feeField.setText(String.valueOf(ev.getFee()));
            }
        });
    }

    private void refreshEventTable() {
        eventTableModel.setRowCount(0);
        for(Event ev: eventManager.getAllEvents()) {
            eventTableModel.addRow(new Object[]{
                ev.getName(),
                ev.getDate(),
                ev.getVenue(),
                ev.getType(),
                ev.getCapacity(),
                ev.getFee(),
                ev.isCancelled() ? "CANCELLED" : "ACTIVE"
            });
        }
    }

    private void addEvent() {
        try {
            String name = nameField.getText().trim();
            String date = dateField.getText().trim();
            String venue = venueField.getText().trim();
            String type = (String) typeComboBox.getSelectedItem();
            int capacity = Integer.parseInt(capacityField.getText().trim());
            double fee = Double.parseDouble(feeField.getText().trim());

            if(capacity <= 0) {
                JOptionPane.showMessageDialog(this,"Capacity must be positive.");
                return;
            }
            Event ev = new Event(name,date,venue,type,capacity,fee);
            eventManager.addEvent(ev);
            refreshEventTable();
            clearFields();
        } catch (NumberFormatException | NullPointerException ex)  {
            JOptionPane.showMessageDialog(this,"Invalid or missing data!");
        }
    }

    private void updateEvent() {
        int idx = eventTable.getSelectedRow();
        if(idx < 0) {
            JOptionPane.showMessageDialog(this,"Please select an event to update.");
            return;
        }
        Event existing = eventManager.getAllEvents().get(idx);
        if(existing.isCancelled()) {
            JOptionPane.showMessageDialog(this,"Cannot update a cancelled event.");
            return;
        }
        try {
            String name = nameField.getText().trim();
            String date = dateField.getText().trim();
            String venue = venueField.getText().trim();
            String type = (String) typeComboBox.getSelectedItem();
            int capacity = Integer.parseInt(capacityField.getText().trim());
            double fee = Double.parseDouble(feeField.getText().trim());

            if(capacity <= 0) {
                JOptionPane.showMessageDialog(this,"Capacity must be positive.");
                return;
            }
            Event ev = new Event(name,date,venue,type,capacity,fee);
            eventManager.updateEvent(idx, ev);
            refreshEventTable();
            clearFields();
            JOptionPane.showMessageDialog(this,"Event updated successfully!");
        } catch (NumberFormatException | NullPointerException ex)  {
            JOptionPane.showMessageDialog(this,"Invalid or missing data!");
        }
    }

    private void cancelEvent() {
        int idx = eventTable.getSelectedRow();
        if(idx >= 0) {
            eventManager.cancelEvent(idx);
            refreshEventTable();
            JOptionPane.showMessageDialog(this,"Event cancelled.");
        }
    }

    private void viewRegistrations() {
    int idx = eventTable.getSelectedRow();
    if(idx < 0) {
        JOptionPane.showMessageDialog(this,"Please select an event first.");
        return;
    }
    Event ev = eventManager.getAllEvents().get(idx);
    if(ev.isCancelled()) {
        JOptionPane.showMessageDialog(this,
            "This event has been cancelled. Registrations are unavailable.");
        return;
    }
    new EventRegistrationListGUI(this, ev).setVisible(true);
    setVisible(false);
}

    private void clearFields() {
        nameField.setText("");
        dateField.setText("");
        venueField.setText("");
        capacityField.setText("");
        feeField.setText("");
    }
}
