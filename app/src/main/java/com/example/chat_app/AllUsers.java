package com.example.chat_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.IOException;

public class AllUsers extends AppCompatActivity {
    Login login = new Login();
    LinearLayout lin;
    String name;
    Button user;
    TextView tempUser;
    Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        getUsers();
        showUsers();

    }

    public void getUsers(){

         new Thread(){
            public void run(){
                 try {
                    while(true) {
                        name= login.client.getDi().readUTF();
                        try {
                            if(!name.equals("")) {
                                user = new Button(AllUsers.this);
                                user.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.wtf("a",name);
                                    }
                                });
                                user.setGravity(Gravity.CENTER);
                                tempUser.setText(name);

                                user.setText(tempUser.getText().toString());
                                lin.addView(user);
                                lin.refreshDrawableState();

                            }
                        } catch (Exception e) {
                            Log.wtf("a","whattttttt");
                        }
                    }
                 } catch (Exception e){

                }

            }
        }.start();

    }
    public void showUsers() {
        lin = (LinearLayout) findViewById(R.id.userbox);
        lin.removeAllViews();


        tempUser =(TextView) findViewById(R.id.tempUser);

        refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lin.removeAllViews();
                lin.refreshDrawableState();
                //recreate();

            }
        });
    }
}




