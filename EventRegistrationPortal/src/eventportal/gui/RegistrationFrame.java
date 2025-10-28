package eventportal.gui;

import eventportal.models.Participant;
import eventportal.utils.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RegistrationFrame extends JFrame {

    private JTextField nameField, emailField, collegeField, eventField, phoneField;

    public RegistrationFrame() {
        setTitle("Participant Registration");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel collegeLabel = new JLabel("College:");
        JLabel eventLabel = new JLabel("Event:");
        JLabel phoneLabel = new JLabel("Phone:");

        nameField = new JTextField();
        emailField = new JTextField();
        collegeField = new JTextField();
        eventField = new JTextField();
        phoneField = new JTextField();

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(collegeLabel);
        panel.add(collegeField);
        panel.add(eventLabel);
        panel.add(eventField);
        panel.add(phoneLabel);
        panel.add(phoneField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(this::handleRegister);

        JButton viewButton = new JButton("View Participants");
        viewButton.addActionListener(this::handleViewParticipants);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        buttonPanel.add(viewButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void handleRegister(ActionEvent e) {
        String name = nameField.getText();
        String email = emailField.getText();
        String college = collegeField.getText();
        String event = eventField.getText();
        String phone = phoneField.getText();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (name.isEmpty() || email.isEmpty() || college.isEmpty() || event.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        Participant participant = new Participant(name, email, college, event, phone, timestamp);
        FileHandler.saveParticipant(participant);

        JOptionPane.showMessageDialog(this, "Participant Registered Successfully!");
    }

    private void handleViewParticipants(ActionEvent e) {
        List<Participant> participants = FileHandler.readParticipants();

        if (participants.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No participants found!");
            return;
        }

        StringBuilder sb = new StringBuilder("Registered Participants:\n\n");
        for (Participant p : participants) {
            sb.append(p.getName()).append(" - ").append(p.getCollege()).append(" - ").append(p.getEvent()).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Participants List", JOptionPane.INFORMATION_MESSAGE);
    }
}
