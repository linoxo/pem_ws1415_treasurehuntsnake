package hunt.snake.com.snaketreasurehunt.wifi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Tom on 12/12/14.
 */
public class Client {

    private static final int PORT = 7652;

    private Socket socket;
    private String serverAddress;
    private PrintWriter out;

    public Client(String serverAddress) {
        this.serverAddress = serverAddress;
        new Thread(new ClientThread()).start();
    }

    public void sendMessage() {
        System.out.println("Out: " + out + " Socket: " + socket);
        out.println("First message, yaaay!");
        out.flush();
    }

    class ClientThread implements Runnable {

        private BufferedReader reader;

        @Override
        public void run() {
            System.out.println("In clientthread!");
            try {
                socket = new Socket(serverAddress, PORT);
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                System.out.println("Out created!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            initInputStream();
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String read = reader.readLine();
                    if(read != null)
                        System.out.println("In client " + read);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void initInputStream() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
