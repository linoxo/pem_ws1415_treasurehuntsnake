package hunt.snake.com.snaketreasurehunt.wifi;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Tom on 12/11/14.
 */
public class Server {

    private static final int PORT = 7652;

    private ServerSocket serverSocket;
    private Thread serverThread;
    public boolean isStarted;
    public String serverAddress;

    public Server() {
        serverThread = new Thread(new ServerThread());

        serverThread.start();
        System.out.println("Start server...");
    }

    public void disconnect() {
        try {
            serverSocket.close();
            isStarted = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ServerThread implements Runnable{

        @Override
        public void run() {
            Socket socket = null;

            try {
                serverSocket = new ServerSocket(PORT);
                serverAddress = serverSocket.getInetAddress().getHostAddress();
                isStarted = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(!Thread.currentThread().isInterrupted()) {

                try {
                    socket = serverSocket.accept();

                    System.out.println("New socket " + socket + "...");

                    CommunicationThread comThread = new CommunicationThread(socket);
                    new Thread(comThread).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class CommunicationThread implements Runnable {

        private Socket clientSocket;

        private BufferedReader reader;

        public CommunicationThread(Socket clientSocket) {
            this.clientSocket = clientSocket;

            initInputStream();
        }

        public void initInputStream() {
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                try {
                    String read = reader.readLine();

                    System.out.println(read);

                    //DO SOMETHING!!!!
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
