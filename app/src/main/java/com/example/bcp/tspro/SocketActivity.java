package com.example.bcp.tspro;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import pool.ThreadPool;

public class SocketActivity extends Activity {
    private static final String HOST = "192.168.1.4";
    private static final int PORT = 5000;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x001) {
                String text = (String) msg.obj;
                Toast.makeText(SocketActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        Button mButton = (Button) findViewById(R.id.send_msg);
        mEditText = (EditText) findViewById(R.id.input_msg);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();
                sendMsg(text);
            }
        });
    }

    private void sendMsg(final String msg) {

        ThreadPool.getThreadPool().execute(new Runnable() {
            Socket socket;

            @Override
            public void run() {
                try {
                    socket = new Socket(HOST, PORT);
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(msg.replace("\n", " ") + "\n");
                    writer.flush();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    String result = reader.readLine();
                    Message message = mHandler.obtainMessage();
                    message.what = 0x001;
                    message.obj = result;
                    mHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
