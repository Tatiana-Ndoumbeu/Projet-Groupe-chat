import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Sender {
    private static final String LOG_FILE = "messages.log";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Sender <username>");
            return;
        }

        String username = args[0];
        List<String> ipAddresses = new ArrayList<>();

        // Load IP addresses from IP.txt using ClassLoader
        try (InputStream inputStream = Sender.class.getResourceAsStream("/IP.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                ipAddresses.add(line.trim());
            }

        } catch (Exception e) {
            System.out.println("Error reading IP.txt: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter messages (type 'exit' to quit):");
        while (true) {
            String messageContent = scanner.nextLine();

            if (messageContent.equalsIgnoreCase("exit")) {
                break;
            }

            // Create a new Message object
            Message message = new Message(username, messageContent);

            // Send the message to all Receivers
            for (String ip : ipAddresses) {
                try (Socket socket = new Socket(ip, 2024);
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    out.print(message.toFormattedString()); // Send formatted message

                    // Log the sent message
                    logMessage("Sent to " + ip + ": " + message);

                } catch (IOException e) {
                    System.out.println("Failed to send message to " + ip + ": " + e.getMessage());
                }
            }
        }

        scanner.close();
    }

    private static void logMessage(String log) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(LOG_FILE), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(log);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }
}
