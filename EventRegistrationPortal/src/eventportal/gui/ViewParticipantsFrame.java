import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class ViewParticipantsFrame extends JFrame {

    private JTable participantsTable;

    public ViewParticipantsFrame() {
        setTitle("Registered Participants");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table model with columns
        String[] columnNames = {"Name", "College", "Event"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        participantsTable = new JTable(tableModel);

        // Load participants from file
        ArrayList<String[]> participants = loadParticipants();
        for (String[] participant : participants) {
            tableModel.addRow(participant);
        }

        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(participantsTable);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private ArrayList<String[]> loadParticipants() {
        ArrayList<String[]> participants = new ArrayList<>();
        File file = new File("participants.txt");

        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "No participants found!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return participants;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Expected format: Name - College - Event
                String[] parts = line.split(" - ");
                if (parts.length == 3) {
                    participants.add(parts);
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading participants.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return participants;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewParticipantsFrame::new);
    }
}
