package eventportal.gui;

import eventportal.models.Participant;
import eventportal.utils.FileHandler;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ViewParticipantsFrame extends JFrame {

    private JTable participantsTable;
    private DefaultTableModel tableModel;
    private JLabel countLabel;

    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color PRIMARY_HOVER = new Color(52, 152, 219);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color DANGER_HOVER = new Color(192, 57, 43);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color SUCCESS_HOVER = new Color(46, 204, 113);
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Color PANEL_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Color HEADER_COLOR = new Color(52, 73, 94);
    private static final Color ROW_ALT_COLOR = new Color(245, 247, 250);

    public ViewParticipantsFrame() {
        setTitle("Registered Participants - Event Portal");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Main container
        JPanel mainContainer = new JPanel(new BorderLayout(0, 15));
        mainContainer.setBackground(BACKGROUND_COLOR);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add header
        mainContainer.add(createHeaderPanel(), BorderLayout.NORTH);

        // Add table panel
        mainContainer.add(createTablePanel(), BorderLayout.CENTER);

        // Add button panel
        mainContainer.add(createButtonPanel(), BorderLayout.SOUTH);

        add(mainContainer);

        // Load initial data
        loadParticipants();

        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Registered Participants");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(TEXT_COLOR);

        countLabel = new JLabel("Total: 0 participants");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        countLabel.setForeground(new Color(108, 117, 125));

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        titlePanel.add(countLabel);

        headerPanel.add(titlePanel, BorderLayout.WEST);

        return headerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(PANEL_COLOR);
        tablePanel.setBorder(new LineBorder(new Color(206, 212, 218), 1));

        // Table model with columns
        String[] columnNames = {"Name", "Email", "College", "Event", "Phone", "Registration Time"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        participantsTable = new JTable(tableModel);
        participantsTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        participantsTable.setRowHeight(35);
        participantsTable.setShowGrid(false);
        participantsTable.setIntercellSpacing(new Dimension(0, 0));
        participantsTable.setSelectionBackground(new Color(224, 242, 254));
        participantsTable.setSelectionForeground(TEXT_COLOR);
        participantsTable.setGridColor(new Color(233, 236, 239));

        // Alternate row colors
        participantsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : ROW_ALT_COLOR);
                    c.setForeground(TEXT_COLOR);
                } else {
                    c.setForeground(table.getSelectionForeground());
                }
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return c;
            }
        });

        // Style table header
        JTableHeader header = participantsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(HEADER_COLOR);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setReorderingAllowed(false);

        // Set column widths
        participantsTable.getColumnModel().getColumn(0).setPreferredWidth(130); // Name
        participantsTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Email
        participantsTable.getColumnModel().getColumn(2).setPreferredWidth(180); // College
        participantsTable.getColumnModel().getColumn(3).setPreferredWidth(140); // Event
        participantsTable.getColumnModel().getColumn(4).setPreferredWidth(120); // Phone
        participantsTable.getColumnModel().getColumn(5).setPreferredWidth(160); // Timestamp

        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(participantsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton refreshButton = createStyledButton("🔄 Refresh", SUCCESS_COLOR, SUCCESS_HOVER);
        refreshButton.addActionListener(e -> loadParticipants());

        JButton deleteButton = createStyledButton("🗑 Delete Selected", DANGER_COLOR, DANGER_HOVER);
        deleteButton.addActionListener(e -> deleteSelectedParticipant());

        buttonPanel.add(refreshButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setPreferredSize(new Dimension(180, 42));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void loadParticipants() {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Load participants from file using FileHandler
        List<Participant> participants = FileHandler.readParticipants();

        // Update count label
        countLabel.setText("Total: " + participants.size() + " participant" + (participants.size() != 1 ? "s" : ""));

        if (participants.isEmpty()) {
            return;
        }

        // Add each participant to the table
        for (Participant p : participants) {
            Object[] row = {
                p.getName(),
                p.getEmail(),
                p.getCollege(),
                p.getEvent(),
                p.getPhone(),
                p.getTimestamp()
            };
            tableModel.addRow(row);
        }
    }

    private void deleteSelectedParticipant() {
        int selectedRow = participantsTable.getSelectedRow();

        if (selectedRow == -1) {
            showStyledMessageDialog(
                "Please select a participant to delete!",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Get the participant details from the selected row
        String name = (String) tableModel.getValueAt(selectedRow, 0);
        String email = (String) tableModel.getValueAt(selectedRow, 1);
        String college = (String) tableModel.getValueAt(selectedRow, 2);
        String event = (String) tableModel.getValueAt(selectedRow, 3);

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this,
            createDeleteConfirmPanel(name, email, college, event),
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // Delete from file using FileHandler
            boolean success = FileHandler.deleteParticipant(selectedRow);

            if (success) {
                // Remove from table
                tableModel.removeRow(selectedRow);
                // Update count
                countLabel.setText("Total: " + tableModel.getRowCount() + " participant" + (tableModel.getRowCount() != 1 ? "s" : ""));

                showStyledMessageDialog(
                    "Participant deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                showStyledMessageDialog(
                    "Failed to delete participant from file!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private JPanel createDeleteConfirmPanel(String name, String email, String college, String event) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel messageLabel = new JLabel("Are you sure you want to delete this participant?");
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        messageLabel.setForeground(TEXT_COLOR);
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel detailsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 0, 0, 0),
            new LineBorder(new Color(206, 212, 218), 1)
        ));
        detailsPanel.setBackground(new Color(248, 249, 250));

        detailsPanel.add(createDetailLabel("Name: " + name));
        detailsPanel.add(createDetailLabel("Email: " + email));
        detailsPanel.add(createDetailLabel("College: " + college));
        detailsPanel.add(createDetailLabel("Event: " + event));

        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(detailsPanel);

        return panel;
    }

    private JLabel createDetailLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(TEXT_COLOR);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return label;
    }

    private void showStyledMessageDialog(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewParticipantsFrame::new);
    }
}
