package eventportal.gui;

import eventportal.models.Participant;
import eventportal.utils.FileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminFrame extends JFrame {

    public AdminFrame() {
        setTitle("Admin Dashboard");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton viewButton = new JButton("View Participants");
        viewButton.addActionListener(e -> showParticipantsTable());

        add(viewButton, BorderLayout.CENTER);
        setVisible(true);
    }

    private void showParticipantsTable() {
        List<Participant> participants = FileHandler.readParticipants();

        if (participants.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No participants registered yet!");
            return;
        }

        String[] columns = {"Name", "Email", "College", "Event", "Phone", "Timestamp"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Participant p : participants) {
            model.addRow(new Object[]{
                    p.getName(),
                    p.getEmail(),
                    p.getCollege(),
                    p.getEvent(),
                    p.getPhone(),
                    p.getTimestamp()
            });
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(650, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Registered Participants", JOptionPane.PLAIN_MESSAGE);
    }
}
