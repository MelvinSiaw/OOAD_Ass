package gui;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import model.Event;
import model.EventManager;
import model.Registration;
import model.RegistrationManager;

public class EventManagementGUI extends JFrame {
    private final EventManager eventManager = EventManager.getInstance();
    private final DefaultListModel<String> eventListModel = new DefaultListModel<>();
    private final JList<String> eventJList = new JList<>(eventListModel);

    private final JTextField nameField, dateField, venueField, capacityField, feeField;
    private final JComboBox<String> typeComboBox;

    private final MainMenuGUI parentMenu;

    public EventManagementGUI(MainMenuGUI parentMenu) {
        this.parentMenu = parentMenu;

        setTitle("Event Management");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // form fields
        nameField = new JTextField(20);
        dateField = new JTextField(20);
        venueField = new JTextField(20);
        capacityField = new JTextField(5);
        feeField = new JTextField(5);

        String[] eventTypes = {"Seminar", "Workshop", "Cultural Event", "Sports Event"};
        typeComboBox = new JComboBox<>(eventTypes);

        // buttons
        JButton addButton = new JButton("Add Event");
        JButton updateButton = new JButton("Update Event");
        JButton cancelButton = new JButton("Cancel Event");
        JButton viewRegsButton = new JButton("View Registrations"); // 新增
        JButton backButton = new JButton("Back");

        // layout
        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("Date:")); add(dateField);
        add(new JLabel("Venue:")); add(venueField);
        add(new JLabel("Type:")); add(typeComboBox);
        add(new JLabel("Capacity:")); add(capacityField);
        add(new JLabel("Fee:")); add(feeField);
        add(addButton); 
        add(updateButton); 
        add(cancelButton); 
        add(viewRegsButton);  // 新增
        add(backButton);

        JScrollPane scroll = new JScrollPane(eventJList);
        scroll.setPreferredSize(new Dimension(650, 200));
        add(scroll);

        refreshEventList();

        // actions
        addButton.addActionListener(e -> addEvent());
        updateButton.addActionListener(e -> updateEvent());
        cancelButton.addActionListener(e -> cancelEvent());
        backButton.addActionListener(e -> {
            parentMenu.showOrganizerMenu();
            parentMenu.setVisible(true);
            dispose();
        });
        viewRegsButton.addActionListener(e -> viewRegistrations());

        eventJList.addListSelectionListener(e -> {
            int idx = eventJList.getSelectedIndex();
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

    private void addEvent() {
        try {
            String name = nameField.getText().trim();
            String date = dateField.getText().trim();
            String venue = venueField.getText().trim();
            String type = (String) typeComboBox.getSelectedItem();
            int capacity = Integer.parseInt(capacityField.getText().trim());
            double fee = Double.parseDouble(feeField.getText().trim());

            if (capacity <= 0) {
                JOptionPane.showMessageDialog(this, "Capacity must be positive.");
                return;
            }

            Event ev = new Event(name, date, venue, type, capacity, fee);
            eventManager.addEvent(ev);
            refreshEventList();
            clearFields();
        } catch (NumberFormatException | NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Invalid or missing data!");
        }
    }

    private void updateEvent() {
        int idx = eventJList.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Please select an event to update!");
            return;
        }

        Event existing = eventManager.getAllEvents().get(idx);
        if (existing.isCancelled()) {
            JOptionPane.showMessageDialog(this, "Cannot update a cancelled event!");
            return;
        }

        try {
            String name = nameField.getText().trim();
            String date = dateField.getText().trim();
            String venue = venueField.getText().trim();
            String type = (String) typeComboBox.getSelectedItem();
            int capacity = Integer.parseInt(capacityField.getText().trim());
            double fee = Double.parseDouble(feeField.getText().trim());

            if (capacity <= 0) {
                JOptionPane.showMessageDialog(this, "Capacity must be positive");
                return;
            }

            Event ev = new Event(name, date, venue, type, capacity, fee);
            eventManager.updateEvent(idx, ev);
            refreshEventList();
            clearFields();
            JOptionPane.showMessageDialog(this, "Event updated successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Capacity and fee must be valid numbers!");
        }
    }

    private void cancelEvent() {
        int idx = eventJList.getSelectedIndex();
        if (idx >= 0) {
            eventManager.cancelEvent(idx);
            refreshEventList();
        }
    }

private void viewRegistrations() {
    int idx = eventJList.getSelectedIndex();
    if (idx < 0) {
        JOptionPane.showMessageDialog(this, "Please select an event first!");
        return;
    }
    Event ev = eventManager.getAllEvents().get(idx);

    if (ev.isCancelled()) {
        JOptionPane.showMessageDialog(this,
            "This event has been cancelled. Registrations are no longer available.",
            "Cancelled Event", JOptionPane.WARNING_MESSAGE);
        return;
    }

    java.util.List<Registration> regs = RegistrationManager
            .getInstance()
            .getRegistrationsByEvent(ev);

    if (regs.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No registrations for this event yet.");
        return;
    }

    StringBuilder sb = new StringBuilder();
    for (var r : regs) {
        sb.append("- ")
          .append(r.getParticipant().getName())
          .append(" (").append(r.getParticipant().getId()).append(")\n")
          .append("  Group Size: ").append(r.getGroupSize()).append("\n")
          .append("  Catering: ").append(r.hasCatering() ? "Yes" : "No").append("\n")
          .append("  Transport: ").append(r.hasTransportation() ? "Yes" : "No").append("\n")
          .append("----------------------------\n");
    }
    JOptionPane.showMessageDialog(this, sb.toString(),
            "Registrations for " + ev.getName(),
            JOptionPane.INFORMATION_MESSAGE);
}


    private void refreshEventList() {
        eventListModel.clear();
        ArrayList<Event> events = eventManager.getAllEvents();
        for (Event ev : events) {
            eventListModel.addElement(ev.toString());
        }
    }

    private void clearFields() {
        nameField.setText("");
        dateField.setText("");
        venueField.setText("");
        capacityField.setText("");
        feeField.setText("");
    }
}
