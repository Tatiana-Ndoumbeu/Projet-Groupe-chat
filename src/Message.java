import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private String sender;
    private String timestamp;
    private int length;
    private String content;

    // Constructor for sending messages
    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.length = content.length();
        this.timestamp = generateTimestamp();
    }

    // Constructor for received messages
    public Message(String sender, String content, String timestamp) {
        this.sender = sender;
        this.content = content;
        this.length = content.length();
        this.timestamp = timestamp;
    }

    // Generate the current timestamp
    private String generateTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    // Convert the message to a formatted string for sending
    public String toFormattedString() {
        return sender + "\n" +
                timestamp + "\n" +
                length + "\n" +
                content + "\n";
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + sender + ": " + content;
    }
}
