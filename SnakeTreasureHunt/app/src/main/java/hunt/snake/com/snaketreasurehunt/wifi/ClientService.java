package hunt.snake.com.snaketreasurehunt.wifi;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import hunt.snake.com.snaketreasurehunt.communication.MessageHandler;

/**
 * Created by Tom on 1/20/15.
 */
public class ClientService extends Service {

    private final IBinder binder = new ClientBinder();
    private static final int PORT = 7652;

    private Socket socket;
    private String serverAddress;
    private ObjectOutputStream out;
    private ObjectInputStream input;
    private MessageHandler mHandler;
    private boolean initialized = false;
    private Activity currentActivity;
    private boolean isHost;

    public void sendMessage(Object message) {
        try {
            System.out.println("Before send");
            out.writeObject(message);
            out.flush();
            System.out.println("Sent!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void test() {
        mHandler.test();
    }

    public MessageHandler getMessageHandler() {
        System.out.println("Handler: " + mHandler);
        return mHandler;
    }

    public void initClient(String serverAddress) {
        if(!initialized) {
            this.serverAddress = serverAddress;
            mHandler = new MessageHandler(this);
            new Thread(new ClientThread()).start();
            initialized = true;
        }
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("On Bind " + binder);
        currentActivity = null;
        return binder;
    }

    public class ClientBinder extends Binder {
        public ClientService getClient() {
            return ClientService.this;
        }
    }

    class ClientThread implements Runnable {
        @Override
        public void run() {
            System.out.println("In clientthread!");
            try {
                socket = new Socket(serverAddress, PORT);
                out = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("OutputStream created!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            initInputStream();
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Object obj = null;
                    try{
                        obj = input.readObject();
                        System.out.println("OBJ: " + obj);
                        mHandler.handleIncoming(obj);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread Interrupted!");
        }

        public void initInputStream() {
            try {
                input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
