package com.example.jp.socketapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TCPClient tcpClient = new TCPClient();
        tcpClient.Connect("192.168.2.11",1024);
    }
}