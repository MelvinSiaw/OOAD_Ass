package gui;

import javax.swing.*;

public class MainMenuGUI extends JFrame {
    public MainMenuGUI() {
        setTitle("Campus Event Management System");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton eventButton = new JButton("Event Management");
        eventButton.setBounds(50, 30, 300, 30);
        add(eventButton);

        JButton regButton = new JButton("Registration");
        regButton.setBounds(50, 80, 300, 30);
        add(regButton);

        JButton billButton = new JButton("Bill");
        billButton.setBounds(50, 130, 300, 30);
            add(billButton);

        // listeners
        eventButton.addActionListener(e -> {
            new EventManagementGUI().setVisible(true);
            dispose();
        });

        regButton.addActionListener(e -> {
            new RegistrationGUI().setVisible(true);
            dispose();
        });

        billButton.addActionListener(e -> {
            new BillGUI().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuGUI().setVisible(true));
    }

    
}
