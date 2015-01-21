package hunt.snake.com.snaketreasurehunt.wifi;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import hunt.snake.com.snaketreasurehunt.communication.MessageHandler;

/**
 * Created by Tom on 12/12/14.
 */
public class Client {
    private static final int PORT = 7652;

    private Socket socket;
    private String serverAddress;
    private PrintWriter pOut;
    private ObjectOutputStream out;
    private ObjectInputStream input;
    private MessageHandler mHandler;

    public Client(String serverAddress) {
        this.serverAddress = serverAddress;
       // mHandler = new MessageHandler(this);
        new Thread(new ClientThread()).start();
    }

    public void sendMessage() {
        System.out.println("Out: " + out + " Socket: " + socket);
        pOut.println("First message, yaaay!");
        pOut.flush();
    }

    public void sendMessage(Object message) {
        try {
            out.writeObject(message);
            out.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MessageHandler getMessageHandler() {
        return mHandler;
    }

    public void saysHallo() {
        System.out.println("Client says hallo!");
    }

    class ClientThread implements Runnable {

        private BufferedReader reader;

        @Override
        public void run() {
            System.out.println("In clientthread!");
            try {
                socket = new Socket(serverAddress, PORT);
                pOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                out = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Out created!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            initInputStream();
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    //String read = reader.readLine();
                    try {
                        System.out.println("Input: " + input.toString());
                        Object obj = input.readObject();
                        if(obj != null) {
                            System.out.println("Object " + obj);
                            mHandler.handleIncoming(obj);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void initInputStream() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
