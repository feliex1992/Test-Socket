package com.example.jp.socketapp;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by JP on 12/18/2017.
 */

public class TCPClient {
    //Socket Connection
    private static String TAG = "TCPClient";
    private String serverIP = "192.168.2.11";
    private long startTime = 01;
    private int serverPort = 1024;
    private Socket connectionSocket;
    //____________________

    private SendRunnable sendRunnable;
    private Thread sendThread;
    byte[] dataToSend;

    private void startSending(){
        sendRunnable = new SendRunnable(connectionSocket);
        sendThread = new Thread(sendRunnable);
        sendThread.start();
    }

    public boolean isConnected(){
        return connectionSocket != null && connectionSocket.isConnected() && !connectionSocket.isClosed();
    }

    public class ConnectRunnable implements Runnable{
        public void run(){
            try {

                Log.d(TAG, "C: Connecting...");

                InetAddress serverAddr = InetAddress.getByName(serverIP);
                startTime = System.currentTimeMillis();
                //Create a new instance of Socket
                connectionSocket = new Socket();

                //Start connecting to the server with 5000ms timeout
                //This will block the thread until a connection is established
                Log.d(TAG, "Server IP : " + serverIP);
                Log.d(TAG, "Server Port : " + serverPort);
                connectionSocket.connect(new InetSocketAddress(serverAddr, serverPort), 5000);

                long time = System.currentTimeMillis() - startTime;
                Log.d(TAG, "Connected! Current duration: " + time + "ms");
            } catch (Exception e){
                Log.d(TAG,"Error "+ e.toString());
            }
            Log.d(TAG,"Connection Thread Stopped.");
        }
    }

    public void Connect(String ip, int port){
        serverIP = ip;
        serverPort = port;

        new Thread(new ConnectRunnable()).start();
    }

    public class SendRunnable implements Runnable{

        //Send Variable
        byte[] data;
        private OutputStream out;
        private boolean hasMessage = false;
        int dataType = 1;
        //_____________

        public SendRunnable(Socket server){
            try {
                this.out = server.getOutputStream();
            }catch (IOException e) {

            }
        }

        public void Send(byte[] bytes){
            this.data = bytes;
            dataType = TCPCommands.TYPE_FILE_CONTENT;
            this.hasMessage = true;
        }

        public void SendCMD(byte[] bytes){
            this.data = bytes;
            dataType = TCPCommands.TYPE_CMD;
            this.hasMessage = true;
        }

        @Override
        public void run(){
            Log.d(TAG,"Sending Started.");
            while (!Thread.currentThread().isInterrupted() && isConnected()) {
                if (this.hasMessage){
                    startTime = System.currentTimeMillis();
                    try {
                        this.out.write(ByteBuffer.allocate(4).putInt(data.length).array());
                        this.out.write(ByteBuffer.allocate(4).putInt(dataType).array());
                        this.out.write(data, 0, data.length);
                        this.out.flush();
                    }catch (IOException e){

                    }
                    this.hasMessage = false;
                    this.data =  null;
                    long time = System.currentTimeMillis() - startTime;
                    Log.d(TAG, "Command has been sent! Current duration: " + time + "ms");
                }
            }
            Log.d(TAG, "Sending stopped");
        }
    }
}
