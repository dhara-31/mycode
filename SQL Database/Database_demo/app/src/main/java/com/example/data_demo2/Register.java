package com.example.data_demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    DatabaseHelper db;

    EditText edittext,edittext2,edittext3;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        db=new DatabaseHelper(this);

        edittext=(EditText)findViewById(R.id.edittext);
        edittext2=(EditText)findViewById(R.id.edittext2);
        edittext3=(EditText)findViewById(R.id.edittext3);
        button=(Button)findViewById(R.id.button);

        AddData();


    }




        public void AddData () {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = edittext.getText().toString().trim();
                String email = edittext2.getText().toString().trim();
                String password = edittext3.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

                    edittext.setError(null);
                    edittext2.setError(null);
                    edittext3.setError(null);

                    if (email.matches(emailPattern)) {
                        Toast.makeText(Register.this, "valid email", Toast.LENGTH_SHORT).show();

                        long val = db.addUser(name, email, password);
                        Log.e("TAG======", "onClick: "+val );
                        if (val > 0) {
                            Toast.makeText(Register.this, "You have registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);

                                     }
                            }
                    else {
                            Toast.makeText(Register.this, "Invalid email ", Toast.LENGTH_SHORT).show();


                        }
                    }

                else {
                        if (name.isEmpty()){
                            edittext.setError("Enter your name");
                            Toast.makeText(Register.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                        }
                        else if(email.isEmpty()){
                            edittext2.setError("Enter your Email");
                            Toast.makeText(Register.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                        }
                        else if (password.isEmpty())
                        {
                            edittext3.setError("Enter your Password");
                        }
                    }

            }
        });
    }

}