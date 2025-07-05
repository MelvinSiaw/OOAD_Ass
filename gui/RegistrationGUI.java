package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Event;
import model.EventManager;
import model.Participant;
import model.Registration;
import model.RegistrationManager;

public class RegistrationGUI extends JFrame {
    private final EventManager eventManager = EventManager.getInstance();
    private final RegistrationManager registrationManager = RegistrationManager.getInstance();

    private final String[] columns = {"Name", "Date", "Venue", "Type", "Capacity", "Fee"};
    private final DefaultTableModel eventTableModel = new DefaultTableModel(columns, 0);
    private final JTable eventTable = new JTable(eventTableModel);

    private final List<Event> activeEventList = new ArrayList<>();  
    private final JTextField nameField, idField, groupSizeField;
    private final JCheckBox cateringBox, transportBox;
    private final JButton registerButton, backButton;

    private final MainMenuGUI parentMenu;

    public RegistrationGUI(MainMenuGUI parentMenu) {
        this.parentMenu = parentMenu;

        setTitle("Event Registration");
        setSize(1000, 600);                 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15,15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JLabel titleLabel = new JLabel("Event Registration", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Registration Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField(15);
        idField = new JTextField(15);
        groupSizeField = new JTextField(15);
        cateringBox = new JCheckBox("Catering (+RM20 per person)");
        transportBox = new JCheckBox("Transportation (+RM10 per person)");

        gbc.gridx=0; gbc.gridy=0;
        formPanel.add(new JLabel("Participant Name:"), gbc);
        gbc.gridx=1;
        formPanel.add(nameField, gbc);

        gbc.gridx=0; gbc.gridy++;
        formPanel.add(new JLabel("Participant ID:"), gbc);
        gbc.gridx=1;
        formPanel.add(idField, gbc);

        gbc.gridx=0; gbc.gridy++;
        formPanel.add(new JLabel("Group Size:"), gbc);
        gbc.gridx=1;
        formPanel.add(groupSizeField, gbc);

        gbc.gridx=0; gbc.gridy++;
        formPanel.add(cateringBox, gbc);
        gbc.gridx=1;
        formPanel.add(transportBox, gbc);

        registerButton = new JButton("Register");
        backButton = new JButton("Back");

        gbc.gridx=0; gbc.gridy++;
        formPanel.add(registerButton, gbc);
        gbc.gridx=1;
        formPanel.add(backButton, gbc);

        mainPanel.add(formPanel, BorderLayout.WEST);

        // event table
        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Available Events"));
        scrollPane.setPreferredSize(new Dimension(600,400));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        refreshEventTable();

        // button actions
        registerButton.addActionListener(e -> register());
        backButton.addActionListener(e -> {
            parentMenu.showParticipantMenu();
            parentMenu.setVisible(true);
            dispose();
        });

        // table selection (double-check active)
        eventTable.getSelectionModel().addListSelectionListener(e -> {
            int idx = eventTable.getSelectedRow();
            if(idx >= 0){
                if (idx < activeEventList.size()) {
                    Event ev = activeEventList.get(idx);
                    if(ev.isCancelled()){
                        JOptionPane.showMessageDialog(this,
                            "This event is cancelled and cannot be registered.");
                        eventTable.clearSelection();
                    }
                }
            }
        });
    }

    private void refreshEventTable() {
        eventTableModel.setRowCount(0);
        activeEventList.clear();
        for(Event ev: eventManager.getAllEvents()) {
            if (!ev.isCancelled()) {
                eventTableModel.addRow(new Object[]{
                    ev.getName(),
                    ev.getDate(),
                    ev.getVenue(),
                    ev.getType(),
                    ev.getCapacity(),
                    ev.getFee()
                });
                activeEventList.add(ev);  
            }
        }
    }

    private void register() {
        int idx = eventTable.getSelectedRow();
        if(idx < 0) {
            JOptionPane.showMessageDialog(this, "Please select an event to register!");
            return;
        }

        if (idx >= activeEventList.size()) {
            JOptionPane.showMessageDialog(this, "Selected event is invalid.");
            return;
        }

        Event e = activeEventList.get(idx);

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
