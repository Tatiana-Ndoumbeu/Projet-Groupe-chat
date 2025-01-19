import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.*;

public class Receiver {
    public static void main(String[] args) {
        int port = 2024;
        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Receiver is running on port " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new MessageHandler(clientSocket));
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            executorService.shutdown();
        }
    }
}

