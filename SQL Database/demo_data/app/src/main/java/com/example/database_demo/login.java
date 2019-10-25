package com.example.database_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    EditText edittext,edittext2;
    Button button;
    TextView textview;

    Databasehelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edittext=(EditText)findViewById(R.id.edittext);
        edittext2=(EditText)findViewById(R.id.edittext2);
        textview=(TextView)findViewById(R.id.textview);
        button=(Button)findViewById(R.id.button);

        db=new Databasehelper(this);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                    String email = edittext.getText().toString().trim();
                    String password = edittext2.getText().toString().trim();

                    if (!email.isEmpty() && !password.isEmpty()){
                        edittext.setError(null);
                        edittext2.setError(null);

                        if (db.checkuser(email,password)) {
                            //intent
                            Intent intent = new Intent(getApplicationContext(), UsersListActivity.class);
                            intent.putExtra("EMAIL", edittext.getText().toString().trim());
                            startActivity(intent);
                        }else {
                            Toast.makeText(login.this,"Login Error",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                        {

                        if (email.isEmpty()){
                            edittext.setError("Enter your Email");
                        }
                        else if (password.isEmpty()){
                            edittext2.setError("Enter your password");
                        }
                    }
            }
        });


        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent

                Intent intent=new Intent(getApplicationContext(),register.class);
                startActivity(intent);
            }
        });
    }


}
