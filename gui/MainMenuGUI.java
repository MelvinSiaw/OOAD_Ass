package gui;

import javax.swing.*;

public class MainMenuGUI extends JFrame {

    public MainMenuGUI() {
        initRoleSelectionUI();
    }

    // 角色选择
    private void initRoleSelectionUI() {
        getContentPane().removeAll();
        repaint();

        setTitle("Campus Event Management System - Select Role");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel label = new JLabel("Select your role:");
        label.setBounds(140, 30, 150, 30);
        add(label);

        JButton organizerButton = new JButton("Organizer");
        organizerButton.setBounds(100, 80, 200, 30);
        add(organizerButton);

        JButton participantButton = new JButton("Participant");
        participantButton.setBounds(100, 130, 200, 30);
        add(participantButton);

        organizerButton.addActionListener(e -> {
            String password = JOptionPane.showInputDialog(
                    this,
                    "Enter Organizer Password:",
                    "Authentication",
                    JOptionPane.PLAIN_MESSAGE
            );
            if (password != null && password.equals("admin123")) {
                showOrganizerMenu();
            } else if (password != null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Incorrect password.",
                        "Access Denied",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        participantButton.addActionListener(e -> {
            showParticipantMenu();
        });
    }

    public void showOrganizerMenu() {
        getContentPane().removeAll();
        repaint();

        setTitle("Campus Event Management System - Organizer Menu");
        setLayout(null);

        JButton eventButton = new JButton("Event Management");
        eventButton.setBounds(50, 80, 300, 30);
        add(eventButton);

        JButton roleButton = new JButton("Switch Role");
        roleButton.setBounds(50, 130, 300, 30);
        add(roleButton);

        eventButton.addActionListener(e -> {
            new EventManagementGUI(this).setVisible(true);
            setVisible(false);
        });

        roleButton.addActionListener(e -> {
            initRoleSelectionUI();
            revalidate();
            repaint();
        });

        revalidate();
        repaint();
    }

    public void showParticipantMenu() {
        getContentPane().removeAll();
        repaint();

        setTitle("Campus Event Management System - Participant Menu");
        setLayout(null);

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
            new RegistrationGUI(this).setVisible(true);
            setVisible(false);
        });

        billButton.addActionListener(e -> {
            new BillGUI(this).setVisible(true);
            setVisible(false);
        });

        roleButton.addActionListener(e -> {
            initRoleSelectionUI();
            revalidate();
            repaint();
        });

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuGUI().setVisible(true));
    }
}
