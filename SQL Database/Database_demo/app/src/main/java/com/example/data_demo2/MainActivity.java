package com.example.data_demo2;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   TextInputEditText editEmail,editPassword;
    Button button,button2;

    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editEmail=(TextInputEditText)findViewById(R.id.editEmail);
        editPassword=(TextInputEditText)findViewById(R.id.editPassword);
        button=(Button)findViewById(R.id.button);
        button2=(Button)findViewById(R.id.button2);

        db=new DatabaseHelper(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email=editEmail.getText().toString();
                String password=editPassword.getText().toString();


                if (db.checkuser(email,password)){

                    Intent intent=new Intent(MainActivity.this,login_Successfull.class);

                    intent.putExtra("EMAIL",editEmail.getText().toString());
                    intent.putExtra("PASSWORD",editPassword.getText().toString());

                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,Register.class);
                startActivity(intent);

            }
        });

    }

}