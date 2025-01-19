import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class MessageHandler implements Runnable {
    private final Socket clientSocket;

    public MessageHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            // Read the four lines of the message
            String sender = in.readLine();
            String timestamp = in.readLine();
            int length = Integer.parseInt(in.readLine());
            String content = in.readLine();

            // Validate message length
            if (content.length() != length) {
                System.out.println("Warning: Message length mismatch from sender " + sender);
                return;
            }

            // Create a Message object
            Message message = new Message(sender, content);

            // Print the message to the console
            System.out.println(message);

            // Log the message to messages.log
            logMessage(message);

        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }

    private void logMessage(Message message) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("messages.log"), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(message.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }
}
