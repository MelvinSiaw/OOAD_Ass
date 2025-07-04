package gui;

import javax.swing.*;

public class MainMenuGUI extends JFrame {
    public MainMenuGUI() {
        String[] roles = {"Organizer", "Participant"};
        String role = (String) JOptionPane.showInputDialog(
                null,  
                "Select your role:",
                "Role Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                roles,
                roles[0]
        );

        if (role != null) {
            initUI(role);
        } else {
            dispose();
        }
    }

    public MainMenuGUI(String role) {
        initUI(role);
    }

    private void initUI(String role) {
        setTitle("Campus Event Management System");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        if (role.equals("Organizer")) {
            JButton eventButton = new JButton("Event Management");
            eventButton.setBounds(50, 80, 300, 30);
            add(eventButton);

            JButton roleButton = new JButton("Switch Role");
            roleButton.setBounds(50, 130, 300, 30);
            add(roleButton);

            eventButton.addActionListener(e -> {
                new EventManagementGUI().setVisible(true);
                dispose();
            });

            roleButton.addActionListener(e -> {
                new MainMenuGUI().setVisible(true);
                dispose();
            });

        } else if (role.equals("Participant")) {
            JButton regButton = new JButton("Registration");
            regButton.setBounds(50, 50, 300, 30);
            add(regButton);

            JButton billButton = new JButton("Bill");
            billButton.setBounds(50, 100, 300, 30);
            add(billButton);

            JButton roleButton = new JButton("Switch Role");
            roleButton.setBounds(50, 150, 300, 30);
            add(roleButton);

            regButton.addActionListener(e -> {
                new RegistrationGUI().setVisible(true);
                dispose();
            });

            billButton.addActionListener(e -> {
                new BillGUI().setVisible(true);
                dispose();
            });


            roleButton.addActionListener(e -> {
                new MainMenuGUI().setVisible(true);
                dispose();
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuGUI().setVisible(true));
    }
}
