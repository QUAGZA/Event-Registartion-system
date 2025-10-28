package eventportal.models;

public class Participant {
    private String name;
    private String email;
    private String college;
    private String event;
    private String phone;
    private String timestamp;

    public Participant(String name, String email, String college, String event, String phone, String timestamp) {
        this.name = name;
        this.email = email;
        this.college = college;
        this.event = event;
        this.phone = phone;
        this.timestamp = timestamp;
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCollege() { return college; }
    public String getEvent() { return event; }
    public String getPhone() { return phone; }
    public String getTimestamp() { return timestamp; }

    // Converts participant data to a line for file saving
    @Override
    public String toString() {
        return name + "," + email + "," + college + "," + event + "," + phone + "," + timestamp;
    }

    // Parse a line from file back into a Participant object
    public static Participant fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length < 6) return null;
        return new Participant(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
    }
}
