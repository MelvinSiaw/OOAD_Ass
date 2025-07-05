package gui;

import java.awt.*;
import javax.swing.*;

public class MainMenuGUI extends JFrame {

    public MainMenuGUI() {
        initRoleSelectionUI();
    }

    private void initRoleSelectionUI() {
        getContentPane().removeAll();
        repaint();

        setTitle("Campus Event Management System - Select Role");
        setSize(1000, 600);                 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Select Your Role", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 15, 15));
        JButton organizerButton = new JButton("Organizer");
        JButton participantButton = new JButton("Participant");

        organizerButton.setFont(new Font("Arial", Font.PLAIN, 16));
        participantButton.setFont(new Font("Arial", Font.PLAIN, 16));

        buttonPanel.add(organizerButton);
        buttonPanel.add(participantButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);

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

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Organizer Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 15, 15));
        JButton eventButton = new JButton("Event Management");
        JButton roleButton = new JButton("Switch Role");

        eventButton.setFont(new Font("Arial", Font.PLAIN, 16));
        roleButton.setFont(new Font("Arial", Font.PLAIN, 16));

        buttonPanel.add(eventButton);
        buttonPanel.add(roleButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);

        eventButton.addActionListener(e -> {
            new EventManagementGUI(this).setVisible(true);
            setVisible(false);
        });

        roleButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Switching role...");
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

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Participant Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        JButton regButton = new JButton("Registration");
        JButton billButton = new JButton("Bill");
        JButton roleButton = new JButton("Switch Role");

        regButton.setFont(new Font("Arial", Font.PLAIN, 16));
        billButton.setFont(new Font("Arial", Font.PLAIN, 16));
        roleButton.setFont(new Font("Arial", Font.PLAIN, 16));

        buttonPanel.add(regButton);
        buttonPanel.add(billButton);
        buttonPanel.add(roleButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);

        regButton.addActionListener(e -> {
            new RegistrationGUI(this).setVisible(true);
            setVisible(false);
        });

        billButton.addActionListener(e -> {
            new BillGUI(this).setVisible(true);
            setVisible(false);
        });

        roleButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Switching role...");
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
