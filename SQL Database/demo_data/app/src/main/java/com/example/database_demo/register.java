package com.example.database_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {

    EditText edittext,edittext2,edittext3,edittext4;
    Button button;
    Databasehelper db;
    user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db=new Databasehelper(this);

        edittext=(EditText)findViewById(R.id.edittext);
        edittext2=(EditText)findViewById(R.id.edittext2);
        edittext3=(EditText)findViewById(R.id.edittext3);
        edittext4=(EditText)findViewById(R.id.edittext4);
        button=(Button)findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name=edittext.getText().toString().trim();
                String email=edittext2.getText().toString().trim();
                String password=edittext3.getText().toString().trim();
                String confirm_pass=edittext4.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirm_pass.isEmpty()) {
                    edittext.setError(null);
                    edittext2.setError(null);
                    edittext3.setError(null);
                    edittext4.setError(null);


                    if (email.matches(emailPattern)) {
                        Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();


                        if (password.equals(confirm_pass)) {
                            long val = db.addUser(name, email, password);
                            if (val > 0) {
                                Toast.makeText(register.this, "You have registered", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(register.this, login.class);
                                startActivity(intent);
                            }

                        } else {
                            Toast.makeText(register.this, "Password is not matching", Toast.LENGTH_LONG).show();

                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    if (name.isEmpty()){
                        edittext.setError("Enter your name");
                    }
                    else if(email.isEmpty()){
                        edittext2.setError("Enter your Email");
                    }
                    else if (password.isEmpty())
                    {
                        edittext3.setError("Enter your Password");
                    }
                    else if(confirm_pass.isEmpty()){
                        edittext4.setError("Enter your Confirm Password");
                    }
                }

            }
        });

    }

}