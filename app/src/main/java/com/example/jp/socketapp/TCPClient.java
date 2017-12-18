package com.example.jp.socketapp;

import android.util.Log;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by JP on 12/18/2017.
 */

public class TCPClient {
    private static String TAG = "TCPClient";
    private String serverIP = "192.168.2.11";
    private long startTime = 01;
    private int serverPort = 1024;
    private Socket connectionSocket;

public class ConnectRunnable implements Runnable
{
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
public void Connect(String ip, int port)
{
    serverIP = ip;
    serverPort = port;

    new Thread(new ConnectRunnable()).start();
}
}
