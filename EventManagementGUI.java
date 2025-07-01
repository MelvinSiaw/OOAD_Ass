import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EventManagementGUI extends JFrame {
    private EventManager eventManager = new EventManager();
    private DefaultListModel<String> eventListModel = new DefaultListModel<>();
    private JList<String> eventJList = new JList<>(eventListModel);

    private JTextField nameField, dateField, venueField, capacityField, feeField;
    private JComboBox<String> typeComboBox;

    public EventManagementGUI() {
        setTitle("Campus Event Management System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        nameField = new JTextField(20);
        dateField = new JTextField(20);
        venueField = new JTextField(20);
        capacityField = new JTextField(5);
        feeField = new JTextField(5);

        String[] eventTypes = {"Seminar", "Workshop", "Cultural Event", "Sports Event"};
        typeComboBox = new JComboBox<>(eventTypes);

        JButton addButton = new JButton("Create Event");
        JButton updateButton = new JButton("Update Selected Event");
        JButton deleteButton = new JButton("Cancel Selected Event");

        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("Date:")); add(dateField);
        add(new JLabel("Venue:")); add(venueField);
        add(new JLabel("Type:")); add(typeComboBox);
        add(new JLabel("Capacity:")); add(capacityField);
        add(new JLabel("Fee:")); add(feeField);

        add(addButton); add(updateButton); add(deleteButton);
        add(new JScrollPane(eventJList));

        addButton.addActionListener(e -> createEvent());
        updateButton.addActionListener(e -> updateEvent());
        deleteButton.addActionListener(e -> deleteEvent());
    }

    private void createEvent() {
        String name = nameField.getText();
        String date = dateField.getText();
        String venue = venueField.getText();
        String type = (String) typeComboBox.getSelectedItem();
        int capacity = Integer.parseInt(capacityField.getText());
        double fee = Double.parseDouble(feeField.getText());

        Event event = new Event(name, date, venue, type, capacity, fee);
        eventManager.addEvent(event);
        eventListModel.addElement(event.toString());
        clearFields();
    }

    private void updateEvent() {
        int index = eventJList.getSelectedIndex();
        if (index >= 0) {
            String name = nameField.getText();
            String date = dateField.getText();
            String venue = venueField.getText();
            String type = (String) typeComboBox.getSelectedItem();
            int capacity = Integer.parseInt(capacityField.getText());
            double fee = Double.parseDouble(feeField.getText());

            Event updatedEvent = new Event(name, date, venue, type, capacity, fee);
            eventManager.updateEvent(index, updatedEvent);
            eventListModel.set(index, updatedEvent.toString());
            clearFields();
        }
    }

    private void deleteEvent() {
        int index = eventJList.getSelectedIndex();
        if (index >= 0) {
            eventManager.deleteEvent(index);
            eventListModel.remove(index);
        }
    }

    private void clearFields() {
        nameField.setText("");
        dateField.setText("");
        venueField.setText("");
        capacityField.setText("");
        feeField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventManagementGUI().setVisible(true));
    }
}
