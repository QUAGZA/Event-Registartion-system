package eventportal.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Admin Login window. Modern dark-themed styling applied.
 * Admin credentials (hardcoded): username=admin  password=admin123
 */
public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {
        setTitle("Event Portal - Admin Login");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(43,43,43));
        root.setBorder(BorderFactory.createEmptyBorder(18,18,18,18));

        JLabel title = new JLabel("Admin Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(220,220,220));
        root.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx=0; gbc.gridy=0; form.add(new JLabel("Username:"), gbc);
        gbc.gridx=1; userField = new JTextField(14); form.add(userField, gbc);

        gbc.gridx=0; gbc.gridy=1; form.add(new JLabel("Password:"), gbc);
        gbc.gridx=1; passField = new JPasswordField(14); form.add(passField, gbc);

        JButton loginBtn = createPrimaryButton("Login");
        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=2; gbc.anchor = GridBagConstraints.CENTER;
        form.add(loginBtn, gbc);

        root.add(form, BorderLayout.CENTER);

        // Actions
        loginBtn.addActionListener((ActionEvent e) -> {
            String u = userField.getText().trim();
            String p = new String(passField.getPassword()).trim();
            if ("admin".equals(u) && "admin123".equals(p)) {
                // open admin
                SwingUtilities.invokeLater(() -> {
                    AdminFrame af = new AdminFrame();
                    af.setVisible(true);
                });
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        setContentPane(root);
    }

    private JButton createPrimaryButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(new Color(70,130,180));
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));
        return b;
    }
}
