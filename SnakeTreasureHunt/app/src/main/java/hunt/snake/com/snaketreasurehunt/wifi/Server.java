package hunt.snake.com.snaketreasurehunt.wifi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Tom on 12/11/14.
 */
public class Server {

    private static final int PORT = 7652;

    private ServerSocket serverSocket;
    private Thread serverThread;
    public boolean isStarted;
    public String serverAddress;
    private ArrayList<CommunicationThread> clients;

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

                    if(clients == null)
                        clients = new ArrayList<CommunicationThread>();
                    clients.add(comThread);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CommunicationThread implements Runnable {

        private Socket clientSocket;
        public ObjectOutputStream out;
        public ObjectInputStream in;

        public CommunicationThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            initInputStream();
        }

        public void initInputStream() {
            try {
                //reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                in = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                try {
                    Object obj = null;
                    try {
                        obj = in.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            in.close();
                            out.close();
                            serverSocket.close();
                            System.out.println("Socket closed...");
                            Thread.currentThread().interrupt();
                        } catch (IOException el) {
                            el.printStackTrace();
                        }
                    }
                    System.out.println("In server: " + obj);
                    broadcast(obj);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        private void broadcast(Object obj) {
            for(CommunicationThread comThread : clients) {
                if(comThread.clientSocket != clientSocket) {
                    try {
                        comThread.out.writeObject(obj);
                        comThread.out.flush();
                        System.out.println("Send to: " + comThread.clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
