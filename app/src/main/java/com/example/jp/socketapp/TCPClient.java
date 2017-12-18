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
    private int serverPort = 1234;
    private Socket connectionSocket;

public class ConnectRunnable implements Runnable
{
    public void run(){
        try {

            Log.d(TAG,"C: Connecting...");

            InetAddress serverAddr = InetAddress.getByName(serverIP);
            startTime = System.currentTimeMillis();

            connectionSocket = new Socket();
            connectionSocket.connect(new InetSocketAddress(serverAddr,serverPort),5000);

            long time = System.currentTimeMillis() - startTime;
            Log.d(TAG, "Connected! Current duration : " + time + " ms");
        } catch (Exception e){

        }
        Log.d(TAG,"Connection Thread Stopped.");
    }
}
public void Connect(String ip, int port)
{
    serverIP = ip;
    serverPort = port;
    new Thread(new TCPClient.ConnectRunnable()).start();
}
}
