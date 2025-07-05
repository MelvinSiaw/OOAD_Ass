package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;
import model.Event;
import model.EventManager;
import model.Participant;
import model.Registration;
import model.RegistrationManager;

public class RegistrationGUI extends JFrame {
    private final EventManager eventManager = EventManager.getInstance();
    private final RegistrationManager registrationManager = RegistrationManager.getInstance();

    private final DefaultListModel<Event> eventListModel = new DefaultListModel<>();
    private final JList<Event> eventJList = new JList<>(eventListModel);

    private final JTextField nameField, idField, groupSizeField;
    private final JCheckBox cateringBox, transportBox;
    private final JButton registerButton, backButton;

    private final MainMenuGUI parentMenu;

    public RegistrationGUI(MainMenuGUI parentMenu) {
        this.parentMenu = parentMenu;

        setTitle("Event Registration");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        nameField = new JTextField(20);
        idField = new JTextField(10);
        groupSizeField = new JTextField(5);
        cateringBox = new JCheckBox("Catering (+RM20 per person)");
        transportBox = new JCheckBox("Transportation (+RM10 per person)");

        registerButton = new JButton("Register");
        backButton = new JButton("Back");

        JScrollPane scroll = new JScrollPane(eventJList);
        scroll.setPreferredSize(new Dimension(550, 200));

        add(new JLabel("Participant Name:")); add(nameField);
        add(new JLabel("Participant ID:")); add(idField);
        add(new JLabel("Group Size:")); add(groupSizeField);
        add(cateringBox); add(transportBox);
        add(registerButton); add(backButton);
        add(scroll);

        refreshEventList();

        registerButton.addActionListener(e -> register());
        backButton.addActionListener(e -> {
            parentMenu.showParticipantMenu();
            parentMenu.setVisible(true);
            dispose();
        });
    }

    private void refreshEventList() {
        eventListModel.clear();
        for (Event ev : eventManager.getAllEvents()) {
            if (!ev.isCancelled()) {
                eventListModel.addElement(ev);
            }
        }
    }

    private void register() {
        Event e = eventJList.getSelectedValue();
        if (e == null) {
            JOptionPane.showMessageDialog(this, "Please select an event to register!");
            return;
        }

        String name = nameField.getText().trim();
        String pid = idField.getText().trim();

        if (name.isEmpty() || pid.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and ID are required!");
            return;
        }

        int groupSize;
        try {
            groupSize = Integer.parseInt(groupSizeField.getText().trim());
            if (groupSize <= 0) {
                JOptionPane.showMessageDialog(this, "Group size must be positive!");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Group size must be a valid number!");
            return;
        }

        Participant p = new Participant(name, pid);
        Registration reg = new Registration(
                p, e,
                cateringBox.isSelected(),
                transportBox.isSelected(),
                groupSize
        );
        registrationManager.addRegistration(reg);

        JOptionPane.showMessageDialog(this, "Registered successfully for group size: " + groupSize);
        clearFields();
    }

    private void clearFields() {
        nameField.setText("");
        idField.setText("");
        groupSizeField.setText("");
        cateringBox.setSelected(false);
        transportBox.setSelected(false);
    }
}
