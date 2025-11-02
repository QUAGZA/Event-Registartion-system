package eventportal.gui;

import eventportal.models.Participant;
import eventportal.utils.FileHandler;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class RegistrationFrame extends JFrame {

    private JTextField nameField, emailField, collegeField, eventField, phoneField;
    private JLabel nameError, emailError, collegeError, eventError, phoneError;
    private JButton registerButton, viewButton;

    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);      // Blue
    private static final Color PRIMARY_HOVER = new Color(52, 152, 219);      // Lighter Blue
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);       // Green
    private static final Color ERROR_COLOR = new Color(231, 76, 60);         // Red
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);  // Light Gray
    private static final Color PANEL_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(44, 62, 80);           // Dark Blue Gray
    private static final Color BORDER_COLOR = new Color(206, 212, 218);      // Light Border

    public RegistrationFrame() {
        setTitle("Event Registration Portal");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Main container with padding
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(BACKGROUND_COLOR);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Add header
        mainContainer.add(createHeaderPanel());
        mainContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add form panel
        mainContainer.add(createFormPanel());
        mainContainer.add(Box.createRigidArea(new Dimension(0, 25)));

        // Add button panel
        mainContainer.add(createButtonPanel());

        // Add to frame
        JScrollPane scrollPane = new JScrollPane(mainContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);

        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Participant Registration");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Register for upcoming events");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(108, 117, 125));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(subtitleLabel);

        return headerPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(PANEL_COLOR);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        formPanel.setMaximumSize(new Dimension(600, 500));
        formPanel.setPreferredSize(new Dimension(600, 500));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create form fields with error labels
        formPanel.add(createFieldGroup("Full Name *", nameField = createStyledTextField(), nameError = createErrorLabel()));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        formPanel.add(createFieldGroup("Email Address *", emailField = createStyledTextField(), emailError = createErrorLabel()));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        formPanel.add(createFieldGroup("College/Institution *", collegeField = createStyledTextField(), collegeError = createErrorLabel()));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        formPanel.add(createFieldGroup("Event Name *", eventField = createStyledTextField(), eventError = createErrorLabel()));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        formPanel.add(createFieldGroup("Phone Number *", phoneField = createStyledTextField(), phoneError = createErrorLabel()));

        return formPanel;
    }

    private JPanel createFieldGroup(String labelText, JTextField textField, JLabel errorLabel) {
        JPanel fieldGroup = new JPanel();
        fieldGroup.setLayout(new BoxLayout(fieldGroup, BoxLayout.Y_AXIS));
        fieldGroup.setBackground(PANEL_COLOR);
        fieldGroup.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        fieldGroup.add(label);
        fieldGroup.add(Box.createRigidArea(new Dimension(0, 6)));
        fieldGroup.add(textField);
        fieldGroup.add(Box.createRigidArea(new Dimension(0, 3)));
        fieldGroup.add(errorLabel);

        return fieldGroup;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(450, 40));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        textField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        // Add focus listener for border highlight
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(PRIMARY_COLOR, 2),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });

        return textField;
    }

    private JLabel createErrorLabel() {
        JLabel errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        errorLabel.setForeground(ERROR_COLOR);
        return errorLabel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        registerButton = createStyledButton("Register Participant", PRIMARY_COLOR, PRIMARY_HOVER);
        registerButton.addActionListener(this::handleRegister);

        viewButton = createStyledButton("View All Participants", SUCCESS_COLOR, new Color(46, 204, 113));
        viewButton.addActionListener(this::handleViewParticipants);

        buttonPanel.add(registerButton);
        buttonPanel.add(viewButton);

        return buttonPanel;
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setPreferredSize(new Dimension(210, 45));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void handleRegister(ActionEvent e) {
        // Clear previous errors
        clearErrors();

        // Get field values
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String college = collegeField.getText().trim();
        String event = eventField.getText().trim();
        String phone = phoneField.getText().trim();

        // Validate fields
        boolean isValid = true;

        if (name.isEmpty()) {
            nameError.setText("⚠ Name is required");
            highlightErrorField(nameField);
            isValid = false;
        } else if (name.length() < 2) {
            nameError.setText("⚠ Name must be at least 2 characters");
            highlightErrorField(nameField);
            isValid = false;
        }

        if (email.isEmpty()) {
            emailError.setText("⚠ Email is required");
            highlightErrorField(emailField);
            isValid = false;
        } else if (!isValidEmail(email)) {
            emailError.setText("⚠ Please enter a valid email address");
            highlightErrorField(emailField);
            isValid = false;
        }

        if (college.isEmpty()) {
            collegeError.setText("⚠ College/Institution is required");
            highlightErrorField(collegeField);
            isValid = false;
        }

        if (event.isEmpty()) {
            eventError.setText("⚠ Event name is required");
            highlightErrorField(eventField);
            isValid = false;
        }

        if (phone.isEmpty()) {
            phoneError.setText("⚠ Phone number is required");
            highlightErrorField(phoneField);
            isValid = false;
        } else if (!isValidPhone(phone)) {
            phoneError.setText("⚠ Please enter a valid phone number");
            highlightErrorField(phoneField);
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        // Save participant
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Participant participant = new Participant(name, email, college, event, phone, timestamp);
        FileHandler.saveParticipant(participant);

        // Show success message
        showSuccessDialog();

        // Clear form
        clearForm();
    }

    private void clearErrors() {
        nameError.setText(" ");
        emailError.setText(" ");
        collegeError.setText(" ");
        eventError.setText(" ");
        phoneError.setText(" ");

        // Reset borders
        resetFieldBorder(nameField);
        resetFieldBorder(emailField);
        resetFieldBorder(collegeField);
        resetFieldBorder(eventField);
        resetFieldBorder(phoneField);
    }

    private void highlightErrorField(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ERROR_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }

    private void resetFieldBorder(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPhone(String phone) {
        String phoneRegex = "^[0-9+\\-\\s()]{10,15}$";
        return Pattern.matches(phoneRegex, phone);
    }

    private void showSuccessDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel iconLabel = new JLabel("✓");
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        iconLabel.setForeground(SUCCESS_COLOR);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel messageLabel = new JLabel("Registration Successful!");
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        messageLabel.setForeground(TEXT_COLOR);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("Participant has been registered successfully.");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLabel.setForeground(new Color(108, 117, 125));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(subLabel);

        JOptionPane.showMessageDialog(this, panel, "Success", JOptionPane.PLAIN_MESSAGE);
    }

    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        collegeField.setText("");
        eventField.setText("");
        phoneField.setText("");
        nameField.requestFocus();
    }

    private void handleViewParticipants(ActionEvent e) {
        // Open the ViewParticipantsFrame which displays participants in a table
        new ViewParticipantsFrame();
    }
}
