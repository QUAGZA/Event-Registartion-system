package eventportal.utils;

import eventportal.models.Participant;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String FILE_PATH = "participants.txt";

    // Save a participant to the file
    public static void saveParticipant(Participant participant) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(participant.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read all participants from the file
    public static List<Participant> readParticipants() {
        List<Participant> participants = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Participant p = Participant.fromString(line);
                if (p != null) participants.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return participants;
    }
    public static java.util.List<eventportal.models.Participant> read() {
        java.util.List<eventportal.models.Participant> participants = new java.util.ArrayList<>();
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("participants.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    eventportal.models.Participant p = new eventportal.models.Participant(
                            parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                    participants.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return participants;
    }

}
