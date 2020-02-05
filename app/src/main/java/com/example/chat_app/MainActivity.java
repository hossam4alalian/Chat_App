package com.example.chat_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText text;
    TextView recieved;
    LinearLayout lin;
    String str;

    Login login = new Login();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        new Thread(){
            public void run(){
                try {
                    while(true) {
                        str= login.client.getDi().readUTF();
                        String text=str.substring(0,6);

                        try {

                            if(!str.equals("") && text.equals("<!text")) {
                                lin.refreshDrawableState();
                                recieved=new TextView(MainActivity.this);

                                recieved.setText(str.substring(6));

                                lin.post(new Runnable() {
                                    public void run() {
                                        lin.addView(recieved);
                                    }
                                });
                            }
                        } catch (Exception e) {
                            Log.wtf("a","whattttttt");
                        }
                    }
                } catch (Exception e){

                }
            }
        }.start();

        chat();
    }

    public void chat(){
        lin = (LinearLayout) findViewById(R.id.chatBox);
        lin.removeAllViews();

        Button send=(Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();

            }
        });
    }

    public void send(){
        try {
            text = (EditText) findViewById(R.id.text);

            if(!text.getText().toString().equals("")){
                Bundle extras = getIntent().getExtras();

                String theName = extras.getString("name");
                TextView sentMessage = new TextView(MainActivity.this);

                sentMessage.setText(theName+" : "+text.getText().toString());
                lin.addView(sentMessage);

                login.client.getDs().writeUTF(text.getText().toString());
                text.setText("");
            }
        } catch (IOException e){
        }
    }
}